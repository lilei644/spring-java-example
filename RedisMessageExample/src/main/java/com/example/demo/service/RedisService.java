package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * redis 操作类
 */
@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 保存元素对象进入缓存
     *
     * @param key   存储的Key键
     * @param value 存储的Value值
     */
//    public void save(String key, String value) {
//        stringRedisTemplate.opsForValue().set(key, value);
//    }
    /**
     * 从缓存中删除元素对象
     *
     * @param key 删除的Key键
     * @return
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }
    /**
     * 获得Key对应的值
     *
     * @param key
     */
    public String query(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    public void save(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public boolean save(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        String newValue = query(key);
        return value.equals(newValue);
    }


    public Set<String> keys(String key) {
        return stringRedisTemplate.keys(key);
    }


    /**
     * redis setnx 操作
     * @param key key
     * @param value value
     * @return 之前不存在key的话则为true
     */
    public boolean setNx(String key, String value) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value);
    }


    /**
     * 实现分布式锁时为保证原子性，直接使用命令赋值和设置超时时间
     * @param key key
     * @param value value
     * @param timeout 超时时间，毫秒
     * @return OK为成功，null表示已经存在
     */
    public boolean setNxEx(String key, String value, long timeout) {
        String result = (String) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.set(key, value, "NX", "PX", timeout);
            }
        });
        return result != null && result.equals("OK");
    }


    /**
     * 实现分布式锁解锁时利用lua脚本保证删除时的原子性
     * @param key key
     * @param value value
     * @return 1成功，0 失败
     */
    public boolean unLock(String key, String value) {
        // 使用Lua脚本删除Redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
        // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
        String UNLOCK_LUA = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then " +
                "    return redis.call(\"del\",KEYS[1]) else " +
                "    return 0 end";
        Long result = (Long) redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, 1, key, value);
                }

                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, 1,  key, value);
                }
                return 0L;
            }
        });
        return result > 0;
    }


}

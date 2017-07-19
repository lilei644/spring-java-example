package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


/**
 * redis 操作类
 */
@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 保存元素对象进入缓存
     *
     * @param key   存储的Key键
     * @param value 存储的Value值
     */
    public void save(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }
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


}

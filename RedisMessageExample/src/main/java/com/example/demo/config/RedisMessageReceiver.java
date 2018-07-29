package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

/**
 * Created by LiLei on 2017/7/18.
 * redis 接受消息处理的类
 */
public class RedisMessageReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisMessageReceiver.class);

    //消息消费方法名称
    public static final String DEFAULT_LISTENEER_METHOD = "receiveMessage";

    //同步计数器,控制消息监听器进程退出时机
    private CountDownLatch latch;

    @Autowired
    public RedisMessageReceiver(CountDownLatch latch) {
        this.latch = latch;
    }

    /**
     * 消息消费方法
     */
    public void receiveMessage(String message) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }
        LOGGER.info("接收到消息：" + Thread.currentThread().getName() + ">>." + message);

        latch.countDown();
    }
}

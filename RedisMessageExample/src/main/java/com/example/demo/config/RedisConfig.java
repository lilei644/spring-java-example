package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;

/**
 * Created by LiLei on 2017/7/18.
 */
@Configuration
public class RedisConfig {

    /**
     * 订阅的频道
     */
    @Value("${redis.channel.message}")
    private String channel;

    /**
     * 同步计数器
     * 用于保持redis消息监听器状态，主线程等待子线程退出
     * 当count值为0时，子线程退出
     */
    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(1);
    }

    /**
     * 操作模板
     * connectionFactory 为spring-data-redis 自动注入
     */
    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    /**
     * redis消息接收/消费类
     */
    @Bean
    RedisMessageReceiver receiver(CountDownLatch latch) {
        return new RedisMessageReceiver(latch);
    }

    /**
     * 消息消费适配器
     */
    @Bean
    MessageListenerAdapter listenerAdapter(RedisMessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, RedisMessageReceiver.DEFAULT_LISTENEER_METHOD);
    }

    /**
     * 消息消费主体
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(channel));
        return container;
    }


}
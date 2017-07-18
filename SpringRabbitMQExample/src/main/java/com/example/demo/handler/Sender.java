package com.example.demo.handler;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by LiLei on 2017/7/18.
 * 消息队列的发送者
 */
@Component
public class Sender {


    @Autowired
    private AmqpTemplate rabbitTemplate;


    public void send() {
        String context = "hello " + new Date();
        rabbitTemplate.convertAndSend("hello", context);
    }
}

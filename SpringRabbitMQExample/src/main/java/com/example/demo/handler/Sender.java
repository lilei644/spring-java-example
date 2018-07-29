package com.example.demo.handler;

import com.example.demo.RabbitmqApplication;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * Created by LiLei on 2017/7/18.
 * 消息队列的发送者
 */
@Component
public class Sender {


//    @Autowired
//    private AmqpTemplate rabbitTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send() throws InterruptedException {
        String context = "hello " + new Date();

        String uuid = UUID.randomUUID().toString();
        System.out.println("send message id : " + uuid);
        rabbitTemplate.convertAndSend(RabbitmqApplication.EXCHANGE_NAME, "blacks", context + "  black", new CorrelationData(uuid));


        //    Thread.sleep(2000);
        uuid = UUID.randomUUID().toString();
        System.out.println("send message id : " + uuid);
        rabbitTemplate.convertAndSend(RabbitmqApplication.EXCHANGE_NAME, "orange", context + "  orange", new CorrelationData(uuid));

        //    Thread.sleep(2000);
        uuid = UUID.randomUUID().toString();
        System.out.println("send message id : " + uuid);
        rabbitTemplate.convertAndSend(RabbitmqApplication.EXCHANGE_NAME, "black", context + "  black", new CorrelationData(uuid));
    }
}

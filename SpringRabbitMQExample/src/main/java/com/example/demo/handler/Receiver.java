package com.example.demo.handler;

import com.excthink.middleware.bean.User;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by LiLei on 2017/7/18.
 * 消息接受的类
 */
@Component
@RabbitListener(queues = "MiddleWareLog")
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    @Bean
    Queue queue() {
        return new Queue("MiddleWareLog");
    }

//    @RabbitHandler
//    public void receiveMessage(String message) {
//        System.out.println("Received <" + message + ">" + "time：" + new Date());
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.printf("end>>>>>>>>>>>>>>>>>>\n");
//        latch.countDown();
//    }

    @RabbitHandler
    public void receiverMessage(User user) {
        System.out.println(user.toStrings());
    }


    public CountDownLatch getLatch() {
        return latch;
    }

}

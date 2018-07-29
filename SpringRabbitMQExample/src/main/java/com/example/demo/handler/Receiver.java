package com.example.demo.handler;

import com.example.demo.RabbitmqApplication;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by LiLei on 2017/7/18.
 * 消息接受的类
 */
@Component
@RabbitListener(queues = RabbitmqApplication.MESSAGE_QUENE)
public class Receiver {


    @Bean
    Queue queue() {
        return new Queue(RabbitmqApplication.MESSAGE_QUENE);
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

    /**
     * 创建路由器
     */
    @Bean
    Exchange exchange() {
        return new DirectExchange(RabbitmqApplication.EXCHANGE_NAME);
    }

    /**
     * 绑定路由器和队列
     */
    @Bean
    Binding bindExchangeBlack(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("black").noargs();
    }

    @Bean
    Binding bindExchangeOrange(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("orange").noargs();
    }


    /**
     * 接收消息的监听
     *
     * @param message 收到的消息
     */
    @RabbitHandler
    public void receiverMessage(String message) {
        System.out.println("receive message：" + message);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        System.out.println(1/0);
    }

}

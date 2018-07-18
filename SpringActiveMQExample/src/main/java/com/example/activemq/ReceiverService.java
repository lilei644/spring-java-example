package com.example.activemq;

import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;

@Component
public class ReceiverService {

    // 接收queue点对点消息
    @JmsListener(destination = ActivemqApplication.MESSAGE_QUEUE)
    public void receiveMessage(String message) {
        System.out.println(">>>接收Queue1：" + message);
    }

    @JmsListener(destination = ActivemqApplication.MESSAGE_QUEUE)
    public void receiveMessage2(String message) {
        System.out.println(">>>接收Queue2：" + message);
    }


    //需要给topic定义独立的JmsListenerContainer
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(activeMQConnectionFactory);
        return bean;
    }

    // 接收topic发布订阅的消息
    @JmsListener(destination = ActivemqApplication.MESSAGE_TOPIC, containerFactory = "jmsListenerContainerTopic")
    public void receiveTopic(String message) {
        System.out.println(">>>接收Topic1：" + message);
    }

    @JmsListener(destination = ActivemqApplication.MESSAGE_TOPIC, containerFactory = "jmsListenerContainerTopic")
    public void receiveTopic2(String message) {
        System.out.println(">>>接收Topic2：" + message);
    }


}

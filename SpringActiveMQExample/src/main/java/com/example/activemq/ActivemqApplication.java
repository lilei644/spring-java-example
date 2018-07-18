package com.example.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import java.util.Date;

@SpringBootApplication
@RestController
public class ActivemqApplication {

    @Autowired
    private JmsMessagingTemplate template;

    public final static String MESSAGE_QUEUE = "test.queue";
    public final static String MESSAGE_TOPIC = "test.topic";

    public static void main(String[] args) {
        SpringApplication.run(ActivemqApplication.class, args);
    }

    @GetMapping("/test")
    public String sendMessage() {
        Destination destination = new ActiveMQQueue(MESSAGE_QUEUE);

        for (int i = 0; i < 10; i++) {
            template.convertAndSend(destination, "hello " + new Date() + " -- " + i);
        }

        return "hello world";
    }


    @GetMapping("/test2")
    public String sendMessage2() {
        Destination destination = new ActiveMQTopic(MESSAGE_TOPIC);

        for (int i = 0; i < 10; i++) {
            template.convertAndSend(destination, "hello " + new Date() + " -- " + i);
        }

        return "hello world";
    }


}

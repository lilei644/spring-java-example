package com.example.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@SpringBootApplication
@RestController
public class KafkaApplication {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}

	@GetMapping("/test")
	public String testSendMessage() {
		ListenableFuture future = kafkaTemplate.send("TestTopic", "Hello world : " + new Date());
		future.addCallback(new ListenableFutureCallback<String>() {
			@Override
			public void onFailure(Throwable throwable) {
				System.out.println("send message fail");
			}

			@Override
			public void onSuccess(String message) {
				System.out.println("send message success");
			}
		});
		return "hello world";
	}


	@KafkaListener(topics = {"TestTopic"})
	public void receive(String content){
		System.err.println(">>>>>>Receive : " + content);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

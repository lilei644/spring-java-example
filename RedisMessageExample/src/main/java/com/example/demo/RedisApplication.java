package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RedisApplication {

	@Autowired
	private StringRedisTemplate redisTemplate;

	public static void main(String[] args) throws InterruptedException {

		ApplicationContext ctx = SpringApplication.run(RedisApplication.class, args);


//		CountDownLatch latch = ctx.getBean(CountDownLatch.class);
//		//让主进程Application 等待子进程RedisMessageListener 退出
//		latch.await();
//		System.exit(0);
	}


	@GetMapping("/main")
	public String testRedis() throws InterruptedException {
		redisTemplate.convertAndSend("task_message", "Hello from Redis!");
		return "Hello World";
	}
}

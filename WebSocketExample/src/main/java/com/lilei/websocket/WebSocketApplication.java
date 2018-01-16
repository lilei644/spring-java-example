package com.lilei.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class WebSocketApplication {

	@Autowired
	private MyWebSocket myWebSocket;

	public static void main(String[] args) {
		SpringApplication.run(WebSocketApplication.class, args);
	}


	/**
	 * 测试发送
	 * @param id 发送到哪个用户
	 * @param message 发送的数据
	 * @return yes
	 */
	@GetMapping("{id}/{message}")
	public String test(@PathVariable String id, @PathVariable String message) {
		myWebSocket.sendMessage(message, id);
		return "yes";
	}

}

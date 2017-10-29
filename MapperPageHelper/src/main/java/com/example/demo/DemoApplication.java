package com.example.demo;

import com.example.demo.bean.User;
import com.example.demo.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	@Autowired
	private UserMapper userMapper;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@GetMapping("test")
	public Object getUser() {
		PageHelper.startPage(2,2);
		return new PageInfo<User>(userMapper.selectAll());
	}


}

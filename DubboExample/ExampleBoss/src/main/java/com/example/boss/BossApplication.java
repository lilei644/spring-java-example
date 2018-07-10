package com.example.boss;

import com.example.boss.service.DubboServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAutoConfiguration
@ServletComponentScan
@RestController
public class BossApplication {

    @Autowired
    private DubboServiceImpl dubboService;

    public static void main(String[] args) {
        SpringApplication.run(BossApplication.class, args);
    }

    @GetMapping("/test")
    public String httpRequest() {
        return dubboService.sayHello();
    }

}

package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ServletComponentScan
public class DemoApplication {

    @Autowired
    private MainService mainService;


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    // 测试读
    @GetMapping("/Main")
    public String TestRead() {
        return mainService.testRead();
    }


    // 测试写
    @GetMapping("/Main2")
    public String TestWrite() {
        return mainService.testWrite();
    }


    // 测试事务
    @GetMapping("/Main3")
    public Object TestTransactional() {
        return mainService.testTransactional();
    }

}

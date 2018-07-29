package com.example.springdockerexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringDockerExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDockerExampleApplication.class, args);
    }


    @GetMapping("/test")
    public String testDocker() {
        return "Hello World";
    }
}

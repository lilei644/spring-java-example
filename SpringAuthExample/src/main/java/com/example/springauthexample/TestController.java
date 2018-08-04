package com.example.springauthexample;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/test")
    public String testRequest(@RequestParam("name") String name) {
        System.out.println(">>>>> request:" + name);
        return "Hello World";
    }


}

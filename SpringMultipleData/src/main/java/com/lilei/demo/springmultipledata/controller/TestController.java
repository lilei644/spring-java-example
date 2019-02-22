package com.lilei.demo.springmultipledata.controller;

import com.lilei.demo.springmultipledata.service.TestService01;
import com.lilei.demo.springmultipledata.service.TestService02;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    TestService01 testService01;

    @Autowired
    TestService02 testService02;


    @GetMapping("/test/{index}")
    @Transactional
    public String test(@PathVariable int index) {

        testService01.saveInfo("aaaa", 20);

        testService02.saveInfo("bbbb", 18);

        int i = 1 / index;
        return "success";
    }

}

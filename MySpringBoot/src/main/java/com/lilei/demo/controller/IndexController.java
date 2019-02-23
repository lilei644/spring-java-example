package com.lilei.demo.controller;


import com.lilei.demo.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    IndexService indexService;


    @GetMapping("/index")
    public String testIndex() {
        return indexService.test();
    }


}

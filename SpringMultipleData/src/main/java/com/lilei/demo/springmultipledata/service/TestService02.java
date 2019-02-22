package com.lilei.demo.springmultipledata.service;

import com.lilei.demo.springmultipledata.mapper.test02.TestMapper02;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService02 {

    @Autowired
    TestMapper02 testMapper02;

    public void saveInfo(String name, int age) {
        testMapper02.saveUser(name, age);
    }


}

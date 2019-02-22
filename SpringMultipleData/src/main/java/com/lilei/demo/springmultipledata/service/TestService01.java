package com.lilei.demo.springmultipledata.service;

import com.lilei.demo.springmultipledata.mapper.test01.TestMapper01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService01 {

    @Autowired
    TestMapper01 testMapper01;

    public void saveInfo(String name, int age) {
        testMapper01.saveUser(name, age);
    }


}

package com.lilei.demo.service.impl;

import com.lilei.demo.annotation.MyResource;
import com.lilei.demo.annotation.MyService;
import com.lilei.demo.service.TestService;
import com.lilei.demo.service.UserService;


@MyService
public class TestServiceImpl implements TestService {

    @MyResource
    UserService userServiceImpl;

    @Override
    public void test() {
        System.out.println(">>>>>>>>TestService");
        userServiceImpl.add();
    }

}

package com.lilei.demo.service.impl;

import com.lilei.demo.annotation.MyService;
import com.lilei.demo.service.UserService;


@MyService
public class UserServiceImpl implements UserService {


    @Override
    public void add() {
        System.out.println("......UserService");
    }
}

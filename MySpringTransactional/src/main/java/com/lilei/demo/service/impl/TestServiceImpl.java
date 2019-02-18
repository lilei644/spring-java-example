package com.lilei.demo.service.impl;

import com.lilei.demo.annotation.MyTransactional;
import com.lilei.demo.dao.TestDao;
import com.lilei.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestDao testDao;


    @Override
    @MyTransactional
    public void addUser() {

        // 先执行第一条语句
        testDao.add("aaaaa", 10);

        // 添加一条错误语句
//        int index = 1 / 0;

        // 在执行第二条语句
        testDao.add("bbbbb", 20);

    }

}

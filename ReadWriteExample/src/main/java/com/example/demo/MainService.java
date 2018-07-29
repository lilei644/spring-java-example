package com.example.demo;

import com.example.demo.config.ReadDataSource;
import com.example.demo.config.WriteDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LiLei on 2017/6/19.
 */
@Service
public class MainService {

    @Autowired
    private MainMapper mainMapper;


    // 测试读
    @ReadDataSource
    public String testRead() {
        return mainMapper.getUser().toString();

    }


    // 测试写
    @WriteDataSource
    public String testWrite() {
        return String.valueOf(mainMapper.updateUser("sss"));
    }


    // 测试事务
    @WriteDataSource
    @Transactional
    public Object testTransactional() {

        mainMapper.updateUser("lss");
        System.out.print(mainMapper.getUser() + "\n");
        System.out.print(1 / 0);
        return "hello Transactional";
    }


}

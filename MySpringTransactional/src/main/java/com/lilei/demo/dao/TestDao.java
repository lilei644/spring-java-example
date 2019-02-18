package com.lilei.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void add(String name, int age) {
        String sql = "insert into user(name, age) values(?, ?)";
        int result = jdbcTemplate.update(sql, name, age);
        System.out.println("执行结果：" + result);
    }

}

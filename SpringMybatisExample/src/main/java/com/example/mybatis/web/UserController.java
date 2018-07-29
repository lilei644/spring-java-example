package com.example.mybatis.web;


import com.example.mybatis.entity.User;
import com.example.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lei
 * @since 2018-07-18
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public List<User> queryAllUser() {
        return userService.queryAllUser();
    }

    @GetMapping("/count")
    public int queryAllUserCount() {
        return userService.queryAllUserCount();
    }

    @GetMapping("/id/{id}")
    public User queryAllUserCount(@PathVariable int id) {
        return userService.queryUserById(id);
    }

}


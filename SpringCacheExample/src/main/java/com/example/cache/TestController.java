package com.example.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    TestService testService;


    @GetMapping("/test")
    public UserInfo getUserInfo() {
        return testService.getUserInfo(1);
    }

    @PutMapping("/test")
    public UserInfo putUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setName("张三");
        userInfo.setAge(30);
        return testService.updateUserInfo(userInfo);
    }

    @DeleteMapping("/test")
    public int deleteUserInfo() {
        return testService.deleteUserInfo(1);
    }


}

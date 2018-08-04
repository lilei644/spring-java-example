package com.example.cache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "user-info")
public class TestService {



    @Cacheable(key = "#p0")
    public UserInfo getUserInfo(int id) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setName("sss");
        userInfo.setAge(20);
        System.out.println(">>>>> 查询的数据库返回的数据");
        return userInfo;
    }


    @CachePut(key = "#p0.id")
    public UserInfo updateUserInfo(UserInfo userInfo) {
        System.out.println(">>>>> 更新数据和缓存");
        return userInfo;
    }


    @CacheEvict(key = "#p0")
    public int deleteUserInfo(int id) {
        System.out.println(">>>>> 删除数据和缓存");
        return 1;
    }


}

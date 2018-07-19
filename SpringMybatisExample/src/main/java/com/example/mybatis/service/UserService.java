package com.example.mybatis.service;

import com.example.mybatis.entity.User;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lei
 * @since 2018-07-18
 */
public interface UserService extends IService<User> {

    List<User> queryAllUser();

    int queryAllUserCount();

    User queryUserById(int id);

}

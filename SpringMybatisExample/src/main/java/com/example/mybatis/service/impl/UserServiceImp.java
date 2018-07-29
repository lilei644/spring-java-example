package com.example.mybatis.service.impl;

import com.example.mybatis.entity.User;
import com.example.mybatis.mapper.UserDao;
import com.example.mybatis.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lei
 * @since 2018-07-18
 */
@Service
public class UserServiceImp extends ServiceImpl<UserDao, User> implements UserService {

    public List<User> queryAllUser() {
        return baseMapper.queryAllUser();
    }


    @Override
    public int queryAllUserCount() {
        return baseMapper.queryAllUserCount();
    }

    @Override
    public User queryUserById(int id) {
        return baseMapper.queryUserById(id);
    }
}

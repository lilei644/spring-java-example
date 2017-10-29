package com.example.demo.mapper;

import com.example.demo.bean.User;
import com.example.demo.config.MyMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends MyMapper<User> {
}

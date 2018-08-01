package com.example.eurekaconsumer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ConsumerMapper {

    @Update("update user set age = age + 1 where id = 19")
    int updateUserInfo();

}
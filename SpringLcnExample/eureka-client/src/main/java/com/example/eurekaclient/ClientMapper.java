package com.example.eurekaclient;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ClientMapper {

    @Update("update user set age = age + 1 where id = 18")
    int updateUserInfo();

}

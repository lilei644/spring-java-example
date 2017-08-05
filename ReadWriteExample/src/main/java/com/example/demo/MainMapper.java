package com.example.demo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * Created by LiLei on 2017/6/19.
 */
@Mapper
public interface MainMapper {

    @Select("select * from user")
    Map<String, String> getUser();


    @Update("update user set name = #{name}")
    int updateUser(String name);

}

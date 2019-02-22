package com.lilei.demo.springmultipledata.mapper.test01;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface TestMapper01 {

    @Insert("insert into user(name, age) values(#{name}, #{age})")
    void saveUser(@Param("name") String name,
                  @Param("age") int age);


}

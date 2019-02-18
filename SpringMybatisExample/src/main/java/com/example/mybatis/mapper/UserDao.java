package com.example.mybatis.mapper;

import com.example.mybatis.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lei
 * @since 2018-07-18
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    List<User> queryAllUser();

    int queryAllUserCount();
    
    User queryUserById(int id);

}

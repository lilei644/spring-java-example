package com.example.demo.config;

import java.lang.annotation.*;

/**
 * Created by LiLei on 2017/6/19.
 * 定义写的库的标签
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface WriteDataSource {

}
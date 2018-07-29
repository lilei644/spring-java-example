package com.example.demo.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;


/**
 * Created by LiLei on 2017/6/19.
 * service的切面
 */
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Component
public class DataSourceAopInService implements PriorityOrdered {


    @Before(" execution(* com.example.demo..*.*(..)) && @annotation(com.example.demo.config.ReadDataSource) ")
    public void setReadDataSourceType() {
        //如果已经开启写事务了，那之后的所有读都从写库读
//        if(!DataSourceType.write.getType().equals(DataSourceContextHolder.getReadOrWrite())){
//            DataSourceContextHolder.setRead();
//        }

        DataSourceContextHolder.setRead();
    }

    @Before("execution(* com.example.demo..*.*(..)) && @annotation(com.example.demo.config.WriteDataSource) ")
    public void setWriteDataSourceType() {
        DataSourceContextHolder.setWrite();
    }

    @Override
    public int getOrder() {

        return 0;
    }

}
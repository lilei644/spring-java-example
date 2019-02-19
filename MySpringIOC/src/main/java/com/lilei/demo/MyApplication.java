package com.lilei.demo;

import com.lilei.demo.service.TestService;
import com.lilei.demo.spring.ClassPathXmlApplicationContext;

/**
 * 手动实现SpringIOC 控制反转功能
 * 实现思路：
 * 1、创建类加载器和自定义service、resource注解
 * 2、加载器中启动时通过扫包获取到所有标注了注解的类对象并将对象添加到容器中
 * 3、定义getBean方法，获取对象时从容器中提取并给依赖对象完成注入
 */
public class MyApplication {

    public static void main(String[] args) {

        // Spring采用xml配置扫包路径，此处简化直接传入包名
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com.lilei.demo");
        TestService testService = (TestService) context.getBean("testServiceImpl");
        testService.test();

    }

}

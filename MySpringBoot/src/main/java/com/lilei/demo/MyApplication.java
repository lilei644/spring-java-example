package com.lilei.demo;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

/**
 * 根据Spring和SpringMVC手写实现SpringBoot的基础功能
 * SpringBoot的特点：
 * 1、无xml配置，全部通过注解实现
 * 2、提供了对其它第三方的工具的便捷整合
 * 3、内嵌tomcat方便部署
 *
 * 手写解决思路：
 * 根据SpringBoot的特点，对其它第三方工具的整合主要采用maven依赖以及注解化配置，暂时不做说明
 * 所以此处主要解决两个问题：
 * 1、无配置文件启动servlet
 * 2、内嵌tomcat已Java程序的方式运行
 */
public class MyApplication {


    public static void main(String[] args) throws LifecycleException, ServletException {
        // 创建Tomcat容器
        Tomcat tomcatServer = new Tomcat();
        // 端口号设置
        tomcatServer.setPort(9090);
        // 读取项目路径 加载静态资源
        StandardContext ctx = (StandardContext) tomcatServer.addWebapp("/", new File("src/main").getAbsolutePath());
        // 禁止重新载入
        ctx.setReloadable(false);
        // class文件读取地址
        File additionWebInfClasses = new File("target/classes");
        // 创建WebRoot
        WebResourceRoot resources = new StandardRoot(ctx);
        // tomcat内部读取Class执行
        resources.addPreResources(
                new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        tomcatServer.start();
        // 异步等待请求执行
        tomcatServer.getServer().await();
    }

}

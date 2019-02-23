package com.lilei.demo.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


/**
 * 加载springmvc--dispatcherServlet
 */
public class SpringWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    // 加载根配置信息 spring核心
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ RootConfig.class };
    }

    // springmvc 加载 配置信息
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }

    // springmvc 拦截url映射 拦截所有请求
    protected String[] getServletMappings() {
        return new String[]{ "/" };
    }
}

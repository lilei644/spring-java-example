package com.lilei.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * web 配置中心
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.lilei.demo.controller" })
public class WebConfig {
}

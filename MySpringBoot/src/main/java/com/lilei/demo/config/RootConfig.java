package com.lilei.demo.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 根配置中心，配置扫包根目录
 */
@Configuration
@ComponentScan("com.lilei.demo")
public class RootConfig {
}

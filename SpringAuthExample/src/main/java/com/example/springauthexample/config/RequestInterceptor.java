package com.example.springauthexample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println(">>>>> preHandle : " + httpServletRequest.getRequestURL() + "--" + httpServletRequest.getMethod());
        String name = httpServletRequest.getParameter("name");
        if (name == null || name.isEmpty()) {
            return false;
        }

        // 从redis中查询出name比对
        if (redisTemplate.opsForValue().get("name").equals(name)) {
            return true;
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

        System.out.println(">>>>> postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println(">>>>> afterCompletion");
    }
}

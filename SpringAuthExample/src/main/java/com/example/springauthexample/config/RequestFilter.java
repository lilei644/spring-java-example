package com.example.springauthexample.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 过滤器，过滤器可以对接收和返回的参数进行修改
 */
@Component
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println(">>>> init filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(">>>> doFilter");

        // 对req和resp进行修改
        MyRequestWrapper requestWrapper = new MyRequestWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println(">>>> filter destory");
    }


    /**
     * 对返回的数据进行修改
     * @param response response
     * @param responseString 返回的数据
     * @throws IOException
     */
    private void writeResponse(ServletResponse response, String responseString)
            throws IOException {
        PrintWriter out = response.getWriter();
        out.print(responseString);
        out.flush();
        out.close();
    }

}

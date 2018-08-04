package com.example.springauthexample.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 对request进行重新包装
 */
public class MyRequestWrapper extends HttpServletRequestWrapper {

    private HttpServletRequest request;

    public MyRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
    }


    /**
     * 重写获取参数方法对提交的参数进行修改
     * @param name 参数的key
     * @return
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);

        // 过去请求的参数
        return filter(value);
    }

    private String filter(String message) {

        if (message == null)
            return (null);

        char content[] = new char[message.length()];
        message.getChars(0, message.length(), content, 0);
        StringBuilder result = new StringBuilder(content.length + 50);
        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
                case '<':
                    result.append("<<");
                    break;
                case '>':
                    result.append(">>");
                    break;
                case '&':
                    result.append("&");
                    break;
                case '"':
                    result.append("\"");
                    break;
                default:
                    result.append(content[i]);
            }
        }
        System.out.println(">>>> 过滤参数完成");
        return (result.toString());
    }
}

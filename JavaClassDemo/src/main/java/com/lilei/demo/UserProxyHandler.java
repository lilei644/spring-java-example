package com.lilei.demo;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 利用cglib实现动态代理
 */
public class UserProxyHandler implements MethodInterceptor {

    public  <T> T getInstance(Class<?> cls) {
        Enhancer enhancer = new Enhancer(); //创建加强器，用来创建动态代理类
        enhancer.setSuperclass(cls);  //为加强器指定要代理的业务类（即：为下面生成的代理类指定父类）
        //设置回调：对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实现intercept()方法进行拦
        enhancer.setCallback(this);
        // 创建动态代理类对象并返回
        return (T) enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(">>>>>>>>>>before");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("<<<<<<<<<<after");
        return result;
    }


    public static void main(String[] args) {
        UserProxyHandler proxyHandler = new UserProxyHandler();
//        User user1 = proxyHandler.getInstance(User.class);
//        user1.show("ss", 20);
    }

}

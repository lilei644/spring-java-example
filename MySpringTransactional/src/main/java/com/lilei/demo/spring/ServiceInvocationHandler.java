package com.lilei.demo.spring;

import com.lilei.demo.util.TransactionUtil;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 使用动态代理重写初始化方法
 */
public class ServiceInvocationHandler implements InvocationHandler {

    private Object object;
    private String methodName;

    private TransactionUtil transactionUtil;

    public ServiceInvocationHandler(Object object, String methodName, TransactionUtil transactionUtil) {
        this.object = object;
        this.methodName = methodName;
        this.transactionUtil = transactionUtil;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getName().equals(methodName)) {

            // 开始事务
            TransactionStatus transactionStatus = transactionUtil.begin();
            Object returnObj = null;
            try {
                returnObj = method.invoke(object);
                transactionUtil.commit(transactionStatus);
            } catch (Exception e) {

                e.printStackTrace();

                // 抓到报错，进行事务回滚
                transactionUtil.rollBack(transactionStatus);
            }
            // 提交事务
            return returnObj;
        }
        return method.invoke(object);
    }
}

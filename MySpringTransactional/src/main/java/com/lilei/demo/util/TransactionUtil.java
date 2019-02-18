package com.lilei.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 简单封装的事务处理的方法
 */
@Component
//@Scope("prototype") // 将当前注入对象设置成非单例
public class TransactionUtil {


    @Autowired
    DataSourceTransactionManager transactionManager;

    // 开启事务
    public TransactionStatus begin() {
        System.out.println(">>>>>事务开始");
        return transactionManager.getTransaction(new DefaultTransactionDefinition());
    }


    // 提交事务
    public void commit(TransactionStatus transactionStatus) {
        if (transactionStatus == null) {
            return;
        }
        System.out.println(">>>>>事务提交");
        transactionManager.commit(transactionStatus);
    }

    // 事务回滚
    public void rollBack(TransactionStatus transactionStatus) {
        if (transactionStatus == null) {
            return;
        }
        System.out.println(">>>>>事务回滚");
        transactionManager.rollback(transactionStatus);
    }


}

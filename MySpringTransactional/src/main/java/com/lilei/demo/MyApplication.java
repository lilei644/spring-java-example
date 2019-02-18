package com.lilei.demo;

import com.lilei.demo.annotation.MyTransactional;
import com.lilei.demo.service.TestService;
import com.lilei.demo.spring.ServiceInvocationHandler;
import com.lilei.demo.util.ClassUtil;
import com.lilei.demo.util.TransactionUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手写实现Spring transactional注解的事务功能
 * 思路说明：
 * 1、先自定义事务注解
 * 2、全局扫包获取所有的class，并解析出class里哪个方法有事务注解
 * 3、通过动态代理原理手动实现方法的切面功能
 * 4、在代理方法的前后加上事务处理的方法
 */
public class MyApplication {

    // 临时使用固定的扫包名称，spring采用xml配置，原理类似
    private static final String PACKAGE_NAME = "com.lilei.demo.service";

    // 暂时使用静态的list模拟IOC注入容器
    private static List<Map<String, Object>> IOC_LIST = new ArrayList<>();

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        // 初始化依赖注入
        initIOC();

        // 调用示例
        TestService testService = (TestService)initObject(context, "testServiceImpl");
        testService.addUser();


    }


    /**
     * 初始化扫包注入
     * 由于此处的重点不是IOC，所以扫包注入的过程在外部直接调用初始化
     */
    private static void initIOC() {

        // 获取包下有多少类
        List<Class<?>> classList = ClassUtil.getClasses(PACKAGE_NAME);
        for (Class<?> cls : classList) {

            // 遍历每个类查询类的所有方法
            Method[] declaredMethods = cls.getDeclaredMethods();
            for (Method method : declaredMethods) {

                // 判断方法上是否有标注的事务注解，有则添加到容器内
                if (method.getAnnotation(MyTransactional.class) != null) {
                    // 此方法有事务注解，则利用动态代理原理添加事务机制
                    Map<String, Object> map = new HashMap<>();
                    map.put("class", cls);
                    map.put("method", method.getName());
                    IOC_LIST.add(map);
                }
            }
        }
    }


    /**
     * 初始化获取对象，并检测是否需要添加事务代理
     */
    private static Object initObject(ClassPathXmlApplicationContext context, String beanId) {
        Object object = context.getBean(beanId);

        // 判断是否在IOC容器内有事务标签
        for (Map<String, Object> map : IOC_LIST) {
            Class<?> cls = (Class<?>)map.get("class");
            if (cls.getName().equals(object.getClass().getName())) {
                for (Method method : cls.getDeclaredMethods()) {
                    if (method.getName().equals(map.get("method"))) {
                        // 存在保存的事务方法，则执行动态代理方法重新初始化对象
                        ServiceInvocationHandler invocationHandler = new ServiceInvocationHandler(object,
                                method.getName(), (TransactionUtil) context.getBean("transactionUtil"));

                        // 重新生成代理之后的对象
                        return Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), invocationHandler);
                    }
                }
            }
        }
        return object;
    }

}

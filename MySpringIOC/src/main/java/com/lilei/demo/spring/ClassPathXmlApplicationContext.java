package com.lilei.demo.spring;


import com.lilei.demo.annotation.MyResource;
import com.lilei.demo.annotation.MyService;
import com.lilei.demo.utils.ClassUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 初始化启动程序的加载器
 */
public class ClassPathXmlApplicationContext {

    // 包名
    private String packageName;

    // IOC容器集合
    private ConcurrentHashMap<String, Object> iocMap = new ConcurrentHashMap<>();

    public ClassPathXmlApplicationContext(String packageName) {
        this.packageName = packageName;
        initIOC();
    }


    /**
     * 初始化IOC容器
     */
    private void initIOC() {

        // 获取包下所有的类
        List<Class<?>> classList = ClassUtil.getClasses(packageName);

        for (Class<?> cls : classList) {
            // 判断哪些类有service注解
            if (cls.getDeclaredAnnotation(MyService.class) == null) {
                continue;
            }

            // 存在注解则保存类对象
            Object object = null;
            try {
                object = cls.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String beanId = toLowerCaseFirstOne(cls.getSimpleName());
            iocMap.put(beanId, object);
        }

        System.out.println("添加的所有注入对象：" + iocMap);
    }


    /**
     * 通过beanId获取对象
     * @param beanId 注入的id，默认为类名
     * @return 获取到注入的对象
     */
    public Object getBean(String beanId) {

        // 从容器中提取出对象，并解析依赖关系
        Object object = iocMap.get(beanId);
        if (object == null) {
            return null;
        }

        // 遍历该类的所有属性，判断是否有存在resource的对象
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 判断参数是否存在resource注解
            if (field.getDeclaredAnnotation(MyResource.class) == null) {
                continue;
            }
            // 存在注解，则获取对象类对象
            // 递归获取
            Object fieldObject = getBean(field.getName());

            try {
                // 设置私有变量允许访问
                field.setAccessible(true);

                // 给对象的属性赋值
                field.set(object, fieldObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return object;
    }



    // 首字母转小写
    private String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

}

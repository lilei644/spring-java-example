package com.lilei.demo;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import java.lang.reflect.Method;

/**
 * 利用javassit生成User class文件，并利用反射执行方法
 */
public class TestJavassit {


    public static void main(String[] args) throws Exception {
        ClassPool classPool = ClassPool.getDefault();

        // 创建类
        CtClass ctClass = classPool.makeClass("com.lilei.demo.User");

        // 创建参数
        ctClass.addField(CtField.make("private String name;", ctClass));
        ctClass.addField(CtField.make("private int age;", ctClass));

        // 创建方法
        ctClass.addMethod(CtMethod.make("public void show(String name, int age) " +
                "{System.out.println(\"我是动态生成的对象，name：\" + name + \" --- age：\" + age);}", ctClass));

        // 生成class文件
        ctClass.writeFile();


        // 利用反射执行方法
        Class<?> aClass = ctClass.toClass();
        Method method = aClass.getDeclaredMethod("show", String.class, int.class);
        method.invoke(aClass.newInstance(), "testName", 20);
    }
}

package com.lt.personal_stadiumbookingsystem.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @作者: LinTan
 * @日期: 2018/12/25 17:12
 * @版本: 1.0
 * @描述: //反射的工具类。
 * 源址: https://blog.csdn.net/ShiXueTanLang/article/details/79512356
 * 1.0: Initial Commit
 */

public class ReflectionUtil {

    private ReflectionUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取私有成员变量的值
     */
    public static Object getPrivateField(Object instance, String filedName) throws NoSuchFieldException, IllegalAccessException {
        Field field = instance.getClass()
                              .getDeclaredField(filedName);
        field.setAccessible(true);
        return field.get(instance);
    }

    /**
     * 设置私有成员变量的值
     */
    public static void setPrivateField(Object instance, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = instance.getClass()
                              .getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }

    /**
     * 访问私有方法
     */
    public static Object invokePrivateMethod(Object instance, String methodName, Class[] classes, String objects) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = instance.getClass()
                                .getDeclaredMethod(methodName, classes);
        method.setAccessible(true);
        return method.invoke(instance, objects);
    }
}

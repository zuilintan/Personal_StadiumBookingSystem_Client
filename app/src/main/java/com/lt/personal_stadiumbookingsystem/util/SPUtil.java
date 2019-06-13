package com.lt.personal_stadiumbookingsystem.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @作者: LinTan
 * @日期: 2019/5/6 22:40
 * @版本: 1.0
 * @描述: //SharedPreferences的工具类。
 * 源址: https://blog.csdn.net/lmj623565791/article/details/38965311
 * 1.0: Initial Commit
 * 1.1: 优化put与get的确定数据类型的流程
 */

public class SPUtil {
    private static final String FILE_NAME = "sp_data";

    private SPUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 存放数据，通过反射获取value的类型，然后调用对应的方法存值
     */
    public static void put(Context context, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String typeName = value.getClass().getSimpleName();
        switch (typeName) {
            case "String":
                editor.putString(key, (String) value);
                break;
            case "Integer":
                editor.putInt(key, (Integer) value);
                break;
            case "Boolean":
                editor.putBoolean(key, (Boolean) value);
                break;
            case "Float":
                editor.putFloat(key, (Float) value);
                break;
            case "Long":
                editor.putLong(key, (Long) value);
                break;
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 获取数据，通过反射获取value的类型，然后调用对应的方法取值
     */
    public static Object get(Context context, String key, Object defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String typeName = defaultValue.getClass().getSimpleName();
        Object result = null;
        switch (typeName) {
            case "String":
                result = sp.getString(key, (String) defaultValue);
                break;
            case "Integer":
                result = sp.getInt(key, (Integer) defaultValue);
                break;
            case "Boolean":
                result = sp.getBoolean(key, (Boolean) defaultValue);
                break;
            case "Float":
                result = sp.getFloat(key, (Float) defaultValue);
                break;
            case "Long":
                result = sp.getLong(key, (Long) defaultValue);
                break;
        }
        return result;
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clazz = SharedPreferences.Editor.class;
                return clazz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException | InvocationTargetException
                    | IllegalAccessException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }
}

package com.lt.personal_stadiumbookingsystem.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @作者: LinTan
 * @日期: 2018/12/25 17:16
 * @版本: 1.0
 * @描述: //Gson的工具类。注意引入依赖。
 * 源址: https://www.jianshu.com/p/5ad3d87a7e47
 * 1.0: Initial Commit
 * <p>
 * implementation 'com.google.code.gson:gson:2.8.5'
 */

public class GsonUtil {
    private static Gson sGson = new Gson();

    private GsonUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 将object对象转成json字符串
     */
    public static String objectToJson(Object object) {
        String json = null;
        if (sGson != null) {
            json = sGson.toJson(object);
        }
        return json;
    }

    /**
     * 将json字符串转成泛型Object(JavaBean)
     */
    public static <T> T jsonToObject(String json, Class<T> cls) {
        T object = null;
        if (sGson != null) {
            object = sGson.fromJson(json, cls);
        }
        return object;
    }

    /**
     * 转成list(弃用)
     * 存在泛型在编译期间类型被擦除导致报错问题
     */
    /*public static <T> List<T> jsonToList(String json) {
        List<T> list = null;
        if (sGson != null) {
            list = sGson.fromJson(json, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }*/

    /**
     * 转成list(推荐)
     * 解决泛型在编译期间类型被擦除导致报错问题
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json)
                                          .getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

    /**
     * 转成map
     */
    public static <T> Map<String, T> jsonToMap(String json) {
        Map<String, T> map = null;
        if (sGson != null) {
            map = sGson.fromJson(json, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    /**
     * 转成list，并且list中每个元素都是map
     */
    public static <T> List<Map<String, T>> jsonToMapList(String json) {
        List<Map<String, T>> mapList = null;
        if (sGson != null) {
            mapList = sGson.fromJson(json, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        }
        return mapList;
    }

    /**
     * 将Object强转为List，避免使用@SuppressWarnings("unchecked")
     * eg: Map<String, Object>强转为Map<String, List<T>>，可与jsonToMapList搭配使用
     */
    public static <T> List<T> objectCastList(Object obj, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                list.add(clazz.cast(o));
            }
            return list;
        }
        return null;
    }
}

package com.lt.personal_stadiumbookingsystem.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lt.personal_stadiumbookingsystem.base.GlideApp;

/**
 * @作者: LinTan
 * @日期: 2018/12/25 16:53
 * @版本: 1.2
 * @描述: //Glide的工具类。注意引入依赖。
 * 1.0: Initial Commit
 * <p>
 * implementation 'com.github.bumptech.glide:glide:4.8.0'
 * annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'
 */

public class GlideUtil {

    private GlideUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 加载图片
     */
    public static <T> void loadImage(Context context, T data, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        load(context, data, options, imageView);
    }

    /**
     * 加载图片(无缓存)
     */
    @SuppressLint("CheckResult")
    public static <T> void loadImageNoCache(Context context, T data, ImageView imageView) {
        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)
                                                     .skipMemoryCache(true);
        load(context, data, options, imageView);
    }

    /**
     * 加载圆形图片
     */
    public static <T> void loadCircleImage(Context context, T data, ImageView imageView) {
        RequestOptions options = new RequestOptions().circleCrop();
        load(context, data, options, imageView);
    }

    private static <T> void load(Context context, T t, RequestOptions options, ImageView imageView) {
        GlideApp.with(context)
                .load(t)
                .apply(options)
                .into(imageView);
    }
}

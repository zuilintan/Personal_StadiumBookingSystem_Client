package com.lt.personal_stadiumbookingsystem.base;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ayvytr.okhttploginterceptor.LoggingInterceptor;
import com.ayvytr.okhttploginterceptor.LoggingLevel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @作者: LinTan
 * @日期: 2018/12/26 9:47
 * @版本: 1.1
 * @描述: //Glide的配置类。替换其默认的网络模式HttpURLConnection为OkHttp，注意引入依赖。
 * 源址: https://blog.csdn.net/ysy950803/article/details/85083160
 * 1.0: Initial Commit
 * 1.1: 新增OkHttpLogInterceptor(OkHttp Logging拦截器)，输出OkHttp响应信息
 * <p>
 * implementation 'com.squareup.okhttp3:okhttp:3.12.1'
 * implementation 'com.ayvytr:okhttploginterceptor:2.1.0'
 * implementation 'com.github.bumptech.glide:glide:4.8.0'
 * implementation 'com.github.bumptech.glide:okhttp3-integration:4.8.0'
 * annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'
 */

@GlideModule
public class BaseAppGlideModule extends AppGlideModule {
    private LoggingInterceptor interceptor = new LoggingInterceptor(LoggingLevel.STATE);
    private OkHttpClient mClient = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build();

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(mClient));
    }
}

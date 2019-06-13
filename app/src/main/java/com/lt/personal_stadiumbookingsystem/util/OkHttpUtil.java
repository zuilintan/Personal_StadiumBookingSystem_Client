package com.lt.personal_stadiumbookingsystem.util;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @作者: LinTan
 * @日期: 2019/3/17 17:52
 * @版本: 1.0
 * @描述: //OkHttp的工具类。注意引入依赖。
 * 源址: https://blog.csdn.net/qq_16240393/article/details/54863646
 * 1.0: Initial Commit
 * <p>
 * implementation 'com.squareup.okhttp3:okhttp:3.12.1'
 */

public class OkHttpUtil {
    private static OkHttpUtil sOkHttpUtil;
    private static OkHttpClient sOkHttpClient;
    private Handler mHandler;

    private OkHttpUtil() {
        sOkHttpClient = new OkHttpClient();//创建OkHttpClient对象
        sOkHttpClient.newBuilder()
                     .connectTimeout(10, TimeUnit.SECONDS)
                     .readTimeout(10, TimeUnit.SECONDS)
                     .writeTimeout(10, TimeUnit.SECONDS);
        mHandler = new Handler(Looper.getMainLooper());//切换主线程执行handleMessage()
    }

    private static OkHttpUtil getInstance() {
        if (sOkHttpUtil == null) {
            sOkHttpUtil = new OkHttpUtil();
        }
        return sOkHttpUtil;
    }//单例模式，获取OkHttpUtil实例

    public static void getFormRequest(String url, Map<String, String> params,
                                      DataCallBack callBack) {
        getInstance().getFormAsync(url, params, callBack);
    }//get异步请求(Form)，对外提供

    public static void postFormRequest(String url, Map<String, String> params,
                                       DataCallBack callBack) {
        getInstance().postFormAsync(url, params, callBack);
    }//post异步请求(Form)，对外提供

    public static void postJsonRequest(String url, String params,
                                       DataCallBack callBack) {
        getInstance().postJsonAsync(url, params, callBack);
    }//post异步请求(Json)，对外提供

    private void getFormAsync(String url, Map<String, String> params, final DataCallBack callBack) {
        if (params == null) {
            params = new HashMap<>();
        }
        final String doUrl = jointUrl(url, params);//拼接URL(baseUrl+参数)
        final Request request = new Request.Builder().get()
                                                     .url(doUrl)
                                                     .build();//创建请求
        Call call = sOkHttpClient.newCall(request);//执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    deliverDataSuccess(result, callBack);
                } else {
                    throw new IOException(String.valueOf(response));
                }
            }
        });//获得响应结果
    }

    private void postFormAsync(String url, Map<String, String> params,
                               final DataCallBack callBack) {
        RequestBody requestBody;
        if (params == null) {
            params = new HashMap<>();
        }
        FormBody.Builder builder = new FormBody.Builder();//创建表单体
        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey();
            String value;
            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }
            builder.add(key, value);//添加Key与value到表单体
        }//遍历参数
        requestBody = builder.build();//创建请求体
        final Request request = new Request.Builder().post(requestBody)
                                                     .url(url)
                                                     .build();//创建请求
        Call call = sOkHttpClient.newCall(request);//执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body()
                                            .string();
                    deliverDataSuccess(result, callBack);
                } else {
                    throw new IOException(String.valueOf(response));
                }
            }
        });//获得响应结果
    }

    private void postJsonAsync(String url, String params,
                               final DataCallBack callBack) {
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), params);//创建请求体
        final Request request = new Request.Builder().url(url)
                                                     .post(requestBody)
                                                     .build();//创建请求
        Call call = sOkHttpClient.newCall(request);//执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body()
                                            .string();
                    deliverDataSuccess(result, callBack);
                } else {
                    throw new IOException(String.valueOf(response));
                }
            }
        });//获得响应结果
    }

    private void deliverDataFailure(final Request request, final IOException e,
                                    final DataCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.requestFailure(request, e);
                }
            }
        });//异步处理
    }

    private void deliverDataSuccess(final String result, final DataCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.requestSuccess(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });//异步处理
    }

    private static String jointUrl(String url, Map<String, String> params) {
        StringBuilder endUrl = new StringBuilder(url);
        boolean isFirst = true;
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            if (isFirst && !url.contains("?")) {
                isFirst = false;
                endUrl.append("?");
            } else {
                endUrl.append("&");
            }
            endUrl.append(entry.getKey());
            endUrl.append("=");
            endUrl.append(entry.getValue());
        }
        return endUrl.toString();
    }

    public interface DataCallBack {
        void requestSuccess(String result) throws Exception;

        void requestFailure(Request request, IOException e);
    }
}

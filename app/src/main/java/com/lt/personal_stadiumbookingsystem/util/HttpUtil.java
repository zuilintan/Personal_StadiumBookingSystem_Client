package com.lt.personal_stadiumbookingsystem.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @作者: LinTan
 * @日期: 2019/1/6 18:13
 * @版本: 1.0
 * @描述: //HttpURLConnection的工具类。
 * 源址: https://blog.csdn.net/lmj623565791/article/details/38965311
 * 1.0: Initial Commit
 */

public class HttpUtil {
    private static final int TIMEOUT_IN_MILLIONS = 5000;

    private HttpUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 异步的Get请求
     */
    public static void doGetAsyn(final String urlStr, final CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    String result = doGet(urlStr);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 异步的Post请求
     */
    public static void doPostAsyn(final String urlStr, final String params,
                                  final CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    String result = doPost(urlStr, params);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * Get请求，获得返回数据
     */
    private static String doGet(String stringURL) {
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            url = new URL(stringURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(TIMEOUT_IN_MILLIONS);
            httpURLConnection.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                byteArrayOutputStream = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];
                while ((len = inputStream.read(buf)) != -1) {
                    byteArrayOutputStream.write(buf, 0, len);
                }
                byteArrayOutputStream.flush();
                return byteArrayOutputStream.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (byteArrayOutputStream != null)
                    byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            httpURLConnection.disconnect();
        }
        return null;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param param 请求参数，格式: name1=value1&name2=value2
     * @return 远程资源的响应结果
     */
    private static String doPost(String stringURL, String param) {
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(stringURL);
            //打开和URL之间的连接
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置通用的请求属性
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type",
                                                 "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("charset", "utf-8");
            httpURLConnection.setUseCaches(false);
            //发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setReadTimeout(TIMEOUT_IN_MILLIONS);
            httpURLConnection.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            if (param != null && !param.trim()
                                       .equals("")) {
                //获取URLConnection对象对应的输出流
                printWriter = new PrintWriter(httpURLConnection.getOutputStream());
                //发送请求参数
                printWriter.print(param);
                //flush输出流的缓冲
                printWriter.flush();
            }
            //定义BufferedReader输入流来读取URL的响应
            bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }//使用finally块来关闭输出流、输入流
        return result.toString();
    }

    public interface CallBack {
        void onRequestComplete(String result);
    }
}
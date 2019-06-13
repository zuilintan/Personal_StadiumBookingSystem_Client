package com.lt.personal_stadiumbookingsystem.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @作者: LinTan
 * @日期: 2019/1/5 18:02
 * @版本: 1.1
 * @描述: //Toast的工具类。
 * 源址: https://www.jianshu.com/p/c226225db747
 * 1.0: Initial Commit
 * 1.1: 优化Toast多次触发效果
 */

public class ToastUtil {
    private static boolean sIsEnabled = true;//默认启用Toast
    private static Toast sToast = null;

    private ToastUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 全局控制是否启用Toast
     */
    public static void isEnabled(boolean isEnabled) {
        sIsEnabled = isEnabled;
    }

    /**
     * 取消显示
     */
    public static void cancelToast() {
        if (sIsEnabled && sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }

    /**
     * 居上显示 短时间
     */
    public static void showShortTop(Context context, Object message) {
        show(context, message, Gravity.TOP, Toast.LENGTH_SHORT);
    }

    /**
     * 居中显示 短时间
     */
    public static void showShortCenter(Context context, Object message) {
        show(context, message, Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    /**
     * 居下显示 短时间
     */
    public static void showShortBottom(Context context, Object message) {
        show(context, message, Gravity.BOTTOM, Toast.LENGTH_SHORT);
    }

    /**
     * 居上显示 长时间
     */
    public static void showLongTop(Context context, Object message) {
        show(context, message, Gravity.TOP, Toast.LENGTH_LONG);
    }

    /**
     * 居中显示 长时间
     */
    public static void showLongCenter(Context context, Object message) {
        show(context, message, Gravity.CENTER, Toast.LENGTH_LONG);
    }

    /**
     * 居下显示 长时间
     */
    public static void showLongBottom(Context context, Object message) {
        show(context, message, Gravity.BOTTOM, Toast.LENGTH_LONG);
    }

    /**
     * 显示 不对外开放
     */
    @SuppressLint("ShowToast")
    private static void show(Context context, Object message, int gravity, int duration) {
        String showMessage = "";
        if (message instanceof Integer) {
            showMessage = context.getString((Integer) message);
        } else if (message instanceof String) {
            showMessage = (String) message;
        }
        if (sToast == null) {
            sToast = Toast.makeText(context, showMessage, duration);
        }//如果Toast不存在则创建
        else {
            sToast.cancel();
            sToast = Toast.makeText(context, showMessage, duration);
        }//如果当前Toast没有消失，则取消该Toast，然后重新创建
        sToast.setGravity(gravity, 0, 0);//设置位置只有在初次创建的时候有效
        sToast.show();
    }
}
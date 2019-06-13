package com.lt.personal_stadiumbookingsystem.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * @作者: LinTan
 * @日期: 2018/12/25 17:11
 * @版本: 1.2
 * @描述: //屏幕的工具类。
 * 源址: https://blog.csdn.net/lmj623565791/article/details/38965311
 * 1.0: Initial Commit
 * 1.1: 替换setNoTitleBar()为setNoActionBar()
 * 1.2: 修改snapShotWithStatusBar()和snapShotWithoutStatusBar()的返回值类型为Drawable
 */

public class ScreenUtil {

    private ScreenUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 设置无ActionBar(extends AppCompatActivity)，setContentView()后调用
     */
    public static void setNoActionBar(AppCompatActivity appCompatActivity) {
        appCompatActivity.getSupportActionBar()
                         .hide();
    }

    /**
     * 设置无ActionBar(extends AppCompatActivity)，setContentView()后调用
     */
    public static void cancelNoActionBar(AppCompatActivity appCompatActivity) {
        appCompatActivity.getSupportActionBar()
                         .show();
    }

    /**
     * 设置无StatusBar
     */
    public static void setNoStatusBar(Activity activity) {
        activity.getWindow()
                .setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                );
    }

    /**
     * 取消无StatusBar
     */
    public static void cancelNoStatusBar(Activity activity) {
        activity.getWindow()
                .clearFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                );
    }

    /**
     * 获得屏幕高度
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay()
                     .getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 获得状态栏的高度
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay()
                     .getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 获得屏幕宽度
     */
    public static int getStatusBarHeight(Context context) {
        int statusHeight = -1;
        try {
            @SuppressLint("PrivateApi")
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                                               .get(object)
                                               .toString());
            statusHeight = context.getResources()
                                  .getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏(但状态栏无内容)
     */
    public static Drawable snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow()
                            .getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap viewDrawingCache = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bitmap = Bitmap.createBitmap(viewDrawingCache, 0, 0, width, height);
        view.destroyDrawingCache();
        return new BitmapDrawable(activity.getResources(), bitmap);
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    public static Drawable snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow()
                            .getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap viewDrawingCache = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow()
                .getDecorView()
                .getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bitmap = Bitmap.createBitmap(viewDrawingCache, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return new BitmapDrawable(activity.getResources(), bitmap);
    }
}

package com.lt.personal_stadiumbookingsystem.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * @作者: LinTan
 * @日期: 2019/1/6 11:12
 * @版本: 1.1
 * @描述: //密度的工具类。
 * 源址: https://blog.csdn.net/lmj623565791/article/details/38965311
 * 1.0: Initial Commit
 * 1.1: 加0.5F以四舍五入，eg: 1.4+0.5=1.9转为int是1，而1.5 + 0.5 = 2.0转换成int后就是2
 */

public class DensityUtil {

    private DensityUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpValue) {
        float density = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                                                  context.getResources()
                                                         .getDisplayMetrics());
        return (int) (density + 0.5F);
    }

    /**
     * px转dp
     */
    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources()
                             .getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5F);
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, float spValue) {
        float density = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
                                                  context.getResources()
                                                         .getDisplayMetrics());
        return (int) (density + 0.5F);
    }

    /**
     * px转sp
     */
    public static int px2sp(Context context, float pxValue) {
        float scale = context.getResources()
                             .getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5F);
    }

    //dp转sp略

    //sp转dp略
}
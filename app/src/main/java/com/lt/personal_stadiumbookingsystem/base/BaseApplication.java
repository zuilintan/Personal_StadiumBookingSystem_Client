package com.lt.personal_stadiumbookingsystem.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;

import com.lt.personal_stadiumbookingsystem.R;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

/**
 * @作者: LinTan
 * @日期: 2019/5/15 10:24
 * @版本: 1.0
 * @描述: //Application的封装类。
 * 1.0: Initial Commit
 */

public class BaseApplication extends Application {

    static {//static代码段，防止内存泄露
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setEnableRefresh(true);
                layout.setEnableLoadMore(false);
                layout.setDisableContentWhenRefresh(false);
                layout.setDisableContentWhenLoading(false);
                layout.setHeaderHeight(100);
                layout.setFooterHeight(100);
                layout.setDragRate(0.9F);
                layout.autoRefresh();
            }
        });//设置全局的基本参数(可以被下面的DefaultRefreshHeaderCreator覆盖)

        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                MaterialHeader header = new MaterialHeader(context);
                header.setColorSchemeResources(R.color.colorPinkA200);
                return header;
            }
        });//设置全局的Header构建器

        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                ClassicsFooter footer = new ClassicsFooter(context);
                footer.setSpinnerStyle(SpinnerStyle.Scale);
                return footer;
            }
        });//设置全局的Footer构建器
    }
}

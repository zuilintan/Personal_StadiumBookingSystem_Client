package com.lt.personal_stadiumbookingsystem.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.AsyncLayoutInflater;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.ui.activity.MainActivity;
import com.lt.personal_stadiumbookingsystem.util.ActivityUtil;
import com.lt.personal_stadiumbookingsystem.util.SPUtil;

/**
 * @作者: LinTan
 * @日期: 2018/12/29 19:13
 * @版本: 1.1
 * @描述: //Activity的封装类。配合ActivityUtil，管理所有Activity的出入栈，并输出当前Activity名称。
 * 源址: 《第一行代码》中 2.6 活动的最佳实践内的Example
 * 1.0: Initial Commit
 * 1.1: 重写onBackPressed()，双击Back键退出
 * 1.2: 优化onBackPressed()判断逻辑，非返回栈最后的Activity时正常单击Back键返回
 */

@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity {
    private long mPressBackTime = 0;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.add(this);
        bindData(savedInstanceState);//绑定数据，eg: 接收bundle数据
        int i = (int) SPUtil.get(this, "theme", 0);
        if (i != 0) {
            if (this instanceof MainActivity) {
                switch (i) {
                    case R.style.AppThemeTeal:
                        setTheme(R.style.AppThemeTeal_NoActionBar);
                        break;

                    case R.style.AppThemeIndigo:
                        setTheme(R.style.AppThemeIndigo_NoActionBar);
                        break;
                }
            } else {
                setTheme(i);
            }
        }//读取主题
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (ActivityUtil.getFirst() != this) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
        new AsyncLayoutInflater(this).inflate(setLayoutResId(), null, new AsyncLayoutInflater.OnInflateFinishedListener() {
            @Override
            public void onInflateFinished(@NonNull View view, int i, @Nullable ViewGroup viewGroup) {
                setContentView(view);
                bindView(view);//绑定视图，eg: findViewById()
                initData();//初始化数据，eg: 将bundle传过来的数据进行处理
                initView(view);//初始化视图，eg: textView.setText()
                initListener();//初始化监听器，eg: setOnclickListener()
            }
        });//异步加载View
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.wtf("BaseActivity", getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.remove(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityUtil.finish(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (ActivityUtil.getFirst() == this) {//判断当前Activity是否位于BackStack中栈底
            if ((System.currentTimeMillis() - mPressBackTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mPressBackTime = System.currentTimeMillis();
            } else {
                ActivityUtil.finishAll();
                android.os.Process.killProcess(android.os.Process.myPid());//杀掉进程
            }
        } else {
            super.onBackPressed();
        }
    }

    protected abstract int setLayoutResId();

    protected abstract void bindData(Bundle savedInstanceState);

    protected abstract void bindView(View view);

    protected abstract void initData();

    protected abstract void initView(View view);

    protected abstract void initListener();
}

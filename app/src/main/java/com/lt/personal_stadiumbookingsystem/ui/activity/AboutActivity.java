package com.lt.personal_stadiumbookingsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseActivity;

public class AboutActivity extends BaseActivity {
    private TextView mTvAboutInfo;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {

    }

    @Override
    protected void bindView(View view) {
        mTvAboutInfo = view.findViewById(R.id.tv_about_info);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView(View view) {
        String stringInfo = "一个场馆";
        stringInfo += "\n";
        stringInfo += "By LinTan";
        stringInfo += "\n";
        stringInfo += "https://www.lintan.design";
        stringInfo += "\n";
        stringInfo += "https://github.com/zuilintan";
        mTvAboutInfo.setText(stringInfo);
    }

    @Override
    protected void initListener() {

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AboutActivity.class);
        context.startActivity(intent);//默认包含android.intent.category.LAUNCHER
    }
}

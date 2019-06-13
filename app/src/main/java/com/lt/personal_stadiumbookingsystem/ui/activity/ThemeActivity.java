package com.lt.personal_stadiumbookingsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.RadioButton;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseActivity;
import com.lt.personal_stadiumbookingsystem.util.ActivityUtil;
import com.lt.personal_stadiumbookingsystem.util.SPUtil;
import com.lt.personal_stadiumbookingsystem.util.ToastUtil;

public class ThemeActivity extends BaseActivity implements View.OnClickListener {

    private RadioButton mRbThemeTeal;
    private ConstraintLayout mCslThemeTeal;
    private RadioButton mRbThemeIndigo;
    private ConstraintLayout mCslThemeIndigo;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_theme;
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {

    }

    @Override
    protected void bindView(View view) {
        mRbThemeTeal = findViewById(R.id.rb_theme_teal);
        mCslThemeTeal = findViewById(R.id.csl_theme_teal);
        mRbThemeIndigo = findViewById(R.id.rb_theme_Indigo);
        mCslThemeIndigo = findViewById(R.id.csl_theme_Indigo);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        int theme = (int) SPUtil.get(this, "theme", R.style.AppThemeTeal);
        switch (theme) {
            case R.style.AppThemeTeal:
                mRbThemeTeal.setChecked(true);
                break;

            case R.style.AppThemeIndigo:
                mRbThemeIndigo.setChecked(true);
                break;
        }
    }

    @Override
    protected void initListener() {
        mCslThemeTeal.setOnClickListener(this);
        mCslThemeIndigo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.csl_theme_teal:
                mRbThemeTeal.setChecked(true);
                mRbThemeIndigo.setChecked(false);
                SPUtil.put(this, "theme", R.style.AppThemeTeal);
                ToastUtil.showShortCenter(this, "主题切换成功");
                MainActivity.actionStart(this, 2);
                ActivityUtil.finishAll();
                break;

            case R.id.csl_theme_Indigo:
                mRbThemeIndigo.setChecked(true);
                mRbThemeTeal.setChecked(false);
                SPUtil.put(this, "theme", R.style.AppThemeIndigo);
                ToastUtil.showShortCenter(this, "主题切换成功");
                MainActivity.actionStart(this, 2);
                ActivityUtil.finishAll();
                break;
        }
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ThemeActivity.class);
        context.startActivity(intent);//默认包含android.intent.category.LAUNCHER
    }
}

package com.lt.personal_stadiumbookingsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseActivity;
import com.lt.personal_stadiumbookingsystem.base.BaseDialog;
import com.lt.personal_stadiumbookingsystem.ui.dialog.ConfirmDialog;
import com.lt.personal_stadiumbookingsystem.util.ActivityUtil;
import com.lt.personal_stadiumbookingsystem.util.SPUtil;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ConstraintLayout mCslSettingLogout;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {

    }

    @Override
    protected void bindView(View view) {
        mCslSettingLogout = view.findViewById(R.id.csl_setting_logout);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initListener() {
        mCslSettingLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.csl_setting_logout:
                final ConfirmDialog dialog = ConfirmDialog.newInstance("退出登录");
                dialog.show(getSupportFragmentManager());
                dialog.setOnDialogClickListener(new BaseDialog.OnDialogClickListener() {
                    @Override
                    public void onAgreeClickListener() {
                        dialog.dismiss();
                        SPUtil.clear(SettingActivity.this);
                        LoginActivity.actionStart(SettingActivity.this);
                        ActivityUtil.finishAll();
                    }

                    @Override
                    public void onCancelClickListener() {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SettingActivity.class);
        context.startActivity(intent);//默认包含android.intent.category.LAUNCHER
    }
}

package com.lt.personal_stadiumbookingsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseActivity;
import com.lt.personal_stadiumbookingsystem.constant.SpConstant;
import com.lt.personal_stadiumbookingsystem.constant.UrlConstant;
import com.lt.personal_stadiumbookingsystem.util.ActivityUtil;
import com.lt.personal_stadiumbookingsystem.util.GlideUtil;
import com.lt.personal_stadiumbookingsystem.util.LogUtil;
import com.lt.personal_stadiumbookingsystem.util.OkHttpUtil;
import com.lt.personal_stadiumbookingsystem.util.SPUtil;
import com.lt.personal_stadiumbookingsystem.util.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class LoginActivity extends BaseActivity {

    private ProgressBar mPbLogin;
    private ImageView mIvLoginAvatar;
    private EditText mEtLoginAccountnumber;
    private EditText mEtLoginAccountpasswd;
    private TextView mTvLoginRegister;
    private Button mBtnLoginGo;
    private String mInputAccountName;
    private String mInputAccountPasswd;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {
    }

    @Override
    protected void bindView(View view) {
        mPbLogin = view.findViewById(R.id.pb_login);
        mIvLoginAvatar = view.findViewById(R.id.iv_login_avatar);
        mEtLoginAccountnumber = view.findViewById(R.id.et_login_accountnumber);
        mEtLoginAccountpasswd = view.findViewById(R.id.et_login_accountpasswd);
        mTvLoginRegister = view.findViewById(R.id.tv_login_register);
        mBtnLoginGo = view.findViewById(R.id.btn_login_go);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        GlideUtil.loadImage(this, R.drawable.ic_avatar01, mIvLoginAvatar);
    }

    @Override
    protected void initListener() {
        mTvLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.actionStart(LoginActivity.this);
            }
        });

        mBtnLoginGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputAccountName = mEtLoginAccountnumber.getText().toString();
                mInputAccountPasswd = mEtLoginAccountpasswd.getText().toString();
                boolean isEffective = checkEffective();
                if (isEffective) {
                    mPbLogin.setVisibility(View.VISIBLE);
                    Map<String, String> map = new HashMap<>();
                    map.put("account_name", mInputAccountName);
                    map.put("account_passwd", mInputAccountPasswd);
                    OkHttpUtil.postFormRequest(UrlConstant.URL_ACCOUNT_LOGIN, map, new OkHttpUtil.DataCallBack() {
                        @Override
                        public void requestSuccess(String result) throws Exception {
                            LogUtil.wtf(result);
                            if ("success".equals(result)) {
                                ToastUtil.showShortCenter(LoginActivity.this,
                                                          "登录成功");
                                SPUtil.put(LoginActivity.this,
                                           SpConstant.SP_ACCOUNT_NAME,
                                           mInputAccountName);
                                SPUtil.put(LoginActivity.this,
                                           SpConstant.SP_ACCOUNT_PASSWD,
                                           mInputAccountPasswd);
                                SPUtil.put(LoginActivity.this,
                                           SpConstant.SP_ACCOUNT_LOGINSTATE,
                                           1);

                                mPbLogin.setVisibility(View.GONE);
                                MainActivity.actionStart(LoginActivity.this,
                                                         0);
                                ActivityUtil.finish(LoginActivity.this);
                            } else {
                                ToastUtil.showShortCenter(LoginActivity.this,
                                                          "登录失败，账号或密码错误，请重试");
                                mPbLogin.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void requestFailure(Request request, IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    private boolean checkEffective() {
        boolean result = false;

        boolean accountNumberEffective = false;
        if (TextUtils.isEmpty(mInputAccountName)) {
            mEtLoginAccountnumber.setError("账号不能为空");
        } else {
            String regex = "^[0-9A-Za-z]{4,10}$";//仅包含数字、字母，并只有4~10位
            boolean matches = mInputAccountName.matches(regex);
            if (matches) {
                accountNumberEffective = true;
            } else {
                mEtLoginAccountnumber.setError("账号格式有误");
            }
        }

        boolean accountPasswdEffective = false;
        if (TextUtils.isEmpty(mInputAccountPasswd)) {
            mEtLoginAccountpasswd.setError("密码不能为空");
        } else {
            String regex = "^[0-9A-Za-z]{8,16}$";//仅包含数字、字母，并只有6~12位
            boolean matches = mInputAccountPasswd.matches(regex);
            if (matches) {
                accountPasswdEffective = true;
            } else {
                mEtLoginAccountpasswd.setError("密码格式有误");
            }
        }

        if (accountNumberEffective && accountPasswdEffective) {
            result = true;
        }
        return result;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);//默认包含android.intent.category.LAUNCHER
    }
}

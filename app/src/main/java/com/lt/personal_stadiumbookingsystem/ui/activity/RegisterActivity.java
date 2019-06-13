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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseActivity;
import com.lt.personal_stadiumbookingsystem.constant.SpConstant;
import com.lt.personal_stadiumbookingsystem.constant.UrlConstant;
import com.lt.personal_stadiumbookingsystem.util.GlideUtil;
import com.lt.personal_stadiumbookingsystem.util.LogUtil;
import com.lt.personal_stadiumbookingsystem.util.OkHttpUtil;
import com.lt.personal_stadiumbookingsystem.util.SPUtil;
import com.lt.personal_stadiumbookingsystem.util.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class RegisterActivity extends BaseActivity {

    private ProgressBar mPbRegister;
    private ImageView mIvRegisterAvatar;
    private EditText mEtRegisterAccountnumber;
    private EditText mEtRegisterAccountpasswd;
    private EditText mEtRegisterConfirmpasswd;
    private RadioGroup mRgRegisterSex;
    private RadioButton mRadioButton;
    private Button mBtnRegisterGo;
    private String mInputAccountName;
    private String mInputAccountPasswd;
    private String mInputConfirmPasswd;
    private String mInputSex;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {

    }

    @Override
    protected void bindView(View view) {
        mPbRegister = view.findViewById(R.id.pb_register);
        mIvRegisterAvatar = view.findViewById(R.id.iv_register_avatar);
        mEtRegisterAccountnumber = view.findViewById(R.id.et_register_accountnumber);
        mEtRegisterAccountpasswd = view.findViewById(R.id.et_register_accountpasswd);
        mEtRegisterConfirmpasswd = view.findViewById(R.id.et_register_confirmpasswd);
        mRgRegisterSex = view.findViewById(R.id.rg_register_sex);
        mBtnRegisterGo = view.findViewById(R.id.btn_register_go);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        GlideUtil.loadImage(this, R.drawable.ic_avatar01, mIvRegisterAvatar);
    }

    @Override
    protected void initListener() {
        mBtnRegisterGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputAccountName = mEtRegisterAccountnumber.getText().toString();
                mInputAccountPasswd = mEtRegisterAccountpasswd.getText().toString();
                mInputConfirmPasswd = mEtRegisterConfirmpasswd.getText().toString();
                mRadioButton = findViewById(mRgRegisterSex.getCheckedRadioButtonId());
                mInputSex = mRadioButton.getText().toString();
                boolean isEffective = checkEffective();
                if (isEffective) {
                    mPbRegister.setVisibility(View.VISIBLE);
                    Map<String, String> map = new HashMap<>();
                    map.put("account_name", mInputAccountName);
                    map.put("account_passwd", mInputAccountPasswd);
                    map.put("account_sex", mInputSex);
                    OkHttpUtil.postFormRequest(UrlConstant.URL_ACCOUNT_REGISTER, map, new OkHttpUtil.DataCallBack() {
                        @Override
                        public void requestSuccess(String result) throws Exception {
                            if ("success".equals(result)) {
                                ToastUtil.showShortCenter(RegisterActivity.this, "注册成功，正在为您登陆");
                                SPUtil.put(RegisterActivity.this,
                                           SpConstant.SP_ACCOUNT_NAME,
                                           mInputAccountName);
                                SPUtil.put(RegisterActivity.this,
                                           SpConstant.SP_ACCOUNT_PASSWD,
                                           mInputAccountPasswd);
                                SPUtil.put(RegisterActivity.this,
                                           SpConstant.SP_ACCOUNT_LOGINSTATE,
                                           1);
                                mPbRegister.setVisibility(View.GONE);
                                MainActivity.actionStart(RegisterActivity.this, 0);
                            } else {
                                ToastUtil.showShortCenter(RegisterActivity.this, "注册失败，账号已被占用");
                                mPbRegister.setVisibility(View.GONE);
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

        boolean accountNumberEffective;
        if (TextUtils.isEmpty(mInputAccountName)) {
            accountNumberEffective = false;
            mEtRegisterAccountnumber.setError("账号不能为空");
        } else {
            String regex = "^[0-9A-Za-z]{4,10}$";//仅包含数字、字母，并只有4~10位
            boolean matches = mInputAccountName.matches(regex);
            if (matches) {
                accountNumberEffective = true;
            } else {
                accountNumberEffective = false;
                mEtRegisterAccountnumber.setError("账号格式有误");
            }
        }

        boolean accountPasswdEffective;
        if (TextUtils.isEmpty(mInputAccountPasswd)) {
            accountPasswdEffective = false;
            mEtRegisterAccountpasswd.setError("密码不能为空");
        } else {
            String regex = "^[0-9A-Za-z]{8,16}$";//仅包含数字、字母，并只有6~12位
            boolean matches = mInputAccountPasswd.matches(regex);
            if (matches) {
                accountPasswdEffective = true;
            } else {
                accountPasswdEffective = false;
                mEtRegisterAccountpasswd.setError("密码格式有误");
            }
        }

        if (mInputConfirmPasswd.equals(mInputAccountPasswd)) {
            if (accountNumberEffective && accountPasswdEffective) {
                result = true;
            }
        } else {
            mEtRegisterConfirmpasswd.setError("两次密码输入不一致");
        }
        return result;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RegisterActivity.class);
        context.startActivity(intent);//默认包含android.intent.category.LAUNCHER
    }
}

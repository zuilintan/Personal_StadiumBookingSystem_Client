package com.lt.personal_stadiumbookingsystem.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseActivity;
import com.lt.personal_stadiumbookingsystem.constant.UrlConstant;
import com.lt.personal_stadiumbookingsystem.util.ActivityUtil;
import com.lt.personal_stadiumbookingsystem.util.OkHttpUtil;
import com.lt.personal_stadiumbookingsystem.util.SPUtil;
import com.lt.personal_stadiumbookingsystem.util.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class AccountActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvAccountName;
    private ConstraintLayout mCslAccountName;
    private TextView mTvAccountNickname;
    private ConstraintLayout mCslAccountNickname;
    private TextView mTvAccountRealname;
    private ConstraintLayout mCslAccountRealname;
    private TextView mTvAccountSex;
    private ConstraintLayout mCslAccountSex;
    private TextView mTvAccountPhone;
    private ConstraintLayout mCslAccountPhone;
    private TextView mTvAccountEmail;
    private ConstraintLayout mCslAccountEmail;
    private TextView mTvAccountQq;
    private ConstraintLayout mCslAccountQq;
    private ConstraintLayout mCslAccountPassword;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_account;
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {

    }

    @Override
    protected void bindView(View view) {
        mTvAccountName = view.findViewById(R.id.tv_account_name);
        mCslAccountName = view.findViewById(R.id.csl_account_name);
        mTvAccountNickname = view.findViewById(R.id.tv_account_nickname);
        mCslAccountNickname = view.findViewById(R.id.csl_account_nickname);
        mTvAccountRealname = view.findViewById(R.id.tv_account_realname);
        mCslAccountRealname = view.findViewById(R.id.csl_account_realname);
        mTvAccountSex = view.findViewById(R.id.tv_account_sex);
        mCslAccountSex = view.findViewById(R.id.csl_account_sex);
        mTvAccountPhone = view.findViewById(R.id.tv_account_phone);
        mCslAccountPhone = view.findViewById(R.id.csl_account_phone);
        mTvAccountEmail = view.findViewById(R.id.tv_account_email);
        mCslAccountEmail = view.findViewById(R.id.csl_account_email);
        mTvAccountQq = view.findViewById(R.id.tv_account_qq);
        mCslAccountQq = view.findViewById(R.id.csl_account_qq);
        mCslAccountPassword = view.findViewById(R.id.csl_account_password);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView(View view) {
        mTvAccountName.setText((String) SPUtil.get(AccountActivity.this, "account_name", "获取失败"));
        mTvAccountNickname.setText((String) SPUtil.get(AccountActivity.this, "account_nickname", "未设置"));
        mTvAccountRealname.setText((String) SPUtil.get(AccountActivity.this, "account_realname", "未设置"));
        mTvAccountSex.setText((String) SPUtil.get(AccountActivity.this, "account_sex", "未设置"));
        mTvAccountPhone.setText((String) SPUtil.get(AccountActivity.this, "account_phone", "未设置"));
        mTvAccountEmail.setText((String) SPUtil.get(AccountActivity.this, "account_email", "未设置"));
        mTvAccountQq.setText((String) SPUtil.get(AccountActivity.this, "account_qq", "未设置"));
    }

    @Override
    protected void initListener() {
        mCslAccountName.setOnClickListener(this);
        mCslAccountNickname.setOnClickListener(this);
        mCslAccountRealname.setOnClickListener(this);
        mCslAccountSex.setOnClickListener(this);
        mCslAccountPhone.setOnClickListener(this);
        mCslAccountEmail.setOnClickListener(this);
        mCslAccountQq.setOnClickListener(this);
        mCslAccountPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.csl_account_name:
                ToastUtil.showShortCenter(this, "账户名无法修改");
                break;
            case R.id.csl_account_nickname:
                editInfoDialog("account_nickname", mTvAccountNickname);
                break;
            case R.id.csl_account_realname:
                editInfoDialog("account_realname", mTvAccountRealname);
                break;
            case R.id.csl_account_sex:
                choiceSexDialog();
                break;
            case R.id.csl_account_phone:
                editInfoDialog("account_phone", mTvAccountPhone);
                break;
            case R.id.csl_account_email:
                editInfoDialog("account_email", mTvAccountEmail);
                break;
            case R.id.csl_account_qq:
                editInfoDialog("account_qq", mTvAccountQq);
                break;
            case R.id.csl_account_password:
                editPasswd("account_passwd");
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MainActivity.actionStart(this, 2);
                ActivityUtil.finishAll();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        MainActivity.actionStart(this, 2);
        ActivityUtil.finishAll();
    }

    private void editInfoDialog(final String columnName, final TextView textView) {
        final EditText editText = new EditText(this);
        if (textView != null) {
            editText.setText(textView.getText().toString());
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("修改信息");
        builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String input = editText.getText().toString();
                Map<String, String> map = new HashMap<>();
                map.put(columnName, input);
                map.put("account_name", (String) SPUtil.get(AccountActivity.this, "account_name", ""));
                OkHttpUtil.postFormRequest(UrlConstant.URL_ACCOUNT_EDIT, map, new OkHttpUtil.DataCallBack() {
                    @Override
                    public void requestSuccess(String result) throws Exception {
                        if (!"failure".equals(result)) {
                            ToastUtil.showShortCenter(AccountActivity.this, "修改成功");
                            SPUtil.put(AccountActivity.this, columnName, input);
                            if (textView != null) {
                                textView.setText(input);
                            }
                        } else {
                            ToastUtil.showShortCenter(AccountActivity.this, "修改失败");
                        }
                    }

                    @Override
                    public void requestFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        builder.show();
    }

    private void choiceSexDialog() {
        final String[] sexStrings = new String[]{"男", "女"};// 性别选择
        int checkedItem = 0;
        if (!sexStrings[0].equals(mTvAccountSex.getText().toString())) {
            checkedItem = 1;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
        builder.setTitle("修改信息");
        builder.setSingleChoiceItems(sexStrings, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                dialog.dismiss();
                Map<String, String> map = new HashMap<>();
                map.put("account_sex", sexStrings[which]);
                map.put("account_name", (String) SPUtil.get(AccountActivity.this, "account_name", ""));
                OkHttpUtil.postFormRequest(UrlConstant.URL_ACCOUNT_EDIT, map, new OkHttpUtil.DataCallBack() {
                    @Override
                    public void requestSuccess(String result) throws Exception {
                        if (!"failure".equals(result)) {
                            ToastUtil.showShortCenter(AccountActivity.this, "修改成功");
                            mTvAccountSex.setText(sexStrings[which]);
                        } else {
                            ToastUtil.showShortCenter(AccountActivity.this, "修改失败");
                        }
                    }

                    @Override
                    public void requestFailure(Request request, IOException e) {

                    }
                });
            }
        });
        builder.show();
    }

    private void editPasswd(final String columnName) {
        final EditText editText = new EditText(this);
        editText.setHint("请输入8~16位数字、字母");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("修改信息");
        builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String input = editText.getText().toString();
                boolean isEffective = checkEffective(input, editText);
                if (isEffective) {
                    Map<String, String> map = new HashMap<>();
                    map.put(columnName, input);
                    map.put("account_name", (String) SPUtil.get(AccountActivity.this, "account_name", ""));
                    OkHttpUtil.postFormRequest(UrlConstant.URL_ACCOUNT_EDIT, map, new OkHttpUtil.DataCallBack() {
                        @Override
                        public void requestSuccess(String result) throws Exception {
                            if (!"failure".equals(result)) {
                                ToastUtil.showShortCenter(AccountActivity.this, "修改成功");
                                SPUtil.put(AccountActivity.this, columnName, input);
                            } else {
                                ToastUtil.showShortCenter(AccountActivity.this, "修改失败");
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
        builder.show();
    }

    private boolean checkEffective(String input, EditText editText) {
        boolean accountPasswdEffective = false;
        if (TextUtils.isEmpty(input)) {
            ToastUtil.showShortCenter(AccountActivity.this, "密码不能为空");
        } else {
            String regex = "^[0-9A-Za-z]{8,16}$";//仅包含数字、字母，并只有6~12位
            boolean matches = input.matches(regex);
            if (matches) {
                accountPasswdEffective = true;
            } else {
                ToastUtil.showShortCenter(AccountActivity.this, "密码格式错误");
            }
        }
        return accountPasswdEffective;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AccountActivity.class);
        context.startActivity(intent);//默认包含android.intent.category.LAUNCHER
    }
}

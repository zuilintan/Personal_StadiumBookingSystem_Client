package com.lt.personal_stadiumbookingsystem.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseFragment;
import com.lt.personal_stadiumbookingsystem.constant.UrlConstant;
import com.lt.personal_stadiumbookingsystem.entity.Account;
import com.lt.personal_stadiumbookingsystem.ui.activity.AboutActivity;
import com.lt.personal_stadiumbookingsystem.ui.activity.AccountActivity;
import com.lt.personal_stadiumbookingsystem.ui.activity.SettingActivity;
import com.lt.personal_stadiumbookingsystem.ui.activity.ThemeActivity;
import com.lt.personal_stadiumbookingsystem.util.GlideUtil;
import com.lt.personal_stadiumbookingsystem.util.GsonUtil;
import com.lt.personal_stadiumbookingsystem.util.OkHttpUtil;
import com.lt.personal_stadiumbookingsystem.util.SPUtil;
import com.lt.personal_stadiumbookingsystem.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class MeFragment extends BaseFragment {

    private SmartRefreshLayout mSrlMe;
    private ImageView mIvMeAvatar;
    private TextView mTvMeNickname;
    private TextView mTvMeCoin;
    private ConstraintLayout mCslMeInfo;
    private ConstraintLayout mCslMeTheme;
    private ConstraintLayout mCslMeSetting;
    private ConstraintLayout mCslMeAbout;

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {
    }

    @Override
    protected void bindView(View view) {
        mSrlMe = view.findViewById(R.id.srl_me);
        mIvMeAvatar = view.findViewById(R.id.iv_me_avatar);
        mTvMeNickname = view.findViewById(R.id.tv_me_nickname);
        mTvMeCoin = view.findViewById(R.id.tv_me_coin);
        mCslMeInfo = view.findViewById(R.id.csl_me_info);
        mCslMeTheme = view.findViewById(R.id.csl_me_theme);
        mCslMeSetting = view.findViewById(R.id.csl_me_setting);
        mCslMeAbout = view.findViewById(R.id.csl_me_about);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        setAvatar(null);
        setNickName(null);
        setCoin(null);
    }

    @Override
    protected void initListener() {
        mSrlMe.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Map<String, String> map = new HashMap<>();
                map.put("account_name", (String) SPUtil.get(mContext, "account_name", ""));
                OkHttpUtil.postFormRequest(UrlConstant.URL_ACCOUNT_SHOW, map, new OkHttpUtil.DataCallBack() {
                    @Override
                    public void requestSuccess(String result) throws Exception {
                        if (!"failure".equals(result)) {
                            Account account = GsonUtil.jsonToObject(result, Account.class);
                            setAvatar(account.getAccount_sex());
                            setNickName(account.getAccount_nickname());
                            setCoin(account.getAccount_coin());
                            SPUtil.put(mContext, "account_nickname", account.getAccount_nickname());
                            SPUtil.put(mContext, "account_realname", account.getAccount_realname());
                            SPUtil.put(mContext, "account_coin", account.getAccount_coin());
                            SPUtil.put(mContext, "account_sex", account.getAccount_sex());
                            SPUtil.put(mContext, "account_phone", account.getAccount_phone());
                            SPUtil.put(mContext, "account_email", account.getAccount_email());
                            SPUtil.put(mContext, "account_qq", account.getAccount_qq());
                            mSrlMe.finishRefresh(true);
                            ToastUtil.showShortCenter(mContext, "数据刷新成功");
                        } else {
                            mSrlMe.finishRefresh(false);
                            ToastUtil.showShortCenter(mContext, "暂无数据");
                        }
                    }

                    @Override
                    public void requestFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        mCslMeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountActivity.actionStart(mContext);
            }
        });

        mCslMeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeActivity.actionStart(mContext);
            }
        });

        mCslMeSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.actionStart(mContext);
            }
        });

        mCslMeAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.actionStart(mContext);
            }
        });
    }

    private void setAvatar(String accountSex) {
        if (accountSex == null) {
            accountSex = (String) SPUtil.get(mContext, "account_sex", "");
        }
        if (!"".equals(accountSex)) {
            switch (accountSex) {
                case "男":
                    GlideUtil.loadCircleImage(mContext, R.drawable.ic_avatar_boy, mIvMeAvatar);
                    break;

                case "女":
                    GlideUtil.loadCircleImage(mContext, R.drawable.ic_avatar_girl, mIvMeAvatar);
                    break;
            }
        } else {
            GlideUtil.loadCircleImage(mContext, R.drawable.ic_avatar01, mIvMeAvatar);
        }
    }

    private void setNickName(String accountNickName) {
        if (accountNickName == null) {
            accountNickName = (String) SPUtil.get(mContext, "account_nickname", "");
        }
        if (!"".equals(accountNickName)) {
            mTvMeNickname.setText("昵称: " + accountNickName);
        } else {
            mTvMeNickname.setText("昵称: 未设置");
        }
    }

    private void setCoin(Float accountCoin) {
        if (accountCoin == null) {
            accountCoin = (Float) SPUtil.get(mContext, "account_coin", -1.0F);
        }
        if (accountCoin != -1.0F) {
            mTvMeCoin.setText("硬币: " + accountCoin.intValue() + "枚");
        } else {
            mTvMeCoin.setText("硬币: ??枚");
        }
    }
}

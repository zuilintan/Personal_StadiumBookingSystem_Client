package com.lt.personal_stadiumbookingsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;
import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseActivity;
import com.lt.personal_stadiumbookingsystem.constant.SpConstant;
import com.lt.personal_stadiumbookingsystem.ui.fragment.GymFragment;
import com.lt.personal_stadiumbookingsystem.ui.fragment.HistoryFragment;
import com.lt.personal_stadiumbookingsystem.ui.fragment.MeFragment;
import com.lt.personal_stadiumbookingsystem.util.ActivityUtil;
import com.lt.personal_stadiumbookingsystem.util.GlideUtil;
import com.lt.personal_stadiumbookingsystem.util.SPUtil;


public class MainActivity extends BaseActivity {

    private CollapsingToolbarLayout mCtlTop;
    private ImageView mIvTop;
    private Toolbar mTbTop;
    private BottomNavigationView mBnvMain;
    private GymFragment mGymFragment;
    private HistoryFragment mHistoryFragment;
    private MeFragment mMeFragment;
    private int mTabIndex;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {
        int isLogin = (int) SPUtil.get(this, SpConstant.SP_ACCOUNT_LOGINSTATE, 0);
        if (isLogin == 0) {
            LoginActivity.actionStart(this);
            ActivityUtil.finish(this);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTabIndex = bundle.getInt("data1", 0);
        }
    }

    @Override
    protected void bindView(View view) {
        mCtlTop = view.findViewById(R.id.ctl_top);
        mIvTop = view.findViewById(R.id.iv_top);
        mTbTop = view.findViewById(R.id.tb_top);
        mBnvMain = view.findViewById(R.id.bnv_main);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        TypedValue typedValuePrimary = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValuePrimary, true);
        int colorPrimary = typedValuePrimary.data;
        mCtlTop.setContentScrimColor(colorPrimary);
        setSupportActionBar(mTbTop);
        StatusBarUtil.setTranslucentForCoordinatorLayout(this, 0);
        GlideUtil.loadImage(MainActivity.this, R.drawable.ic_appbar, mIvTop);
    }

    @Override
    protected void initListener() {
        BottomNavigationView.OnNavigationItemSelectedListener onNavigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_gym:
                        FragmentTransaction transactionHome = getSupportFragmentManager().beginTransaction();
                        if (mGymFragment == null) {
                            mGymFragment = GymFragment.newInstance();
                            transactionHome.add(R.id.fl_main, mGymFragment);
                        }
                        hideFragment(transactionHome);
                        transactionHome.show(mGymFragment);
                        transactionHome.commit();
                        return true;
                    case R.id.navigation_history:
                        FragmentTransaction transactionHistory = getSupportFragmentManager().beginTransaction();
                        if (mHistoryFragment == null) {
                            mHistoryFragment = HistoryFragment.newInstance();
                            transactionHistory.add(R.id.fl_main, mHistoryFragment);
                        }
                        hideFragment(transactionHistory);
                        transactionHistory.show(mHistoryFragment);
                        transactionHistory.commit();
                        return true;
                    case R.id.navigation_me:
                        FragmentTransaction transactionMe = getSupportFragmentManager().beginTransaction();
                        if (mMeFragment == null) {
                            mMeFragment = MeFragment.newInstance();
                            transactionMe.add(R.id.fl_main, mMeFragment);
                        }
                        hideFragment(transactionMe);
                        transactionMe.show(mMeFragment);
                        transactionMe.commit();
                        return true;
                }
                return false;
            }
        };
        mBnvMain.setOnNavigationItemSelectedListener(onNavigationListener);
        mBnvMain.setSelectedItemId(mBnvMain.getMenu()
                                           .getItem(mTabIndex)
                                           .getItemId());//设置默认选中item
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mGymFragment != null) {
            transaction.hide(mGymFragment);
        }
        if (mHistoryFragment != null) {
            transaction.hide(mHistoryFragment);
        }
        if (mMeFragment != null) {
            transaction.hide(mMeFragment);
        }
    }//隐藏非当前屏幕的Fragment

    public static void actionStart(Context context, int tabIndex) {
        Bundle bundle = new Bundle();
        bundle.putInt("data1", tabIndex);
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);//默认包含android.intent.category.LAUNCHER
    }
}
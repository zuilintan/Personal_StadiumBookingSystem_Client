package com.lt.personal_stadiumbookingsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseActivity;
import com.lt.personal_stadiumbookingsystem.constant.UrlConstant;
import com.lt.personal_stadiumbookingsystem.entity.DataTime;
import com.lt.personal_stadiumbookingsystem.ui.adapter.TimeAdapter;
import com.lt.personal_stadiumbookingsystem.util.GsonUtil;
import com.lt.personal_stadiumbookingsystem.util.LogUtil;
import com.lt.personal_stadiumbookingsystem.util.OkHttpUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class TimeActivity extends BaseActivity {

    private List<DataTime> mDataTimeList;
    private RefreshLayout mSrlTime;
    private TabLayout mTlTime;
    private LinearLayoutManager mLayoutManager;
    private TimeAdapter mTimeAdapter;
    private RecyclerView mRcvTime;
    private String mGymName;
    private String mSiteNumber;
    private boolean mIsRcvActiveScroll;//是否为rcv激活的滚动(即用户手指滑动)
    private int mRcvScrollOrientation;//滚动方向(小于0为View左滚，大于0为右滚)
    private int mPosition;//记录TabLayout与RecyclerView的位置

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_time;
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mGymName = bundle.getString("data1", "");
            mSiteNumber = bundle.getString("data2", "");
        }
    }

    @Override
    protected void bindView(View view) {
        mSrlTime = view.findViewById(R.id.srl_time);
        mTlTime = view.findViewById(R.id.tl_time);
        mRcvTime = view.findViewById(R.id.rcv_time);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        mLayoutManager = new LinearLayoutManager(TimeActivity.this);//创建线性布局管理器
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//为线性布局管理器设置布局方向
        mTimeAdapter = new TimeAdapter(R.layout.time_item, mDataTimeList);
        mTimeAdapter.setHasStableIds(true);
        mRcvTime.setLayoutManager(mLayoutManager);
        mRcvTime.setAdapter(mTimeAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRcvTime);
    }

    @Override
    protected void initListener() {
        mSrlTime.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                HashMap<String, String> map = new HashMap<>();
                map.put("site_number", mSiteNumber);
                map.put("gym_name", mGymName);
                OkHttpUtil.postFormRequest(UrlConstant.URL_TIME_SHOWLIST, map, new OkHttpUtil.DataCallBack() {
                    @Override
                    public void requestSuccess(String result) throws Exception {
                        if (!"failure".equals(result)) {
                            mDataTimeList = GsonUtil.jsonToList(result, DataTime.class);
                            mTimeAdapter.refresh(mDataTimeList);
                            mRcvTime.scrollToPosition(mPosition);
                            mSrlTime.finishRefresh(true);
                        } else {
                            mSrlTime.finishRefresh(false);
                        }
                    }

                    @Override
                    public void requestFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        mTlTime.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtil.wtf("onTabSelected");
                mIsRcvActiveScroll = false;
                int tabPosition = tab.getPosition();
                mRcvTime.smoothScrollToPosition(tabPosition);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LogUtil.wtf("onTabUnselected");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                LogUtil.wtf("onTabReselected");
                onTabSelected(tab);
            }
        });

        mRcvTime.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_DRAGGING://正在被外部拖拽，一般为用户手指滑动
                        LogUtil.wtf("SCROLL_STATE_DRAGGING");
                        mIsRcvActiveScroll = true;
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://自动滚动开始
                        LogUtil.wtf("SCROLL_STATE_SETTLING");
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                        LogUtil.wtf("SCROLL_STATE_IDLE");
                        break;
                }
                updateTabSelect(mRcvScrollOrientation);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mIsRcvActiveScroll) {
                    mRcvScrollOrientation = dx;//将rcv水平滚动距离保存到成员变量中
                }
            }
        });
    }

    private void updateTabSelect(int dx) {
        if (mIsRcvActiveScroll) {
            if (dx > 0) {//View右滑
                LogUtil.wtf("dx > 0，View右滑");
                mPosition = mLayoutManager.findLastVisibleItemPosition();
            }
            if (dx < 0) {//View左滑
                LogUtil.wtf("dx < 0，View左滑");
                mPosition = mLayoutManager.findFirstVisibleItemPosition();
            }
            mTlTime.setScrollPosition(mPosition, 0F, true);
        }
    }//更新TabLayout的选中的TabItem

    public static void actionStart(Context context, String gymName, String siteNumber) {
        Bundle bundle = new Bundle();
        bundle.putString("data1", gymName);
        bundle.putString("data2", siteNumber);
        Intent intent = new Intent();
        intent.setClass(context, TimeActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);//默认包含android.intent.category.LAUNCHER
    }
}

package com.lt.personal_stadiumbookingsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseActivity;
import com.lt.personal_stadiumbookingsystem.base.BaseRecyclerViewAdapter;
import com.lt.personal_stadiumbookingsystem.constant.UrlConstant;
import com.lt.personal_stadiumbookingsystem.entity.Site;
import com.lt.personal_stadiumbookingsystem.ui.adapter.SiteAdapter;
import com.lt.personal_stadiumbookingsystem.util.GsonUtil;
import com.lt.personal_stadiumbookingsystem.util.OkHttpUtil;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class SiteActivity extends BaseActivity {

    private RefreshLayout mSrlSite;
    private List<Site> mSiteList;
    private GridLayoutManager mLayoutManager;
    private SiteAdapter mSiteAdapter;
    private RecyclerView mRcvSite;
    private String mGymName;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_site;
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mGymName = bundle.getString("data1", "");
        }
    }

    @Override
    protected void bindView(View view) {
        mSrlSite = view.findViewById(R.id.srl_site);
        mRcvSite = view.findViewById(R.id.rcv_site);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        DeliveryHeader header = new DeliveryHeader(this);
        mSrlSite.setRefreshHeader(header);
        mLayoutManager = new GridLayoutManager(this, 2);
        mSiteAdapter = new SiteAdapter(R.layout.site_item, mSiteList);
        mSiteAdapter.setHasStableIds(true);
        mRcvSite.setLayoutManager(mLayoutManager);
        mRcvSite.setAdapter(mSiteAdapter);
    }

    @Override
    protected void initListener() {
        mSrlSite.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                HashMap<String, String> map = new HashMap<>();
                map.put("gym_name", mGymName);
                OkHttpUtil.postFormRequest(UrlConstant.URL_SITE_SHOWLIST, map, new OkHttpUtil.DataCallBack() {
                    @Override
                    public void requestSuccess(String result) throws Exception {
                        if (!"failure".equals(result)) {
                            mSiteList = GsonUtil.jsonToList(result, Site.class);
                            mSiteAdapter.refresh(mSiteList);//刷新RecyclerView数据
                            mSrlSite.finishRefresh(true);
                        } else {
                            mSrlSite.finishRefresh(false);
                        }
                    }

                    @Override
                    public void requestFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        mSiteAdapter.setItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Site site = mSiteList.get(position);
                String siteNumber = site.getSite_number();
                TimeActivity.actionStart(SiteActivity.this, mGymName, siteNumber);
            }
        });
    }

    public static void actionStart(Context context, String gymName) {
        Bundle bundle = new Bundle();
        bundle.putString("data1", gymName);
        Intent intent = new Intent();
        intent.setClass(context, SiteActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);//默认包含android.intent.category.LAUNCHER
    }
}

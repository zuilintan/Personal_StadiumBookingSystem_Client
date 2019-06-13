package com.lt.personal_stadiumbookingsystem.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseFragment;
import com.lt.personal_stadiumbookingsystem.base.BaseRecyclerViewAdapter;
import com.lt.personal_stadiumbookingsystem.constant.SpConstant;
import com.lt.personal_stadiumbookingsystem.constant.UrlConstant;
import com.lt.personal_stadiumbookingsystem.entity.Order;
import com.lt.personal_stadiumbookingsystem.ui.activity.OrderActivity;
import com.lt.personal_stadiumbookingsystem.ui.adapter.HistoryAdapter;
import com.lt.personal_stadiumbookingsystem.util.GsonUtil;
import com.lt.personal_stadiumbookingsystem.util.OkHttpUtil;
import com.lt.personal_stadiumbookingsystem.util.OkHttpUtil.DataCallBack;
import com.lt.personal_stadiumbookingsystem.util.SPUtil;
import com.lt.personal_stadiumbookingsystem.util.ToastUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class HistoryFragment extends BaseFragment {

    private List<Order> mOrderList;
    private RefreshLayout mSrlHistory;
    private LinearLayoutManager mLayoutManager;
    private HistoryAdapter mHistoryAdapter;
    private RecyclerView mRcvHistory;
    private int mPage = 1;//页数
    private int mLimit = 10;//每页数据量

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_history;
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {

    }

    @Override
    protected void bindView(View view) {
        mRcvHistory = view.findViewById(R.id.rcv_history);
        mSrlHistory = view.findViewById(R.id.srl_history);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView(View view) {
        mSrlHistory.setEnableLoadMore(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        mHistoryAdapter = new HistoryAdapter(R.layout.history_item, mOrderList);
        mHistoryAdapter.setHasStableIds(true);
        mRcvHistory.setLayoutManager(mLayoutManager);
        mRcvHistory.setAdapter(mHistoryAdapter);
    }

    @Override
    protected void initListener() {
        mSrlHistory.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                Map<String, String> map = new HashMap<>();
                map.put("account_name",
                        (String) SPUtil.get(mContext, SpConstant.SP_ACCOUNT_NAME, ""));
                map.put("page", String.valueOf(mPage));
                map.put("limit", String.valueOf(mLimit));
                OkHttpUtil.postFormRequest(UrlConstant.URL_ORDER_SHOWLIST, map, new DataCallBack() {
                    @Override
                    public void requestSuccess(String result) throws Exception {
                        if (!"failure".equals(result)) {
                            mOrderList = GsonUtil.jsonToList(result, Order.class);
                            mHistoryAdapter.refresh(mOrderList);
                            mSrlHistory.finishRefresh(true);
                            ToastUtil.showShortCenter(mContext, "数据刷新成功");
                        } else {
                            mSrlHistory.finishRefresh(false);
                            ToastUtil.showShortCenter(mContext, "暂无数据");
                        }
                    }

                    @Override
                    public void requestFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Map<String, String> map = new HashMap<>();
                map.put("account_name",
                        (String) SPUtil.get(mContext, SpConstant.SP_ACCOUNT_NAME, ""));
                map.put("page", String.valueOf(++mPage));
                map.put("limit", String.valueOf(mLimit));
                OkHttpUtil.postFormRequest(UrlConstant.URL_ORDER_SHOWLIST, map, new DataCallBack() {
                    @Override
                    public void requestSuccess(String result) throws Exception {
                        if (!"failure".equals(result)) {
                            List<Order> orderList = GsonUtil.jsonToList(result, Order.class);
                            if (orderList.isEmpty()) {
                                mSrlHistory.finishLoadMoreWithNoMoreData();
                            } else {
                                mOrderList.addAll(orderList);
                                mHistoryAdapter.loadMore(orderList);
                                mSrlHistory.finishLoadMore(true);
                            }
                        } else {
                            mSrlHistory.finishLoadMore(false);
                        }
                    }

                    @Override
                    public void requestFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        mHistoryAdapter.setItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Order order = mOrderList.get(position);
                String orderJson = GsonUtil.objectToJson(order);
                OrderActivity.actionStart(mContext, orderJson);
            }
        });
    }
}

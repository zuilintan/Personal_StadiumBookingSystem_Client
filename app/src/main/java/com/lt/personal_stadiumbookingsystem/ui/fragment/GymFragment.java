package com.lt.personal_stadiumbookingsystem.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseFragment;
import com.lt.personal_stadiumbookingsystem.base.BaseRecyclerViewAdapter;
import com.lt.personal_stadiumbookingsystem.constant.UrlConstant;
import com.lt.personal_stadiumbookingsystem.entity.Gym;
import com.lt.personal_stadiumbookingsystem.ui.activity.SiteActivity;
import com.lt.personal_stadiumbookingsystem.ui.adapter.GymAdapter;
import com.lt.personal_stadiumbookingsystem.util.GsonUtil;
import com.lt.personal_stadiumbookingsystem.util.LogUtil;
import com.lt.personal_stadiumbookingsystem.util.OkHttpUtil;
import com.lt.personal_stadiumbookingsystem.util.OkHttpUtil.DataCallBack;
import com.lt.personal_stadiumbookingsystem.util.ToastUtil;
import com.scwang.smartrefresh.header.DropBoxHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

public class GymFragment extends BaseFragment {

    private RefreshLayout mSrlGym;
    private List<Gym> mGymList;
    private GridLayoutManager mLayoutManager;
    private GymAdapter mGymAdapter;
    private RecyclerView mRcvGym;

    public static GymFragment newInstance() {
        return new GymFragment();
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_gym;
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {

    }

    @Override
    protected void bindView(View view) {
        mRcvGym = view.findViewById(R.id.rcv_gym);
        mSrlGym = view.findViewById(R.id.srl_gym);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        DropBoxHeader header = new DropBoxHeader(mContext);
        mSrlGym.setRefreshHeader(header);
        mLayoutManager = new GridLayoutManager(mContext, 2);
        mGymAdapter = new GymAdapter(R.layout.gym_item, mGymList);
        mGymAdapter.setHasStableIds(true);
        mRcvGym.setLayoutManager(mLayoutManager);
        mRcvGym.setAdapter(mGymAdapter);
    }

    @Override
    protected void initListener() {
        mSrlGym.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                OkHttpUtil.postFormRequest(UrlConstant.URL_GYM_SHOWALL, null, new DataCallBack() {
                    @Override
                    public void requestSuccess(String result) throws Exception {
                        if (!"failure".equals(result)) {
                            mGymList = GsonUtil.jsonToList(result, Gym.class);
                            mGymAdapter.refresh(mGymList);
                            mSrlGym.finishRefresh(true);
                            ToastUtil.showShortCenter(mContext, "数据刷新成功");
                        } else {
                            mSrlGym.finishRefresh(false);
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

        mGymAdapter.setItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView tvGymitemManagestate = view.findViewById(R.id.tv_gymitem_managestate);
                if (TextUtils.isEmpty(tvGymitemManagestate.getText().toString())) {
                    Gym gym = mGymList.get(position);
                    String gymName = gym.getGym_name();
                    SiteActivity.actionStart(mContext, gymName);
                } else {
                    ToastUtil.showShortCenter(view.getContext(), "场馆尚在维护，无法接受预订");
                }
            }
        });
    }
}

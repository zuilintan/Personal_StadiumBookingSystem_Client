package com.lt.personal_stadiumbookingsystem.ui.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseDialog;
import com.lt.personal_stadiumbookingsystem.base.BaseRecyclerViewAdapter;
import com.lt.personal_stadiumbookingsystem.base.BaseViewHolder;
import com.lt.personal_stadiumbookingsystem.constant.SpConstant;
import com.lt.personal_stadiumbookingsystem.constant.UrlConstant;
import com.lt.personal_stadiumbookingsystem.entity.DataTime;
import com.lt.personal_stadiumbookingsystem.entity.Time;
import com.lt.personal_stadiumbookingsystem.ui.activity.OrderActivity;
import com.lt.personal_stadiumbookingsystem.ui.dialog.ConfirmDialog;
import com.lt.personal_stadiumbookingsystem.util.OkHttpUtil;
import com.lt.personal_stadiumbookingsystem.util.SPUtil;
import com.lt.personal_stadiumbookingsystem.util.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class TimeAdapter extends BaseRecyclerViewAdapter<DataTime, BaseViewHolder> implements View.OnClickListener {

    private OkHttpUtil.DataCallBack mBookCallBack;
    private int[] mCslTimes;
    private int[] mTimeStarts;
    private int[] mTimeEnds;
    private int[] mTimeExpireStates;
    private int[] mTimeOrderStates;
    private ConfirmDialog mBookDialog;

    public TimeAdapter(int layoutResId, List<DataTime> DataSourceList) {
        super(layoutResId, DataSourceList);
    }

    @Override
    protected void initView(final BaseViewHolder vh, final DataTime ds, int position) {
        initData();
        vh.setText(R.id.tv_timeitem_date, ds.getDate());
        for (int i = 0; i < ds.getTime().size(); i++) {
            final Time time = ds.getTime().get(i);
            vh.setText(mTimeStarts[i], time.getTime_start());
            vh.setText(mTimeEnds[i], time.getTime_end());
            if (time.getTime_expirestate() == 1) {
                vh.setText(mTimeExpireStates[i], "已过期");
                vh.setVisibility(mTimeOrderStates[i], View.GONE);
                vh.setOnClickListener(mCslTimes[i], new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showShortCenter(v.getContext(), "时段已过期");
                    }
                });
            } else if (time.getTime_orderstate() == 1) {
                vh.setText(mTimeOrderStates[i], "已被预订");
                vh.setVisibility(mTimeExpireStates[i], View.GONE);
                vh.setOnClickListener(mCslTimes[i], new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showShortCenter(v.getContext(), "时段已被预订");
                    }
                });
            } else {
                vh.setVisibility(mTimeExpireStates[i], View.GONE);
                vh.setVisibility(mTimeOrderStates[i], View.GONE);
                vh.setOnClickListener(mCslTimes[i], new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Map<String, String> map = new HashMap<>();
                        map.put("gym_name", ds.getGym());
                        map.put("site_number", ds.getSite());
                        map.put("order_date", ds.getDate());
                        map.put("order_timestart", time.getTime_start());
                        map.put("order_timeend", time.getTime_end());
                        map.put("account_name",
                                (String) SPUtil.get(v.getContext(),
                                                    SpConstant.SP_ACCOUNT_NAME,
                                                    ""));
                        mBookDialog = ConfirmDialog.newInstance("下单");
                        mBookDialog.show(((FragmentActivity) v.getContext()).getSupportFragmentManager());
                        mBookDialog.setOnDialogClickListener(new BaseDialog.OnDialogClickListener() {
                            @Override
                            public void onAgreeClickListener() {
                                mBookDialog.dismiss();
                                OkHttpUtil.postFormRequest(UrlConstant.URL_ORDER_BOOK, map, new OkHttpUtil.DataCallBack() {
                                    @Override
                                    public void requestSuccess(String result) throws Exception {
                                        if (!"failure".equals(result)) {
                                            ToastUtil.showShortCenter(vh.itemView.getContext(), "下单成功");
                                            OrderActivity.actionStart(vh.itemView.getContext(), result);
                                        } else {
                                            ToastUtil.showShortCenter(vh.itemView.getContext(), "下单失败");
                                        }
                                    }

                                    @Override
                                    public void requestFailure(Request request, IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                            }

                            @Override
                            public void onCancelClickListener() {
                                mBookDialog.dismiss();
                            }
                        });
                    }
                });
            }
        }
    }

    @Override
    protected void updateView(BaseViewHolder vh, DataTime dataTime, int position, List<Object> payloadList) {
    }

    private void initData() {
        mCslTimes = new int[]{R.id.csl_timeitem_time1,
                              R.id.csl_timeitem_time2,
                              R.id.csl_timeitem_time3,
                              R.id.csl_timeitem_time4,
                              R.id.csl_timeitem_time5,
                              R.id.csl_timeitem_time6,
                              R.id.csl_timeitem_time7};

        mTimeStarts = new int[]{R.id.tv_timeitem_time1_start,
                                R.id.tv_timeitem_time2_start,
                                R.id.tv_timeitem_time3_start,
                                R.id.tv_timeitem_time4_start,
                                R.id.tv_timeitem_time5_start,
                                R.id.tv_timeitem_time6_start,
                                R.id.tv_timeitem_time7_start};

        mTimeEnds = new int[]{R.id.tv_timeitem_time1_end,
                              R.id.tv_timeitem_time2_end,
                              R.id.tv_timeitem_time3_end,
                              R.id.tv_timeitem_time4_end,
                              R.id.tv_timeitem_time5_end,
                              R.id.tv_timeitem_time6_end,
                              R.id.tv_timeitem_time7_end};

        mTimeExpireStates = new int[]{R.id.tv_timeitem_time1_expirestate,
                                      R.id.tv_timeitem_time2_expirestate,
                                      R.id.tv_timeitem_time3_expirestate,
                                      R.id.tv_timeitem_time4_expirestate,
                                      R.id.tv_timeitem_time5_expirestate,
                                      R.id.tv_timeitem_time6_expirestate,
                                      R.id.tv_timeitem_time7_expirestate};

        mTimeOrderStates = new int[]{R.id.tv_timeitem_time1_orderstate,
                                     R.id.tv_timeitem_time2_orderstate,
                                     R.id.tv_timeitem_time3_orderstate,
                                     R.id.tv_timeitem_time4_orderstate,
                                     R.id.tv_timeitem_time5_orderstate,
                                     R.id.tv_timeitem_time6_orderstate,
                                     R.id.tv_timeitem_time7_orderstate};
    }
}

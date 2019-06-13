package com.lt.personal_stadiumbookingsystem.ui.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseDialog;
import com.lt.personal_stadiumbookingsystem.base.BaseRecyclerViewAdapter;
import com.lt.personal_stadiumbookingsystem.base.BaseViewHolder;
import com.lt.personal_stadiumbookingsystem.constant.UrlConstant;
import com.lt.personal_stadiumbookingsystem.entity.Order;
import com.lt.personal_stadiumbookingsystem.ui.dialog.ConfirmDialog;
import com.lt.personal_stadiumbookingsystem.util.LogUtil;
import com.lt.personal_stadiumbookingsystem.util.OkHttpUtil;
import com.lt.personal_stadiumbookingsystem.util.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class HistoryAdapter extends BaseRecyclerViewAdapter<Order, BaseViewHolder> {

    private ConfirmDialog mPayDialog;
    private ConfirmDialog mCancelDialog;

    public HistoryAdapter(int layoutResId, List<Order> DataSourceList) {
        super(layoutResId, DataSourceList);
    }

    @Override
    protected void initView(final BaseViewHolder vh, final Order ds, final int position) {
        vh.setText(R.id.tv_historyitem_gymname, ds.getGym_name());
        vh.setText(R.id.tv_historyitem_sitenumber, ds.getSite_number() + "号场地");
        vh.setText(R.id.tv_historyitem_date, "时间: " + ds.getOrder_date());
        vh.setText(R.id.tv_historyitem_timestart, ds.getOrder_timestart());
        vh.setText(R.id.tv_historyitem_timeend, ds.getOrder_timeend());

        showOrderState(vh, ds.getOrder_state());

        vh.setOnClickListener(R.id.btn_historyitem_pay, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, String> map = new HashMap<>();
                map.put("order_number", ds.getOrder_number());
                mPayDialog = ConfirmDialog.newInstance("支付订单");
                mPayDialog.show(((FragmentActivity) v.getContext()).getSupportFragmentManager());
                mPayDialog.setOnDialogClickListener(new BaseDialog.OnDialogClickListener() {
                    @Override
                    public void onAgreeClickListener() {
                        mPayDialog.dismiss();
                        OkHttpUtil.postFormRequest(UrlConstant.URL_ORDER_PAY, map, new OkHttpUtil.DataCallBack() {
                            @Override
                            public void requestSuccess(String result) throws Exception {
                                if (!"failure".equals(result)) {
                                    ToastUtil.showShortCenter(vh.itemView.getContext(), "支付成功");
                                    ds.setOrder_state(1);
                                    update(ds, position);
                                } else {
                                    ToastUtil.showShortCenter(vh.itemView.getContext(), "支付失败");
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
                        mPayDialog.dismiss();
                    }
                });
            }
        });

        vh.setOnClickListener(R.id.btn_historyitem_cancel, new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Map<String, String> map = new HashMap<>();
                map.put("order_number", ds.getOrder_number());
                mCancelDialog = ConfirmDialog.newInstance("取消订单");
                mCancelDialog.show(((FragmentActivity) v.getContext()).getSupportFragmentManager());
                mCancelDialog.setOnDialogClickListener(new BaseDialog.OnDialogClickListener() {
                    @Override
                    public void onAgreeClickListener() {
                        mCancelDialog.dismiss();
                        OkHttpUtil.postFormRequest(UrlConstant.URL_ORDER_CANCEL, map, new OkHttpUtil.DataCallBack() {
                            @Override
                            public void requestSuccess(String result) throws Exception {
                                LogUtil.wtf(result);
                                if (!"failure".equals(result)) {
                                    ToastUtil.showShortCenter(vh.itemView.getContext(), "取消成功");
                                    ds.setOrder_state(3);
                                    update(ds, position);
                                } else {
                                    ToastUtil.showShortCenter(vh.itemView.getContext(), "取消失败");
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
                        mCancelDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    protected void updateView(BaseViewHolder vh, Order order,
                              int position, List<Object> payloadList) {
        showOrderState(vh, order.getOrder_state());
    }

    private void showOrderState(BaseViewHolder vh, Integer orderState) {
        switch (orderState) {
            case 0:
                vh.setVisibility(R.id.btn_historyitem_cancel, View.VISIBLE);
                vh.setVisibility(R.id.btn_historyitem_pay, View.VISIBLE);
                vh.setText(R.id.tv_historyitem_orderstate, "待付款");
                break;
            case 1:
                vh.setVisibility(R.id.btn_historyitem_cancel, View.VISIBLE);
                vh.setVisibility(R.id.btn_historyitem_pay, View.GONE);
                vh.setText(R.id.tv_historyitem_orderstate, "待执行");
                break;
            case 2:
                vh.setVisibility(R.id.btn_historyitem_cancel, View.GONE);
                vh.setVisibility(R.id.btn_historyitem_pay, View.GONE);
                vh.setText(R.id.tv_historyitem_orderstate, "已完成");
                break;
            case 3:
                vh.setVisibility(R.id.btn_historyitem_cancel, View.GONE);
                vh.setVisibility(R.id.btn_historyitem_pay, View.GONE);
                vh.setText(R.id.tv_historyitem_orderstate, "已取消");
                break;
        }
    }
}

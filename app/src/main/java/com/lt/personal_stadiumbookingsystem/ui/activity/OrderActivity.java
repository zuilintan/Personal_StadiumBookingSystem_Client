package com.lt.personal_stadiumbookingsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseActivity;
import com.lt.personal_stadiumbookingsystem.base.BaseDialog;
import com.lt.personal_stadiumbookingsystem.constant.UrlConstant;
import com.lt.personal_stadiumbookingsystem.entity.Order;
import com.lt.personal_stadiumbookingsystem.ui.dialog.ConfirmDialog;
import com.lt.personal_stadiumbookingsystem.util.ActivityUtil;
import com.lt.personal_stadiumbookingsystem.util.GsonUtil;
import com.lt.personal_stadiumbookingsystem.util.OkHttpUtil;
import com.lt.personal_stadiumbookingsystem.util.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class OrderActivity extends BaseActivity {

    private static boolean mIsFromTimeActivity;
    private TextView mTvOrderGymname;
    private TextView mTvOrderSitenumber;
    private TextView mTvOrderDate;
    private TextView mTvOrderTimestart;
    private TextView mTvOrderTimeend;
    private TextView mTvOrderNumber;
    private TextView mTvOrderPrice;
    private Button mBtnOrderCancel;
    private Button mBtnOrderPay;
    private String mOrderJson;
    private Order mOrder;
    private ConfirmDialog mPayDialog;
    private ConfirmDialog mCancelDialog;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_order;
    }

    @Override
    protected void bindData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mOrderJson = bundle.getString("data1", "");
        }
    }

    @Override
    protected void bindView(View view) {
        mTvOrderGymname = view.findViewById(R.id.tv_order_gymname);
        mTvOrderSitenumber = view.findViewById(R.id.tv_order_sitenumber);
        mTvOrderDate = view.findViewById(R.id.tv_order_date);
        mTvOrderTimestart = view.findViewById(R.id.tv_order_timestart);
        mTvOrderTimeend = view.findViewById(R.id.tv_order_timeend);
        mTvOrderNumber = view.findViewById(R.id.tv_order_number);
        mTvOrderPrice = view.findViewById(R.id.tv_order_price);
        mBtnOrderCancel = view.findViewById(R.id.btn_order_cancel);
        mBtnOrderPay = view.findViewById(R.id.btn_order_pay);
    }

    @Override
    protected void initData() {
        mOrder = GsonUtil.jsonToObject(mOrderJson, Order.class);
    }

    @Override
    protected void initView(View view) {
        if (!mIsFromTimeActivity) {
            mBtnOrderPay.setVisibility(View.GONE);
            mBtnOrderCancel.setVisibility(View.GONE);
        }
        mTvOrderGymname.setText(mOrder.getGym_name());
        mTvOrderSitenumber.setText(mOrder.getSite_number() + "号场地");
        mTvOrderDate.setText(mOrder.getOrder_date());
        mTvOrderTimestart.setText(mOrder.getOrder_timestart());
        mTvOrderTimeend.setText(mOrder.getOrder_timeend());
        mTvOrderNumber.setText("订单号" + "\n" + mOrder.getOrder_number());
        mTvOrderPrice.setText(mOrder.getGym_price().intValue() + "硬币");
    }

    @Override
    protected void initListener() {
        mBtnOrderPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String orderNumber = mOrder.getOrder_number();
                final Map<String, String> map = new HashMap<>();
                map.put("order_number", orderNumber);
                mPayDialog = ConfirmDialog.newInstance("支付订单");
                mPayDialog.show(getSupportFragmentManager());
                mPayDialog.setOnDialogClickListener(new BaseDialog.OnDialogClickListener() {
                    @Override
                    public void onAgreeClickListener() {
                        mPayDialog.dismiss();
                        OkHttpUtil.postFormRequest(UrlConstant.URL_ORDER_PAY, map, new OkHttpUtil.DataCallBack() {
                            @Override
                            public void requestSuccess(String result) throws Exception {
                                if (!"failure".equals(result)) {
                                    ToastUtil.showShortCenter(OrderActivity.this, "支付成功");
                                    MainActivity.actionStart(OrderActivity.this, 1);
                                    ActivityUtil.finishAll();
                                } else {
                                    ToastUtil.showShortCenter(OrderActivity.this, "支付失败");
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

        mBtnOrderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderNumber = mOrder.getOrder_number();
                final Map<String, String> map = new HashMap<>();
                map.put("order_number", orderNumber);
                mCancelDialog = ConfirmDialog.newInstance("取消订单");
                mCancelDialog.show(getSupportFragmentManager());
                mCancelDialog.setOnDialogClickListener(new BaseDialog.OnDialogClickListener() {
                    @Override
                    public void onAgreeClickListener() {
                        mCancelDialog.dismiss();
                        OkHttpUtil.postFormRequest(UrlConstant.URL_ORDER_CANCEL, map, new OkHttpUtil.DataCallBack() {
                            @Override
                            public void requestSuccess(String result) throws Exception {
                                if (!"failure".equals(result)) {
                                    ToastUtil.showShortCenter(OrderActivity.this, "取消成功");
                                    ActivityUtil.finish(OrderActivity.this);
                                } else {
                                    ToastUtil.showShortCenter(OrderActivity.this, "取消失败");
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

    public static void actionStart(Context context, String orderJson) {
        if (context instanceof TimeActivity) {
            mIsFromTimeActivity = true;
        } else {
            mIsFromTimeActivity = false;
        }
        Bundle bundle = new Bundle();
        bundle.putString("data1", orderJson);
        Intent intent = new Intent();
        intent.setClass(context, OrderActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);//默认包含android.intent.category.LAUNCHER
    }
}

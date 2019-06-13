package com.lt.personal_stadiumbookingsystem.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.lt.personal_stadiumbookingsystem.R;

/**
 * @作者: LinTan
 * @日期: 2019/5/15 10:23
 * @版本: 1.0
 * @描述: //DialogFragment的封装类。
 * 1.0: Initial Commit
 */

public abstract class BaseDialog extends DialogFragment {

    public Context mContext;
    private int mLayoutResId;
    private int mWidth;//宽度
    private int mHeight;//高度
    private int mAnimStyle = 0;//过场动画
    private int mHorizontalMargin = 64;//左右边距
    private float mDimAmount = 0.6F;//外围昏暗度
    private boolean mOutCancel = true;//是否外部取消
    private boolean mBottomShow = false;//是否底部显示
    private View mAgreeView;
    private View mDenyView;
    protected OnDialogClickListener mOnDialogClickListener;//2.声明回调事件

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme);
        mLayoutResId = setLayoutResId();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(mLayoutResId, container, false);
        mAgreeView = view.findViewById(setAgreeButtonId());
        mDenyView = view.findViewById(setDenyButtonId());
        initView(BaseViewHolder.newInstance(view), this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAgreeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDialogClickListener != null) {
                    mOnDialogClickListener.onAgreeClickListener();
                }
            }
        });

        mDenyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDialogClickListener != null) {
                    mOnDialogClickListener.onCancelClickListener();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    public void setOnDialogClickListener(OnDialogClickListener dialogClickListener) {
        mOnDialogClickListener = dialogClickListener;
    }//3.对外提供设置事件回调的接口

    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();

            layoutParams.dimAmount = mDimAmount;//设置外围昏暗度

            if (mAnimStyle != 0) {
                window.setWindowAnimations(mAnimStyle);
            }

            if (mBottomShow) {
                layoutParams.gravity = Gravity.BOTTOM;
            }

            if (mWidth == 0) {
                layoutParams.width = getScreenWidth(getContext()) - 2 * dp2px(getContext(),
                                                                              mHorizontalMargin);
            } else {
                layoutParams.width = dp2px(getContext(), mWidth);
            }

            if (mHeight == 0) {
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                layoutParams.height = dp2px(getContext(), mHeight);
            }

            window.setAttributes(layoutParams);
        }
        setCancelable(mOutCancel);//设置外部取消
    }

    public BaseDialog setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
        return this;
    }//设置宽高

    public BaseDialog setHorizontalMargin(int horizontalMargin) {
        mHorizontalMargin = horizontalMargin;
        return this;
    }//设置左右边距

    public BaseDialog setBottomShow(boolean bottomShow) {
        mBottomShow = bottomShow;
        return this;
    }//设置是否底部显示

    public BaseDialog setOutCancel(boolean outCancel) {
        mOutCancel = outCancel;
        return this;
    }//设置是否允许点击外围取消

    public BaseDialog setDimAmout(@FloatRange(from = 0, to = 1) float dimAmount) {
        mDimAmount = dimAmount;
        return this;
    }//设置外围昏暗度

    public BaseDialog setAnimStyle(@StyleRes int animStyle) {
        mAnimStyle = animStyle;
        return this;
    }//设置过场动画

    //显示Dialog
    public BaseDialog show(FragmentManager manager) {
        super.show(manager, String.valueOf(System.currentTimeMillis()));
        return this;
    }

    public int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay()
                     .getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }//获得屏幕宽度

    public int dp2px(Context context, float dpValue) {
        float density = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                                  dpValue,
                                                  context.getResources().getDisplayMetrics());
        return (int) (density + 0.5F);
    }//dp转px

    public interface OnDialogClickListener {
        void onAgreeClickListener();

        void onCancelClickListener();

    }//1.定义回调事件接口

    protected abstract int setLayoutResId();

    protected abstract int setAgreeButtonId();

    protected abstract int setDenyButtonId();

    protected abstract void initView(BaseViewHolder vh, BaseDialog dialog);
}

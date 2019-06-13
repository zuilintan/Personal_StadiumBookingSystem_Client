package com.lt.personal_stadiumbookingsystem.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @作者: LinTan
 * @日期: 2019/4/14 20:58
 * @版本: 1.0
 * @描述: //Fragment的封装类。
 * 1.0: Initial Commit
 */

public abstract class BaseFragment extends Fragment {
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindData(savedInstanceState);//绑定数据，eg: 接收bundle数据
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutResId(), container, false);
        bindView(view);//绑定视图，eg: findViewById()
        initData();//初始化数据，eg: 将bundle传过来的数据进行处理
        initView(view);//初始化视图，eg: textView.setText()
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();//初始化监听器，eg: setOnclickListener()
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.wtf("BaseFragment", getClass().getSimpleName());
    }//注意，使用show()和hide()时，Fragment不会回调onPause()

    protected abstract int setLayoutResId();

    protected abstract void bindData(Bundle savedInstanceState);

    protected abstract void bindView(View view);

    protected abstract void initData();

    protected abstract void initView(View view);

    protected abstract void initListener();
}

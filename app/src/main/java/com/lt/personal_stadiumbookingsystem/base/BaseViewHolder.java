package com.lt.personal_stadiumbookingsystem.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @作者: LinTan
 * @日期: 2018/12/12 12:10
 * @版本: 1.0
 * @描述: //RecyclerView的封装类。注意引入依赖。
 * 源址: https://blog.csdn.net/a_zhon/article/details/66971369
 * 1.0: Initial Commit
 * <p>
 * implementation 'com.android.support:recyclerview-v7:28.0.0'
 */

public class BaseViewHolder extends ViewHolder {

    private SparseArray<View> mSparseArray;//Key为View的Id，Value为View对象
    public View mItemView;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        mSparseArray = new SparseArray<>();
    }

    public static BaseViewHolder newInstance(View view) {
        return new BaseViewHolder(view);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(int viewId) {
        View view = mSparseArray.get(viewId);
        if (view == null) {
            view = mItemView.findViewById(viewId);
            mSparseArray.put(viewId, view);
        }
        return (T) view;
    }//findViewById，并复用

    public void setText(int viewId, CharSequence text) {
        TextView view = findViewById(viewId);
        view.setText(text);
    }//设置文本

    public String getText(int viewId) {
        TextView view = findViewById(viewId);
        return view.getText().toString();
    }//获取文本

    public void setTextColor(int viewId, int colorId) {
        TextView view = findViewById(viewId);
        view.setTextColor(colorId);
    }//设置文本颜色

    public void setImageResource(int viewId, int resId) {
        ImageView view = findViewById(viewId);
        view.setImageResource(resId);
    }//设置图片资源

    public void setBackgroundResource(int viewId, int resId) {
        View view = findViewById(viewId);
        view.setBackgroundResource(resId);
    }//设置背景资源

    public void setBackgroundColor(int viewId, int colorId) {
        View view = findViewById(viewId);
        view.setBackgroundColor(colorId);
    }//设置背景颜色

    public void setVisibility(int viewId, int visibility) {
        View view = findViewById(viewId);
        view.setVisibility(visibility);
    }//设置显隐

    public void setOnClickListener(int viewId,
                                   View.OnClickListener listener) {
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
    }//设置点击事件
}

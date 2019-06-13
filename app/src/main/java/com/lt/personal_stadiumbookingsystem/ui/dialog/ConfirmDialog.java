package com.lt.personal_stadiumbookingsystem.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseDialog;
import com.lt.personal_stadiumbookingsystem.base.BaseViewHolder;

public class ConfirmDialog extends BaseDialog {
    private String mMessage;

    public static ConfirmDialog newInstance(String message) {
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        ConfirmDialog fragment = new ConfirmDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mMessage = bundle.getString("message");
        }
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.dialog_confirm;
    }

    @Override
    protected int setAgreeButtonId() {
        return R.id.tv_confirm_go;
    }

    @Override
    protected int setDenyButtonId() {
        return R.id.tv_confirm_cancel;
    }

    @Override
    protected void initView(BaseViewHolder vh, final BaseDialog dialog) {
        vh.setText(R.id.tv_confirm_title, "提示");
        vh.setText(R.id.tv_confirm_message, "确定要" + mMessage + "吗?");
    }
}

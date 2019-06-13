package com.lt.personal_stadiumbookingsystem.ui.adapter;

import android.widget.ImageView;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseRecyclerViewAdapter;
import com.lt.personal_stadiumbookingsystem.base.BaseViewHolder;
import com.lt.personal_stadiumbookingsystem.entity.Gym;
import com.lt.personal_stadiumbookingsystem.util.GlideUtil;

import java.util.List;

public class GymAdapter extends BaseRecyclerViewAdapter<Gym, BaseViewHolder> {

    public GymAdapter(int layoutResId, List<Gym> DataSourceList) {
        super(layoutResId, DataSourceList);
    }

    @Override
    protected void initView(BaseViewHolder vh, Gym ds, int position) {
        vh.setText(R.id.tv_gymitem_name, ds.getGym_name());
        if (ds.getGym_managestate() == 0) {
            vh.setText(R.id.tv_gymitem_managestate, "维护中");
        } else {
            vh.setText(R.id.tv_gymitem_managestate, "");
        }
        ImageView imageView = vh.findViewById(R.id.iv_gymitem_img);
        switch (ds.getGym_name()) {
            case "篮球馆":
                GlideUtil.loadImage(vh.itemView.getContext(),
                                    R.drawable.ic_gym_basketball,
                                    imageView);
                break;
            case "台球馆":
                GlideUtil.loadImage(vh.itemView.getContext(),
                                    R.drawable.ic_gym_billiardball,
                                    imageView);
                break;
            case "乒乓球馆":
                GlideUtil.loadImage(vh.itemView.getContext(),
                                    R.drawable.ic_gym_tabletennis,
                                    imageView);
                break;
            case "羽毛球馆":
                GlideUtil.loadImage(vh.itemView.getContext(),
                                    R.drawable.ic_gym_badminton,
                                    imageView);
                break;
        }
    }

    @Override
    protected void updateView(BaseViewHolder vh, Gym gym, int position, List<Object> payloadList) {

    }
}

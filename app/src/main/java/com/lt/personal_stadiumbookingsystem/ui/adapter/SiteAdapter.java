package com.lt.personal_stadiumbookingsystem.ui.adapter;

import android.widget.ImageView;

import com.lt.personal_stadiumbookingsystem.R;
import com.lt.personal_stadiumbookingsystem.base.BaseRecyclerViewAdapter;
import com.lt.personal_stadiumbookingsystem.base.BaseViewHolder;
import com.lt.personal_stadiumbookingsystem.entity.Site;
import com.lt.personal_stadiumbookingsystem.util.GlideUtil;

import java.util.List;

public class SiteAdapter extends BaseRecyclerViewAdapter<Site, BaseViewHolder> {

    public SiteAdapter(int layoutResId, List<Site> DataSourceList) {
        super(layoutResId, DataSourceList);
    }

    @Override
    protected void initView(BaseViewHolder vh, Site ds, int position) {
        vh.setText(R.id.tv_site_number, ds.getGym_name() + ds.getSite_number() + "号场");
        ImageView imageView = vh.findViewById(R.id.iv_site_img);
        switch (ds.getGym_name()) {
            case "篮球馆":
                GlideUtil.loadImage(vh.itemView.getContext(), R.drawable.ic_gym_basketball, imageView);
                break;
            case "台球馆":
                GlideUtil.loadImage(vh.itemView.getContext(), R.drawable.ic_gym_billiardball, imageView);
                break;
            case "乒乓球馆":
                GlideUtil.loadImage(vh.itemView.getContext(), R.drawable.ic_gym_tabletennis, imageView);
                break;
            case "羽毛球馆":
                GlideUtil.loadImage(vh.itemView.getContext(), R.drawable.ic_gym_badminton, imageView);
                break;
        }
    }

    @Override
    protected void updateView(BaseViewHolder vh, Site site, int position, List<Object> payloadList) {
    }
}

package com.lt.personal_stadiumbookingsystem.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

import com.lt.personal_stadiumbookingsystem.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2018/12/12 12:10
 * @版本: 1.3
 * @描述: //RecyclerView的封装类。注意引入依赖。
 * 源址: https://blog.csdn.net/a_zhon/article/details/66971369
 * 1.0: Initial Commit
 * 1.1: 替换数据操作的视图更改方式，由notifyDataSetChanged()改为notifyItemXXXX()，提高性能
 * ***  注意: notifyItemXXXX()修改视图不会重新执行onBindViewHolder()，
 * ***       导致了position错乱，所以需要调用notifyItemRangeChanged()重新计算position
 * 1.2: 修复addHeadView后，数据操作时position异常问题
 * 1.3: 修复LayoutManager为网格或瀑布流时，HeadView与FootView不能占满一行的问题
 * 1.4: 优化RecyclerView性能，将ItemView的findViewById和点击事件的初始化移至onCreateViewHolder()
 * 1.5: 发现Bug，1.4版优化后会导致数据源随RecyclerView的ItemView复用，故Return
 * 1.6: 发现Bug，用item中的view其点击事件更新该view，会出现操作错位问题，
 * ***  eg: 在position为0的item中点击按钮，实现该按钮显隐操作，却会在最后一个position上实现相应动作
 * 1.7: 解决RecyclerView刷新列表Item闪烁问题
 * 1.8: 优化item内部控件刷新机制，使用onBindViewHolder的payloads
 * <p>
 * implementation 'com.android.support:recyclerview-v7:28.0.0'
 */

public abstract class BaseRecyclerViewAdapter<DS, VH extends ViewHolder> extends Adapter<VH>
        implements OnClickListener, OnLongClickListener {//DS:DataSource，VH:ViewHolder
    private final int VIEW_TYPE_ITEM = 1008601;
    private final int VIEW_TYPE_HEAD = 1008602;
    private final int VIEW_TYPE_FOOT = 1008603;
    private int mItemViewId;
    private int mHeadViewId = -1;
    private int mFootViewId = -1;
    private List<DS> mDataSourceList;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    public BaseRecyclerViewAdapter(@LayoutRes int layoutResId, List<DS> DataSourceList) {
        if (layoutResId == 0) {
            throw new NullPointerException("请设置item资源id");
        } else {
            mItemViewId = layoutResId;
        }
        if (DataSourceList == null) {
            mDataSourceList = new ArrayList<>();
        } else {
            mDataSourceList = DataSourceList;
        }
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(viewGroup.getContext())
                                     .inflate(mItemViewId, viewGroup, false);
                view.setOnClickListener(this);
                view.setOnLongClickListener(this);
                break;
            case VIEW_TYPE_HEAD:
                view = LayoutInflater.from(viewGroup.getContext())
                                     .inflate(mHeadViewId, viewGroup, false);
                break;
            case VIEW_TYPE_FOOT:
                view = LayoutInflater.from(viewGroup.getContext())
                                     .inflate(mFootViewId, viewGroup, false);
                break;
        }
        return (VH) BaseViewHolder.newInstance(view);
    }//创建视图管理器

    @Override
    public void onBindViewHolder(@NonNull VH viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_ITEM:
                if (mHeadViewId == -1) {
                    viewHolder.itemView.setTag(position);
                    initView(viewHolder, mDataSourceList.get(position),
                             position);
                }//无HeadView时的处理
                else {
                    viewHolder.itemView.setTag(position - 1);
                    initView(viewHolder, mDataSourceList.get(position - 1),
                             (position - 1));
                }//有HeadView时的处理
                break;
            case VIEW_TYPE_HEAD:
                break;
            case VIEW_TYPE_FOOT:
                break;
        }
    }//绑定数据到视图

    @Override
    public void onBindViewHolder(@NonNull VH viewHolder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {//true，说明是更新整个item。false，仅更新item中指定的View
            onBindViewHolder(viewHolder, position);
        } else {
            if (mHeadViewId == -1) {
                updateView(viewHolder, mDataSourceList.get(position), position, payloads);
            }//无HeadView时的处理
            else {
                updateView(viewHolder, mDataSourceList.get(position - 1), position - 1, payloads);
            }//有HeadView时的处理
        }
    }//绑定数据到视图，有效负载

    @Override
    public long getItemId(int position) {
        return position;
    }//为每个Item绑定唯一的Id，需搭配adapter.setHasStableIds(true)

    @Override
    public int getItemCount() {
        if (mHeadViewId != -1 && mFootViewId != -1) {
            return mDataSourceList.size() + 2;
        }//若同时添加HeadView与FootView，数据源大小+2
        if (mHeadViewId != -1 || mFootViewId != -1) {
            return mDataSourceList.size() + 1;
        }//若仅添加HeadView或FootView其一，数据源大小+1
        return mDataSourceList.size();//仅有ItemView，数据源大小正常
    }//获取数据源的大小

    @Override
    public int getItemViewType(int position) {
        int viewType = VIEW_TYPE_ITEM;
        if (position == 0 && mHeadViewId != -1) {
            viewType = VIEW_TYPE_HEAD;
        }//判断是否添加了HeadView
        if (position == getItemCount() - 1 && mFootViewId != -1) {
            viewType = VIEW_TYPE_FOOT;
        }//判断是否添加了FootView
        return viewType;
    }//获取ItemView的类型

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager viewLayoutManager = recyclerView.getLayoutManager();
        if (viewLayoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) viewLayoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (getItemViewType(position)) {
                        case VIEW_TYPE_HEAD:
                            return gridLayoutManager.getSpanCount();
                        case VIEW_TYPE_FOOT:
                            return gridLayoutManager.getSpanCount();
                        default:
                            return 1;
                    }
                }
            });
        }
    }//当LayoutManager为GridLayoutManager时，让HeadView与FootView占满所在行

    @Override
    public void onViewAttachedToWindow(@NonNull VH viewHolder) {
        super.onViewAttachedToWindow(viewHolder);
        ViewGroup.LayoutParams viewGroupLayoutParams = viewHolder.itemView.getLayoutParams();
        if (viewGroupLayoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams sglManagerLayoutParams
                    = (StaggeredGridLayoutManager.LayoutParams) viewGroupLayoutParams;
            switch (getItemViewType(viewHolder.getLayoutPosition())) {
                case VIEW_TYPE_HEAD:
                    sglManagerLayoutParams.setFullSpan(true);
                case VIEW_TYPE_FOOT:
                    sglManagerLayoutParams.setFullSpan(true);
            }
        }
    }//当LayoutManager为StaggeredGridLayoutManager时，让HeadView与FootView占满所在行

    public void addHeadView(@LayoutRes int layoutResId) {
        mHeadViewId = layoutResId;
    }//添加HeadView

    public void addFootView(@LayoutRes int layoutResId) {
        mFootViewId = layoutResId;
    }//添加FootView

    public void add(DS dataSource, int position) {
        int size = mDataSourceList.size();
        mDataSourceList.add(position, dataSource);
        if (mHeadViewId == -1) {
            notifyItemInserted(position);
            notifyItemRangeChanged(size, 0);
        } else {
            notifyItemInserted(position + 1);
            notifyItemRangeChanged(size + 1, 0);
        }
    }//添加数据源到指定位置，并更新item

    public void del(int position) {
        int size = mDataSourceList.size();
        mDataSourceList.remove(position);
        if (mHeadViewId == -1) {
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,
                                   size - position);
        } else {
            notifyItemRemoved(position + 1);
            notifyItemRangeChanged(position + 1,
                                   size - position);
        }
    }//删除指定位置的数据源，并更新item

    public void update(DS dataSource, int position) {
        mDataSourceList.set(position, dataSource);
        if (mHeadViewId == -1) {
            notifyItemChanged(position, 1);
        } else {
            notifyItemChanged(position + 1, 1);
        }
    }//更新指定位置的数据源，并可以单独更新其中的某个View。需与update()联用

    public void clear() {
        int size = mDataSourceList.size();
        mDataSourceList.clear();
        if (mHeadViewId == -1) {
            notifyItemRangeRemoved(0, size);
            notifyItemRangeChanged(0, size);
        } else {
            notifyItemRangeRemoved(1, size);
            notifyItemRangeChanged(1, size);
        }
    }//清空数据源集合，并更新item

    public void refresh(List<DS> dataSourceList) {
        int size = mDataSourceList.size();
        if (mHeadViewId == -1) {
            mDataSourceList.clear();
            notifyItemRangeRemoved(0, size);
            notifyItemRangeChanged(0, size);
            mDataSourceList.addAll(dataSourceList);
            notifyItemRangeInserted(0, dataSourceList.size());
            notifyItemRangeChanged(0, dataSourceList.size());
        } else {
            mDataSourceList.clear();
            notifyItemRangeRemoved(1, size);
            notifyItemRangeChanged(1, size);
            mDataSourceList.addAll(dataSourceList);
            notifyItemRangeInserted(1, dataSourceList.size());
            notifyItemRangeChanged(1, dataSourceList.size());
        }
    }//刷新数据源集合，并更新item

    public void loadMore(List<DS> dataSourceList) {
        int size = mDataSourceList.size();
        mDataSourceList.addAll(dataSourceList);
        if (mHeadViewId == -1) {
            notifyItemRangeInserted(size, dataSourceList.size());
            notifyItemRangeChanged(size, dataSourceList.size());
        } else {
            notifyItemRangeInserted(size + 1, dataSourceList.size());
            notifyItemRangeChanged(size + 1, dataSourceList.size());
        }
    }//加载更多数据到数据源集合，并更新item

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }//item点击监听器

    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }//item长点击监听器

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }//点击回调

    @Override
    public boolean onLongClick(View view) {
        boolean b = false;
        if (mItemLongClickListener != null) {
            b = mItemLongClickListener.onItemLongClick(view, (Integer) view.getTag());
        }
        return b;
    }//长点击回调

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }//item点击监听器的接口

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);

    }//item长点击监听器的接口

    protected abstract void initView(VH vh, DS ds, int position);

    protected abstract void updateView(VH vh, DS ds, int position, List<Object> payloadList);
}

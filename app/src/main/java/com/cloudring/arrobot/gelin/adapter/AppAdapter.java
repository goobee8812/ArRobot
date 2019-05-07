package com.cloudring.arrobot.gelin.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudring.arrobot.gelin.R;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;
import com.cloudring.arrobot.gelin.utils.Constant;
import com.cloudring.arrobot.gelin.utils.imageloader.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Goobee_yuer on 2018/8/14.
 */
public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    private List<AppItem> mAppList = new ArrayList<>();

    private int type = 0; //0：默认  1：打开游戏  2：下载游戏

    // 申明一个点击事件接口变量 下载
    private OnItemClickCallback downloadCallback = null;

    // 申明一个点击事件接口变量 收藏
    private OnItemClickCallback collectionCallback = null;

    public AppAdapter(List<AppItem> appList, OnItemClickCallback downloadCallback,OnItemClickCallback collectionCallback) {
        this.mAppList = appList;
        this.downloadCallback = downloadCallback;
        this.collectionCallback = collectionCallback;
    }

    public void setType(int type){
        this.type = type;
    }

    public void setDataChanged(List<AppItem> list){
        if (this.mAppList != null){
            this.mAppList.clear();
        }
        mAppList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        AppItem item = mAppList.get(position);
     //   LogUtil.e("CategoryId = "+item.getCategoryId());
        holder.nameTv.setText(item.getFileName());
        if (type == 0){
            holder.mCheckBox.setVisibility(View.GONE);
        }else if (type == 1){
            holder.collectionStr.setVisibility(View.GONE);
            holder.mCheckBox.setVisibility(View.VISIBLE);
            holder.downloadStr.setText("打开游戏");
            if (item.isSelect()) {
                holder.mCheckBox.setImageResource(R.mipmap.ic_checked);
            } else {
                holder.mCheckBox.setImageResource(R.mipmap.ic_uncheck);
            }
        }else if (type == 2) {
            holder.collectionStr.setVisibility(View.GONE);
            holder.mCheckBox.setVisibility(View.VISIBLE);
            holder.downloadStr.setText("下载");
            if (item.isSelect()) {
                holder.mCheckBox.setBackgroundResource(R.mipmap.ic_checked);
            } else {
                holder.mCheckBox.setBackgroundResource(R.mipmap.ic_uncheck);
            }
        }
        ImageUtils.getInstance().display(holder.appImg, Constant.BaseHttp+item.getIcon1(),R.drawable.ar_robot_img_default,R.drawable.ar_robot_img_default);
        holder.downloadStr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadCallback.onClick(view, mAppList.get(position),position);
            }
        });

        holder.collectionStr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectionCallback.onClick(view, mAppList.get(position),position);
            }
        });

        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(holder.getAdapterPosition(), mAppList);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClickListener(int pos,List<AppItem> myLiveList);
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appImg;
        TextView nameTv;
        TextView downloadStr;
        TextView collectionStr;
        ImageView mCheckBox;
        public ViewHolder(View itemView) {
            super(itemView);
            appImg = (ImageView) itemView.findViewById(R.id.id_img);
            nameTv = itemView.findViewById(R.id.id_name_tv);
            downloadStr = (TextView) itemView.findViewById(R.id.id_down_tv);
            collectionStr = (TextView) itemView.findViewById(R.id.id_collection_tv);
            mCheckBox = (ImageView) itemView.findViewById(R.id.check_box);
        }
    }
}

package com.cloudring.arrobot.gelin.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudring.arrobot.gelin.R;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;


import java.util.List;

/**
 * Created by Goobee_yuer on 2018/8/14.
 */
public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private List<AppItem> mAppList;

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
        this.mAppList = list;
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
        holder.nameTv.setText(item.getFileName());
        if (type == 0){
        }else if (type == 1){
            holder.collectionStr.setVisibility(View.GONE);
            holder.downloadStr.setText("打开游戏");
        }else if (type == 2) {
            holder.collectionStr.setVisibility(View.GONE);
            holder.downloadStr.setText("下载");
        }

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
        public ViewHolder(View itemView) {
            super(itemView);
            appImg = (ImageView) itemView.findViewById(R.id.id_img);
            nameTv = itemView.findViewById(R.id.id_name_tv);
            downloadStr = (TextView) itemView.findViewById(R.id.id_down_tv);
            collectionStr = (TextView) itemView.findViewById(R.id.id_collection_tv);
        }
    }
}

package com.cloudring.arrobot.gelin.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudring.arrobot.gelin.R;
import com.cloudring.arrobot.gelin.bean.AppItem;


import java.util.List;

/**
 * Created by Goobee_yuer on 2018/8/14.
 */
public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private List<AppItem> mAppList;

    // 申明一个点击事件接口变量 下载
    private OnItemClickCallback downloadCallback = null;

    // 申明一个点击事件接口变量 收藏
    private OnItemClickCallback collectionCallback = null;

    public AppAdapter(List<AppItem> appList, OnItemClickCallback downloadCallback,OnItemClickCallback collectionCallback) {
        this.mAppList = appList;
        this.downloadCallback = downloadCallback;
        this.collectionCallback = collectionCallback;
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

        holder.downloadStr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadCallback.onClick(view, mAppList.get(position));
            }
        });

        holder.collectionStr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectionCallback.onClick(view, mAppList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appImg;
        TextView downloadStr;
        TextView collectionStr;
        public ViewHolder(View itemView) {
            super(itemView);
            appImg = (ImageView) itemView.findViewById(R.id.id_img);
            downloadStr = (TextView) itemView.findViewById(R.id.id_down_tv);
            collectionStr = (TextView) itemView.findViewById(R.id.id_collection_tv);
        }
    }
}

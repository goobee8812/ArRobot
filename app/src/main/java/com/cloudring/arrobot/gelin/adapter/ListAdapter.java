package com.cloudring.arrobot.gelin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cloudring.arrobot.gelin.R;
import com.cloudring.arrobot.gelin.mvp.modle.MainType;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<MainType> mDatas;
    private OnItemClickCallback clickCallback;

    public ListAdapter(Context context, List<MainType> mDatas, OnItemClickCallback clickCallback) {
        this.context = context;
        this.mDatas = mDatas;
        this.clickCallback = clickCallback;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
//        判断是否有缓存
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            //得到缓存的布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MainType mainType = mDatas.get(position);
        viewHolder.itemTv.setText(mainType.getCategroyName());
        viewHolder.itemTv.setOnClickListener(v -> clickCallback.onClick(v,mDatas.get(position),position));
        return convertView;
    }

    private final class ViewHolder {
        TextView itemTv;//名字

        public ViewHolder(View view) {
            itemTv = view.findViewById(R.id.id_item_tv);
        }
    }

}

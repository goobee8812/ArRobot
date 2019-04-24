package com.cloudring.arrobot.gelin.adapter;

import android.view.View;

public interface OnItemClickCallback<T> {
    // 点击事件
    void onClick(View view, T info,int position);
//    // 长按事件
//    void onLongClick(View view, T info);
}

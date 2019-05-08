package com.cloudring.arrobot.gelin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cloudring.arrobot.gelin.R;
import com.cloudring.arrobot.gelin.mvp.category.ResultActivity;
import com.cloudring.arrobot.gelin.mvp.modle.MainType;
import com.cloudring.arrobot.gelin.utils.Constant;
import com.cloudring.arrobot.gelin.utils.ContantsUtil;
import com.cloudring.arrobot.gelin.utils.imageloader.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.RViewHolder>{

    private List<MainType> types = new ArrayList<>();
    private Context activity;
    private LayoutInflater inflater;
    private int selectedPosition = 0; //默认一个参数

    public TypeAdapter(Activity activity){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    public void setData(List<MainType> types){
        this.types = types;
        notifyDataSetChanged();
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.item_type, parent, false);
        return new RViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RViewHolder holder, final int position){
        final MainType item = types.get(position);
        ImageUtils.getInstance().display(holder.ivType, Constant.BaseHttp+item.getIcon(), R.drawable.ar_robot_jiyi, R.drawable.ar_robot_jiyi);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(activity, ResultActivity.class);
                intent.putExtra(ContantsUtil.MAIN_TYPE, item);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return types == null ? 0 : types.size();
    }

    public void changeState(int pos){
        selectedPosition = pos;
        notifyDataSetChanged();
    }

    class RViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivType;

        public RViewHolder(View itemView){
            super(itemView);
            ivType = (ImageView) itemView.findViewById(R.id.type_iv);
        }
    }
}
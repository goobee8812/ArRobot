package com.cloudring.arrobot.gelin.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudring.arrobot.gelin.R;
import com.cloudring.arrobot.gelin.mvp.search.SearchActivity;

public class KeyboardAdapter extends RecyclerView.Adapter<KeyboardAdapter.RViewHolder>{

    private String[] keys;
    private SearchActivity activity;
    private LayoutInflater inflater;
    private int selectedPosition = -1; //默认一个参数

    public KeyboardAdapter(Activity activity, String[] keys){
        this.activity = (SearchActivity) activity;
        inflater = LayoutInflater.from(activity);
        this.keys = keys;
        notifyDataSetChanged();
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.item_keyboard, parent, false);
        return new RViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RViewHolder holder, final int position){
        final String key = keys[position];
        holder.tvKey.setText(key);
        if(selectedPosition == position){
            holder.tvKey.setBackgroundResource(R.drawable.key_bg);
        }else{
            holder.tvKey.setBackgroundResource(0);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                changeState(position);
                activity.setKey(key);
            }
        });
    }

    @Override
    public int getItemCount(){
        return keys == null ? 0 : keys.length;
    }

    public void changeState(int pos){
        selectedPosition = pos;
        notifyDataSetChanged();
    }

    class RViewHolder extends RecyclerView.ViewHolder{
        private TextView tvKey;

        public RViewHolder(View itemView){
            super(itemView);
            tvKey = (TextView) itemView.findViewById(R.id.key_tv);
        }
    }
}
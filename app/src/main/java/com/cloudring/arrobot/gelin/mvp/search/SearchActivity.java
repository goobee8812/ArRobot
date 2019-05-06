package com.cloudring.arrobot.gelin.mvp.search;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.cloudring.arrobot.gelin.R;
import com.cloudring.arrobot.gelin.adapter.KeyboardAdapter;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;
import com.cloudring.arrobot.gelin.utils.LogUtil;
import com.cloudring.arrobot.gelin.utils.MyUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends MvpAppCompatActivity implements SearchView{

    @BindView(R.id.id_normal_recycler_view)
    RecyclerView normalRecycler;
    @BindView(R.id.keyboard_recycler_view)
    RecyclerView recyclerKey;
    @BindView(R.id.id_back_iv)
    ImageView backImg;
    @BindView(R.id.id_top_title_tv)
    TextView titleTv;
    @BindView(R.id.search_et)
    EditText etSearch;
    @BindView(R.id.search_iv)
    ImageView ivSearch;
    private String keyString = "";

    private String[] keys = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private KeyboardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        titleTv.setText("搜索");
        MyUtil.disableShowSoftInput(etSearch);
        GridLayoutManager localGridLayoutManager = new GridLayoutManager(this, 5);
        recyclerKey.setLayoutManager(localGridLayoutManager);
        adapter = new KeyboardAdapter(this, keys);
        recyclerKey.setAdapter(adapter);
    }

    @Override
    public void showMsg(String msg){
    }

    @Override
    public void showMsg(int msg){
    }

    @Override
    public void loadFail(){
    }

    @Override
    public void refreshList(List<AppItem> list){
    }

    @OnClick({R.id.id_back_iv, R.id.search_iv})
    public void onClickEvent(View v){
        switch(v.getId()){
            case R.id.id_back_iv:
                finish();
                break;
            case R.id.search_iv://搜索
                break;
            default:
                break;
        }
    }

    public void setKey(String key){
        LogUtil.LogShow("key = " + key, LogUtil.ERROR);
        keyString = keyString + key;
        LogUtil.LogShow("keyString = " + keyString, LogUtil.ERROR);
        etSearch.setText(keyString);
        etSearch.setSelection(keyString.length());
    }

}

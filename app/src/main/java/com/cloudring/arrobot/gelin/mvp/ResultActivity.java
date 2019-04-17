package com.cloudring.arrobot.gelin.mvp;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.cloudring.arrobot.gelin.R;
import com.cloudring.arrobot.gelin.adapter.AppAdapter;
import com.cloudring.arrobot.gelin.adapter.OnItemClickCallback;
import com.cloudring.arrobot.gelin.bean.AppItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends MvpAppCompatActivity implements ResultView{


    @BindView(R.id.id_normal_recycler_view)
    RecyclerView normalRecycler;
    @BindView(R.id.id_hot_recycler_view)
    RecyclerView hotRecycler;


    private AppAdapter normalAdapter;
    private AppAdapter hotAdapter;

    private List<AppItem> normalList;
    private List<AppItem> hotList;

    @InjectPresenter
    public ResultPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resutl);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        normalList = new ArrayList<>();
        //测试
        normalList.add(new AppItem("001","www.baidu.com"));
        normalList.add(new AppItem("002","www.baidu.com"));
        normalList.add(new AppItem("003","www.baidu.com"));
        normalList.add(new AppItem("004","www.baidu.com"));
        normalList.add(new AppItem("005","www.baidu.com"));
        normalList.add(new AppItem("006","www.baidu.com"));
        normalList.add(new AppItem("007","www.baidu.com"));
        normalList.add(new AppItem("008","www.baidu.com"));
        normalList.add(new AppItem("009","www.baidu.com"));
        hotList = new ArrayList<>();
        hotList.add(new AppItem("001","www.ww.ww.www"));
        hotList.add(new AppItem("002","www.ww.ww.www"));
        hotList.add(new AppItem("003","www.ww.ww.www"));
        hotList.add(new AppItem("004","www.ww.ww.www"));
        hotList.add(new AppItem("005","www.ww.ww.www"));
        normalAdapter = new AppAdapter(normalList, new OnItemClickCallback<AppItem>() {
            @Override
            public void onClick(View view, AppItem info) {
                Toast.makeText(ResultActivity.this, "点击了下载" + info.getId(), Toast.LENGTH_SHORT).show();
            }
        }, new OnItemClickCallback<AppItem>() {

            @Override
            public void onClick(View view, AppItem info) {
                Toast.makeText(ResultActivity.this, "点击了收藏" + info.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        hotAdapter = new AppAdapter(hotList, new OnItemClickCallback<AppItem>() {
            @Override
            public void onClick(View view, AppItem info) {
                Toast.makeText(ResultActivity.this, "点击了下载" + info.getId(), Toast.LENGTH_SHORT).show();
            }
        }, new OnItemClickCallback<AppItem>() {

            @Override
            public void onClick(View view, AppItem info) {
                Toast.makeText(ResultActivity.this, "点击了收藏" + info.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
        normalRecycler.setLayoutManager(gridLayoutManager);
        normalRecycler.setAdapter(normalAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hotRecycler.setLayoutManager(linearLayoutManager);
        hotRecycler.setAdapter(hotAdapter);
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showMsg(int msg) {

    }

    @Override
    public void loadFail() {

    }

    @Override
    public void refreshList(String bookCount, String acount, String time) {

    }
}

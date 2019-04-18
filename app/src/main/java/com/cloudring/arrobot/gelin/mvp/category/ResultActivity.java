package com.cloudring.arrobot.gelin.mvp.category;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.cloudring.arrobot.gelin.R;
import com.cloudring.arrobot.gelin.adapter.AppAdapter;
import com.cloudring.arrobot.gelin.adapter.OnItemClickCallback;
import com.cloudring.arrobot.gelin.bean.AppItem;
import com.cloudring.arrobot.gelin.utils.GlobalUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultActivity extends MvpAppCompatActivity implements ResultView{


    @BindView(R.id.id_normal_recycler_view)
    RecyclerView normalRecycler;
    @BindView(R.id.id_hot_recycler_view)
    RecyclerView hotRecycler;
    @BindView(R.id.id_back_iv)
    ImageView backImg;
    @BindView(R.id.id_top_title_tv)
    TextView titleTv;

    private static String type = "";

    private AppAdapter normalAdapter;
    private AppAdapter hotAdapter;

    private List<AppItem> normalList;
    private List<AppItem> hotList;

    @InjectPresenter
    public ResultPresenter mPresenter;

    private static final int REFRESH_DATA = 0x001;      //刷新数据

    private MyHandler myHandler = new MyHandler(this);
    static class MyHandler extends Handler {
        WeakReference<ResultActivity> mActivityReference;
        MyHandler(ResultActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final ResultActivity activity = mActivityReference.get();
            if (activity != null) {
                switch (msg.what){
                    case REFRESH_DATA:
                        activity.normalAdapter.notifyDataSetChanged();
                        activity.hotAdapter.notifyDataSetChanged();
                        Toast.makeText(activity, "更新数据", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resutl);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        titleMap = new HashMap<>();
        titleMap.put(GlobalUtil.INTENT_TYPE_JIYI,"记忆");
        titleMap.put(GlobalUtil.INTENT_TYPE_QINGSHANG,"情商");
        titleMap.put(GlobalUtil.INTENT_TYPE_HOBBY,"习惯");
        titleMap.put(GlobalUtil.INTENT_TYPE_LANGUAGE,"语言");
        titleMap.put(GlobalUtil.INTENT_TYPE_MATH,"数理");
        titleMap.put(GlobalUtil.INTENT_TYPE_COGNITION,"认知");
        titleMap.put(GlobalUtil.INTENT_TYPE_LOGIC,"逻辑");

        if (getIntent().hasExtra(GlobalUtil.INTENT_TYPE_KEY)){
            type = getIntent().getStringExtra(GlobalUtil.INTENT_TYPE_KEY);
            if (titleMap.get(type) != null){
                titleTv.setText(titleMap.get(type));
            }else {
                titleTv.setText("");
            }
        }
        normalList = new ArrayList<>();
        hotList = new ArrayList<>();
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
        mPresenter.getNormalList("","",this);
    }

    private void initHotData() {
        hotList.add(new AppItem("001","www.ww.ww.www"));
        hotList.add(new AppItem("002","www.ww.ww.www"));
        hotList.add(new AppItem("003","www.ww.ww.www"));
        hotList.add(new AppItem("004","www.ww.ww.www"));
        hotList.add(new AppItem("005","www.ww.ww.www"));
    }

    private void initNormalData() {
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
        initNormalData();
        initHotData();
        myHandler.sendEmptyMessage(REFRESH_DATA);
    }

    @OnClick({R.id.id_back_iv})
    public void onClickEvent(View v) {
        switch (v.getId()){
            case R.id.id_back_iv:
                finish();
                break;
            default:
                break;
        }
    }

    private Map<String,String> titleMap;
}

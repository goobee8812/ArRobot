package com.cloudring.arrobot.gelin.mvp.mine;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.cloudring.arrobot.gelin.R;
import com.cloudring.arrobot.gelin.adapter.AppAdapter;
import com.cloudring.arrobot.gelin.adapter.ListAdapter;
import com.cloudring.arrobot.gelin.adapter.OnItemClickCallback;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;
import com.cloudring.arrobot.gelin.utils.GlobalUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineActivity extends MvpAppCompatActivity implements MineView {


    @BindView(R.id.id_normal_recycler_view)
    RecyclerView normalRecycler;
    @BindView(R.id.id_list_view)
    ListView itemList;
    @BindView(R.id.id_back_iv)
    ImageView backImg;
    @BindView(R.id.id_top_title_tv)
    TextView titleTv;


    private AppAdapter normalAdapter;
    private ListAdapter listAdapter;

    private List<AppItem> normalListData;
    private List<String> itemListData;

    private static String type = "";

    @InjectPresenter
    public MinePresenter mPresenter;

    private static final int REFRESH_DATA = 0x001;      //刷新数据

    private MyHandler myHandler = new MyHandler(this);

    static class MyHandler extends Handler {
        WeakReference<MineActivity> mActivityReference;

        MyHandler(MineActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final MineActivity activity = mActivityReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case REFRESH_DATA:
                        activity.normalAdapter.notifyDataSetChanged();
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
        setContentView(R.layout.activity_mine);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        normalListData = new ArrayList<>();
        itemListData = new ArrayList<>();
        initItemData();
        normalAdapter = new AppAdapter(normalListData, new OnItemClickCallback<AppItem>() {
            @Override
            public void onClick(View view, AppItem info) {
                Toast.makeText(MineActivity.this, "点击了下载" + info.getId(), Toast.LENGTH_SHORT).show();
            }
        }, new OnItemClickCallback<AppItem>() {

            @Override
            public void onClick(View view, AppItem info) {
                Toast.makeText(MineActivity.this, "点击了收藏" + info.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        listAdapter = new ListAdapter(this, itemListData, new OnItemClickCallback<String>() {
            @Override
            public void onClick(View view, String info) {
                Toast.makeText(MineActivity.this, "点击了" + info.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        itemList.setAdapter(listAdapter);
        if (getIntent().hasExtra(GlobalUtil.INTENT_TYPE_KEY)){
            type = getIntent().getStringExtra(GlobalUtil.INTENT_TYPE_KEY);
            if (type.equals(GlobalUtil.INTENT_TYPE_GAME)){
                normalAdapter.setType(1);
                titleTv.setText("我的游戏");
            }else if (type.equals(GlobalUtil.INTENT_TYPE_COLLECTION)){
                titleTv.setText("我的收藏");
                normalAdapter.setType(2);
            }
        }
    }

    private void initItemData() {
        itemListData.add("全部");
        itemListData.add("记忆");
        itemListData.add("情商");
        itemListData.add("习惯");
        itemListData.add("语言");
        itemListData.add("数理");
        itemListData.add("认知");
        itemListData.add("逻辑");
    }

    private void initNormalData() {
        //测试
        normalListData.add(new AppItem("001", "www.baidu.com"));
        normalListData.add(new AppItem("002", "www.baidu.com"));
        normalListData.add(new AppItem("003", "www.baidu.com"));
        normalListData.add(new AppItem("004", "www.baidu.com"));
        normalListData.add(new AppItem("005", "www.baidu.com"));
        normalListData.add(new AppItem("006", "www.baidu.com"));
        normalListData.add(new AppItem("007", "www.baidu.com"));
        normalListData.add(new AppItem("008", "www.baidu.com"));
        normalListData.add(new AppItem("009", "www.baidu.com"));
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        normalRecycler.setLayoutManager(gridLayoutManager);
        normalRecycler.setAdapter(normalAdapter);

        mPresenter.getNormalList("","",MineActivity.this);
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
}
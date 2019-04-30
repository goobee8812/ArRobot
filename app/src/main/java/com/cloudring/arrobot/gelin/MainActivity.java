package com.cloudring.arrobot.gelin;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.cloudring.arrobot.gelin.adapter.TypeAdapter;
import com.cloudring.arrobot.gelin.manager.PRClient;
import com.cloudring.arrobot.gelin.mvp.modle.MainType;
import com.cloudring.arrobot.gelin.utils.DeviceUtil;
import com.cloudring.arrobot.gelin.utils.GlobalUtil;
import com.cloudring.arrobot.gelin.utils.LogUtil;
import com.cloudring.arrobot.gelin.utils.PageJumpUtil;
import com.getlearn.library.GetLearnSdk;
import com.getlearn.library.Interface.OnEquipmentDelegateListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpAppCompatActivity implements MainView{

    @BindView(R.id.id_my_game_iv)
    ImageView myGameIv;
    @BindView(R.id.id_jiyi_iv)
    ImageView myJiyiIv;
    @BindView(R.id.id_qingshang_iv)
    ImageView myQingshangIv;
    @BindView(R.id.id_xiguan_iv)
    ImageView myXiguanIv;
    @BindView(R.id.id_yuyan_iv)
    ImageView myYuyanIv;
    @BindView(R.id.id_shuli_iv)
    ImageView myShuliIv;
    @BindView(R.id.id_renzhi_iv)
    ImageView myRenzhiIv;
    @BindView(R.id.id_luoji_iv)
    ImageView myLuojiIv;
    @BindView(R.id.id_collection_iv)
    ImageView myCollectionIv;
    @BindView(R.id.id_search_iv)
    ImageView mySearchIv;
    @BindView(R.id.id_time_tv)
    TextView mTimeTv;
    @BindView(R.id.main_recycler_view)
    RecyclerView recyclerView;

    private TypeAdapter adapter;

    @InjectPresenter
    public MainPresenter mPresenter;

    //格林授权初始化
    private GetLearnSdk mGetLearnSdk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mGetLearnSdk = new GetLearnSdk();
        mGetLearnSdk.init(this,"vcat8yoczbgjkiofrnp5yt4xpx2ojcv0b1xtpkc3znkkm4ctgelej8p5mo5oifsy","y54DyCyunCKG1V73ZcbrwceDMrZqHfG4K1WJWT0QxJu1VTLIhXFCCdwIP9Bi46Bq","","","http://test-rscloud.getlearn.cn");
        mGetLearnSdk.getEquipmentJurisdiction(MainActivity.this, "");
        //设备授权回调
        mGetLearnSdk.setOnEquipmentDelegateListener(new OnEquipmentDelegateListener() {
            @Override
            public void OnEquipment(String s, String s1) {
                LogUtil.LogShow("OnEquipment:状态码： " + s + ",打印消息：" + s1,LogUtil.DEBUG);
            }
        });
        initView();
    }

    private void initView(){
        adapter = new TypeAdapter(MainActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        String deviceId = DeviceUtil.getLocalDeviceID(MainActivity.this);
        mPresenter.getTypeList(deviceId, MainActivity.this);
    }

    @OnClick({R.id.id_my_game_iv,R.id.id_jiyi_iv,R.id.id_qingshang_iv,R.id.id_xiguan_iv,R.id.id_yuyan_iv,R.id.id_shuli_iv,
            R.id.id_renzhi_iv,R.id.id_luoji_iv,R.id.id_collection_iv,R.id.id_search_iv})
    public void onClickEvent(View v) {
        switch (v.getId()) {
            case R.id.id_my_game_iv:
                PageJumpUtil.startMineActivity(MainActivity.this,GlobalUtil.INTENT_TYPE_GAME);
                break;
            case R.id.id_jiyi_iv:
                PageJumpUtil.startResultActivity(MainActivity.this,GlobalUtil.INTENT_TYPE_JIYI);
                break;
            case R.id.id_qingshang_iv:
                PageJumpUtil.startResultActivity(MainActivity.this,GlobalUtil.INTENT_TYPE_QINGSHANG);
                break;
            case R.id.id_xiguan_iv:
                PageJumpUtil.startResultActivity(MainActivity.this,GlobalUtil.INTENT_TYPE_HOBBY);
                break;
            case R.id.id_yuyan_iv:
                PageJumpUtil.startResultActivity(MainActivity.this,GlobalUtil.INTENT_TYPE_LANGUAGE);
                break;
            case R.id.id_shuli_iv:
                PageJumpUtil.startResultActivity(MainActivity.this,GlobalUtil.INTENT_TYPE_MATH);
                break;
            case R.id.id_renzhi_iv:
                PageJumpUtil.startResultActivity(MainActivity.this,GlobalUtil.INTENT_TYPE_COGNITION);
                break;
            case R.id.id_luoji_iv:
                PageJumpUtil.startResultActivity(MainActivity.this,GlobalUtil.INTENT_TYPE_LOGIC);
                break;
            case R.id.id_collection_iv:
                PageJumpUtil.startMineActivity(MainActivity.this,GlobalUtil.INTENT_TYPE_COLLECTION);
                break;
            case R.id.id_search_iv:
                PageJumpUtil.startSearchActivity(MainActivity.this);
                break;
            default:
                break;
        }
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
    public void refreshList(List<MainType> list){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                List<MainType> resultList = PRClient.getInstance().getTypeResultList();
                adapter.setData(resultList);
            }
        });
    }
}

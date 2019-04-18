package com.cloudring.arrobot.gelin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudring.arrobot.gelin.mvp.category.ResultActivity;
import com.cloudring.arrobot.gelin.mvp.mine.MineActivity;
import com.cloudring.arrobot.gelin.utils.GlobalUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    Intent intent1;

    @OnClick({R.id.id_my_game_iv,R.id.id_jiyi_iv,R.id.id_qingshang_iv,R.id.id_xiguan_iv,R.id.id_yuyan_iv,R.id.id_shuli_iv,
            R.id.id_renzhi_iv,R.id.id_luoji_iv,R.id.id_collection_iv,R.id.id_search_iv})
    public void onClickEvent(View v) {
        switch (v.getId()) {
            case R.id.id_my_game_iv:
                intent1 = new Intent(MainActivity.this, MineActivity.class);
                intent1.putExtra(GlobalUtil.INTENT_TYPE_KEY,GlobalUtil.INTENT_TYPE_GAME);
                startActivity(intent1);
                break;
            case R.id.id_jiyi_iv:
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intent);
                break;
            case R.id.id_qingshang_iv:
                intent1.putExtra(GlobalUtil.INTENT_TYPE_KEY,GlobalUtil.INTENT_TYPE_COLLECTION);
                startActivity(intent1);
                break;
            case R.id.id_xiguan_iv:

                break;
            case R.id.id_yuyan_iv:

                break;
            case R.id.id_shuli_iv:

                break;
            case R.id.id_renzhi_iv:

                break;
            case R.id.id_luoji_iv:

                break;
            case R.id.id_collection_iv:

                break;
            case R.id.id_search_iv:

                break;
            default:
                break;
        }
    }
}

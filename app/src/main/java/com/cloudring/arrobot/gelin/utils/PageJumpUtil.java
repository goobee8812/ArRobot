package com.cloudring.arrobot.gelin.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntDef;

import com.cloudring.arrobot.gelin.mvp.category.ResultActivity;
import com.cloudring.arrobot.gelin.mvp.mine.MineActivity;

/**
 * Created by lzx on 2018/6/13.
 * 界面跳转
 */

/**
 * Created by lzx on 2018/6/13.
 * 界面跳转
 */
public class PageJumpUtil {

    public static void startResultActivity(Context context,String type) {
        Intent intent = new Intent();
        intent.setClass(context, ResultActivity.class);
        intent.putExtra(GlobalUtil.INTENT_TYPE_KEY, type);
        context.startActivity(intent);
    }

    public static void startMineActivity(Context context,String type) {
        Intent intent = new Intent();
        intent.setClass(context, MineActivity.class);
        intent.putExtra(GlobalUtil.INTENT_TYPE_KEY, type);
        context.startActivity(intent);
    }
}

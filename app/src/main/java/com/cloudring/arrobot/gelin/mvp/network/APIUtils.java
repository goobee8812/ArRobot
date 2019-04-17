package com.cloudring.arrobot.gelin.mvp.network;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;

import java.util.HashMap;

/**
 * Created by cc on 2016/8/7.
 * <p>
 * 此类用于获取手机基本配置，
 * <p>
 * Http请求get字段
 */

public class APIUtils {

    public static final String CLIENT = "1";

    public static final String APP_ID = "aee38f3f95e57bc19432d30c37c5424b"; //更改appId2-20180917 除了星际大白其他设备专用
    public static final String APP_KEY = "GFb22ImB7sdQsYV7eCKRsmuOEC3C4PoNqu6FzSZzIodSH9Wc6vbE/Q==";

    //获取手机屏幕分辨率
    private static String getWindowScreen(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        float width = dm.widthPixels * dm.density;
        float height = dm.heightPixels * dm.density;
        return width + "x" + height;
    }

    //获取Android版本号
    private static String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    //获取手机品牌
    private static String getMODEL() {
        return Build.MODEL.replaceAll("\\s", "");
    }

    public static HashMap<String, Object> toMap(Context context) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("screen", getWindowScreen(context));
        map.put("clientVersion", getAndroidVersion());
        map.put("client", CLIENT);
        map.put("d_brand", getMODEL());
        return map;
    }

    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 运行时实时请求文件读写权限
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
    // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission1 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
    // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);

        }

        if (permission1 != PackageManager.PERMISSION_GRANTED) {
    // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);

        }
    }
}

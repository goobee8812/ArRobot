package com.cloudring.arrobot.gelin.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.cloudring.arrobot.gelin.R;

import java.io.File;

/**
 * Created by luoren on 2017/8/8.
 */

public class ApkBroadCastReceiver extends BroadcastReceiver {
    public static String downloadApkPath;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction()) || Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
            String pakageDescrption = intent.getDataString();
            if (pakageDescrption.contains(context.getPackageName())) {
                if (downloadApkPath != null && !downloadApkPath.isEmpty()) {
                    String downloadPath = checkAPKExists(context);
                    if (downloadPath != null) {
                        boolean delete = new File(downloadPath).delete();
                    }
                } else {
                    return;
                }
            }
        }
    }

    private String checkAPKExists(Context context) {
        String downloadPath = downloadApkPath + context.getString(R.string.download_apkname, context.getPackageName());
        File file = new File(downloadPath);
        if (file.exists()) {
            return downloadPath;
        }
        return null;
    }
}

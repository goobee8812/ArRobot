package com.cloudring.arrobot.gelin;

import android.app.Application;
import android.content.Context;

import com.cloudring.arrobot.gelin.download.FileHelper;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.io.IOException;

public class MainApplication extends Application {
    private static Context mContext;
    private static MainApplication instance;
    public static final String BASE_URL = "http://prod.czbsit.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        mContext = getApplicationContext();
        FlowManager.init(this);

        Process su;
        try {
            su = Runtime.getRuntime().exec("su");
//            su = Runtime.getRuntime().exec("/system/bin/su");
//            String cmd = "chmod 777 " + FileHelper.getDownloadApkCachePath() + "\n"
//                    + "exit\n";
//            su.getOutputStream().write(cmd.getBytes());
//            if ((su.waitFor() != 0)) {
//                throw new SecurityException();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Context getContext() {
        return mContext;
    }

    public static MainApplication getInstance() {
        return instance;
    }

//    /* Check access permission */
//        if (!device.canRead() || !device.canWrite()) {
//        try {
//            ///exeShell("chmod 777 " + device.getAbsolutePath() );
//            /* Missing read/write permission, trying to chmod the file */
//            Process su;
//            su = Runtime.getRuntime().exec("/system/bin/su");
//            String cmd = "chmod 777 " + device.getAbsolutePath() + "\n"
//                    + "exit\n";
//            su.getOutputStream().write(cmd.getBytes());
//            if ((su.waitFor() != 0) || !device.canRead()
//                    || !device.canWrite()) {
//                throw new SecurityException();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new SecurityException();
//        }
//    }
}

package com.cloudring.arrobot.gelin.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.lang.reflect.Method;

public class MyUtil{

    private static final int MIN_DELAY_TIME= 2000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;
    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    /**
     * 判断安装的应用之中是否安装了指定包名的应用
     *
     * @param pkgName
     * @return
     */
    public static boolean isPkgInstalled(Context context, String pkgName){
        PackageInfo packageInfo = null;
        try{
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        }catch(PackageManager.NameNotFoundException e){
            packageInfo = null;
            e.printStackTrace();
        }
        if(packageInfo == null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 禁止Edittext弹出软件盘，光标依然正常显示。
     */
    public static void disableShowSoftInput(EditText editText){
        if(android.os.Build.VERSION.SDK_INT <= 10){
            editText.setInputType(InputType.TYPE_NULL);
        }else{
            Class<EditText> cls = EditText.class;
            Method method;
            try{
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            }catch(Exception e){
            }
            try{
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            }catch(Exception e){
            }
        }
    }

    /**
     * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
     * @param command 命令：String apkRoot="chmod 777 "+getPackageCodePath(); RootCommand(apkRoot);
     * @return 应用程序是/否获取Root权限
     */
    public static boolean RootCommand(String command)
    {
        Process process = null;
        DataOutputStream os = null;
        try{
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e){
            Log.d("*** DEBUG ***", "ROOT REE " + e.getMessage());
            return false;
        } finally{
            try
            {
                if (os != null)
                {
                    os.close();
                }
                process.destroy();
            } catch (Exception e){
            }
        }
        Log.d("*** DEBUG ***", "Root SUCCESS ");
        return true;
    }
}

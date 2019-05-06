package com.cloudring.arrobot.gelin.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.InputType;
import android.widget.EditText;

import java.lang.reflect.Method;

public class MyUtil{

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
}

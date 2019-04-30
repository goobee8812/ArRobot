package com.cloudring.arrobot.gelin.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.cloudring.arrobot.gelin.MainApplication;

public class DeviceUtil{

    /**
     * 获取deviceId
     *
     * @param context
     * @return
     */
    public static String getLocalDeviceID(Context context){
        Cursor cursor = null;
        String code = "";
        try{
            ContentResolver contentResolver = MainApplication.getContext().getContentResolver();
            Uri uri = Uri.parse("content://com.cloudring.magic.model.db/ParentsSetting");
            cursor = contentResolver.query(uri, null, null, null, null);
            if(cursor != null && cursor.moveToFirst()){
                code = cursor.getString(cursor.getColumnIndex("code"));
                Log.d("getDeviceID", "getDeviceID: " + code);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(cursor != null){
                cursor.close();
            }
        }
        if(!code.equals("")){
            //写进本地
            SpUtil.putString(context, ContantsUtil.DEVICEID, code);
        }
        return code;
    }
}

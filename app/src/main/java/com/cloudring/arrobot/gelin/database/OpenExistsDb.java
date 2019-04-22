package com.cloudring.arrobot.gelin.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 打开一个已经存在的数据库
 * 
 * @author zhanghuayong
 * @date 2013-1-5
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class OpenExistsDb {
    
    private static final String TAG = "OpenExistsDb";
    /**
     * 获得一个连接
     * 
     * @return
     * @throws Exception
     */
    public static synchronized SQLiteDatabase getConn(Context context) throws Exception {
        DBHelper mDbHelperTest = new DBHelper(context);
        SQLiteDatabase mDatabase = mDbHelperTest.getDbconnection();
        Log.i(TAG, "version:" + mDatabase.getVersion());
        return mDatabase;
    }
}

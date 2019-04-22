package com.cloudring.arrobot.gelin.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.cloudring.arrobot.gelin.utils.LogUtil;

import java.util.ArrayList;

/**
 * 公共的数据库操作
 * 
 * @author xiaozhijun
 * @date 2017-7-28
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CommonDao {
    // 当前页面的上下文
    private Context instance;
    
    /**
     * 
     * <默认构造函数>
     */
    public CommonDao(Context context) {
        instance = context;
        // 初始化日志对象
    }
    
    /**
     * 功能的添加方法
     * 
     * @param
     */
    public void updateData(String sql, ArrayList<String[]> list) {
        // 数据库操作对象
        SQLiteDatabase sqLiteDatabase = null;
        try {
            // 获得一个连接
            sqLiteDatabase = OpenExistsDb.getConn(instance);
            // 开始事物
            sqLiteDatabase.beginTransaction();
            // 写入数据
            for (String[] values : list) {
                // 执行新增加的sql语句
                sqLiteDatabase.execSQL(sql, values);
            }
            // 设置事物的提交状态为成功
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            if (null != sqLiteDatabase) {
                try {
                    sqLiteDatabase.endTransaction();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            LogUtil.LogShow("update sql exception:" + e.toString(),LogUtil.DEBUG);
        } finally {
            // 提交事物
            try {
                if (null != sqLiteDatabase && sqLiteDatabase.isOpen()) {
                    sqLiteDatabase.endTransaction();
                    sqLiteDatabase.close();
                }
            } catch (Exception e) {
            }
        }
    }
    
    /**
     * 功能的删除方法
     * 
     * @param
     */
    public void deleteData(String sql, ArrayList<String> values) {
        // 数据库操作对象
        SQLiteDatabase sqLiteDatabase = null;
        try {
            // 获得一个连接
            sqLiteDatabase = OpenExistsDb.getConn(instance);
            // 开始事物
            sqLiteDatabase.beginTransaction();
            // 执行删除
            for (String value : values) {
                // 执行删除
                sqLiteDatabase.execSQL(sql, new String[] {value});
            }
            // 设置事物的提交状态为成功
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            
        } finally {
            // 提交事物
            try {
                if (null != sqLiteDatabase && sqLiteDatabase.isOpen()) {
                    sqLiteDatabase.endTransaction();
                    sqLiteDatabase.close();
                }
            } catch (Exception e) {
            }
        }
        
    }
    
    /**
     * 功能的删除方法
     * 
     * @param
     */
    public void updateDataNotTranscation(String sql, ArrayList<String[]> list) {
        // 数据库操作对象
        SQLiteDatabase sqLiteDatabase = null;
        try {
            // 获得一个连接
            sqLiteDatabase = OpenExistsDb.getConn(instance);
            // 执行删除
            // 写入数据
            for (String[] values : list) {
                // 执行新增加的sql语句
                sqLiteDatabase.execSQL(sql, values);
            }
        } catch (Exception e) {
            LogUtil.LogShow("update sql exception:" + e.toString(),LogUtil.DEBUG);
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                try {
                    sqLiteDatabase.close();
                } catch (Exception e) {
                }
            }
        }
        
    }
    
    /**
     * 只改一条记录
     * 
     * @param
     */
    public boolean updateData(String sql, Object[] values) {
        boolean result = false;
        // 数据库操作对象
        SQLiteDatabase sqLiteDatabase = null;
        try {
            // 获得一个连接
            sqLiteDatabase = OpenExistsDb.getConn(instance);
            // 执行新增加的sql语句
            sqLiteDatabase.execSQL(sql, values);
            result = true;
        } catch (Exception e) {
            LogUtil.LogShow("update sql exception:" + e.toString(),LogUtil.DEBUG);
            result = false;
        } finally {
            if (null != sqLiteDatabase && sqLiteDatabase.isOpen()) {
                try {
                    sqLiteDatabase.close();
                } catch (Exception e) {
                }
            }
        }

        return result;
    }
    
    /**
     * 只改一条记录
     * 
     * @param
     */
    public boolean updateData(String sql) {
        boolean result = false;
        // 数据库操作对象
        SQLiteDatabase sqLiteDatabase = null;
        try {
            // 获得一个连接
            sqLiteDatabase = OpenExistsDb.getConn(instance);
            // 执行新增加的sql语句
            sqLiteDatabase.execSQL(sql);
            result = true;
        } catch (Exception e) {
            Log.i("GGGG", "update sql exception:" + e.toString());
        } finally {
            if (null != sqLiteDatabase && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
        return result;
    }
    
    /**
     * 查询数据库的总记录
     */
    public String getTotalRecord(String tablename, int tagId) {
        String total = "0";
        // 数据库操作对象
        SQLiteDatabase sqLiteDatabase = null;
        String sql = "select count(0) from " + tablename + " where tagid = ?";
        Cursor returnCursor = null;
        try {
            // 获得数据库操作对象
            sqLiteDatabase = OpenExistsDb.getConn(instance);
            returnCursor = sqLiteDatabase.rawQuery(sql, new String[] {String.valueOf(tagId)});
            if (null != returnCursor && returnCursor.moveToNext()) {
                total = returnCursor.getString(0);
            }
            if (null == total || total.equals("")) {
                total = "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != returnCursor) {
                returnCursor.close();
            }
            if (null != sqLiteDatabase && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
        return total;
    }
}

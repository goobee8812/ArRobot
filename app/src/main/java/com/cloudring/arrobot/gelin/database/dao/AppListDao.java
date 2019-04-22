package com.cloudring.arrobot.gelin.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cloudring.arrobot.gelin.database.CommonDao;
import com.cloudring.arrobot.gelin.database.ConstantSQLite;
import com.cloudring.arrobot.gelin.database.OpenExistsDb;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;
import com.cloudring.arrobot.gelin.utils.LogUtil;


import java.util.LinkedList;
import java.util.List;

/**
 * 应用列表操作
 *
 * @author xiaozhijun
 * @version [版本号]
 * @date 2017-7-17
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AppListDao {

    private static final String TAG = "AppListDao";

    // 当前要操作的表名
    private String TABLENAME = ConstantSQLite.APP_LIST_TABLE_NAME;

    // 当前页面的上下文
    private Context instance;

    // 公用的对象
    private CommonDao commonDao;

    /**
     * <默认构造函数>
     */
    public AppListDao(Context context) {
        instance = context;
        commonDao = new CommonDao(context);
    }

    /**
     * 根据id查找出apkList
     *
     * @return
     */
    public LinkedList<AppItem> getApkItemByID(String typeid) {
        LinkedList<AppItem> list = new LinkedList<>();
        SQLiteDatabase sqLiteDatabase = null;
        // 添加按时间降序排列
        String selectsql = "select * from " + TABLENAME + "  where _type_id=?";
        Cursor cursor = null;
        LinkedList<AppItem> apkItems = null;
        try {
            // 获得一个连接
            sqLiteDatabase = OpenExistsDb.getConn(instance);
            cursor = sqLiteDatabase.rawQuery(selectsql, new String[]{String.valueOf(typeid)});
            while (null != cursor && cursor.moveToNext()) {
                // 类型ID
                String typeId = cursor.getString(cursor.getColumnIndexOrThrow("_type_id"));
                String applistString = cursor.getString(cursor.getColumnIndexOrThrow("_app_content"));
                apkItems = JSON.parseObject(applistString, new TypeReference<LinkedList<AppItem>>() {
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
            // 关闭这个连接
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
        return apkItems;
    }

    /**
     * 获取 列表MD5值
     *
     * @return md5值
     */
    public String getApkListMD5(String typeid) {
        SQLiteDatabase sqLiteDatabase = null;
        // 添加按时间降序排列
        String selectsql = "select * from " + TABLENAME + "  where _type_id=?";
        Cursor cursor = null;
        String md5 = null;
        try {
            // 获得一个连接
            sqLiteDatabase = OpenExistsDb.getConn(instance);
            cursor = sqLiteDatabase.rawQuery(selectsql, new String[]{String.valueOf(typeid)});
            while (null != cursor && cursor.moveToNext()) {
                // 类型ID
                md5 = cursor.getString(cursor.getColumnIndexOrThrow("_md5"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
            // 关闭这个连接
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
        return md5;
    }

    /**
     * 添加一条记录/更新一条记录
     */
    public boolean insertAppListAndTypeid(String typeid, List<AppItem> items, String md5) {
        boolean result = false;
        // 新增记录
        String sql =
                "insert into " + TABLENAME
                        + " (_type_id,_app_content,_md5) values (?,?,?)";
        String[] values =
                new String[]{typeid, JSON.toJSONString(items), md5};
        result = commonDao.updateData(sql, values);
        return result;
    }

    /**
     * 删除某一类应用数据
     *
     * @return
     */
    public boolean deleteSingleTypeAppRecord(String typeid) {
        boolean state = false;
        // 数据库操作对象
        SQLiteDatabase sqLiteDatabase = null;
        try {
            // 获得一个连接
            sqLiteDatabase = OpenExistsDb.getConn(instance);
            String sql = "delete  from " + TABLENAME + " where _type_id=? ";
            String[] values =
                    new String[]{typeid};
            state = commonDao.updateData(sql, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 提交事物 ----出现了java.lang.IllegalStateException: attempt to re-open
            // an already-closed object异常
            if (null != sqLiteDatabase && sqLiteDatabase.isOpen()) {// 规避
                sqLiteDatabase.close();
            }
        }
        return state;
    }

    /**
     * 删除所有应用数据
     *
     * @return
     */
    public boolean deleteAllAppRecord() {
        boolean state = false;
        // 数据库操作对象
        SQLiteDatabase sqLiteDatabase = null;
        try {
            // 获得一个连接
            sqLiteDatabase = OpenExistsDb.getConn(instance);
            String sql = "delete  from " + TABLENAME;
            sqLiteDatabase.execSQL(sql);
            state = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 提交事物 ----出现了java.lang.IllegalStateException: attempt to re-open
            // an already-closed object异常
            if (null != sqLiteDatabase && sqLiteDatabase.isOpen()) {// 规避
                sqLiteDatabase.close();
            }
        }
        return state;
    }

}

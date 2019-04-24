package com.cloudring.arrobot.gelin.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cloudring.arrobot.gelin.utils.DataFormat;
import com.cloudring.arrobot.gelin.utils.LogUtil;


public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    public static final int version = 1;

    public static final String DB_NAME = "gelinrobot.db";

    public DBHelper(Context mContext) {
        super(mContext, DB_NAME, null, version);
//        DebugUtil.i(TAG, "dbHelperTest consctruct");
    }

    public SQLiteDatabase getDbconnection() {
        return this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtil.LogShow( "begin oncreate db",LogUtil.DEBUG);
//        db.execSQL(ConstantSQLite.createAppTypeTab);
        db.execSQL(ConstantSQLite.createAppListTab);
//        db.execSQL(ConstantSQLite.createAblumListTab);
        LogUtil.LogShow( "end oncreate db",LogUtil.DEBUG);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.LogShow( "onDowngrade  oldVersion:" + oldVersion + "  newVersion:",LogUtil.DEBUG);
        db.execSQL("DROP TABLE IF EXISTS " + ConstantSQLite.APP_LIST_TABLE_NAME);
        db.execSQL(ConstantSQLite.createAppListTab);
//        db.execSQL("DROP TABLE IF EXISTS " + ConstantSQLite.APP_TYPE_TABLE_NAME);
//        db.execSQL(ConstantSQLite.createAppTypeTab);
//        db.execSQL("DROP TABLE IF EXISTS " + ConstantSQLite.AUDIO_ALBUM_NAME);
//        db.execSQL(ConstantSQLite.createAblumListTab);
        LogUtil.LogShow( "onDowngrade  oldVersion:" + oldVersion + "  newVersion:" + newVersion,LogUtil.DEBUG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.LogShow( "oldVersion:" + oldVersion + "  newVersion:" + newVersion,LogUtil.DEBUG);
        db.execSQL("DROP TABLE IF EXISTS " + ConstantSQLite.APP_LIST_TABLE_NAME);
        db.execSQL(ConstantSQLite.createAppListTab);
//        db.execSQL("DROP TABLE IF EXISTS " + ConstantSQLite.APP_TYPE_TABLE_NAME);
//        db.execSQL(ConstantSQLite.createAppTypeTab);
//        db.execSQL("DROP TABLE IF EXISTS " + ConstantSQLite.AUDIO_ALBUM_NAME);
//        db.execSQL(ConstantSQLite.createAblumListTab);
    }

    /**
     * 获取表中的字段
     *
     * @param db        数据库
     * @param tableName 表名
     * @return 表中的字段
     */
    protected String getColumnNames(SQLiteDatabase db, String tableName) {
        StringBuffer sb = null;
        Cursor c = null;
        try {
            c = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
            if (null != c) {
                int columnIndex = c.getColumnIndex("name");
                if (-1 == columnIndex) {
                    return null;
                }
                int index = 0;
                sb = new StringBuffer(c.getCount());
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    sb.append(c.getString(columnIndex));
                    sb.append(",");
                    index++;
                }
                LogUtil.LogShow(tableName + "--get oldTable colume ==" + sb.toString(),LogUtil.DEBUG);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return sb != null ? sb.toString() : null;
    }

    /***
     * 将旧表同步到新表
     * @param db
     * @param oldTable
     * @param newtable
     */
    public void updateTable(SQLiteDatabase db, String oldTable, String newtable) throws Exception {
        String oldparams = getColumnNames(db, oldTable);
        String newparams = getColumnNames(db, newtable);
        // 新 老表对应后 获取共同拥有的字段
        String params = getLastParams(oldparams, newparams);
        LogUtil.LogShow("updateTable sql111111 ==" + params,LogUtil.DEBUG);
        if (!DataFormat.isEmpty(params)) {
            String coolunmeParams = params.substring(0, params.length() - 1);
            String sql =
                    "INSERT INTO " + newtable + " ( " + coolunmeParams + " ) " + " SELECT " + " " + coolunmeParams + " "
                            + " FROM " + oldTable;
            LogUtil.LogShow("updateTable sql ==" + sql + params,LogUtil.DEBUG);
            db.execSQL(sql);
        }
    }

    /**
     * 获取共同拥有的字段
     *
     * @param oldparams
     * @param newparams
     * @return
     */
    public String getLastParams(String oldparams, String newparams) {
        StringBuffer params = null;
        if (!DataFormat.isEmpty(oldparams) && !DataFormat.isEmpty(newparams)) {
            params = new StringBuffer();
            // 获取所有老表中字段
            String[] pas = oldparams.substring(0, oldparams.length() - 1).split(",");
            if (pas != null) {
                int length = pas.length;
                for (int i = 0; i < length; i++) {
                    String s = pas[i];
                    // 判断字段是否为空
                    if (!DataFormat.isEmpty(s) && !DataFormat.isEmpty(s.trim())) {
                        // 老字段在新表中是否存在
                        if (newparams.contains(s.trim())) {
                            params.append(s);
                            params.append(",");
                        }
                    }
                }
            }
        }
        return params != null ? params.toString() : null;
    }
}

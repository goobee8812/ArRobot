package com.cloudring.arrobot.gelin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

/**
 * 往sharedPreference里面存入和读取数据的工具类
 */
public class SpUtil{

    private static SharedPreferences sp;

    private static void setSp(Context context){
        sp = context.getSharedPreferences(ContantsUtil.FILENAME, Context.MODE_PRIVATE);
    }

    /**
     * 从SharePreference中获取一个指定字段名的字符串
     *
     * @param context  上下文
     * @param key      字段名
     * @param defValue 默认值
     * @return
     */
    public static String getString(Context context, String key, String defValue){
        if(sp == null){
            setSp(context);
        }
        return sp.getString(key, defValue);
    }

    /**
     * 往SharePreference中储存一个指定字段名的字符串
     *
     * @param context 上下文
     * @param key     字段名
     * @param value   默认值
     * @return
     */
    public static void putString(Context context, String key, String value){
        if(sp == null){
            setSp(context);
        }
        sp.edit().putString(key, value).commit();
    }

    /**
     * 从SharePreference中获取一个指定字段名的boolean值
     *
     * @param context  上下文
     * @param key      字段名
     * @param defValue 默认值
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defValue){
        if(sp == null){
            setSp(context);
        }
        return sp.getBoolean(key, defValue);
    }

    /**
     * 往SharePreference中储存一个指定字段名的boolean值
     *
     * @param context 上下文
     * @param key     字段名
     * @param value   默认值
     * @return
     */
    public static void putBoolean(Context context, String key, boolean value){
        if(sp == null){
            setSp(context);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 从SharePreference中获取一个指定字段名的long值
     *
     * @param context  上下文
     * @param key      字段名
     * @param defValue 默认值
     * @return
     */
    public static long getLong(Context context, String key, long defValue){
        if(sp == null){
            setSp(context);
        }
        return sp.getLong(key, defValue);
    }

    /**
     * 往SharePreference中储存一个指定字段名的long值
     *
     * @param context 上下文
     * @param key     字段名
     * @param value   默认值
     * @return
     */
    public static void putLong(Context context, String key, long value){
        if(sp == null){
            setSp(context);
        }
        sp.edit().putLong(key, value).commit();
    }

    /**
     * 从SharePreference中获取一个指定字段名的float值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloat(Context context, String key, float defValue){
        if(sp == null){
            setSp(context);
        }
        return sp.getFloat(key, defValue);
    }

    /**
     * 往SharePreference中储存一个指定字段名的float值
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putFloat(Context context, String key, float value){
        if(sp == null){
            setSp(context);
        }
        sp.edit().putFloat(key, value).commit();
    }

    /**
     * 从SharePreference中获取一个指定字段名的int值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(Context context, String key, int defValue){
        if(sp == null){
            setSp(context);
        }
        return sp.getInt(key, defValue);
    }

    /**
     * 往SharePreference中储存一个指定字段名的int值
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putInt(Context context, String key, int value){
        if(sp == null){
            setSp(context);
        }
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 删除一个键值对
     *
     * @param context
     * @param key
     */
    public static void removeKey(Context context, String key){
        if(sp == null){
            setSp(context);
        }
        sp.edit().remove(key).commit();
    }

    /**
     * 删除sharepreference中存储的值，清除数据，文件保留
     *
     * @param context
     */
    public static void clearData(Context context){
        if(sp == null){
            setSp(context);
        }
        sp.edit().clear().commit();
    }

    /**
     * 删除文件，所有数据全部删除,  "/data/data/+ 包名
     *
     * @param context
     * @param fileName
     */
    public static void deleteSharedPreferencesFile(Context context, String fileName){
        File file = new File(File.separator + "data" + File.separator + "data" + File.separator + context.getPackageName().toString() + "/shared_prefs", fileName + ".xml");
        if(file.exists()){
            file.delete();
        }
    }
}

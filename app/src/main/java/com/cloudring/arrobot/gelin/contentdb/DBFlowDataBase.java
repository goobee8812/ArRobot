package com.cloudring.arrobot.gelin.contentdb;

import android.net.Uri;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.provider.ContentProvider;
import com.raizlabs.android.dbflow.annotation.provider.ContentUri;
import com.raizlabs.android.dbflow.annotation.provider.TableEndpoint;


/**
 * Created by yfa on 2017/8/15.
 */
@ContentProvider(authority = DBFlowDataBase.AUTHORITY,
        database = DBFlowDataBase.class,
        baseContentUri = DBFlowDataBase.BASE_CONTENT_URI)
@Database(name = DBFlowDataBase.NAME, version = DBFlowDataBase.VERSION)
public class DBFlowDataBase {
    //数据库名称
    public static final String NAME = "appDb";
    //数据库版本
    public static final int VERSION = 1;

    public static final String AUTHORITY = "com.gelin.ar.robot.db";

    public static final String BASE_CONTENT_URI = "content://";

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = Uri.parse(BASE_CONTENT_URI + AUTHORITY).buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(name = AppInfo.ENDPOINT, contentProvider = DBFlowDataBase.class)
    public static class AppInfo {

        public static final String ENDPOINT = "AppInfo";

        @ContentUri(path = ENDPOINT,
                type = ContentUri.ContentType.VND_MULTIPLE + ENDPOINT)
        public static Uri CONTENT_URI = buildUri(ENDPOINT);

    }
}

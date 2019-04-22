package com.cloudring.arrobot.gelin.mvp.network;

/**
 * Created by:liulonggang on 2016/6/20 11:33
 * Class: API
 * Description: 全局API接口地址；
 */
public class API {

    public static class URL {

        //获取我的类型
        public static final String REQUEST_MARKET_LISTTYPE = "cloudring-property-mobile-web/donutApp/findDonutAppDevice";

        //获取Apk列表
        public static final String REQUEST_MARKET_LISTAPP = "cloudring-property-mobile-web/geling/gelingApkList";

        //获取视频专辑
        public static final String REQUEST_ALBUM_LIST = "cloudring-property-mobile-web/iqiyi/selectQqvideoByCategoryVersion";

        //获取zip资源包
        public static final String REQUEST_ALBUM_ZIP = "cloudring-property-mobile-web/iqiyi/findJsonZipUrl";

        //获取aiqiyi zip资源包
        public static final String REQUEST_ALBUM_AIQIYI_ZIP = "cloudring-property-mobile-web/iqiyi/findJsonIqiyiZipUrl";
        //获取微视听zip资源包
        public static final String REQUEST_WEISHITING_ALBUM_ZIP = "cloudring-property-mobile-web/iqiyi/findJsonWstZipUrl";
        //获取yysc zip资源包
        public static final String REQUEST_YYSC_ZIP = "cloudring-property-mobile-web/donutApp/findJsonYyscZipUrl";
    }

}

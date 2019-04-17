package com.cloudring.arrobot.gelin.download;


import android.util.Log;

import com.cloudring.arrobot.gelin.MainApplication;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by:liulonggang on 2016/6/20 10:44
 * Class: NetworkClient
 * Description:封装OkHttp网络客户端
 */
public class NetworkClient {
    private static final Object obj = new Object();
    private static final long SESSION_TIME_OUT_SECONDS = 3; //
    private static final long READ_TIME_OUT_SECONDS = 20; //
    public static int CACHE_MAXSIZE = 1024 * 1024 * 20;

    private static NetworkClient INSTANCE;
    private OkHttpClient okHttpClient;
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final int DEFAULT_CACHE_SIZE = CACHE_MAXSIZE;
    private static final int DEFAULT_MAX_AGE = 60 * 60;
    private static final int DEFAULT_MAX_STALE_ONLINE = DEFAULT_MAX_AGE * 24;
    private static final int DEFAULT_MAX_STALE_OFFLINE = DEFAULT_MAX_AGE * 24 * 7;

    private NetworkClient() {
        initOkHttpClient();
    }

    private Retrofit retrofit;

    public static NetworkClient getInstance() {
        if (Check.isNull(INSTANCE)) {
            synchronized (obj) {
                if (Check.isNull(INSTANCE)) {
                    INSTANCE = new NetworkClient();
                }
            }
        }
        return INSTANCE;
    }


    /***
     * 拦截器，保存缓存的方法
     */
    private static Interceptor REQUEST_INTERCEPTOR = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.d("retrofitRequest", String.format("Sending request %s on %s",
                    request.url(), getPostRequestParam(request)));
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxStale(5, TimeUnit.SECONDS)      //这个是控制缓存的过时时间
                    .build();
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
            return chain.proceed(request);
        }

        public String getPostRequestParam(Request request) {
            RequestBody requestBody = request.body();
            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset( Util.UTF_8);
            }
            String paramsStr = buffer.readString(charset);
            return paramsStr;
        }
    };

    /***
     * 拦截器，保存缓存的方法
     */
    private static Interceptor RESPONSE_INTERCEPTOR = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            int maxAge;
            // 缓存数据
            // 控制缓存的最大生命时间
            if (!NetworkUtil.isConnected(MainApplication.getInstance().getApplicationContext())) {
                maxAge = DEFAULT_MAX_STALE_OFFLINE;
            } else {
                maxAge = DEFAULT_MAX_STALE_ONLINE;
            }
            return response.newBuilder()
                    .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        }
    };

//    /**
//     * 设置返回数据的  Interceptor  判断网络   没网读取缓存
//     */
//    private static Interceptor LoggingInterceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            long t1 = System.nanoTime();
//            HttpLoggingInterceptor.Logger.DEFAULT.log(String.format("Sending request %s on %s", request.url(), GsonUtils.toJson(request.body())));
//            Response response = chain.proceed(request);
//            long t2 = System.nanoTime();
//            HttpLoggingInterceptor.Logger.DEFAULT.log(String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
//            return response;
//        }
//    };

    public OkHttpClient getOkHttpClient() {
        if (Check.isNull(okHttpClient)) {
            initOkHttpClient();
        }
        return okHttpClient;
    }

    private void initOkHttpClient() {
//        File cacheFile = new File(PublicApp.getInstance().getContext().getCacheDir(), "httpResponse");
        String str=MainApplication.getInstance().getApplicationContext().getCacheDir().getAbsolutePath();
        File cacheFile = new File(MainApplication.getInstance().getApplicationContext().getExternalCacheDir(), "httpResponse");
        Cache cache = new Cache(cacheFile, DEFAULT_CACHE_SIZE);
//                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//                logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BODY);
//                Cache cache = new Cache(new File(PublicApp.getContext().getCacheDir().getAbsolutePath(), "http"), DEFAULT_CACHE_SIZE);
        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(REQUEST_INTERCEPTOR)
                .addNetworkInterceptor(RESPONSE_INTERCEPTOR)
//                .addInterceptor(mLoggingInterceptor)
                .connectTimeout(SESSION_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(MainApplication.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        if (Check.isNull(retrofit))
            retrofit = createRetrofit();
        return this.retrofit;
    }

    public <S> S getService(Class<S> service) {
        return getRetrofit().create(service);
    }
}


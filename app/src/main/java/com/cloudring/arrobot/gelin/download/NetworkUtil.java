package com.cloudring.arrobot.gelin.download;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;


import com.cloudring.arrobot.gelin.MainApplication;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtil {
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;
    private DhcpInfo dhcpInfo;
    private Context mContext;

    public NetworkUtil(Context context) {
        this.mContext = context;
        // Get the instance of the WifiManager
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    public void startScan() {
        mWifiManager.startScan();
        // Get the WifiInfo
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    public String getWiFiSSID() {

        String CurInfoStr = mWifiInfo.toString() + "";
        String CurSsidStr = mWifiInfo.getSSID().toString() + "";
        if (CurInfoStr.contains(CurSsidStr)) {
            return CurSsidStr;
        } else {
            return CurSsidStr.replaceAll("\"", "") + "";
        }

    }

    public String getGatewayaddr() {
        dhcpInfo = mWifiManager.getDhcpInfo();
        int gatewayip = dhcpInfo.gateway;
        if (gatewayip == 0)
            return "";
        return ((gatewayip & 0xff) + "." + (gatewayip >> 8 & 0xff) + "."
                + (gatewayip >> 16 & 0xff) + "." + (gatewayip >> 24 & 0xff));
    }

    /**
     * 检测当前网络是否畅通
     *
     * @param context 上下文
     * @return 真假
     */
    public static boolean isNetworkConnected(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null && wifiManager.isWifiEnabled()) {
                networkInfo = connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (networkInfo.isConnected())
                    return true;
            }
            networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isConnected();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getNetWorkTypeName() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MainApplication.getInstance().getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            return name;
        }
        return "";
    }

    /**
     * @return
     * @author suncat
     * @category 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     */
    public final Observable<Boolean> ping() {
        return Observable.fromCallable(() -> {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.SECONDS)
                    .readTimeout(1, TimeUnit.SECONDS)
                    .writeTimeout(1, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder()
                    .url("http://www.baidu.com")
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            response.close();
            return response.isSuccessful();
        }).subscribeOn(Schedulers.io());
    }

    /**
     * @return
     * @author suncat
     * @category 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     */
    public final void pingResult() {

//        ping().filter(aBoolean -> aBoolean).subscribe(aBoolean ->
//                ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.connect_fail)));
    }
}

package com.cloudring.arrobot.gelin.manager;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;

import com.cloudring.arrobot.gelin.mvp.modle.AppItem;
import com.cloudring.arrobot.gelin.mvp.modle.MainType;
import com.cloudring.arrobot.gelin.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/11/27 0027.
 */
public class PRClient implements PRListener, PRInterface {

    private static final String tag = "PRClient";

    private static PRClient clientInstance;

    private String mWeekDay;      //设置周期时间
    private String mWeekDayStr;      //设置周期时间


    /***********是否切换设备************/
    private boolean isChangeDevice = false;
    private boolean isGetPhoto = false;

    /**
     * 观察者队列
     */
    public ArrayList<IObserver> notifyObservers;

    private boolean isLogout = false;

    private Handler jniHandler;
    LooperThread lo = new LooperThread();

    private static int refeCount = 0;


    class LooperThread extends Thread {
        public Handler handler;

        public LooperThread() {
            setName("NotifyLooperThread");
        }

        public void run() {
            Looper.prepare();
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg == null)
                        return;
                    notifyObservers(msg.what, msg.arg1, msg.obj);
                }
            };
            Looper.loop();
        }

        void quit() {
            Looper.myLooper().quit();
        }
    }

    /**
     * 构造函数
     */
    private PRClient() {
        lo.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jniHandler = lo.handler;

        notifyObservers = new ArrayList<IObserver>();
    }

    /**
     * 供外部调用，获取唯一实例
     */
    public static synchronized PRClient getInstance() {
        if (clientInstance == null) {
            clientInstance = new PRClient();
        }
        refeCount++;
        return clientInstance;
    }

    /**
     * 释放这个PRClient，如果不继续使用PRClient，则必须释放此客户端以便 SingleExecutor释放资源
     */
    public synchronized void release() {
        refeCount--;
        if (refeCount == 0) {
            notifyObservers.clear();
            lo.quit();
        }
//        RxUtils.dispose(reconnectDisposable);
    }

    /**
     * 通知所有观察者
     */
    @Override
    public void notifyObservers(int event, int resultCode, Object data) {

        if (notifyObservers != null) {
            int iSize = notifyObservers.size();
            if (!LogUtil.LogOFF) {
                LogUtil.LogShow("notifyObservers iSize = " + iSize,
                        LogUtil.INFO);
            }
            /* 将循环从后面到前面，解决只发送一次的问题 */
            for (int i = iSize - 1; i >= 0; i--) {
                if (!LogUtil.LogOFF) {
                    LogUtil.LogShow("event =" + event + "index="
                            + i + ";size= " + iSize, LogUtil.DEBUG);
                }

                notifyObservers.get(i).notify(event, resultCode, data);
            }
        }
    }


    @Override
    public void HandleUSMgrEvent(char cmd, Object data) {
        // TODO Auto-generated method stub
        if (jniHandler != null) {
            Message msg = jniHandler.obtainMessage();
            msg.what = cmd;// 事件类型
            msg.arg1 = 0;// 事件返回码
            msg.obj = data;
            jniHandler.sendMessage(msg);
        }
    }

    public void initClient() {
//        CloudringSDK.getInstance().setUserState(mListen);
    }

    public void sendPushMessage(char cmd, String str) {
        HandleUSMgrEvent(cmd, str);
    }

    /**
     * 注册观察者
     */
    public synchronized void registerObserver(IObserver o) {
        if (!LogUtil.LogOFF) {
            LogUtil.LogShow("registerObserver", LogUtil.ERROR);
        }
        // 避免Array出现重复
        if (!notifyObservers.contains(o))
            notifyObservers.add(o);
    }

    /**
     * 注销观察
     */
    public synchronized void unRegisterObserver(IObserver o) {
        if (!LogUtil.LogOFF) {
            LogUtil.LogShow( "unRegisterObserver o = " + o,
                    LogUtil.ERROR);
        }
        if (notifyObservers != null && o != null)
            notifyObservers.remove(o);
    }

    /**
     * 获取七牛ServerUrl
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getQnServerUrl(Context context) {

//        Map<String, Object> map = new HashMap<>();
//        map.put("token", Account.getLoginToken());
//        QiNiuConfigRequest qiNiuConfigRequest = new QiNiuConfigRequest("1", Account.getUserId(), APIUtils.APP_ID);
//        CompositeDisposable disposables=new CompositeDisposable();
//        NetworkClient.getInstance().getService(APIService.class).getQiNiuConfig(map, qiNiuConfigRequest)
//                .doOnSubscribe(disposable -> disposables.add(disposables))
//                .compose(TransformUtils.defaultSchedulers())
//                .subscribe(qiNiuConfigResponse -> {
//                    if (qiNiuConfigResponse.isResult()) {
//                        LogUtils.d("getQnServerUrl", "accessUrl: " + qiNiuConfigResponse.data.accessUrl);
//                        Account.setQnServerUrl(qiNiuConfigResponse.data.accessUrl);
//                    }
//                }, Timber::w);
//        RxUtils.dispose(disposables);

    }


    /************获取我的绘本***********/
//    private List<BookItem> mMyBookList = new ArrayList<>();
//
//    public List<BookItem> getmMyBookList() {
//        return mMyBookList;
//    }
//
//    public void setmMyBookList(List<BookItem> mMyBookList) {
//        this.mMyBookList = mMyBookList;
//    }
//    public void setEmptymMyBookList() {
//        if (mMyBookList == null)
//            mMyBookList = new ArrayList<>();
//        mMyBookList.clear();
//
//    }

//    public void deleteMyBook(String mBookItemid) {
//
//        if (mBookItemid == null)
//            return;
//        if (mMyBookList == null)
//            return;
//        if (mMyBookList.size() > 0) {
//            for (BookItem item : mMyBookList) {
//                if (item.getId().equals(mBookItemid)) {
//                    mMyBookList.remove(item);
//                    return;
//                }
//            }
//        }
//    }

    private List<AppItem> apkResultList = new ArrayList<>();
    public void setApkResultList(List<AppItem> apkResultList) {
        this.apkResultList = apkResultList;
    }

    public List<AppItem> getApkResultList() {
        return apkResultList;
    }

    private List<MainType> typeList = new ArrayList<>();

    public void setTypeResultList(List<MainType> apkResultList){
        this.typeList = apkResultList;
    }

    public List<MainType> getTypeResultList(){
        return typeList;
    }

//    /************获取今日推荐***********/
//    private List<BookItem> mTodayBookList = new ArrayList<>();
//
//    public List<BookItem> getmTodayBookList() {
//        return mTodayBookList;
//    }
//
//    public void setmTodayBookList(List<BookItem> mTodayBookList) {
//        this.mTodayBookList = mTodayBookList;
//    }
//
//    /************获取上架精选***********/
//    private List<BookItem> mHotBookList = new ArrayList<>();
//
//    public List<BookItem> getmHotBookList() {
//        return mHotBookList;
//    }
//
//    public void setmHotBookList(List<BookItem> mHotBookList) {
//        this.mHotBookList = mHotBookList;
//    }
//
//    /************获取绘本详情***********/
//    private List<BookInfoItem> mBookInfoList = new ArrayList<>();
//    private List<BookInfoItem> mBookInfoComList = new ArrayList<>();  //合并 页数
//
//    public List<BookInfoItem> getmBookInfoList() {
//        return mBookInfoList;
//    }
//
//    public void setmBookInfoList(List<BookInfoItem> mBookInfoList) {
//        this.mBookInfoList = mBookInfoList;
//    }
//
//    public List<BookInfoItem> getmBookInfoComList() {
//        return mBookInfoComList;
//    }
//
//    public void setmBookInfoComList() {
//        if (mBookInfoComList == null)
//            mBookInfoComList = new ArrayList<>();
//        mBookInfoComList.clear();
//        if (mBookInfoList != null && mBookInfoList.size() > 0) {
//            for (int i = 0; i < mBookInfoList.size(); i++) {
//                if (i == 0) {
//                    mBookInfoComList.add(mBookInfoList.get(i));
//                } else {
//                    if (i % 2 == 1 && i < mBookInfoList.size() - 1) {
//                        BookInfoItem item = mBookInfoList.get(i);
//                        int page1 = Integer.valueOf(item.getPageNo());
//
//                        BookInfoItem item1 = mBookInfoList.get(i + 1);
//                        int page2 = Integer.valueOf(item1.getPageNo());
////                        if((page1+1)==page2)
////                        {
//                        item.setPageNext(item1.getPageNo());
//                        item.setPageNextImgurl(item1.getImgUrl());
//                        mBookInfoComList.add(item);
////                        }
//                    } else if (i == mBookInfoList.size() - 1) {
//                        mBookInfoComList.add(mBookInfoList.get(i));
//                    }
//                }
//            }
//        } else {
//
//        }
//    }

    /************文件上传***********/
    private boolean  isTipShow=false;

    public boolean isTipShow() {
        return isTipShow;
    }

    public void setTipShow(boolean tipShow) {
        isTipShow = tipShow;
    }
}

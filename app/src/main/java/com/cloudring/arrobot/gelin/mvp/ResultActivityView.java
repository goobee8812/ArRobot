package com.cloudring.arrobot.gelin.mvp;

import com.arellomobile.mvp.MvpView;


public interface ResultActivityView extends MvpView{

    void showMsg(String msg);
    void showMsg(int msg);

    void loadFail();


    void refreshList(String bookCount, String acount, String time);   //刷新列表

}

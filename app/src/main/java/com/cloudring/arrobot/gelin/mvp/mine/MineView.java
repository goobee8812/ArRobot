package com.cloudring.arrobot.gelin.mvp.mine;

import com.arellomobile.mvp.MvpView;


public interface MineView extends MvpView{

    void showMsg(String msg);
    void showMsg(int msg);

    void loadFail();


    void refreshList(String bookCount, String acount, String time);   //刷新列表

}

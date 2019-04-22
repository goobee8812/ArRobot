package com.cloudring.arrobot.gelin.mvp.category;

import com.arellomobile.mvp.MvpView;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;

import java.util.List;


public interface ResultView extends MvpView{

    void showMsg(String msg);
    void showMsg(int msg);

    void loadFail();


    void refreshList(String bookCount, String acount, String time);   //刷新列表

    void refreshList(List<AppItem> list);   //刷新列表

}

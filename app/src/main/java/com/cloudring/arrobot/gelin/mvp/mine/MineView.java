package com.cloudring.arrobot.gelin.mvp.mine;

import com.arellomobile.mvp.MvpView;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;

import java.util.List;


public interface MineView extends MvpView{

    void showMsg(String msg);
    void showMsg(int msg);

    void loadFail();


    void refreshList(List<AppItem> list);   //刷新列表

}

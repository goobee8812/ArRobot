package com.cloudring.arrobot.gelin;

import com.arellomobile.mvp.MvpView;
import com.cloudring.arrobot.gelin.mvp.modle.MainType;

import java.util.List;

public interface MainView extends MvpView{
    void showMsg(String msg);

    void showMsg(int msg);

    void loadFail();

    void refreshList(List<MainType> list);   //刷新列表
}

package com.cloudring.arrobot.gelin.mvp.mine;

import com.arellomobile.mvp.InjectViewState;
import com.cloudring.arrobot.gelin.base.BasePresenter;
import com.cloudring.arrobot.gelin.contentdb.AppInfo;
import com.cloudring.arrobot.gelin.contentdb.AppInfoDao;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;
import com.cloudring.arrobot.gelin.utils.GlobalUtil;
import com.cloudring.arrobot.gelin.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;


@InjectViewState
public class MinePresenter extends BasePresenter<MineView> {

    // 读取数据库内容
    public void getMineData(String type){
        List<AppInfo> list = new ArrayList<>();
        if (type.equals(GlobalUtil.CATEGORY_TYPE_COLLECTION)){
            list = AppInfoDao.getCollectionList();
        }else if (type.equals(GlobalUtil.CATEGORY_TYPE_MY_GAME)){
            list = AppInfoDao.getGameAppList();
        }
        List<AppItem> appItems = new ArrayList<>();
        if (list.size() > 0){
            for (AppInfo info : list){
                AppItem appItem = new AppItem(info.getId(),info.getFileName(),info.getPackageName(),info.getIcon1(),info.getTopCategoryId());
                appItems.add(appItem);
            }
        }
        getViewState().refreshList(appItems);
    }

    // 读取数据库内容
    public void getMineCategoryData(String type,String categoryId){
        List<AppInfo> list = new ArrayList<>();
        LogUtil.e("type: " + type + " ---categoryId:" + categoryId);
        list = AppInfoDao.getCategoryAppList(type,categoryId);
        List<AppItem> appItems = new ArrayList<>();
        if (list.size() > 0){
            for (AppInfo info : list){
                AppItem appItem = new AppItem(info.getId(),info.getFileName(),info.getPackageName(),info.getIcon1(),info.getTopCategoryId());
                appItems.add(appItem);
            }
        }
        LogUtil.e("list.size = "+list.size());
        getViewState().refreshList(appItems);
    }
}

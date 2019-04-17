package com.cloudring.arrobot.gelin.base;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BasePresenter<View extends MvpView> extends MvpPresenter<View> {
    private CompositeDisposable compositeDisposable;

    @Override
    public void onDestroy() {
        deleteDispose();
        super.onDestroy();
    }

    public void addDispose(@NonNull Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    protected void deleteDispose() {
        if (compositeDisposable != null)
            compositeDisposable.clear();
    }

}

package com.lsh.lib.android.mvp.presenter;


import com.lsh.lib.android.mvp.view.MvpView;

/**
 * presenter 绑定和解绑
 * author:liush
 * version: 2016/5/18  14:25
 */
public interface Presenter<V extends MvpView> {
    void attachView(V mvpView);

    void detachView();
}

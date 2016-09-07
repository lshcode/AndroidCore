package com.lsh.lib.android.mvp.presenter;

import com.lsh.lib.android.mvp.view.MvpView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * presenter层
 * author:liush
 * version: 2016/5/18  14:27
 */
public class MvpPresenter<T extends MvpView> implements Presenter<T> {
    private T mMvpView;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
        mSubscriptions.unsubscribe();
    }

    public void addSubscription(Subscription subscription) {
        mSubscriptions.add(subscription);
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

}

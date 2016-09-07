package com.lsh.lib.android.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsh.lib.android.mvp.presenter.MvpPresenter;
import com.lsh.lib.android.utils.rx.RxUtils;

import rx.subscriptions.CompositeSubscription;

/**
 * Author:liush
 * Date:2016/8/3
 */
public abstract class BFragment<T extends MvpPresenter> extends Fragment {
    protected Context mFragmentContext;
    protected CompositeSubscription mFragmentSubscriptions = new CompositeSubscription();
    protected T fPresenter = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragmentContext = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentSubscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(mFragmentSubscriptions);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fPresenter != null) {
            fPresenter.detachView();
        }
        RxUtils.unsubscribeIfNotNull(mFragmentSubscriptions);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}

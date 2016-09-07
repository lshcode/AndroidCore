package com.lsh.lib.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.lsh.lib.android.app.BApplication;
import com.lsh.lib.android.mvp.presenter.MvpPresenter;
import com.lsh.lib.android.utils.rx.RxUtils;
import com.lsh.lib.android.widget.divider.RecycleViewDivider;
import com.squareup.leakcanary.RefWatcher;

import rx.subscriptions.CompositeSubscription;

/**
 * activity基类
 * Author:liush
 * Date:2016/7/25
 */
public abstract class BActivity<T extends MvpPresenter> extends AppCompatActivity {
    protected Context mContext;
    protected CompositeSubscription mSubscriptions = new CompositeSubscription();
    protected T presenter = null;
    private Fragment currentFragment = null;
    protected FragmentManager fragmentManager = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mSubscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(mSubscriptions);
        RefWatcher refWatcher = BApplication.getRefWatcher(this);
        refWatcher.watch(this);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
        RxUtils.unsubscribeIfNotNull(mSubscriptions);
    }


    /**
     * 动态添加Fragment
     *
     * @param contains 添加位置
     * @param to       需要添加的fragment
     */
    public void addFragment(int contains, Fragment to) {
        if (contains <= 0 || to == null || currentFragment == to) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (currentFragment == null) {
            ft.add(contains, to);
        } else {
            if (!to.isAdded()) {
                ft.add(contains, to);
            }
            currentFragment.setUserVisibleHint(false);
            ft.hide(currentFragment);
            to.setUserVisibleHint(true);
            ft.show(to);
        }
        currentFragment = to;
        ft.commit();
    }

    /**
     * RecyclerView设置
     * 水平线  竖直方向
     *
     * @param recycleView
     */
    public void setRecyclerView(RecyclerView recycleView) {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //垂直方向
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);
        recycleView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
    }

    /**
     * RecyclerView设置
     * 水平线  竖直方向
     *
     * @param recycleView
     */
    public void setRecyclerViewNoline(RecyclerView recycleView) {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //垂直方向
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);
    }

    /**
     * item大小不固定
     *
     * @param recycleView
     */
    public void setRecyclerViewNoline(RecyclerView recycleView, boolean hasFixedSize) {
        recycleView.setHasFixedSize(hasFixedSize);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //垂直方向
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);
    }

    /**
     * RecyclerView设置
     * 水平线  竖直方向
     *
     * @param recycleView
     */
    public void setGridRecyclerView(RecyclerView recycleView, int column) {
        recycleView.setHasFixedSize(true);
        GridLayoutManager girdLayoutManager = new GridLayoutManager(this, column);
        recycleView.setLayoutManager(girdLayoutManager);
    }


    /**
     * 设置swipeRefreshLayout  颜色
     *
     * @param swipeRefreshLayout
     */
    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
    }

    /**
     * 设置swipeRefreshLayout开始出现等待框
     *
     * @param swipeRefreshLayout
     */
    public void startRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources()
                        .getDisplayMetrics()));
    }

    //-----------------------------Activity跳转------------------------------------------------
    public void toActivity(Class<AppCompatActivity> activity) {
        startActivity(new Intent(this, activity));
    }

    public void toActivity(Class<AppCompatActivity> activity, Bundle bundle) {
        Intent intent = new Intent(new Intent(this, activity));
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void toActivityForResult(Class<AppCompatActivity> activity, int requestCode) {
        Intent intent = new Intent(new Intent(this, activity));
        startActivityForResult(new Intent(this, activity), requestCode);
    }

    public void toActivityForResult(Class<AppCompatActivity> activity, int requestCode, Bundle bundle) {
        Intent intent = new Intent(new Intent(this, activity));
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(new Intent(this, activity), requestCode);
    }

    /**
     * 提示
     *
     * @param msg
     */
    public void shotToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void shotToast(int msgID) {
        Toast.makeText(this, getResources().getString(msgID), Toast.LENGTH_SHORT).show();
    }

    public <T extends View> T findView(int resId) {
        return (T) (findViewById(resId));
    }
}

package com.lsh.lib.android.mvp.view;

/**
 * view层
 * author:liush
 * version: 2016/5/18  14:25
 */
public interface MvpView {

    /**
     * 请求开始
     *
     * @param code
     */
    void onBegin(int code);

    /**
     * 请求结束
     *
     * @param code
     */
    void onFinish(int code);
}

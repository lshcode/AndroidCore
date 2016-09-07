package com.lsh.lib.android.app;

import java.util.HashSet;

/**
 * 保存数据
 * Author:liush
 * Date:2016/7/25
 */
public class BData {
    /**
     * 保存cookies
     */
    private HashSet<String> cookies = new HashSet<>();


    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final BData INSTANCE = new BData();
    }

    //获取单例
    public static BData getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public HashSet<String> getCookies() {
        return cookies;
    }

    public void setCookies(HashSet<String> cookies) {
        this.cookies = cookies;
    }
}

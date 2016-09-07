package com.lsh.lib.android.app;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Author:liush
 * Date:2016/8/3
 */
public class BApplication extends Application {
    //检测内存泄露
    private RefWatcher refWatcher;
    public static BApplication instence;

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        instence = this;
    }

    public static RefWatcher getRefWatcher(Context context) {
        BApplication application = (BApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}

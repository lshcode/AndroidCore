package com.lsh.lib.android.utils.rx;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * RxJava工具
 * author:liush
 * version: 2016/4/21  15:50
 */
public class RxUtils {
    /**
     * 取消订阅
     *
     * @param subscription
     */
    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    /**
     * 创建订阅对象
     *
     * @param subscription
     * @return
     */
    public static CompositeSubscription getNewCompositeSubIfUnsubscribed(CompositeSubscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new CompositeSubscription();
        }

        return subscription;
    }
}
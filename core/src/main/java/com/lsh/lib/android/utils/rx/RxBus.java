package com.lsh.lib.android.utils.rx;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 发布事件
 * author:liush
 * version: 2016/4/21  15:49
 */
public class RxBus {

    private final PublishSubject<BusEvent> publishSubject = PublishSubject.create();
    private final Subject<BusEvent, BusEvent> mBus = new SerializedSubject<BusEvent, BusEvent>(publishSubject);

    private static class RxBusHolder {
        private static final RxBus INSTANCE = new RxBus();
    }

    private RxBus() {
    }

    /**
     * 单利模式
     *
     * @return
     */
    public static final RxBus getInstance() {
        return RxBusHolder.INSTANCE;
    }

    /**
     * 发送事件
     *
     * @param event
     */
    public void send(BusEvent event) {
        if (hasObservers()) {
            mBus.onNext(event);
        }

    }

    public Observable<BusEvent> toObserverable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }
}
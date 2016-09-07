package com.lsh.lib.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lsh.lib.android.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 自定义到实际是TextView
 * author:liush
 * version: 2016/5/28  13:54
 */
public class CountDownTextView extends TextView {

    private Subscription subscription = null;
    public int countTime = 60;
    public String data = "";

    public CountDownTextView(Context context) {
        super(context);
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CountDownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    /**
     * 设置计时时间
     *
     * @param countTime
     */
    public void setCountTime(int countTime) {
        if (countTime <= 0) {
            throw new IllegalArgumentException("计时时间不能小于1");
        } else
            this.countTime = countTime;
    }

    /**
     * 开始计时
     */
    public void startCount() {
        Observable<Integer> timeObservable = Observable.interval(0, 1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long aLong) {
                        return countTime - aLong.intValue();
                    }
                }).take(countTime + 1);
        subscription = timeObservable.doOnSubscribe(new Action0() {//订阅时触发
            @Override
            public void call() {
                setEnabled(false);
                setBackgroundResource(R.drawable.shape_corner_gray);
            }
        }).doOnTerminate(new Action0() {//DoOnTerminate会在Observable结束前触发回调，无论是正常还是异常终止
            @Override
            public void call() {
                setEnabled(true);
                setBackgroundResource(R.drawable.shape_corner_blue);
                setText(R.string.register_verify_code_get);
            }
        }).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                setEnabled(true);
                setBackgroundResource(R.drawable.shape_corner_blue);
                setText(R.string.register_verify_code_get);
            }
        })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        setText("" + integer);
                    }
                });
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopCount();
    }

    /**
     * 停止倒计时
     */
    public void stopCount() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}

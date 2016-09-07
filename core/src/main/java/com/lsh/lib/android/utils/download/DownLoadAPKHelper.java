package com.lsh.lib.android.utils.download;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.lsh.lib.android.utils.file.FileUtis;
import com.lsh.lib.android.utils.format.FormatUtil;
import com.lsh.lib.android.utils.rx.BusEvent;
import com.lsh.lib.android.utils.rx.RxBus;
import com.lsh.lib.android.utils.rx.event.FileLoadEvent;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 下载等待成处理类
 * author:liush
 * version: 2016/7/11  11:04
 */
public class DownLoadAPKHelper {
    private static final int DEFAULT_TIMEOUT = 5;
    private Context ctx;
    private ProgressDialog loading;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    private DownLoadAPKHelper(Context ctx) {
        this.ctx = ctx;
        loading = new ProgressDialog(ctx);
        loading.setProgress(ProgressDialog.STYLE_SPINNER);//圆形
        loading.setIndeterminate(false);
        loading.setCancelable(false);
        initEventBus();
    }

    public static DownLoadAPKHelper with(Context ctx) {
        return new DownLoadAPKHelper(ctx);
    }


    /**
     * 下载
     *
     * @param url 下载地址
     */
    public void downLoad(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        String fileName = url.substring(url.lastIndexOf("/") + 1, url.length() - 1);
        final File file = FileUtis.createCacheFile(ctx, fileName);
        try {
            if (file.exists()) {
                if (file.delete()) {
                    file.createNewFile();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        downloadOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    byte[] bytes = response.body().bytes();

                    if (FileUtis.writeOnFile(file, bytes)) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file),
                                "application/vnd.android.package-archive");
                        ctx.startActivity(intent);
                    }
                }
            }
        });

    }

    /**
     * eventbus 监听
     */
    public void initEventBus() {
        mSubscriptions.add(RxBus.getInstance()
                .toObserverable()
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BusEvent>() {
                    @Override
                    public void call(BusEvent rxBusEvent) {
                        if (rxBusEvent instanceof FileLoadEvent) {
                            long read = ((FileLoadEvent) rxBusEvent).getRead();
                            long total = ((FileLoadEvent) rxBusEvent).getTotal();
                            if (read == total) {
                                loading.dismiss();
                            } else {
                                String s = String.format("正在下载：(%s/%s)",
                                        FormatUtil.getFormatSize(read),
                                        FormatUtil.getFormatSize(total));
                                loading.setMessage(s);
                                if (!loading.isShowing()) {
                                    loading.show();
                                }
                            }

                        }
                    }
                }));
    }

    /**
     * 初始化OkHttpClient
     *
     * @return
     */
    private OkHttpClient downloadOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse
                        .newBuilder()
                        .body(new FileResponseBody(originalResponse.body()))
                        .build();
            }
        });
        return builder.build();
    }

    /**
     * 取消订阅
     */
    public void cancel() {
        if (mSubscriptions != null && !mSubscriptions.isUnsubscribed()) {
            mSubscriptions.unsubscribe();
        }
    }
}

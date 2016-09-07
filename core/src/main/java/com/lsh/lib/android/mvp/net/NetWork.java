package com.lsh.lib.android.mvp.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lsh.lib.android.utils.security.Base64Utils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求工具
 * Author:liush
 * Date:2016/7/25
 */
public abstract class NetWork {
    public OkHttpClient okHttpClient = new OkHttpClient();
    private Converter.Factory gsonConverterFactory = null;
    private CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private Map<String, Retrofit> retrofitMap = new HashMap<>();

    public NetWork() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();//使用 gson coverter，统一日期请求格式
        gsonConverterFactory = GsonConverterFactory.create(gson);
    }

    /**
     * 获取 Retrofit
     *
     * @param baseurl
     * @return
     */
    public Retrofit getRetrofit(String baseurl) {
        String key = Base64Utils.encode(baseurl.getBytes());
        Retrofit retrofit = retrofitMap.get(key);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(baseurl)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            retrofitMap.put(key, retrofit);
        }
        return retrofit;
    }

    /**
     * 获取 OkHttpClient
     *
     * @return
     */
    public abstract OkHttpClient getOkHttpClient();
}

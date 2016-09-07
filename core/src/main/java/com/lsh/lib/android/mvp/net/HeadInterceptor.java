package com.lsh.lib.android.mvp.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加头部
 * Author:liush
 * Date:2016/8/4
 */
public class HeadInterceptor implements Interceptor {
    Map<String, String> map = new HashMap<>();

    public HeadInterceptor(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}

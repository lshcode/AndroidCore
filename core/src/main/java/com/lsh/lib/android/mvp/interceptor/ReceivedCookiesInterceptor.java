package com.lsh.lib.android.mvp.interceptor;

import com.lsh.lib.android.app.BData;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 拦截获取cookies
 * author:liush
 * version: 2016/4/18  15:14
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            BData.getInstance().setCookies(cookies);
        }
        return originalResponse;
    }
}
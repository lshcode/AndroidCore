package com.lsh.lib.android.mvp.interceptor;

import com.lsh.lib.android.app.BData;
import com.lsh.lib.android.utils.log.CLog;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求设置cookies
 * author:liush
 * version: 2016/4/18  15:14
 */
public class AddCookiesInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> cookies = BData.getInstance().getCookies();
        for (String cookie : cookies) {
            builder.addHeader("Cookie", cookie);
            CLog.e("OkHttp", "Adding Header: " + cookie);
            // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        }
        return chain.proceed(builder.build());
    }
}

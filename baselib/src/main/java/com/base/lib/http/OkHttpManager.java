package com.base.lib.http;

import com.base.lib.util.AppUtil;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.orhanobut.logger.Logger;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by yixiaofei on 2017/2/24 0024.
 */

public class OkHttpManager {

    private static OkHttpClient mOkHttpClient;
    /**
     * 获取OkHttpClient对象
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.d(message);
            }
        });
        loggingInterceptor.setLevel(level);
        /*****调试****/
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new RequestInterceptor())
                .build();
        /****正式**/
//        mOkHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .addInterceptor(new RequestInterceptor())
//                .readTimeout(30, TimeUnit.SECONDS).build();
        return mOkHttpClient;
    }
    private static class RequestInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Content-Length","0")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Accept","application/json")
                    .addHeader("Agent","Android")
                    .addHeader("Version", "1.4.0")
                    .build();
            return chain.proceed(request);
        }
    }
}

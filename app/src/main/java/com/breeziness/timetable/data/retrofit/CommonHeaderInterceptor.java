package com.breeziness.timetable.data.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加一些统一的头部
 */
public class CommonHeaderInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder()
                //.addHeader("Referer","Referer http://bkjw.guet.edu.cn/")//引导页必须添加
                .addHeader("Host","bkjw.guet.edu.cn")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0");
        return chain.proceed(request.build());
    }
}

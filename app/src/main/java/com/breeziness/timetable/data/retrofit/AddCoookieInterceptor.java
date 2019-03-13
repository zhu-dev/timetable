package com.breeziness.timetable.data.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加cookie的拦截器
 * 将cookie从sp中读出
 */
public class AddCoookieInterceptor implements Interceptor {
    private Context context;//sp需要context,

    public AddCoookieInterceptor(Context context) {
        super();
        this.context = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        SharedPreferences sp = context.getSharedPreferences("Cookie", Context.MODE_PRIVATE);
        Observable.just(sp.getString("Cookie", ""))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        builder.addHeader("Cookie", s);
                    }
                });
        return chain.proceed(builder.build());
    }
}

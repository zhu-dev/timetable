package com.breeziness.timetable.data.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


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

    private static final String TAG = "AddCoookieInterceptor";
    private Context context;//sp需要context,

    public AddCoookieInterceptor(Context context) {
        super();
        this.context = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        StringBuilder sb = new StringBuilder();
        SharedPreferences sp = context.getSharedPreferences("Cookie", Context.MODE_PRIVATE);
      //  Log.e(TAG, "apply:-----cookie--add----"+sp.getString("Cookie", ""));
        if (!sp.getString("session","").equals("")){
            sb.append(sp.getString("session","")).append(";").append(sp.getString("Cookie", ""));
            builder.addHeader("Cookie", sb.toString());
        }else {
            builder.addHeader("Cookie", sp.getString("Cookie", ""));
        }

        return chain.proceed(builder.build());
    }
}

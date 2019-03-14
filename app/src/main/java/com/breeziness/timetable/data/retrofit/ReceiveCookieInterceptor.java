package com.breeziness.timetable.data.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 获取cookie的拦截器
 * 从相应体里面获取cookie，存到sp里
 */
public class ReceiveCookieInterceptor implements Interceptor {
    private Context context;//sp需要context,


    private static final String TAG = "ReceiveCookieIntercepto";

    public ReceiveCookieInterceptor(Context context) {
        super();
        this.context = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        List<String> cookies = response.headers("Set-Cookie");
        final StringBuilder sb = new StringBuilder();
        for (String cookie : cookies) {
            if (!cookie.isEmpty()) {
                if (cookie.contains(".ASPXAUTH")) {
                    sb.append(cookie);
                    SharedPreferences sp = context.getSharedPreferences("Cookie", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("Cookie", sb.toString());
                    editor.apply();
                }else if (cookie.contains("ASP.NET_SessionId")){
                    sb.append(cookie);
                    SharedPreferences sp = context.getSharedPreferences("Cookie", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("Cookie", sb.toString());
                    editor.putString("session",sb.toString());//单独保存session
                    editor.apply();
                }


            }
        }


//        if (!response.header("Set-Cookie").isEmpty()) {
//            //final StringBuilder sb = new StringBuilder();
//
//            Log.e(TAG, "apply:-----cookie--receiver----" + response.header("Set-Cookie"));
//
//            SharedPreferences sp = context.getSharedPreferences("Cookie", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putString("Cookie", response.header("Set-Cookie"));
//            editor.apply();
//        }
        return response;
    }
}

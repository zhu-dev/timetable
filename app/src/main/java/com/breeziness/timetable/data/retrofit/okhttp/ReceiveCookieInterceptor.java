package com.breeziness.timetable.data.retrofit.okhttp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 获取cookie的拦截器
 * 从相应体里面获取cookie，存到sp里
 */
public class ReceiveCookieInterceptor implements Interceptor {
    private Context context;//sp需要context,


    private static final String TAG = "ReceiveCookieInterceptor";

    public ReceiveCookieInterceptor(Context context) {
        super();
        this.context = context;
    }

    /**
     * 接受cookie并保存到sp中
     * @param chain
     * @return
     * @throws IOException
     */
    @SuppressLint("CheckResult")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        List<String> cookies = response.headers("Set-Cookie");
        final StringBuilder sb = new StringBuilder();
        for (String cookie : cookies) {
            if (!cookie.isEmpty()) {
                //刚开始并没有".ASPXAUTH"，只有seesion,但是请求登录后会返回".ASPXAUTH",后面的操作都需要，
                // 同时还需要拼接session在后面，才能正常访问，所以要单独把保存session
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

        return response;
    }
}

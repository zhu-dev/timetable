package com.breeziness.timetable.data.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

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

    public ReceiveCookieInterceptor(Context context) {
        super();
        this.context = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if (!response.header("Set-Cookie").isEmpty()) {
            final StringBuilder sb = new StringBuilder();
            Observable.fromArray(response.header("Set-Cookie"))
                    .map(new Function<String, String>() {
                        @Override
                        public String apply(String s) throws Exception {
                            String[] strArray = s.split(";");
                            return strArray[0];
                        }
                    })
                    .subscribe(new Consumer<String>() {
                        public void accept(String s) throws Exception {
                            sb.append(s).append(";");
                        }
                    });
            SharedPreferences sp = context.getSharedPreferences("Cookie", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Cookie", sb.toString());
            editor.apply();
        }
        return response;
    }
}

package com.breeziness.timetable.data.retrofit;

import android.util.Log;

import com.breeziness.timetable.base.BaseApplication;
import com.breeziness.timetable.data.retrofit.okhttp.AddCoookieInterceptor;
import com.breeziness.timetable.data.retrofit.okhttp.CommonHeaderInterceptor;
import com.breeziness.timetable.data.retrofit.okhttp.ReceiveCookieInterceptor;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 封装一个Retrofit工具类
 */
public class RetrofitFactory {

    private static final String BASE_URL = "http://bkjw.guet.edu.cn/";
    private static final long TIMEOUT = 30;//设置超时时间

    // Retrofit是基于OkHttpClient的，可以创建一个OkHttpClient进行一些配置
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()

            //获取服务器返回的cookie的拦截器
            .addInterceptor(new ReceiveCookieInterceptor(BaseApplication.getContext()))
            //添加cookie到头部的拦截器
            .addInterceptor(new AddCoookieInterceptor(BaseApplication.getContext()))
            //添加统一的拦截器
            .addInterceptor(new CommonHeaderInterceptor())
            //设置拦截器打印接口信息，方便接口调试，这个拦截器必须在后面不然header拦截器不起作用
            .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.e("TAG", "log:" + message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BASIC))
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build();

    private static RetrofitService retrofitService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            /*设置Gson转换器*/
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .setLenient()
                    .create()))
            /*设置Rxjava适配器*/
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RetrofitService.class);

    /*获取RetrofitService接口实现实例*/
    public static RetrofitService getInstance() {
        return retrofitService;
    }
}

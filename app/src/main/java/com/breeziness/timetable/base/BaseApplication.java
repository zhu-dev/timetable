package com.breeziness.timetable.base;

import android.app.Application;
import android.content.Context;

/**
 * 管理程序内一些全局的信息
 * 获取全局的Context
 */
public class BaseApplication extends Application {
    private static Context context;//全局的Context

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();//获得应用程序级别的Context
    }

    /**
     * 获取应用程序的Context
     * @return
     */
    public static Context getContext() {
        return context;
    }
}

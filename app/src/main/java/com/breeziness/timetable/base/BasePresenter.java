package com.breeziness.timetable.base;

import io.reactivex.disposables.Disposable;

public interface BasePresenter {

    //初始化操作
    void start();

    //Activity关闭把view对象置为空
    void detach();

    //将网络请求的每一个disposable添加进入CompositeDisposable，再退出时候一并注销
    void addDisposable(Disposable subscription);

    //注销所有请求
    void unDisposable();

    //当数据丢失时的处理
    boolean isDataMissing();

}

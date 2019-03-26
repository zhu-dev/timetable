package com.breeziness.timetable.coursemain;

import android.annotation.SuppressLint;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CoursePresenter implements CourseContract.Presenter {

    private static final String TAG = "CoursePresenter";
    private CourseContract.View view;//持有View引用

    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private CompositeDisposable mCompositeDisposable;

    public CoursePresenter(CourseContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }


    @Override
    public void start() {
        //这里可以进行一些初始化操作
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCource() {

    }

    @Override
    public void detach() {
        this.view = null;//设置View对象为空，释放资源
        unDisposable();//注销
    }

    /**
     * 将disposable对象添加到管理容器中
     *
     * @param subscription
     */
    @Override
    public void addDisposable(Disposable subscription) {
        /*是否已经解绑或者容器为空时*/
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);//将订阅添加到容器中
    }

    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    @Override
    public void unDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    @Override
    public boolean isDataMissing() {
        return false;
    }


}
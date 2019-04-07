package com.breeziness.timetable.coursemain;

import android.annotation.SuppressLint;
import android.util.Log;


import com.breeziness.timetable.base.BaseActivity;
import com.breeziness.timetable.base.BaseApplication;
import com.breeziness.timetable.data.bean.CourseBean;
import com.breeziness.timetable.data.db.DataBaseManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
        DataBaseManager.getInstance(BaseApplication.getContext()).getAllCourse("course")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CourseBean.DataBean>>() {
                    @Override
                    public void accept(List<CourseBean.DataBean> dataBeans) throws Exception {
                       view.setCource(dataBeans);
                        Log.e(TAG, "accept: ------"+dataBeans.get(5).getCname());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // Log.e(TAG, "accept: -----throwable------" + throwable);
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

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
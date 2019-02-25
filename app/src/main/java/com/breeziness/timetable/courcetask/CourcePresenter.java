package com.breeziness.timetable.courcetask;

import android.annotation.SuppressLint;
import android.util.Log;

import com.breeziness.timetable.data.retrofit.RetrofitFactory;



import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CourcePresenter implements CourceContract.Presenter {

    private static final String TAG = "CourcePresenter";
    private CourceContract.View view;//持有View引用

    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private CompositeDisposable mCompositeDisposable;

    public CourcePresenter( CourceContract.View view) {
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
        RetrofitFactory.getInstance()
                .getCourceBy()
               .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showProgressBar(true);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        view.setCource(s);
                        Log.d(TAG, "accept: "+s);
                        view.showProgressBar(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showProgressBar(false);
                        Log.e(TAG,"访问出错");
                    }
                });

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
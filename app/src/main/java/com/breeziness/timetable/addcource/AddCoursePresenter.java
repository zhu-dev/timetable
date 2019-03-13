package com.breeziness.timetable.addcource;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.breeziness.timetable.data.bean.CourseBean;
import com.breeziness.timetable.data.bean.LoginBean;
import com.breeziness.timetable.data.retrofit.RetrofitFactory;
import com.breeziness.timetable.util.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class AddCoursePresenter implements AddCourseContract.Presenter {

    private static final String TAG = "AddCoursePresenter";
    private AddCourseContract.View view;//持有View引用
    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private CompositeDisposable mCompositeDisposable;


    public AddCoursePresenter(AddCourseContract.View view) {
        this.view = view;
        view.setPresenter(this);

    }

    /**
     * 获取登录信息
     */
    @SuppressLint("CheckResult")
    @Override
    public void getLogin(String CheckCode) {
        //登录请求
        Map<String, String> params = new HashMap<>();
        params.put("UserName", "1600200639");
        params.put("PassWord", "338471");
        params.put("CheckCode", CheckCode);



        RetrofitFactory.getInstance().getCookie(params)

                //被观察者在io子线程
                .subscribeOn(Schedulers.io())
                //初始化操作
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);//将disponsable对象加入容器统一注销
                        view.showProgressBar(true);//显示进度条
                    }
                })
                //观察者在主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        view.setCource(loginBean.getMsg());
                        //Log.e(TAG, "accept: -------" + loginBean.getData() + loginBean.getMsg());
                        view.showProgressBar(false);//显示进度条
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showProgressBar(false);//显示进度条
                       // Log.e(TAG, "accept: -------" + throwable);
                    }
                });
    }

    /**
     * 获取验证码
     */

    @SuppressLint("CheckResult")
    @Override
    public void getImage() {


        RetrofitFactory.getInstance().getIdImage()

                //被观察者在io子线程
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    //订阅时的操作
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);//将disponsable对象加入容器统一注销
                        view.showProgressBar(true);//显示进度条
                    }
                })
                //观察者在主线程
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap apply(ResponseBody responseBody) throws Exception {

                        InputStream in = responseBody.byteStream();
                        //转化为bitmap

                        return BitmapFactory.decodeStream(in);
                    }
                })
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        view.setImage(bitmap);
                        view.showProgressBar(false);//显示进度条
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                       // Log.e(TAG, "-------------accept:----------- " + throwable);//输出异常
                        view.showProgressBar(false);
                    }
                });


    }


    /**
     * 获取课程表
     */
    @SuppressLint("CheckResult")
    @Override
    public void getCource() {
        RetrofitFactory.getInstance().getCourseHtml()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);//将disponsable对象加入容器统一注销
                        view.showProgressBar(true);//显示进度条
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<CourseBean, List<CourseBean.DataBean>>() {
                    @Override
                    public List<CourseBean.DataBean> apply(CourseBean courseBean) throws Exception {
                        return courseBean.getData();
                    }
                })
                .subscribe(new Consumer<List<CourseBean.DataBean>>() {
                    @Override
                    public void accept(List<CourseBean.DataBean> dataBeans) throws Exception {
                        Log.e(TAG, "accept: --------"+dataBeans.get(0).getCname());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ------" + throwable);
                        view.showProgressBar(false);
                    }
                });
    }

    @Override
    public void start() {
        //初始化操作放在这里
    }

    @Override
    public void detach() {
        this.view = null;//设置View对象为空，释放资源
        unDisposable();//注销
    }

    @Override
    public void addDisposable(Disposable subscription) {
        /*是否已经解绑或者容器为空时*/
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);//将订阅添加到容器中
    }

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

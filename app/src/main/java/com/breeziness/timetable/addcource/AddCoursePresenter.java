package com.breeziness.timetable.addcource;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.breeziness.timetable.data.bean.LoginBean;
import com.breeziness.timetable.data.retrofit.RetrofitFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
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
        params.put("CheckCode",CheckCode);



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
                        Log.e(TAG, "accept: -------"+loginBean.getData()+loginBean.getMsg());
                        view.showProgressBar(false);//显示进度条
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showProgressBar(false);//显示进度条
                        Log.e(TAG, "accept: -------"+throwable);
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

                        //获取流
                        InputStream in = responseBody.byteStream();
                        //转化为bitmap
                        Bitmap bitmap = BitmapFactory.decodeStream(in);

                        return bitmap;
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
                        Log.e(TAG, "-------------accept:----------- " + throwable);//输出异常
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
//        Map<String,String> params = new HashMap<>();
//        params.put("username","zhu");
//        params.put("password","123");

//        //登录请求
//        Map<String, String> params = new HashMap<>();
//        params.put("username", "1600200639");
//        params.put("password", "338471");
//        params.put("login", "%B5%C7%A1%A1%C2%BC");
//
//        //查看课表
//        Map<String, String> termMap = new HashMap<>();
//        params.put("term", "2018-2019_2");
//
//        Observable<String> ob_login = RetrofitFactory.getInstance().login(params);//登录被观察者
//        final Observable<String> ob_query = RetrofitFactory.getInstance().getCourse(termMap);//查看课表被观察者
//
//
//        ob_login.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        addDisposable(disposable);
//                        view.showProgressBar(true);
//                    }
//                })
//                .observeOn(Schedulers.io())
//                .flatMap(new Function<String, ObservableSource<String>>() {
//                    @Override
//                    public ObservableSource<String> apply(String s) throws Exception {
//                        return ob_query;
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//
//                    @Override
//                    public void accept(String s) throws Exception {
//                        view.setCource(s);
//                        Log.e(TAG, "-----------accept: ------" + s.length());
//                        view.showProgressBar(false);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        view.showProgressBar(false);
//                        Log.e(TAG, "访问出错");
//                    }
//                });
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

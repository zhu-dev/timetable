package com.breeziness.timetable.data;

import android.annotation.SuppressLint;
import android.util.Log;

import com.breeziness.timetable.base.BaseApplication;
import com.breeziness.timetable.data.bean.CourseBean;
import com.breeziness.timetable.data.bean.CourseNetBean;
import com.breeziness.timetable.data.db.LocalDataRepository;
import com.breeziness.timetable.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DataRepository implements DataSource {

    private static final String TAG = "DataRepository";

    private static volatile DataRepository instance;

    private boolean flag;//插入和更新的标志位

    private DataRepository() {

    }

    public static DataRepository getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (DataRepository.class) {
            if (instance == null) {
                instance = new DataRepository();
            }
        }
        return instance;
    }

    @SuppressLint("CheckResult")
    @Override
    public void saveCourseToDB(List<CourseNetBean.DataBean> dataBeans) {

        Log.e(TAG, "saveCourseToDB: ---1--");
        flag = SharedPreferencesUtil.getBoolen(BaseApplication.getContext(), "InsertFlag", "flag", true);

        if (flag) {
            //第一次插入
            LocalDataRepository.getInstance(BaseApplication.getContext()).insert(dataBeans)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            Log.e(TAG, "accept: ---存入数据表成功ok---");
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.e(TAG, "accept: ---存取出错---");
                        }
                    });

            SharedPreferencesUtil.saveBoolean(BaseApplication.getContext(), "InsertFlag", "flag", false);

        } else {
            LocalDataRepository.getInstance(BaseApplication.getContext()).update(dataBeans)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            Log.e(TAG, "accept: ----更新数据成功---");
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                        }
                    });
        }

    }

    @Override
    public List<CourseNetBean.DataBean> getCourseFromRemote() {
        return null;
    }

    @SuppressLint("CheckResult")
    @Override
    public List<CourseBean> getCourseFromDB() {
        return null;
    }

    @SuppressLint("CheckResult")
    @Override
    public boolean removeAllCourse() {
        LocalDataRepository.getInstance(BaseApplication.getContext()).removeAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.e(TAG, "accept: ----清空数据成功---");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ----清空数据失败---");
                    }
                });
        return false;
    }

}

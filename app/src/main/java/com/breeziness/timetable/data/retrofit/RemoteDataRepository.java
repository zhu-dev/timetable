package com.breeziness.timetable.data.retrofit;

import android.annotation.SuppressLint;
import android.util.Log;

import com.breeziness.timetable.base.BaseApplication;
import com.breeziness.timetable.data.bean.CourseNetBean;
import com.breeziness.timetable.data.db.LocalDataRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RemoteDataRepository implements RemoteDataSource {

    private static final String TAG = "RemoteDataRepository";
    private static volatile RemoteDataRepository instance;



    private RemoteDataRepository() {
    }

    public static RemoteDataRepository getInstance() {
        if (instance != null) {
            return instance;
        }
            synchronized (RemoteDataSource.class) {
                if (instance == null) {
                    instance = new RemoteDataRepository();
                }

            }
        return instance;
    }

//    @SuppressLint("CheckResult")
//    @Override
//    public void saveCourseToDB(List<CourseNetBean.DataBean> dataBeans) {
//
//    }

}

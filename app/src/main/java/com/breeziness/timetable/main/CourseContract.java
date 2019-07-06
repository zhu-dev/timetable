package com.breeziness.timetable.main;

import com.breeziness.timetable.base.BasePresenter;
import com.breeziness.timetable.base.BaseView;
import com.breeziness.timetable.data.bean.CourseBean;

import java.util.List;

public interface CourseContract {
    interface View extends BaseView<Presenter> {
        //设置课程
        void setCourse(List<CourseBean> dataBeans);

    }

    interface Presenter extends BasePresenter {

        void getCourse();//获取课程数据

        void subscribe();//订阅事件

        void unSubscribe();//取消订阅事件
    }
}
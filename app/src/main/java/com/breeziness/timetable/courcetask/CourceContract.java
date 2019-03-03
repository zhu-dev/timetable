package com.breeziness.timetable.courcetask;

import com.breeziness.timetable.base.BasePresenter;
import com.breeziness.timetable.base.BaseView;

public interface CourceContract {
    interface View extends BaseView<Presenter> {
        //显示加载进度动画
        void showProgressBar(boolean isShow);

        //设置课程
        void setCource(String cource);

        //是否存活
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        //获取课程数据
        void getCource();

    }
}
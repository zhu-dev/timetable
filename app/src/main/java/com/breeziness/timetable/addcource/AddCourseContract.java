package com.breeziness.timetable.addcource;

import android.graphics.Bitmap;

import com.breeziness.timetable.base.BasePresenter;
import com.breeziness.timetable.base.BaseView;
import com.breeziness.timetable.data.bean.LoginBean;

public class AddCourseContract {
    interface View extends BaseView<AddCourseContract.Presenter> {
        //显示加载进度动画
        void showProgressBar(boolean isShow);

        //设置课程
        void setCource(String cource);

        //显示验证码
        void setImage(Bitmap bitmap);

    }

    interface Presenter extends BasePresenter {

        //获取登录状态
        void getLogin(String CheckCode);

        //获取验证码
        void getImage();

        //获取课程数据
        void getCource();

    }
}

package com.breeziness.timetable.addcource;

import android.graphics.Bitmap;

import com.breeziness.timetable.base.BasePresenter;
import com.breeziness.timetable.base.BaseView;
import com.breeziness.timetable.data.bean.CourseNetBean;

import java.util.List;

public interface AddCourseContract {
    interface View extends BaseView<AddCourseContract.Presenter> {
        //显示加载进度动画
        void showProgressBar(boolean isShow);

        //跳转到主界面
        void complete();

        //显示验证码
        void setImage(Bitmap bitmap);

        //显示登录结果信息
        void setLoginMassage(boolean isSuccess, String content);

        //显示错误信息
        void showError(int eCode,String e);
    }

    interface Presenter extends BasePresenter {

        //获取登录状态
        void getLogin(String CheckCode);

        //获取验证码
        void getImage();

        //获取课程数据
        void getCourse(String term);

    }
}

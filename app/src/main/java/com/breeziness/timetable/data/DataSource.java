package com.breeziness.timetable.data;

import com.breeziness.timetable.data.bean.CourseBean;
import com.breeziness.timetable.data.bean.CourseNetBean;

import java.util.List;

public interface DataSource {
    void saveCourseToDB(List<CourseNetBean.DataBean> dataBeans);//保存课程到数据库
    List<CourseNetBean.DataBean> getCourseFromRemote();//从网络获取课程
    List<CourseBean> getCourseFromDB();//从数据库获取课程
}

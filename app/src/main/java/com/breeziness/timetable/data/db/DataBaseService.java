package com.breeziness.timetable.data.db;

import com.breeziness.timetable.data.bean.CourseBean;

import java.util.List;

import io.reactivex.Flowable;

/**
 * 数据库的CRUD操作
 * 支持背压
 */
public interface DataBaseService {
    //插入课程
    Flowable<Boolean> insertCourse(List<CourseBean.DataBean> dataBeans);

    //更新课程
    Flowable<Boolean> updataCourse(List<CourseBean.DataBean> dataBeans);

    //获取所有的课程
    Flowable<List<CourseBean.DataBean>> getAllCourse(String tableName);

    //添加一门课程
    Flowable<CourseBean.DataBean> addCourse(String courseid);

    //删除课程
    Flowable<Boolean> removeCourse(String courseid);
}

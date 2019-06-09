package com.breeziness.timetable.data.db;

import com.breeziness.timetable.data.bean.CourseBean;
import com.breeziness.timetable.data.bean.CourseNetBean;

import java.util.List;

import io.reactivex.Flowable;

/**
 * 数据库的CRUD操作
 * 支持背压
 */
public interface LocalDataSource {
    //插入课程
    Flowable<Boolean> insert(List<CourseNetBean.DataBean> dataBeans);

    //插入课程
//    Flowable<Boolean> insertCourses(List<CourseNetBean.DataBean> dataBeans);

    //更新课程
    //Flowable<Boolean> updateCourses(List<CourseNetBean.DataBean> dataBeans);

    //更新课程
    Flowable<Boolean> update(List<CourseNetBean.DataBean> dataBeans);

    //获取所有的课程
    Flowable<List<CourseBean>> getAll(String tableName);

    //从数据库获取课程
    Flowable<List<CourseBean>> getCourseFromDB();

    //测试存储对象
//    Flowable<List<CourseNetBean.DataBean>> getAllCourses(String tableName);

    //添加一门课程
    Flowable<CourseNetBean.DataBean> addCourse(String courseid);

    //删除课程
    Flowable<Boolean> removeCourse(String courseid);

    Flowable<Boolean> removeAll();
}

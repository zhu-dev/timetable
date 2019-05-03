package com.breeziness.timetable.data;

import com.breeziness.timetable.data.bean.CourseBean;

import java.util.ArrayList;
import java.util.List;

public class DataHelper {

    private List<CourseBean> allCourses;
    private List<CourseBean> weekCourses = new ArrayList<>();

    public DataHelper(List<CourseBean> dataBeans) {
        allCourses = dataBeans;
    }

    public List<CourseBean> getCoursesOfWeek(int week) {
        for (int i = 0; i < allCourses.size(); i++) {
            CourseBean course = allCourses.get(i);
            if (week >= course.getStartweek() && week <= course.getEndweek()) {
                weekCourses.add(course);
            }
        }
        return weekCourses;
    }

}

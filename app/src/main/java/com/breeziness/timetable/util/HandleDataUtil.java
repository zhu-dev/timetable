package com.breeziness.timetable.util;

import android.util.Log;

import com.breeziness.timetable.base.BaseApplication;

import java.util.ArrayList;
import java.util.List;

public class HandleDataUtil {

    private static final String TAG = "HandleDataUtil";

    public static String handleGrade(int index) {
        //这是测试数据模拟拿到用户数据，应该用登录页拿到用户数据
        String studentId = "1600200639";
        SharedPreferencesUtil.saveString(BaseApplication.getContext(), "users", "studentId", studentId);
        studentId = SharedPreferencesUtil.getString(BaseApplication.getContext(), "users", "studentId", "");
        String subStr_stu_year = studentId.substring(0, 2);
        Log.e(TAG, "handleGrade: --subStr_stu_year---"+subStr_stu_year );
        int stu_year = 2000 + Integer.valueOf(subStr_stu_year);
        Log.e(TAG, "handleGrade: --stu_year---"+stu_year );
        //例如2018-2019_2
        List<String> grades = new ArrayList<>();

        grades.add(stu_year + "-" + (stu_year + 1));
        grades.add((stu_year + 1) + "-" + (stu_year + 2));
        grades.add((stu_year + 2) + "-" + (stu_year + 3));
        grades.add((stu_year + 3) + "-" + (stu_year + 4));

        return grades.get(index);
    }

    public static String handleTerm(int index) {
        return index == 0 ? "_1" : "_2";
    }

    public static String showGrades(int index) {
        String studentId = "1600200639";
        SharedPreferencesUtil.saveString(BaseApplication.getContext(), "users", "studentId", studentId);
        studentId = SharedPreferencesUtil.getString(BaseApplication.getContext(), "users", "studentId", "");
        String subStr_stu_year = studentId.substring(0, 2);
        int stu_year = 2000 + Integer.valueOf(subStr_stu_year);

        //例如2018-2019_2
        List<String> grades = new ArrayList<>();
        grades.add(stu_year + "-" + (stu_year + 1)+"(大一)");
        grades.add((stu_year + 1) + "-" + (stu_year + 2)+"(大二)");
        grades.add((stu_year + 2) + "-" + (stu_year + 3)+"(大三)");
        grades.add((stu_year + 3) + "-" + (stu_year + 4)+"(大四)");
        return grades.get(index);
    }
}

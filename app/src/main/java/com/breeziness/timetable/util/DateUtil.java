package com.breeziness.timetable.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class DateUtil {
    private static final String TAG = "DateUtil";
    private Calendar calendar;
    private Context context;
    private int mCurrentWeekday = 1;//当前星期几,默认周一
    private int mCurrnetMonth;//当前是月份
    private int mCurrnetYear;//当前年份
    private int mCurrnetDate;//当前日期
    private int mCurrentWeek = 1;//当前周次 默认第一周
    private List<Map<String, String>> eachWeekDaysList = new ArrayList<>();//存储所有周的日期
    private Map<String, String> eachWeekDaysMap = new HashMap<>();//星期和日期的键值对

    public DateUtil(int mCurrentWeek,Context context) {
        this.mCurrentWeek = mCurrentWeek;
        this.context = context;
        init();
    }

    public DateUtil() {
        init();
    }

    private void init() {
        Date date = new Date();
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));//东八区
        mCurrnetMonth = calendar.get(Calendar.MONTH)+1;//月份0~11，从0开始，所以每次都要加上1才是当前的月份
        mCurrnetYear = calendar.get(Calendar.YEAR);
        mCurrnetDate = calendar.get(Calendar.DATE);
        mCurrentWeekday = calendar.get(Calendar.DAY_OF_WEEK)-1;//西方国家星期从星期天开始算，我们是星期一，所以减去一
    }

    public int getYear() {
        return mCurrnetYear;
    }

    public int getMonth() {
        return mCurrnetMonth;
    }

    public int getDate() {
        return mCurrnetDate;
    }

    public int getWeekday() {
        return mCurrentWeekday;
    }

    public void setCurrentWeek(int week) {
        mCurrentWeek = week;
    }
    public void updateDate(int mCurrentWeek){

//        SharedPreferences sp = context.getSharedPreferences("Cookie", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("Cookie", sb.toString());
//        editor.apply();
    }
}
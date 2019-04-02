package com.breeziness.timetable.UI.weekview;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtil {
    private int mCurWeekday;//当前星期几  默认从周一开始  1-7
    private int mCurMonth;//当前是月份  1-12
    private int mCurYear;//当前年份
    private int mCurDate;//当前日期

    public DateTimeUtil() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(today);
        //calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));//东八区
        mCurMonth = calendar.get(Calendar.MONTH) + 1;//月份0~11，从0开始，所以每次都要加上1才是当前的月份
        mCurYear = calendar.get(Calendar.YEAR);
        mCurDate = calendar.get(Calendar.DATE);
        mCurWeekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (mCurWeekday == 0) {
            mCurWeekday = 7;
        }
    }

    public int getCurWeekday() {
        return mCurWeekday;
    }

    public int getCurMonth() {
        return mCurMonth;
    }

    public int getCurYear() {
        return mCurYear;
    }

    public int getCurDate() {
        return mCurDate;
    }
}

package com.breeziness.timetable.UI.weekview;

import android.util.Log;

import com.breeziness.timetable.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;

public class CalendarDate {

    private static final String TAG = "CalendarDate";

    private int year;
    private int month;
    private int date;
    private int weekday;

    private List<Integer> weekdate = new ArrayList<>();

    public CalendarDate(int year, int month, int date) {
        this.year = year;
        this.month = month;
        this.date = date;
    }

    public CalendarDate() {
        DateTimeUtil dateUtil = new DateTimeUtil();
        this.year = dateUtil.getCurYear();
        this.month = dateUtil.getCurMonth();
        this.date = dateUtil.getCurDate();

    }

    public int getThisSunday() {
        CalendarDate c1 = getTargetThisWeekSunday(0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(c1.year, c1.month - 1, c1.date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public List<Integer> getTargetWeekDays(int offset) {
        //Log.e(TAG, "getTargetWeekDays: ---offset----"+offset );
        CalendarDate c1 = getTargetLastWeekSunday(offset);
        Calendar calendar = Calendar.getInstance();
        calendar.set(c1.year, c1.month - 1, c1.date);
        //Log.e(TAG, "getTargetWeekDays: ----size--"+weekdate.size() );
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            weekdate.add(calendar.get(Calendar.DAY_OF_MONTH));
        }

        // Log.e(TAG, "getTargetWeekDays: ----size--"+weekdate.size() );
        return weekdate;
    }

    private CalendarDate getTargetLastWeekSunday(int offset) {
        CalendarDate c1 = getModifiedWeek(offset);
        weekdate.add(c1.month);
        weekdate.add(c1.weekday);
        Log.e(TAG, "getTargetThisWeekSunday: --size---" + weekdate.size());
        Calendar calendar = Calendar.getInstance();
        calendar.set(c1.year, c1.month - 1, c1.date);
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, -(7 - Math.abs(calendar.get(Calendar.DAY_OF_WEEK) - 7) - 1));//获取上周的周日作为计算日期的起点
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, -7);//如果当前是星期天，则直接回退7天
        }
        return new CalendarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    private CalendarDate getTargetThisWeekSunday(int offset) {
        CalendarDate c1 = getModifiedWeek(offset);
        Calendar calendar = Calendar.getInstance();
        calendar.set(c1.year, c1.month - 1, c1.date);
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            // Log.e(TAG, "getTargetWeekSunday:---weekday---- " + calendar.get(Calendar.DAY_OF_WEEK));
            calendar.add(Calendar.DAY_OF_MONTH, 7 - calendar.get(Calendar.DAY_OF_WEEK) + 1);//获取这周的周日作为计算起点日期
        }
        // Log.e(TAG, "getTargetWeekSunday: ---location--sunday-----" + calendar.get(Calendar.DAY_OF_MONTH));
        return new CalendarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }


    private CalendarDate getModifiedWeek(int offset) {
        CalendarDate result = new CalendarDate();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, date);
        calendar.add(Calendar.DATE, 7 * offset);
        result.setYear(calendar.get(Calendar.YEAR));
        result.setMonth(calendar.get(Calendar.MONTH) + 1);
        result.setDate(calendar.get(Calendar.DATE));
        result.setWeekday(calendar.get(Calendar.DAY_OF_WEEK));
        // Log.e(TAG, "getModifiedWeek: ----Weekday----"+calendar.get(Calendar.DAY_OF_WEEK));
        return result;
    }

    public CalendarDate getModifiedMonth(int offset) {
        CalendarDate result = new CalendarDate();

        int targetMonth = month + offset;

        if (offset > 0) {
            if (targetMonth > 12) {
                result.setYear(year + (targetMonth - 1) / 12);//减一是为了本年的范围内和超过12不加年份
                result.setMonth(targetMonth % 12 == 0 ? 12 : targetMonth % 12);//周期12
            }
        } else {
            if (targetMonth == 0) {
                result.setYear(year - 1);
                result.setMonth(12);
            } else if (targetMonth < 0) {
                result.setYear(year + targetMonth / 12 - 1);
                result.setMonth((12 - Math.abs(targetMonth) % 12));
            } else {
                result.setYear(year);
                result.setMonth(month);
            }
        }
        return result;
    }

    public int getYear() {
        return year;
    }

    private void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    private void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    private void setDate(int date) {
        this.date = date;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    @NonNull
    @Override
    public String toString() {
        return year + "年" + month + "月" + date + "日";
    }


}

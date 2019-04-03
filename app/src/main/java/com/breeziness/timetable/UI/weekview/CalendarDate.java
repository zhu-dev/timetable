package com.breeziness.timetable.UI.weekview;

import android.util.Log;

import java.util.Calendar;

import androidx.annotation.NonNull;

public class CalendarDate {

    private static final String TAG = "CalendarDate";

    private int year;
    private int month;
    private int date;

    private int[] weekdate = new int[9];

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

    public void getTargetWeek(int offset) {
        weekdate = getTargetWeekDays(offset);
        for (int i = 0; i < 7; i++) {
            Log.e(TAG, "showWeekday: " + weekdate[i]);
        }

    }


    private int[] getTargetWeekDays(int offset) {
        int[] days = new int[7];
        CalendarDate c1 = getTargetLastWeekSunday(offset);
        Calendar calendar = Calendar.getInstance();
        calendar.set(c1.year, c1.month - 1, c1.date);
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            days[i] = calendar.get(Calendar.DAY_OF_MONTH);
        }
        return days;
    }

    private CalendarDate getTargetLastWeekSunday(int offset) {
        CalendarDate c1 = getModifiedWeek(offset);
        Calendar calendar = Calendar.getInstance();
        calendar.set(c1.year, c1.month - 1, c1.date);
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            Log.e(TAG, "getTargetWeekSunday:---weekday---- " + calendar.get(Calendar.DAY_OF_WEEK));
            //calendar.add(Calendar.DAY_OF_MONTH, 7 - calendar.get(Calendar.DAY_OF_WEEK) + 1);//获取这周的周日作为计算起点日期
            calendar.add(Calendar.DAY_OF_MONTH, -(7 - Math.abs(calendar.get(Calendar.DAY_OF_WEEK) - 7) - 1));//获取上周的周日作为计算日期的起点
        }
        Log.e(TAG, "getTargetWeekSunday: ---location--sunday-----" + calendar.get(Calendar.DAY_OF_MONTH));
        return new CalendarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    private CalendarDate getTargetThisWeekSunday(int offset) {
        CalendarDate c1 = getModifiedWeek(offset);
        Calendar calendar = Calendar.getInstance();
        calendar.set(c1.year, c1.month - 1, c1.date);
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            Log.e(TAG, "getTargetWeekSunday:---weekday---- " + calendar.get(Calendar.DAY_OF_WEEK));
            calendar.add(Calendar.DAY_OF_MONTH, 7 - calendar.get(Calendar.DAY_OF_WEEK) + 1);//获取这周的周日作为计算起点日期
        }
        Log.e(TAG, "getTargetWeekSunday: ---location--sunday-----" + calendar.get(Calendar.DAY_OF_MONTH));
        return new CalendarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }


    private CalendarDate getModifiedWeek(int offset) {
        CalendarDate result = new CalendarDate();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, date);
        calendar.add(Calendar.DATE, 7 * offset);//因为我获取的周日定位日期是下一周的周日 所以我在这里减一，但还是希望找到更好的算法
        result.setYear(calendar.get(Calendar.YEAR));
        result.setMonth(calendar.get(Calendar.MONTH) + 1);
        result.setDate(calendar.get(Calendar.DATE));

        weekdate[8] = calendar.get(Calendar.MONTH) + 1;//记下种子日期所属的月份,即跳转到的那一天

//        Log.e(TAG, "getModifiedWeek:----today--- " + date);
//        Log.e(TAG, "getModifiedWeek: -----offsetday------" + calendar.get(Calendar.DATE));
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

    @NonNull
    @Override
    public String toString() {
        return year + "年" + month + "月" + date + "日";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

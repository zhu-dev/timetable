package com.breeziness.timetable.UI.weekview;

import android.util.Log;

import java.util.Calendar;

import androidx.annotation.NonNull;

public class CalendarDateBean {

    private static final String TAG = "CalendarDateBean";

    private int year;
    private int month;
    private int date;

    private int[] weekdate = new int[9];

    public CalendarDateBean(int year, int month, int date) {
        this.year = year;
        this.month = month;
        this.date = date;
    }

    public CalendarDateBean() {
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
        CalendarDateBean c1 = getTargetWeekSunday(offset);
        Calendar calendar = Calendar.getInstance();
        calendar.set(c1.year, c1.month - 1, c1.date);
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            days[i] = calendar.get(Calendar.DAY_OF_MONTH);
        }
        return days;
    }

    private CalendarDateBean getTargetWeekSunday(int offset) {
        CalendarDateBean c1 = getModifiedWeek(offset);
        Calendar calendar = Calendar.getInstance();
        calendar.set(c1.year, c1.month - 1, c1.date);
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            Log.e(TAG, "getTargetWeekSunday:---weekday---- " + calendar.get(Calendar.DAY_OF_WEEK));
            calendar.add(Calendar.DAY_OF_MONTH, 7 - calendar.get(Calendar.DAY_OF_WEEK) + 1);
            //calendar.add(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_WEEK) % 7 == 0 ? 6 : (calendar.get(Calendar.DAY_OF_WEEK) % 7 - 1));//算法不对
        }
        Log.e(TAG, "getTargetWeekSunday: ---location--sunday-----" + calendar.get(Calendar.DAY_OF_MONTH));
        return new CalendarDateBean(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }


    private CalendarDateBean getModifiedWeek(int offset) {
        CalendarDateBean result = new CalendarDateBean();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, date);
        calendar.add(Calendar.DATE, 7 * (offset - 1));//因为我获取的周日定位日期是下一周的周日 所以我在这里减一
        result.setYear(calendar.get(Calendar.YEAR));
        result.setMonth(calendar.get(Calendar.MONTH) + 1);
        result.setDate(calendar.get(Calendar.DATE));

        weekdate[8] = calendar.get(Calendar.MONTH) + 1;//记下种子日期所属的月份

//        Log.e(TAG, "getModifiedWeek:----today--- " + date);
//        Log.e(TAG, "getModifiedWeek: -----offsetday------" + calendar.get(Calendar.DATE));
        return result;
    }

    public CalendarDateBean getModifiedMonth(int offset) {
        CalendarDateBean result = new CalendarDateBean();

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

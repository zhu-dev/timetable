package com.breeziness.timetable.util;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * 注意点：
 * 1.Java中的月份遵循了罗马历中的规则：
 * 当时一年中的月份数量是不固定的，
 * 第一个月是JANUARY。而Java中Calendar.MONTH返回的数值其实是当前月距离第一个月有多少个月份的数值，
 * JANUARY在Java中返回“0”，所以我们需要+1。
 * 2.在获取星期几 Calendar.DAY_OF_WEEK – 1 的原因
 * Java中Calendar.DAY_OF_WEEK其实表示：一周中的第几天，所以他会受到 第一天是星期几 的影响。
 * 有些地区以星期日作为一周的第一天，而有些地区以星期一作为一周的第一天，这2种情况是需要区分的。
 */
public class DateUtil {
    private static final String TAG = "DateUtil";
    private Calendar calendar;
    private Context context;

    public static final int MONTH = 7;
    public static final int WEEKDAY = 8;

    public static final int MONDAY = 0;
    public static final int TUESDAY = 1;
    public static final int WEDESDAY = 2;
    public static final int THURSDAY = 3;
    public static final int FRIDAY = 4;
    public static final int SATURDAY = 5;
    public static final int SUNDAY = 6;


    private int mCurrentWeekday = 1;//当前星期几,默认周一
    private int mCurrnetMonth;//当前是月份
    private int mCurrnetYear;//当前年份
    private int mCurrnetDate;//当前日期
    private int mCurrentWeek = 1;//当前周次 默认第一周

    //private Map<String, String> eachWeekDaysMap = new HashMap<>();//星期和日期的键值对

    //作为定制的日历类工具时的有参构造器
    public DateUtil(int mCurrentWeek, Context context) {
        this.mCurrentWeek = mCurrentWeek;
        this.context = context;
        init();
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


    private void init() {
        Date today = new Date();
        calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));//东八区
        mCurrnetMonth = calendar.get(Calendar.MONTH) + 1;//月份0~11，从0开始，所以每次都要加上1才是当前的月份
        mCurrnetYear = calendar.get(Calendar.YEAR);
        mCurrnetDate = calendar.get(Calendar.DATE);
        mCurrentWeekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;// 星期默认从周日开始    查阅文档发现这里时一个坑，Calendar类默认从周日开始，范围0~6，当到星期天时，需要转化
        if (mCurrentWeekday == 0) {
            mCurrentWeekday = 7;
        }
        Log.e(TAG, "init: -----firstday-----" + calendar.get(Calendar.DAY_OF_WEEK));
        Log.e(TAG, "init: --mCurrnetYear--mCurrnetMonth--mCurrentWeekday--mCurrnetDate--" + mCurrnetYear + "--" + mCurrnetMonth + "--" + mCurrentWeekday + "--" + mCurrnetDate);
    }

/*************************定制的日历功能*************************/
    /**
     * 获取特定周次的一周所有的日期
     *
     * @param selectWeek 指定的周次
     * @return dateMap
     */
    public Map<Integer, Integer> getWeekRange(int selectWeek) {
        int deltaWeek = Math.abs(selectWeek - mCurrentWeek);//计算跳转到特定周和当前周的差值
        Map<Integer, Integer> dateMap;

        if (deltaWeek == 0) {
            dateMap = getCurWeekRange();
        } else if (deltaWeek > 0) {
            dateMap = getfutureWeekRange(deltaWeek);
        } else {
            dateMap = getPastWeekRange(deltaWeek);
        }

        return dateMap;
    }

    /**
     * 获取当前周次所有的日期
     *
     * @return dateMap
     */
    private Map<Integer, Integer> getCurWeekRange() {
        Map<Integer, Integer> dateMap = new HashMap();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        //calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//每周从周日开始
        dateMap.put(WEEKDAY, mCurrentWeekday);
        for (int i = 0; i <7; i++) {
            dateMap.put(i, calendar.get(Calendar.DAY_OF_MONTH));
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            Log.e(TAG, "getCurWeekRange: -----date----"+calendar.get(Calendar.DAY_OF_MONTH));
           // dateMap.put(i, calendar.get(Calendar.DAY_OF_MONTH));
            if (i == mCurrentWeekday) {
                dateMap.put(MONTH, calendar.get(Calendar.MONTH) + 1);
            }
        }
        calendar.add(Calendar.DAY_OF_WEEK, -7);

        return dateMap;

    }

    /**
     * 获取当前周之前的特定周的所有日期
     *
     * @param deltaWeek 周次差值
     * @return dateMap
     */
    private Map<Integer, Integer> getPastWeekRange(int deltaWeek) {
        Map<Integer, Integer> dateMap = new HashMap();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        //calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//每周从周日开始
        calendar.add(Calendar.DAY_OF_WEEK, -7 * deltaWeek);
        dateMap.put(WEEKDAY, mCurrentWeekday);
        for (int i = 1; i <= 7; i++) {
            //dateMap.put(i, calendar.get(Calendar.DAY_OF_MONTH));
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            dateMap.put(i, calendar.get(Calendar.DAY_OF_MONTH));
            if (i == mCurrentWeekday) {
                dateMap.put(MONTH, calendar.get(Calendar.MONTH) + 1);
            }
        }
        calendar.add(Calendar.DAY_OF_WEEK, 7 * deltaWeek - 7);
        return dateMap;
    }

    /**
     * 获取当前周之后的特定周的所有日期
     *
     * @param deltaWeek 周次差值
     * @return dateMap
     */
    private Map<Integer, Integer> getfutureWeekRange(int deltaWeek) {
        Map<Integer, Integer> dateMap = new HashMap();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        //calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//每周从周日开始
        calendar.add(Calendar.DAY_OF_WEEK, 7 * deltaWeek);
        dateMap.put(WEEKDAY, mCurrentWeekday);
        for (int i = 1; i <= 7; i++) {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            dateMap.put(i, calendar.get(Calendar.DAY_OF_MONTH));
            if (i == mCurrentWeekday) {
                dateMap.put(MONTH, calendar.get(Calendar.MONTH) + 1);
            }
        }
        calendar.add(Calendar.DAY_OF_WEEK, -7 * deltaWeek - 7);
        return dateMap;
    }

    private void test() {
        List<Integer> dateList = new ArrayList<>();

        //设置时间
        calendar.set(Calendar.YEAR, mCurrnetYear);
        calendar.set(Calendar.MONTH, mCurrnetMonth);

        //设置当前为这周的第x天，赋值今天的星期数,记得是0~6  周日开始的
        calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK));

    }


}
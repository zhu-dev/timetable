package com.breeziness.timetable.UI.weekview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.breeziness.timetable.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;

public class OldWeekView extends LinearLayout {

    private static final String TAG = "WeekView";
    private Context mContext;

    private static final int monday = 1;
    private static final int tuesday = 2;
    private static final int wednesday = 3;
    private static final int thursday = 4;
    private static final int friday = 5;
    private static final int saturday = 6;
    private static final int sunday = 7;

    private int mWeekday = 2;
    private List<String> mDaysList = new ArrayList<>();
    private String mMonth = "2";


    private TextView tv_month;
    private LinearLayout monday_contain;
    private TextView monday_day;

    private LinearLayout tuesday_contain;
    private TextView tuesday_day;

    private LinearLayout wednesday_contain;
    private TextView wednesday_day;

    private LinearLayout thursday_contain;
    private TextView thursday_day;

    private LinearLayout friday_contain;
    private TextView friday_day;

    private LinearLayout saturday_contain;
    private TextView saturday_day;

    private LinearLayout sunday_contain;
    private TextView sunday_day;


    public OldWeekView(Context context) {
        this(context, null);
    }

    public OldWeekView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OldWeekView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View contentView = LayoutInflater.from(context).inflate(R.layout.weekview, this);
        initView(contentView);//初始化子view
    }

    private void initView(View contentView) {
        tv_month = contentView.findViewById(R.id.weekview_month);

        monday_contain = contentView.findViewById(R.id.monday_contain);
        monday_day = contentView.findViewById(R.id.monday_day);

        tuesday_contain = contentView.findViewById(R.id.tuesday_contain);
        tuesday_day = contentView.findViewById(R.id.tuesday_day);

        wednesday_contain = contentView.findViewById(R.id.wednesday_contain);
        wednesday_day = contentView.findViewById(R.id.wednesday_day);

        thursday_contain = contentView.findViewById(R.id.thursday_contain);
        thursday_day = contentView.findViewById(R.id.thursday_day);

        friday_contain = contentView.findViewById(R.id.friday_contain);
        friday_day = contentView.findViewById(R.id.friday_day);

        saturday_contain = contentView.findViewById(R.id.saturday_contain);
        saturday_day = contentView.findViewById(R.id.saturday_day);

        sunday_contain = contentView.findViewById(R.id.sunday_contain);
        sunday_day = contentView.findViewById(R.id.sunday_day);
    }


    private void showWeekdayChecked(int weekday) {
        switch (weekday) {
            case monday:
                monday_contain.setBackgroundColor(getResources().getColor(R.color.checked_color));
                break;
            case tuesday:
                tuesday_contain.setBackgroundColor(getResources().getColor(R.color.checked_color));
                break;
            case wednesday:
                wednesday_contain.setBackgroundColor(getResources().getColor(R.color.checked_color));
                break;
            case thursday:
                thursday_contain.setBackgroundColor(getResources().getColor(R.color.checked_color));
                break;
            case friday:
                friday_contain.setBackgroundColor(getResources().getColor(R.color.checked_color));
                break;
            case saturday:
                saturday_contain.setBackgroundColor(getResources().getColor(R.color.checked_color));
                break;
            case sunday:
                sunday_contain.setBackgroundColor(getResources().getColor(R.color.checked_color));
                break;
        }
    }


    private void showDays(List<String> days) {
        if (days.size() != 0) {
            monday_day.setText(days.get(0));
            tuesday_day.setText(days.get(1));
            wednesday_day.setText(days.get(2));
            thursday_day.setText(days.get(3));
            friday_day.setText(days.get(4));
            saturday_day.setText(days.get(5));
            sunday_day.setText(days.get(6));
        }

    }

    private void showMonth(String month) {
        tv_month.setText(String.format("%s月", month));
    }


    public void setData(int weekday, String month, List<String> days) {
        mMonth = month;
        mDaysList = days;
        mWeekday = weekday;
        showWeekdayChecked(mWeekday);//显示当前星期
        showMonth(mMonth);//显示月份
        showDays(mDaysList);//显示日期
        postInvalidate();
    }

    public void setData(Map days) {
        mDaysList.clear();
        for (int i = 0; i < 7; i++) {
            mDaysList.add(days.get(i).toString());
        }
        mMonth = days.get(7).toString();
        mWeekday = (int) days.get(8);
        showWeekdayChecked(mWeekday);//显示当前星期
        showMonth(mMonth);//显示月份
        showDays(mDaysList);//显示日期
        postInvalidate();
    }

}

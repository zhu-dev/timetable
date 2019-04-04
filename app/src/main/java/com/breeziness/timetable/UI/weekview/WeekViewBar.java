package com.breeziness.timetable.UI.weekview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.breeziness.timetable.R;


import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;

@SuppressLint("DefaultLocale")
public class WeekViewBar extends ConstraintLayout {

    private static final String TAG = "WeekViewBar";
    //自定义属性
    private int mTextColor;
    private int mSelectDateTextColor;
    private int mSelectDateCircleColor;

    //子view
    private DateView dateView;
    private TextView tv_month;

    //日期数据
    private List<Integer> mTextList = new ArrayList<>();
    private int selectedmonth = 1;

    public WeekViewBar(Context context) {
        this(context, null);
    }

    public WeekViewBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekViewBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initView(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WeekViewBar);
        mTextColor = array.getColor(R.styleable.WeekViewBar_mCommomTextColor, mTextColor);
        mSelectDateTextColor = array.getColor(R.styleable.WeekViewBar_mSelectDateTextColor, mSelectDateTextColor);
        mSelectDateCircleColor = array.getColor(R.styleable.WeekViewBar_mSelectDateCircleColor, mSelectDateCircleColor);
        array.recycle();
    }

    private void initView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_weekbar, this);
        dateView = contentView.findViewById(R.id.dateview);
        tv_month = contentView.findViewById(R.id.tv_weekview_month);

        dateView.setSelectedDateCircleColor(mSelectDateCircleColor);
        dateView.setSelectedDateTextColor(mSelectDateTextColor);
        dateView.setTextColor(mTextColor);

        setBackgroundColor(Color.WHITE);
    }


    public void setTextList(List<Integer> mTextList) {
        this.mTextList = mTextList;
        Log.e(TAG, "setTextList: -size-----" + mTextList.size());
        selectedmonth = mTextList.get(0);
        tv_month.setText(selectedmonth + "月");
        dateView.setContentText(mTextList);
        invalidate();
    }


    public void setSelectedmonth(int selectedmonth) {
        this.selectedmonth = selectedmonth;
        invalidate();
    }
}

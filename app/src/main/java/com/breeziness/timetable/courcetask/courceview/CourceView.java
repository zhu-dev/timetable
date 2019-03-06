package com.breeziness.timetable.courcetask.courceview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.breeziness.timetable.R;

/**
 * 自定义的课程view
 */
public class CourceView extends TextView {
    private int courceId;//课程ID
    private int startSection;//开始节次  确定在容器的上坐标
    private int endSection;//结束节次  确定在容器中的下坐标
    private int weekday;//星期几   确定所属的容器

    public CourceView(Context context) {
        this(context, null);
    }

    public CourceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //绑定自定义的属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CourceView);

        //获得自定义的属性
        courceId = array.getInt(R.styleable.CourceView_courceId, 0);
        startSection = array.getInt(R.styleable.CourceView_startSection, 0);
        endSection = array.getInt(R.styleable.CourceView_endSection, 0);
        weekday = array.getInt(R.styleable.CourceView_weekday, 0);

        array.recycle();

    }

    public int getCourceId() {
        return courceId;
    }

    public void setCourceId(int courceId) {
        this.courceId = courceId;
    }

    public int getStartSection() {
        return startSection;
    }

    public void setStartSection(int startSection) {
        this.startSection = startSection;
    }

    public int getEndSection() {
        return endSection;
    }

    public void setEndSection(int endSection) {
        this.endSection = endSection;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }
}

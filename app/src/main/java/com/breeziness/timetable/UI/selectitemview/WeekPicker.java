package com.breeziness.timetable.UI.selectitemview;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class WeekPicker extends WheelSelectView<Integer> {
    private int selectedWeek = 0;
    private List<Integer> weekList = new ArrayList<>();

    private OnWeekSelectedListener onWeekSelectedListener;

    public WeekPicker(Context context) {
        this(context,null);
    }

    public WeekPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WeekPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSelectedWeek(selectedWeek);
        setOnSelectedChangeListener(new OnSelectedChangeListener() {
            @Override
            public void OnSelectedChange(String item, int position) {
                onWeekSelectedListener.onWeekSelected(item,position);
            }
        });
    }

    public int getSelectedWeek() {
        return selectedWeek;
    }

    public void setSelectedWeek(int selectedWeek) {
        this.selectedWeek = selectedWeek;
        setCurrentPosition(selectedWeek);
    }

    public List<Integer> getWeekList() {
        return weekList;
    }

    public void setWeekList(List<Integer> weekList) {
        this.weekList = weekList;
        setDataList(weekList);
    }



    public void setOnWeekSelectedListener(OnWeekSelectedListener onWeekSelectedListener) {
        this.onWeekSelectedListener = onWeekSelectedListener;
    }
    public interface OnWeekSelectedListener {
        void onWeekSelected(String item, int position);
    }
}

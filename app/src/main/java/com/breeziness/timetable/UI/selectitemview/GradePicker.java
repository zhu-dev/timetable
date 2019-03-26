package com.breeziness.timetable.UI.selectitemview;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class GradePicker extends WheelSelectView<String> {

    private int mSelectedGrade = 0;//选择的年级
    private List<String> mGrade = new ArrayList<>();//本人年级集合
    private OnGradeSelectedListener onGradeSelectedListener;

    public GradePicker(Context context) {
        this(context, null);
    }

    public GradePicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setmSelectedGrade(mSelectedGrade);
        setOnSelectedChangeListener(new OnSelectedChangeListener() {
            @Override
            public void OnSelectedChange(String item, int position) {
                onGradeSelectedListener.onGradeSelected(item,position);
            }
        });
    }

    public int getmSelectedGrade() {
        return mSelectedGrade;
    }

    public void setmSelectedGrade(int mSelectedGrade) {
        setCurrentPosition(mSelectedGrade);
    }

    public List<String> getmGrade() {
        return mGrade;
    }

    public void setmGrade(List<String> mGrade) {
        this.mGrade = mGrade;
        setDataList(mGrade);
    }

    /**
     * 选择回调接口
     * @param onGradeSelectedListener
     */
    public void setOnGradeSelectedListener(OnGradeSelectedListener onGradeSelectedListener) {
        this.onGradeSelectedListener = onGradeSelectedListener;
    }

    public interface OnGradeSelectedListener {
        void onGradeSelected(String item, int position);
    }
}

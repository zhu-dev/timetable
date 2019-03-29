package com.breeziness.timetable.UI.wheelpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.breeziness.timetable.R;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class InfoPicker extends LinearLayout implements GradePicker.OnGradeSelectedListener, TermPicker.OnTermSelectedListener, WeekPicker.OnWeekSelectedListener {
    private Context mContext;

    private TermPicker mTermPicker;
    private GradePicker mGradePicker;
    private WeekPicker mWeekPicker;

    private List<String> mGradeList = new ArrayList<>();
    private List<String> mTermList = new ArrayList<>();
    private List<String> mWeekList = new ArrayList<>();

    private String mGrade;
    private String mTerm;
    private String mWeek;

    private int SelectedWeek;
    private int SelectedTerm;
    private int SelectedGrade;


    private TextView tv_grade_title;
    private TextView tv_term_title;
    private TextView tv_week_title;

    private OnDataSelectedListener onDataSelectedListener;


    public InfoPicker(Context context) {
        this(context, null);
    }

    public InfoPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfoPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_info_picker, this);

        //初始化子view
        initChild(contentView);

        //设置初始数据，避免提交空数据，引起崩溃
        initData();

    }

    /**
     * 初始化子view
     */
    private void initChild(View contentView) {

        tv_grade_title = contentView.findViewById(R.id.info_grade_title);
        tv_term_title = contentView.findViewById(R.id.info_term_title);
        tv_week_title = contentView.findViewById(R.id.info_week_title);

        mTermPicker = contentView.findViewById(R.id.term_picker);
        mGradePicker = contentView.findViewById(R.id.grade_picker);
        mWeekPicker = contentView.findViewById(R.id.week_picker);

        mTermPicker.setOnTermSelectedListener(this);
        mGradePicker.setOnGradeSelectedListener(this);
        mWeekPicker.setOnWeekSelectedListener(this);

        mWeekPicker.setSelectedWeek(SelectedWeek);
        mTermPicker.setSelectedTerm(SelectedTerm);
        mGradePicker.setSelectedGrade(SelectedGrade);

    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    public void setGradeList(List<String> mGradeList) {
        this.mGradeList = mGradeList;
        if (mGradePicker != null) {
            mGradePicker.setGradeList(mGradeList);
        }

    }

    public void setmTermList(List<String> mTermList) {
        this.mTermList = mTermList;
        if (mTermPicker != null) {
            mTermPicker.setTermList(mTermList);
        }
    }

    public void setmWeekList(List<String> mWeekList) {
        this.mWeekList = mWeekList;
        if (mWeekPicker != null) {
            mWeekPicker.setWeekList(mWeekList);
        }
    }

    public void setSelectedInfo(int selectedGrade, int selectedTerm, int selectedWeek) {
        SelectedWeek = selectedWeek;
        SelectedTerm = selectedTerm;
        SelectedGrade = selectedGrade;

        mWeekPicker.setSelectedWeek(SelectedWeek);
        mTermPicker.setSelectedTerm(SelectedTerm);
        mGradePicker.setSelectedGrade(SelectedGrade);

    }

    @Override
    public void onGradeSelected(String item, int position) {
        mGrade = item;
        tv_grade_title.setText(item);
        onDataSelectedListener.onDataSelected(mGrade, mTerm, mWeek);
    }

    @Override
    public void onTermSelected(String item, int position) {
        mTerm = item;
        tv_term_title.setText(item);
        onDataSelectedListener.onDataSelected(mGrade, mTerm, mWeek);
    }

    @Override
    public void onWeekSelected(String item, int position) {
        mWeek = item;
        tv_week_title.setText(item);
        onDataSelectedListener.onDataSelected(mGrade, mTerm, mWeek);
    }

    public void setOnDataSelectedListener(OnDataSelectedListener onDataSelectedListener) {
        this.onDataSelectedListener = onDataSelectedListener;
    }

    public interface OnDataSelectedListener {
        void onDataSelected(String grade, String term, String week);
    }
}

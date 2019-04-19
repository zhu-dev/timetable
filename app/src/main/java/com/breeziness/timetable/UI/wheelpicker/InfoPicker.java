package com.breeziness.timetable.UI.wheelpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.breeziness.timetable.R;
import com.breeziness.timetable.util.HandleDataUtil;


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

    //选中的item的数值
    private int mGrade = 0;
    private int mTerm = 0;
    private int mWeek = 0;

    //选中的item
    private int SelectedWeek;
    private int SelectedTerm;
    private int SelectedGrade;


    private TextView tv_grade_title;
    private TextView tv_term_title;
    private TextView tv_week_title;

    private OnDataSelectedListener onDataSelectedListener;//回调接口


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
        //设置初始数据，避免提交空数据，引起崩溃
        initData();
        //初始化子view
        initChild(contentView);


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

        setGradeList(mGradeList);
        setTermList(mTermList);
        setWeekList(mWeekList);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (mGradeList.size() == 0) {
            mGradeList.add(HandleDataUtil.showGrades(0));
            mGradeList.add(HandleDataUtil.showGrades(1));
            mGradeList.add(HandleDataUtil.showGrades(2));
            mGradeList.add(HandleDataUtil.showGrades(3));
        }


        if (mTermList.size() == 0) {
            mTermList.add("第一学期");
            mTermList.add("第二学期");
        }

        if (mWeekList.size() == 0) {
            for (int i = 1; i <= 20; i++) {
                mWeekList.add("第" + i + "周");
            }
        }


    }

    public void setGradeList(List<String> mGradeList) {
        this.mGradeList = mGradeList;
        if (mGradePicker != null) {
            mGradePicker.setGradeList(mGradeList);
        }

    }

    public void setTermList(List<String> mTermList) {
        this.mTermList = mTermList;
        if (mTermPicker != null) {
            mTermPicker.setTermList(mTermList);
        }
    }

    public void setWeekList(List<String> mWeekList) {
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
        mGrade = position;
        tv_grade_title.setText(item);
        onDataSelectedListener.onDataSelected(mGrade, mTerm, mWeek);
    }

    @Override
    public void onTermSelected(String item, int position) {
        mTerm = position;
        tv_term_title.setText(item);
        onDataSelectedListener.onDataSelected(mGrade, mTerm, mWeek);
    }

    @Override
    public void onWeekSelected(String item, int position) {
        mWeek = position;
        tv_week_title.setText(item);
        onDataSelectedListener.onDataSelected(mGrade, mTerm, mWeek);
    }

    public void setOnDataSelectedListener(OnDataSelectedListener onDataSelectedListener) {
        this.onDataSelectedListener = onDataSelectedListener;
    }

    public interface OnDataSelectedListener {
        void onDataSelected(int grade, int term, int week);
    }
}

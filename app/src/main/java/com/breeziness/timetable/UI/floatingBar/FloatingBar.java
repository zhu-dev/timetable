package com.breeziness.timetable.UI.floatingBar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.breeziness.timetable.R;


public class FloatingBar extends LinearLayout implements RadioGroup.OnCheckedChangeListener {

    private Context context;

    private OnFloatingItemChecked onFloatingItemChecked;

    private RadioGroup radioGroup;
    private RadioButton rb_btn_course;
    private RadioButton rb_btn_study_helper;
    private RadioButton rb_btn_utils;
    private RadioButton rb_btn_more;

    public FloatingBar(Context context) {
        this(context, null);
    }

    public FloatingBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews(context);
    }

    private void initViews(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.content_floating_bar, this);
        rb_btn_course = contentView.findViewById(R.id.rb_btn_course);
        rb_btn_study_helper = contentView.findViewById(R.id.rb_btn_study_helper);
        rb_btn_utils = contentView.findViewById(R.id.rb_btn_utils);
        rb_btn_more = contentView.findViewById(R.id.rb_btn_more);
        rb_btn_course.setChecked(true);//必须放在radioGroup监听之前，否则报错

        radioGroup = contentView.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }



    public void setOnFloatingItemChecked(OnFloatingItemChecked onFloatingItemChecked) {
        this.onFloatingItemChecked = onFloatingItemChecked;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_btn_course:
                onFloatingItemChecked.onItemChecked(0);
                break;
            case R.id.rb_btn_study_helper:
                onFloatingItemChecked.onItemChecked(1);
                break;
            case R.id.rb_btn_utils:
                onFloatingItemChecked.onItemChecked(2);
                break;
            case R.id.rb_btn_more:
                onFloatingItemChecked.onItemChecked(3);
                break;
        }
    }

    public interface OnFloatingItemChecked {
        void onItemChecked(int id);
    }
}

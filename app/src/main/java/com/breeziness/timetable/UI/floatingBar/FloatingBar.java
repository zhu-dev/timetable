package com.breeziness.timetable.UI.floatingBar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.breeziness.timetable.R;
import com.breeziness.timetable.util.ScreenUtils;


public class FloatingBar extends LinearLayout {

    private Context context;

    public FloatingBar(Context context) {
        this(context, null);
    }

    public FloatingBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(context, attrs);
        initViews(context);

    }

    private void initAttrs(Context context, AttributeSet attrs) {

    }

    private void initViews(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.content_floating_bar, this);
        RadioButton rb_btn_course = contentView.findViewById(R.id.rb_btn_course);
        rb_btn_course.setChecked(true);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //int width = getWidth();
//        int height = getHeight();
//
//        int paddingHorizontal = ScreenUtils.dip2px(context, 20.0f);
//        int paddingVertical = ScreenUtils.dip2px(context, 20.0f);
//        int screenWidth = ScreenUtils.getScreenWidth(context);
//        int NavigationBarHeight = ScreenUtils.getNavigationBarHeight(context);
//        int screenHeight = ScreenUtils.getScreenHeitht(context);
//        layout(paddingHorizontal, screenHeight - (NavigationBarHeight + height + paddingVertical),
//                screenWidth - paddingHorizontal * 2,
//                screenHeight-(NavigationBarHeight+paddingVertical));
        super.onLayout(changed, l, t, r, b);
    }
}

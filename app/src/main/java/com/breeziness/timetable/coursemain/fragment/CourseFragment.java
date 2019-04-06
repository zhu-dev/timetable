package com.breeziness.timetable.coursemain.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.breeziness.timetable.R;
import com.breeziness.timetable.UI.courselayout.CourseLayout;
import com.breeziness.timetable.UI.courseview.CourseView;
import com.breeziness.timetable.UI.weekview.CalendarDate;
import com.breeziness.timetable.UI.weekview.WeekViewBar;
import com.breeziness.timetable.base.BaseFragment;
import com.breeziness.timetable.coursemain.CourseActivity;
import com.breeziness.timetable.data.bean.TestCourseBean;
import com.breeziness.timetable.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends BaseFragment implements  View.OnClickListener, CourseActivity.OnWeekChangeListener {




    //weekview
    private CalendarDate calendarDate;
    private WeekViewBar weekViewBar;
    private List<Integer> days;

    private int CurWeek = 6;//当前周

    private int[] bg_color = new int[]{
            R.drawable.bg_cource_gray
            , R.drawable.bg_cource_green
            , R.drawable.bg_cource_light_green
            , R.drawable.bg_cource_pink
            , R.drawable.bg_cource_blue
            , R.drawable.bg_cource_brown
            , R.drawable.bg_cource_yellow
    };

    //测试内容
    private List<TestCourseBean> cources = new ArrayList<>();//测试用课程数据

    public CourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View contentView = inflater.inflate(R.layout.fragment_course, container, false);
        initView(contentView);
        return contentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof )
    }

    private void initView(View contentView) {
        weekViewInit(contentView);//初始化星期头部
        courseLayoutInit(contentView);//课程布局初始化

    }

    private void weekViewInit(View contentView) {
        calendarDate = new CalendarDate();
        days = calendarDate.getTargetWeekDays(0);//默认显示当前周 偏移0
        weekViewBar = contentView.findViewById(R.id.weekbar);
        weekViewBar.setTextList(days);
    }


    private void courseLayoutInit(View contentView) {
        TestCourseBean cource1 = new TestCourseBean("通信原理A", "黎", 1, 1, 1, 1, "11C107", "1-16");
        TestCourseBean cource2 = new TestCourseBean("微波天线", "黎", 2, 1, 2, 2, "02201Y", "1-16");
        TestCourseBean cource3 = new TestCourseBean("科技文献阅读和写作（信息类）", "黎", 3, 7, 2, 2, "11C107", "1-16");
        TestCourseBean cource4 = new TestCourseBean("科技文献阅读和写作（信息类）", "黎", 3, 4, 5, 5, "11C107", "1-16");
        cources.add(cource1);
        cources.add(cource2);
        cources.add(cource3);
        cources.add(cource4);

        CourseLayout layout = contentView.findViewById(R.id.cources);
        for (int i = 0; i < cources.size(); i++) {
            int randBg = bg_color[RandomUtil.getRandomInt(bg_color.length - 1)];
            TestCourseBean cource = cources.get(i);
            CourseView courseView = new CourseView(getActivity().getApplicationContext());
            courseView.setCourceId(cource.getCourceId());
            courseView.setStartSection(cource.getStartSection());
            courseView.setEndSection(cource.getEndSection());
            courseView.setWeekday(cource.getWeekday());
            courseView.setBackground(getActivity().getDrawable(randBg));
            courseView.setText(String.format("%s@%s", cource.getCourceName(), cource.getClassroom()));
            courseView.setTextColor(Color.WHITE);
            //courceView.setAlpha(0.5f);
            courseView.setTextSize(10);
            courseView.setGravity(Gravity.CENTER);
            layout.addView(courseView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void OnWeekChange(int position) {
        calendarDate = new CalendarDate();//此处有缺陷，每次获取都是不同对象  会new出很多对象
        days = calendarDate.getTargetWeekDays(position + 1 - 6);
        weekViewBar.setTextList(days);
    }



}

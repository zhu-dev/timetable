package com.breeziness.timetable.coursemain.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.breeziness.timetable.R;
import com.breeziness.timetable.UI.courselayout.CourseLayout;
import com.breeziness.timetable.UI.courseview.CourseView;
import com.breeziness.timetable.UI.weekview.CalendarDate;
import com.breeziness.timetable.UI.weekview.WeekViewBar;
import com.breeziness.timetable.base.BaseApplication;
import com.breeziness.timetable.base.BaseFragment;
import com.breeziness.timetable.coursemain.CourseActivity;
import com.breeziness.timetable.coursemain.CourseContract;
import com.breeziness.timetable.data.bean.CourseBean;
import com.breeziness.timetable.data.bean.CourseNetBean;
import com.breeziness.timetable.data.bean.TestCourseBean;
import com.breeziness.timetable.util.RandomUtil;
import com.breeziness.timetable.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends BaseFragment implements CourseContract.View, View.OnClickListener, CourseActivity.OnWeekChangeListener {

    private static final String TAG = "CourseFragment";

    private CourseContract.Presenter mPresenter;

    //weekview
    private CalendarDate calendarDate;
    private WeekViewBar weekViewBar;
    private List<Integer> days;

    //courseLayout
    private CourseLayout layout;

    //Data
    private int CurWeek = 6;//当前周
    private List<CourseBean> dataBeans;


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
        mPresenter.getCourse();
        return contentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initView(View contentView) {
        weekViewInit(contentView);//初始化星期头部
        courseLayoutInit(contentView);//课程布局初始化
        //courseLayoutInitTest(contentView);
    }

    private void weekViewInit(View contentView) {
        calendarDate = new CalendarDate();
        days = calendarDate.getTargetWeekDays(0);//默认显示当前周 偏移0
        weekViewBar = contentView.findViewById(R.id.weekbar);
        weekViewBar.setTextList(days);
    }

    private void courseLayoutInitTest(View contentView) {
        layout = contentView.findViewById(R.id.cources);

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

        layout = contentView.findViewById(R.id.cources);


        //记得在这里判断数据集合是否为空，为空不显示，避免ANR
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
    public void onResume() {
        super.onResume();
        //在这里订阅事件
    }

    @Override
    public void onPause() {
        super.onPause();
        //在这里取消订阅事件
        mPresenter.detach();//将presenter中正在执行的任务取消，将view对象置为空。
    }


    @Override
    public void onClick(View v) {
    }

    @Override
    public void OnWeekChange(int position) {
        calendarDate = new CalendarDate();//此处有缺陷，每次获取都是不同对象  会new出很多对象
        days = calendarDate.getTargetWeekDays(position + 1 - getCurWeek());
        weekViewBar.setTextList(days);
    }

    private int getCurWeek() {
        return SharedPreferencesUtil.getInt(getContext(), "CurrentWeek", "curweek", 0);
    }

    @Override
    public void setCourse(List<CourseBean> dataBeans) {
        this.dataBeans = dataBeans;
    }

    @Override
    public void setPresenter(CourseContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }
}

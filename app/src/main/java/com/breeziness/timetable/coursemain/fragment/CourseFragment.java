package com.breeziness.timetable.coursemain.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.breeziness.timetable.data.DataHelper;
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


    private List<CourseBean> dataBeans;
    private List<CourseBean> weekCourses;


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
    private List<CourseBean> cources = new ArrayList<>();//课程数据

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
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.getCourse();//获取这学期课表
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        layout = contentView.findViewById(R.id.courses);

    }

    private void courseLayoutInit(View contentView) {

//        CourseBean c = new CourseBean();
//        c.setCname("机器学习");
//        c.setCroomno("02306Y");
//        c.setWeek(5);
//        c.setStartweek(8);
//        c.setEndweek(16);
//        c.setSeq("2");
//        c.setName("林乐平");

        layout = contentView.findViewById(R.id.courses);

//        int randBg = bg_color[RandomUtil.getRandomInt(bg_color.length - 1)];
//        CourseView courseView = new CourseView(BaseApplication.getContext());
//        courseView.setCourceId(c.getId());
//        courseView.setSeq(c.getSeq());
//        courseView.setWeekday(c.getWeek());
//        courseView.setBackground(BaseApplication.getContext().getDrawable(randBg));
//        courseView.setText(String.format("%s@%s", c.getCname(), c.getCroomno()));
//        courseView.setTextColor(Color.WHITE);
//        courseView.setTextSize(10);
//        courseView.setGravity(Gravity.CENTER);
//        layout.addView(courseView);
    }

    private void showCourses(List<CourseBean> weekCourses) {
        for (int i = 0; i < weekCourses.size(); i++) {
            CourseBean c = weekCourses.get(i);
            int randBg = bg_color[RandomUtil.getRandomInt(bg_color.length - 1)];
            CourseView courseView = new CourseView(BaseApplication.getContext());
            courseView.setCourceId(c.getId());
            courseView.setSeq(c.getSeq());
            courseView.setWeekday(c.getWeek());
            courseView.setBackground(BaseApplication.getContext().getDrawable(randBg));
            courseView.setText(String.format("%s@%s", c.getCname(), c.getCroomno()));
            courseView.setTextColor(Color.WHITE);
            courseView.setTextSize(10);
            courseView.setGravity(Gravity.CENTER);
            layout.addView(courseView);
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void OnWeekChange(int position) {
        //改变星期日期栏
        calendarDate = new CalendarDate();//此处有缺陷，每次获取都是不同对象  会new出很多对象
        days = calendarDate.getTargetWeekDays(position + 1 - getCurWeek());
        weekViewBar.setTextList(days);
        //改变课表显示
        if (mPresenter != null) {
            mPresenter.getCourse();//获取这学期课表
        }
        // handleCourseData(position+1);
    }

    private int getCurWeek() {
        return SharedPreferencesUtil.getInt(getContext(), "CurrentWeek", "curweek", 0);
    }

    @Override
    public void setCourse(List<CourseBean> dataBeans) {
        this.dataBeans = dataBeans;
        Log.e(TAG, "setCourse: -----" + dataBeans.get(0).getCname());
        handleCourseData(getCurWeek());
        if (weekCourses.size() != 0) {
            Log.e(TAG, "setCourse: ---weekCourses.size()----"+weekCourses.size());
            showCourses(weekCourses);
        }else {
            Log.e(TAG, "setCourse: ---weekCourses.size()----"+weekCourses.size());
        }

    }

    @Override
    public void setPresenter(CourseContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    private void handleCourseData(int week) {
        DataHelper dh = new DataHelper(dataBeans);
        weekCourses = dh.getCoursesOfWeek(week);
        for (int i = 0; i < weekCourses.size(); i++) {
            Log.e(TAG, "handleCourseData: -------" + weekCourses.get(i).getCname() + weekCourses.get(i).getSeq());
        }

    }
}

package com.breeziness.timetable.coursemain;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.breeziness.timetable.R;
import com.breeziness.timetable.UI.weekview.CalendarDate;
import com.breeziness.timetable.UI.weekview.OldWeekView;
import com.breeziness.timetable.addcource.AddCourseActivity;
import com.breeziness.timetable.UI.courselayout.CourseLayout;
import com.breeziness.timetable.UI.courseview.CourseView;
import com.breeziness.timetable.UI.popwin.weekpopwin.DropBean;
import com.breeziness.timetable.UI.popwin.weekpopwin.PopView;
import com.breeziness.timetable.data.bean.TestCourseBean;
import com.breeziness.timetable.homePage.HomeActivity;
import com.breeziness.timetable.util.DateUtil;
import com.breeziness.timetable.util.RandomUtil;
import com.google.android.material.navigation.NavigationView;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import static android.widget.Toast.LENGTH_SHORT;


public class CourseActivity extends AppCompatActivity implements CourseContract.View, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, PopView.OnDropItemSelectListener {

    private static final String TAG = "CourseActivity";

    private PopView popView;

    private DrawerLayout drawer;
    private List<DropBean> weekList;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private OldWeekView oldWeekView;
    private DateUtil dateUtil;
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
    private List<String> days = new ArrayList<>();
    protected CourseContract.Presenter mPresenter;//Presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cource);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏，禁止屏幕横屏显示
        getWindow().setNavigationBarColor(Color.WHITE);//设置底部导航虚拟按键颜色为白色

        initData();

        //初始化选择周次数据
        initWeeksData();
        //初始化view
        initView();
        //沉浸式状态栏
        setStatusBarColor();

        //获取Presenter实例，传入view对象
        CoursePresenter coursePresenter = new CoursePresenter(this);


        //测试区域
        CalendarDate cca = new CalendarDate();
        cca.getTargetWeek(1);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();//将presenter中正在执行的任务取消，将view对象置为空。
    }


    /**
     * 初始化控件
     */
    private void initView() {

        //初始化选择周次弹出菜单
        popViewInit();
        //初始化toolbar
        toolbarInit();
        //初始化测换菜单
        drawerInit();
        //初始化侧滑菜单内容视图
        navigatinViewInit();
        //初始化星期头部
        weekviewInit();

        //测试
        initTestCourceData();
        for (int i = 0; i < cources.size(); i++) {
            int randBg = bg_color[RandomUtil.getRandomInt(bg_color.length - 1)];
            TestCourseBean cource = cources.get(i);
            CourseLayout layout = findViewById(R.id.cources);
            CourseView courseView = new CourseView(getApplicationContext());
            courseView.setCourceId(cource.getCourceId());
            courseView.setStartSection(cource.getStartSection());
            courseView.setEndSection(cource.getEndSection());
            courseView.setWeekday(cource.getWeekday());
            courseView.setBackground(getDrawable(randBg));
            courseView.setText(String.format("%s@%s", cource.getCourceName(), cource.getClassroom()));
            courseView.setTextColor(Color.WHITE);
            //courceView.setAlpha(0.5f);
            courseView.setTextSize(10);
            courseView.setGravity(Gravity.CENTER);
            layout.addView(courseView);
        }

    }

    //测试数据
    public void initTestCourceData() {
        TestCourseBean cource1 = new TestCourseBean("通信原理A", "黎", 1, 1, 1, 1, "11C107", "1-16");
        TestCourseBean cource2 = new TestCourseBean("微波天线", "黎", 2, 1, 2, 2, "02201Y", "1-16");
        TestCourseBean cource3 = new TestCourseBean("科技文献阅读和写作（信息类）", "黎", 3, 7, 2, 2, "11C107", "1-16");
        TestCourseBean cource4 = new TestCourseBean("科技文献阅读和写作（信息类）", "黎", 3, 4, 4, 4, "11C107", "1-16");
        cources.add(cource1);
        cources.add(cource2);
        cources.add(cource3);
        cources.add(cource4);
    }

    /**
     * view的点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击事件
        }
    }

    private void weekviewInit() {
        oldWeekView = findViewById(R.id.weekview);
        dateUtil = new DateUtil(6, this);
        oldWeekView.setData(dateUtil.getWeekRange(6));

    }

    private void initData() {
        for (int i = 1; i <= 7; i++) {
            days.add(i + "日");
        }
    }

    /**
     * 初始化选择周次弹出框
     */
    private void popViewInit() {
        popView = findViewById(R.id.drop_couerce_select);
        popView.setData(weekList);//这里记得传入当前周号
        popView.setOnDropItemSelectListener(this);
    }
    /************************************透明状态栏********************************/
    /**
     * 透明状态栏,不再支持6.0以下
     */
    protected void setStatusBarColor() {
        getWindow().setStatusBarColor(Color.TRANSPARENT);//透明
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }


    /************************************toolbar相关*************************************/
    /**
     * 初始化toolbar
     */
    private void toolbarInit() {
        /*toolbar*/
        toolbar = findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_menu_more, null));//设置溢出菜单按钮图标，默认是三个点
        setSupportActionBar(toolbar);
    }

    /**
     * 向toolbar添加menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * toolbar menu菜单点击处理
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_toolbar_add:
                Toast.makeText(CourseActivity.this, "添加课程", LENGTH_SHORT).show();
                intent = new Intent(CourseActivity.this, AddCourseActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_toolbar_remove:
                Toast.makeText(CourseActivity.this, "删除课程", LENGTH_SHORT).show();
                intent = new Intent(CourseActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_toolbar_share:
                Toast.makeText(CourseActivity.this, "分享课程", LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /******************************侧滑菜单**************************/

    /**
     * 初始化侧滑菜单内容视图
     */
    private void navigatinViewInit() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * 初始化侧滑菜单
     */
    private void drawerInit() {
        /*drawerlayout侧滑菜单*/
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);//切换（滑出与关闭）的监听
        toggle.setDrawerIndicatorEnabled(false);//不适用默认的图标,但是这里是直接禁止了它点击导航icon弹出侧滑菜单，所以到toolbar的icon点击事件设置弹出
        toggle.setHomeAsUpIndicator(R.drawable.ic_navigation);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);//设置图标点击事件
            }
        });
        toggle.syncState();
    }

    /**
     * 侧滑菜单点击事件
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_cources:
                Toast.makeText(CourseActivity.this, "我的课程", LENGTH_SHORT).show();
                break;
            case R.id.nav_share_cource:
                Toast.makeText(CourseActivity.this, "分享课程", LENGTH_SHORT).show();
                break;
            case R.id.nav_mark:
                Toast.makeText(CourseActivity.this, "我的成绩", LENGTH_SHORT).show();
                break;
            case R.id.nav_credit_score:
                Toast.makeText(CourseActivity.this, "我的学分绩", LENGTH_SHORT).show();
                break;
            case R.id.nav_share_app:
                Toast.makeText(CourseActivity.this, "分享应用", LENGTH_SHORT).show();
                break;
            case R.id.nav_about_us:
                Toast.makeText(CourseActivity.this, "关于我们", LENGTH_SHORT).show();
                break;
            case R.id.nav_system_setting:
                Toast.makeText(CourseActivity.this, "系统设置", LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);//带有动画的关闭侧滑菜单
        return true;
    }

    /**
     * 添加周次数据
     */
    private void initWeeksData() {
        weekList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            weekList.add(new DropBean("第" + (i + 1) + "周"));
        }
    }

    /*********************************以下是View接口的方法*******************************************/
    @Override
    public void showProgressBar(boolean isShow) {

    }

    @Override
    public void setCource(String cource) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(CourseContract.Presenter presenter) {
        mPresenter = presenter;
    }



    /**
     * 周次弹出菜单选择监听
     *
     * @param Postion
     */
    @Override
    public void onDropItemSelect(int Postion) {
        oldWeekView.setData(dateUtil.getWeekRange(Postion+1));
        Log.e(TAG, "onDropItemSelect: -----Postion------"+(Postion+1));
    }
}

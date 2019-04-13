package com.breeziness.timetable.coursemain;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.breeziness.timetable.R;
import com.breeziness.timetable.UI.floatingBar.FloatingBar;
import com.breeziness.timetable.UI.weekview.CalendarDate;
import com.breeziness.timetable.UI.weekview.WeekViewBar;
import com.breeziness.timetable.addcource.AddCourseActivity;
import com.breeziness.timetable.UI.popwin.weekpopwin.PopView;
import com.breeziness.timetable.coursemain.fragment.CourseFragment;
import com.breeziness.timetable.coursemain.fragment.StudentUtilsFragment;
import com.breeziness.timetable.coursemain.fragment.StudyHelperFragment;
import com.breeziness.timetable.data.bean.TestCourseBean;
import com.breeziness.timetable.homePage.HomeActivity;
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


public class CourseActivity extends AppCompatActivity implements PopView.OnDropItemSelectListener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, FloatingBar.OnFloatingItemChecked {

    private static final String TAG = "CourseActivity";
    protected CoursePresenter coursePresenter;//Presenter
    //private OnWeekChangeListener onWeekChangeListener;// fragment和activity的监听器  当选择周次变化时通知fragment更新数据

    private PopView popView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FloatingBar floatingBar;

    private CourseFragment courseFragment;
    private StudyHelperFragment helperFragment;
    private StudentUtilsFragment utilsFragment;

    private int CurWeek = 6;//当前周

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cource);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏，禁止屏幕横屏显示
        getWindow().setNavigationBarColor(Color.WHITE);//设置底部导航虚拟按键颜色为白色
        initView();//初始化view
        setStatusBarColor();//沉浸式状态栏

        coursePresenter = new CoursePresenter(courseFragment);//传入view对象
    }


    //初始化控件
    private void initView() {
        popViewInit(); //初始化选择周次弹出菜单
        toolbarInit();//初始化toolbar
        drawerInit();//初始化测换菜单
        navigationViewInit();//初始化侧滑菜单内容视图
        floatingBarInit();//初始化底部悬浮菜单栏
        fragmentInit();//初始化fragment
    }


    private void fragmentInit() {
        courseFragment = new CourseFragment();
        helperFragment = new StudyHelperFragment();
        utilsFragment = new StudentUtilsFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.content_container, courseFragment).commit();
    }

    //初始化底部悬浮菜单栏
    private void floatingBarInit() {
        floatingBar = findViewById(R.id.fb_bar);
        floatingBar.setOnFloatingItemChecked(this);
    }


    //初始化选择周次弹出框
    private void popViewInit() {
        popView = findViewById(R.id.drop_couerce_select);
        popView.setData(CurWeek, CurWeek);//这里记得传入当前周号
        popView.setOnDropItemSelectListener(this);
    }

    /************************************toolbar相关*************************************/

    private void toolbarInit() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_menu_more, null));//设置溢出菜单按钮图标，默认是三个点
        setSupportActionBar(toolbar);
    }

    //透明状态栏,不再支持6.0以下
    protected void setStatusBarColor() {
        getWindow().setStatusBarColor(Color.TRANSPARENT);//透明
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_toolbar_add:
                Toast.makeText(CourseActivity.this, "修改当前周", LENGTH_SHORT).show();
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


    private void navigationViewInit() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void drawerInit() {
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.nav_cources:
                //Toast.makeText(CourseActivity.this, "我的课程", LENGTH_SHORT).show();
                intent = new Intent(CourseActivity.this, AddCourseActivity.class);
                startActivity(intent);
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

    @Override
    public void onDropItemSelect(int Postion) {
        courseFragment.OnWeekChange(Postion);
    }

    @Override
    public void onItemChecked(int id) {
        switch (id) {
            case 0:
                popView.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_container, courseFragment).commit();
                break;
            case 1:
                popView.setVisibility(View.GONE);
                toolbar.setTitle("学习助手");
                toolbar.setTitleTextColor(getColor(R.color.colorPrimary));

                getSupportFragmentManager().beginTransaction().replace(R.id.content_container, helperFragment).commit();
                break;
            case 2:
                popView.setVisibility(View.GONE);
                toolbar.setTitle("小工具");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_container, utilsFragment).commit();
                break;
            case 3:
                Toast.makeText(CourseActivity.this, "功能未开放", LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击事件
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

//    public void setOnWeekChangeListener(OnWeekChangeListener onWeekChangeListener) {
//        this.onWeekChangeListener = onWeekChangeListener;
//    }

    public interface OnWeekChangeListener {
        void OnWeekChange(int position);
    }

}

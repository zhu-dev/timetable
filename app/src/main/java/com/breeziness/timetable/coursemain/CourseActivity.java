package com.breeziness.timetable.coursemain;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.breeziness.timetable.LoginPage.LoginActivity;
import com.breeziness.timetable.R;
import com.breeziness.timetable.UI.floatingBar.FloatingBar;
import com.breeziness.timetable.UI.weekview.CalendarDate;
import com.breeziness.timetable.setCurrentWeek.CurrentWeekActivity;
import com.breeziness.timetable.util.DateTimeUtil;
import com.breeziness.timetable.addcourse.AddCourseActivity;
import com.breeziness.timetable.UI.popwin.weekpopwin.PopView;
import com.breeziness.timetable.coursemain.fragment.CourseFragment;
import com.breeziness.timetable.coursemain.fragment.StudentUtilsFragment;
import com.breeziness.timetable.coursemain.fragment.StudyHelperFragment;
import com.breeziness.timetable.homePage.HomeActivity;
import com.breeziness.timetable.util.SharedPreferencesUtil;
import com.google.android.material.navigation.NavigationView;


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

    private boolean flag = true;//开启定时器的标志
    private static final int TIMER = 1;//定时器Handler标识

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cource);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏，禁止屏幕横屏显示
        getWindow().setNavigationBarColor(Color.WHITE);//设置底部导航虚拟按键颜色为白色
        setStatusBarColor();//沉浸式状态栏
        initView();//初始化view
        //coursePresenter = new CoursePresenter(courseFragment);//传入view对象
        autoWeekIncrement();//判断周次自动增加
        Log.e(TAG, "onCreate: ------->");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //解决在其他界面设置了Toolbar的透明度后，回到这个界面Toolbar等界面显示不正常的Bug
        toolbar.getBackground().setAlpha(255);
        popView.setData(getCurWeek(), getCurWeek());//在这个再设置一次
        Log.e(TAG, "onResume: ------->");
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
        // popView.setData(getCurWeek(), getCurWeek());//这里记得传入当前周号
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //注意要清除 FLAG_TRANSLUCENT_STATUS flag
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);//透明
        //6.0以后的系统需要设置状态栏的字体和图案为亮色才能正常显示
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
                intent = new Intent(CourseActivity.this, CurrentWeekActivity.class);
                startActivity(intent);
                //  Toast.makeText(CourseActivity.this, "修改当前周", LENGTH_SHORT).show();
                break;
            case R.id.menu_toolbar_remove:
                //  Toast.makeText(CourseActivity.this, "删除课程", LENGTH_SHORT).show();
                intent = new Intent(CourseActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_toolbar_share:
                //Toast.makeText(CourseActivity.this, "分享课程", LENGTH_SHORT).show();
                intent = new Intent(CourseActivity.this, LoginActivity.class);
                startActivity(intent);
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
                finish();
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
    public void onDropItemSelect(int position) {
        courseFragment.OnWeekChange(position);
        if (position != getCurWeek() - 1) {
            stopTimer();//每次点击之前都先关闭定时器
            setTimer();//启动定时器
           // Log.e(TAG, "onDropItemSelect: ---position--" + position);
        }

    }

    @Override
    public void onItemChecked(int id) {
        switch (id) {
            case 0:
                popView.setVisibility(View.VISIBLE);
                //可能需要判断是否当前显示的就是这个界面
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

    private int getCurWeek() {
        return SharedPreferencesUtil.getInt(CourseActivity.this, "CurrentWeek", "curweek", 1);
    }

    /*****************定时器*********************/
    private void setTimer() {
        flag = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        Thread.sleep(7000); //休眠5秒
                        mHandler.sendEmptyMessage(TIMER);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TIMER:
                    //去执行定时操作逻辑
                    stopTimer();
                    popView.backToCurrentWeek();
                   // Log.e(TAG, "handleMessage: ----stopTimer---");
                    break;
                default:
                    break;
            }
        }
    };

    private void stopTimer() {
        flag = false;
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
        Log.e(TAG, "onDestroy: ----->" );
    }

    private void autoWeekIncrement() {
        DateTimeUtil date = new DateTimeUtil();
        //如果今天时周一就会判读是否要将当前周次加一显示
        if (date.getCurWeekday() == 1) {
            //获取当前周日时间
            CalendarDate cd = new CalendarDate();
            int sunday = cd.getThisSunday();
            //1.读取时间戳
            int query_sunday = SharedPreferencesUtil.getInt(CourseActivity.this, "ThisSunday", "sunday", sunday);
            //如果当前是20周则不再自加
            if (getCurWeek() != 20) {
                Log.e(TAG, "autoWeekIncrement: ----getThisSunday---" + sunday);
                Log.e(TAG, "autoWeekIncrement: ----query_sunday---" + query_sunday);
                //2. 如果存取的是上周日的时间戳，就将周次加一，否则不加
                if (sunday != query_sunday) {
                    SharedPreferencesUtil.saveInt(CourseActivity.this, "CurrentWeek", "curweek", getCurWeek() + 1);//当前周数自加一
                    SharedPreferencesUtil.saveInt(CourseActivity.this, "ThisSunday", "sunday", sunday);//保存最新的周日日期
                }
            }
        }
    }

    public interface OnWeekChangeListener {
        void OnWeekChange(int position);
    }
}

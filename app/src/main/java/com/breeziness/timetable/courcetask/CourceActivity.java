package com.breeziness.timetable.courcetask;


import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.breeziness.timetable.R;
import com.breeziness.timetable.courcetask.courcelayout.CourceLayout;
import com.breeziness.timetable.courcetask.courceview.CourceView;
import com.breeziness.timetable.courcetask.popwin.DropBean;
import com.breeziness.timetable.courcetask.popwin.PopView;
import com.breeziness.timetable.data.CourceBean;
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


public class CourceActivity extends AppCompatActivity implements CourceContract.View, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "CourceActivity";

    private PopView popView;
    private DrawerLayout drawer;
    private List<DropBean> weekList;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView tv;

    private boolean isLoop = true;
    //测试内容
    private List<CourceBean> cources = new ArrayList<>();//测试用课程数据

    protected CourceContract.Presenter mPresenter;//Presenter


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cource);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏，禁止屏幕横屏显示
        getWindow().setNavigationBarColor(Color.WHITE);//设置底部导航虚拟按键颜色为白色

        //初始化选择周次数据
        initWeeksData();
        //初始化view
        initView();
        //沉浸式状态栏
        setStatusBarColor();

        //获取Presenter实例，传入view对象
        CourcePresenter courcePresenter = new CourcePresenter(this);
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


        //测试
        initTestCourceData();
        for (int i = 0; i < cources.size(); i++) {
            CourceBean cource = cources.get(i);
            CourceLayout layout = findViewById(R.id.cources);
            final CourceView courceView = new CourceView(getApplicationContext());
            courceView.setCourceId(cource.getCourceId());
            courceView.setStartSection(cource.getStartSection());
            courceView.setEndSection(cource.getEndSection());
            courceView.setWeekday(cource.getWeekday());
            courceView.setBackground(getDrawable(R.drawable.bg_cource_pink));
            courceView.setText(String.format("%s%s", cource.getCourceName(), cource.getClassroom()));
            //courceView.setTextColor(Color.WHITE);
            //courceView.setTextSize(18);
            // courceView.setGravity(Gravity.CENTER);
            //courceView.setEms(4);
            layout.addView(courceView);

        }

    }

    //测试数据
    public void initTestCourceData() {
        CourceBean cource1 = new CourceBean("通信原理", "黎", 1, 1, 1, 1, "", "1-16");
        CourceBean cource2 = new CourceBean("微波天线", "黎", 2, 1, 2, 3, "11C107", "1-16");
        CourceBean cource3 = new CourceBean("科技文献阅读和写作（信息类）", "黎", 3, 2, 2, 2, "11C107", "1-16");
        cources.add(cource1);
        cources.add(cource2);
        cources.add(cource3);
    }

    /**
     * view的点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


    /**
     * 初始化选择周次弹出框
     */
    private void popViewInit() {
        popView = findViewById(R.id.drop_couerce_select);
        popView.setData(weekList);
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

        switch (item.getItemId()) {
            case R.id.menu_toolbar_add:
                Toast.makeText(CourceActivity.this, "添加课程", LENGTH_SHORT).show();
                break;
            case R.id.menu_toolbar_remove:
                Toast.makeText(CourceActivity.this, "删除课程", LENGTH_SHORT).show();
                break;
            case R.id.menu_toolbar_share:
                Toast.makeText(CourceActivity.this, "分享课程", LENGTH_SHORT).show();
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
                Toast.makeText(CourceActivity.this, "我的课程", LENGTH_SHORT).show();
                break;
            case R.id.nav_share_cource:
                Toast.makeText(CourceActivity.this, "分享课程", LENGTH_SHORT).show();
                break;
            case R.id.nav_mark:
                Toast.makeText(CourceActivity.this, "我的成绩", LENGTH_SHORT).show();
                break;
            case R.id.nav_credit_score:
                Toast.makeText(CourceActivity.this, "我的学分绩", LENGTH_SHORT).show();
                break;
            case R.id.nav_share_app:
                Toast.makeText(CourceActivity.this, "分享应用", LENGTH_SHORT).show();
                break;
            case R.id.nav_about_us:
                Toast.makeText(CourceActivity.this, "关于我们", LENGTH_SHORT).show();
                break;
            case R.id.nav_system_setting:
                Toast.makeText(CourceActivity.this, "系统设置", LENGTH_SHORT).show();
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
    public void setPresenter(CourceContract.Presenter presenter) {
        mPresenter = presenter;
    }


}

package com.breeziness.timetable.courcetask;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.breeziness.timetable.BaseActivity;
import com.breeziness.timetable.R;
import com.breeziness.timetable.courcetask.popwin.DropBean;
import com.breeziness.timetable.courcetask.popwin.PopView;
import com.google.android.material.navigation.NavigationView;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import static android.widget.Toast.LENGTH_SHORT;


public class CourceActivity extends BaseActivity implements CourceContract.View, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "CourceActivity";
    private Button btn_start;
    private TextView tv_show;
    private ProgressBar pb;
    private ImageButton ib;
    private PopView popView;
    private DrawerLayout drawer;

    private List<DropBean> weekList;

    protected CourceContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cource);
        //初始化选择周次数据
        initWeeksData();
        /*初始化view*/
        initView();

        /*获取Presenter实例，传入view对象*/
        CourcePresenter courcePresenter = new CourcePresenter(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();//将presenter中正在执行的任务取消，将view对象置为空。
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

    /**
     * 初始化控件
     */
    private void initView() {
        /*选择周次弹出菜单*/
        popView = findViewById(R.id.drop_couerce_select);
        popView.setData(weekList);

        /*toolbar*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_menu_more, null));//设置溢出菜单按钮图标，默认是三个点
        setSupportActionBar(toolbar);

        /*drawerlayout侧滑菜单*/
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);//切换（滑出与关闭）的监听
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    /************************************设置toolbar*************************************
     /**
     *向toolbar添加menu
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

    /******************************有关侧滑菜单**************************/
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


    /*********************************以下是View接口的方法*******************************************/
    @Override
    public void showProgressBar(boolean isShow) {
        if (isShow) {
            pb.setVisibility(View.VISIBLE);
        } else {
            pb.setVisibility(View.GONE);
        }
    }

    @Override
    public void setCource(String cource) {
        tv_show.setText(cource);
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

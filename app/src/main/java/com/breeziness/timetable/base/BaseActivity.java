package com.breeziness.timetable.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.breeziness.timetable.R;

/**
 * 通用的Activity模板
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    
    //Toolbar 相关
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private CharSequence mToolbarTitleContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);//添加当前的activity到管理容器中
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏
        //getWindow().setNavigationBarColor(Color.WHITE);//设置底部导航虚拟按键颜色为白色
        setStatusBarColor(true);
        setContentView(getLayoutId());
        initData();
        initView();
        setToolBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);//移除当前马上要销毁的活动
    }

    /**
     * 显示短的Toast
     *
     * @param msg
     */
    protected void showShortToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示长的Toast
     *
     * @param msg
     */
    protected void showLongToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 透明状态栏
     *
     * @param useStatusBarColor 是否使用透明状态栏
     */
    protected void setStatusBarColor(boolean useStatusBarColor) {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //注意要清除 FLAG_TRANSLUCENT_STATUS flag
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);//透明
        //6.0以后的系统需要设置状态栏的字体和图案为亮色才能正常显示
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    /**
     * 设置layout布局
     * 让子类重写这个方法，获得布局文件
     *
     * @return layout xml id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图组件
     * 让子类重写这个方法，规范初始化View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     * 让子类重写这个方法，统一的在这个方法里初始化数据
     */
    protected abstract void initData();





    /**
     * 设置Toolbar
     * 不需要子类重写，默认统一的显示Toolbar,不需要Toolbar的不要继承这个基类
     */
    private void setToolBar() {
        mToolbar = findViewById(R.id.base_toolbar);
        mToolbarTitle = findViewById(R.id.base_toolbar_title);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getToolbar().setNavigationIcon(R.mipmap.back);
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * 设置Toolbar的标题
     *
     * @param title toolbar title
     */
    protected void setToolbarTitle(CharSequence title) {
        mToolbarTitleContent = title;
        if(mToolbarTitle != null){
            mToolbarTitle.setText(title);
        }
    }

    /**
     * 获取Toolbar实例
     * @return toolbar instance
     */
    private Toolbar getToolbar() {
        return findViewById(R.id.base_toolbar);
    }

    /**
     * 设置Toolbar的透明度
     * @param alpha Toolbar的透明度
     */
    protected void setToolbarAlpha(int alpha){
        mToolbar.getBackground().setAlpha(alpha);
        //setSupportActionBar(mToolbar);
    }
}

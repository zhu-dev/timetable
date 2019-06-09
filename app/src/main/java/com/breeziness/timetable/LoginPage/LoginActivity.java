package com.breeziness.timetable.LoginPage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.breeziness.timetable.R;
import com.breeziness.timetable.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_login);

        getWindow().setNavigationBarColor(Color.WHITE);//设置底部导航虚拟按键颜色为白色

        //Toolbar相关
        setToolbarTitle("登录胖圆课表");
        setToolbarAlpha(0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}

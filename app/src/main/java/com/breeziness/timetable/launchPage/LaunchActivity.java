package com.breeziness.timetable.launchPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.breeziness.timetable.loginPage.LoginActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_launch); // 不需要加载layout资源，在主题里放置一张图片
        getWindow().setNavigationBarColor(Color.BLACK);//设置底部导航虚拟按键颜色为白色
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从sp查询登录状态

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //如果放广告可以沉睡几秒
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //根据是否登录的状态跳转到相应得activity
                        Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
            }
        }).start();
    }


}

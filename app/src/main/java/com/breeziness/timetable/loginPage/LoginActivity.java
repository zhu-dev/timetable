package com.breeziness.timetable.loginPage;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.breeziness.timetable.R;
import com.breeziness.timetable.base.ActivityCollector;
import com.breeziness.timetable.base.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText login_ev_id_input;//账号
    private EditText login_ev_password_input;//密码
    private Button login_btn_login; //登录按键
    private ImageView login_iv_clear_input;//清空输入


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
        login_ev_id_input = findViewById(R.id.login_ev_id_input);
        login_ev_password_input = findViewById(R.id.login_ev_password_input);
        login_btn_login = findViewById(R.id.login_btn_login);
        login_iv_clear_input = findViewById(R.id.login_iv_clear_input);

        login_btn_login.setOnClickListener(this);
        login_iv_clear_input.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login: // 登录之前要判断输入得值是否合法
                if (checkInputMessage(login_ev_id_input.getText(), login_ev_password_input.getText())) {
                    //登录验证账号和密码
                    //验证成功则保存账号密码
                }
                break;
            case R.id.login_iv_clear_input: // 清空输入
                login_ev_id_input.setText("");
                login_ev_password_input.setText("");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //在取消登录时退出本应用
        ActivityCollector.finishAll();
    }

    private boolean checkInputMessage(CharSequence user_name, CharSequence password) {
        if (user_name.length() == 0 || password.length() == 0) {
            Toast.makeText(LoginActivity.this, "账号/密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (user_name.length() != 10 || password.length() < 6) {
            Toast.makeText(LoginActivity.this, "账号/密码格式错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}

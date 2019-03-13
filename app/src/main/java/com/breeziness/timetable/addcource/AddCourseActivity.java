package com.breeziness.timetable.addcource;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.breeziness.timetable.R;
import com.breeziness.timetable.data.jsoup.JsoupManager;
import com.breeziness.timetable.util.DataCharset;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener, AddCourseContract.View {
    private static final String TAG = "AddCourseActivity";
    private TextView tv_show;
    private Button btn_start;
    private Button btn_login;
    private Button btn_getCourse;
    private ProgressBar pb;
    private EditText et_identify;
    private ImageView iv_show;


    private AddCourseContract.Presenter mPresenter;
    private AddCoursePresenter courcePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_cource);
        tv_show = findViewById(R.id.tv_show);
        btn_start = findViewById(R.id.btn_start);
        pb = findViewById(R.id.pb);
        et_identify = findViewById(R.id.et_identify);
        iv_show = findViewById(R.id.iv_show);
        btn_login = findViewById(R.id.btn_login);
        btn_getCourse = findViewById(R.id.btn_getcourse);

        btn_getCourse.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        pb.setVisibility(View.GONE);

        courcePresenter = new AddCoursePresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();//将presenter中正在执行的任务取消，将view对象置为空。
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            mPresenter.getCource();
//            pb.setVisibility(View.VISIBLE);
            //登录请求
            case R.id.btn_start:
                mPresenter.getImage();
                break;
            case R.id.btn_login:
                if (!et_identify.getText().toString().isEmpty()) {
                    mPresenter.getLogin(et_identify.getText().toString());
                }
                break;
            case R.id.btn_getcourse:
                mPresenter.getCource();
                break;
        }


    }

    @Override
    public void showProgressBar(boolean isShow) {
        if (!isShow) {
            pb.setVisibility(View.GONE);
        } else {
            pb.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void setCource(String cource) {

        tv_show.setText(cource);
        Log.d(TAG, "setCource: " + cource);
    }

    @Override
    public void setImage(Bitmap bitmap) {
        iv_show.setImageBitmap(bitmap);
    }


    @Override
    public void setPresenter(AddCourseContract.Presenter presenter) {
        mPresenter = presenter;
    }
}

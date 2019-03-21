package com.breeziness.timetable.addcource;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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
import com.breeziness.timetable.UI.dialog.LoadingDialog;
import com.breeziness.timetable.UI.dialog.LoginImageDialog;
import com.breeziness.timetable.UI.editText.NumberInputView;
import com.breeziness.timetable.data.bean.CourseBean;
import com.breeziness.timetable.data.db.DataBaseHelper;
import com.breeziness.timetable.data.db.DataBaseManager;
import com.breeziness.timetable.data.jsoup.JsoupManager;
import com.breeziness.timetable.util.DataCharset;

import org.reactivestreams.Subscription;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener, AddCourseContract.View {
    private static final String TAG = "AddCourseActivity";
    private TextView tv_show;
    private Button btn_start;
    private Button btn_login;
    private Button btn_getCourse;
    private Button btn_getcourse_from_db;
    private Button btn_login_image_dialog;
    private ProgressBar pb;
    private EditText et_identify;
    private ImageView iv_show;
    private DataBaseHelper helper;

    private AddCourseContract.Presenter mPresenter;
    private AddCoursePresenter courcePresenter;


    //测试数据库
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        // helper = new DataBaseHelper(AddCourseActivity.this, "timetable.db", null, 1);//获得数据库helper实例

        courcePresenter = new AddCoursePresenter(this);
    }


    private void initView() {
        setContentView(R.layout.activity_add_cource);

        tv_show = findViewById(R.id.tv_show);
        btn_start = findViewById(R.id.btn_start);
        pb = findViewById(R.id.pb);
        et_identify = findViewById(R.id.et_identify);
        iv_show = findViewById(R.id.iv_show);
        btn_login = findViewById(R.id.btn_login);
        btn_getCourse = findViewById(R.id.btn_getcourse);
        btn_getcourse_from_db = findViewById(R.id.btn_get_course_from_db);
        btn_login_image_dialog = findViewById(R.id.btn_login_image_dialog);


        btn_login_image_dialog.setOnClickListener(this);
        btn_getcourse_from_db.setOnClickListener(this);
        btn_getCourse.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        pb.setVisibility(View.GONE);
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
                mPresenter.getCource("2018-2019_2");
                break;
            case R.id.btn_get_course_from_db:
                DataBaseManager.getInstance(AddCourseActivity.this).getAllCourse("course")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<CourseBean.DataBean>>() {
                            @Override
                            public void accept(List<CourseBean.DataBean> dataBeans) throws Exception {
                                tv_show.setText(dataBeans.get(5).getCname());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                // Log.e(TAG, "accept: -----throwable------" + throwable);
                            }
                        });
                break;
            case R.id.btn_login_image_dialog:
                LoginImageDialog loginImageDialog = new LoginImageDialog.Builder(AddCourseActivity.this).create();
                loginImageDialog.show();

                loginImageDialog.setOnDismissListener(new LoginImageDialog.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        LoadingDialog loadingDialog = new LoadingDialog.Builder(AddCourseActivity.this).create();
                        loadingDialog.show();
                    }
                });

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

    @SuppressLint("CheckResult")
    @Override
    public void setCource(List<CourseBean.DataBean> dataBeans) {

        tv_show.setText(dataBeans.get(20).getCname());

        if (flag) {
            //第一次插入
            DataBaseManager.getInstance(AddCourseActivity.this).insertCourse(dataBeans)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                //Log.e(TAG, "accept: ---1---" + aBoolean);
                                //  Toast.makeText(AddCourseActivity.this, "插入数据成功", Toast.LENGTH_SHORT).show();
                            } else {
                                //  Toast.makeText(AddCourseActivity.this, "插入数据失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            //Log.e(TAG, "accept: ---throwable1-----" + throwable);
                            //  Toast.makeText(AddCourseActivity.this, "插入异常", Toast.LENGTH_SHORT).show();
                        }
                    });

            flag = false;//改变标志位

        } else {
            DataBaseManager.getInstance(AddCourseActivity.this).updataCourse(dataBeans)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                //Log.e(TAG, "accept: ----2----" + aBoolean);
                                //Toast.makeText(AddCourseActivity.this, "更新数据成功", Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(AddCourseActivity.this, "更新数据失败", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            //Log.e(TAG, "accept: ---throwable2-----" + throwable);
                            // Toast.makeText(AddCourseActivity.this, "更新数据异常", Toast.LENGTH_SHORT).show();
                        }
                    });

        }

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

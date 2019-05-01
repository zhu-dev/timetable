package com.breeziness.timetable.addcource;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.breeziness.timetable.R;
import com.breeziness.timetable.UI.dialog.LoadingDialog;
import com.breeziness.timetable.UI.dialog.LoginImageDialog;
import com.breeziness.timetable.UI.weekview.CalendarDate;
import com.breeziness.timetable.UI.wheelpicker.InfoPicker;
import com.breeziness.timetable.base.BaseActivity;
import com.breeziness.timetable.coursemain.CourseActivity;
import com.breeziness.timetable.util.HandleDataUtil;
import com.breeziness.timetable.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import static com.breeziness.timetable.util.ErrorCodeUtil.getCourseError;
import static com.breeziness.timetable.util.ErrorCodeUtil.getImageError;
import static com.breeziness.timetable.util.ErrorCodeUtil.getLoginError;

public class AddCourseActivity extends BaseActivity implements View.OnClickListener, AddCourseContract.View {
    private static final String TAG = "AddCourseActivity";

    private int CurrentWeek = 1;//这个需要用户选择来确定

    private InfoPicker infoPicker;
    private List<String> mGradeList = new ArrayList<>();
    private List<String> mTermList = new ArrayList<>();
    private List<String> mWeekList = new ArrayList<>();

    private int mGrade;
    private int mTerm;
    private int mWeek;


    private TextView tv_student_name;
    private TextView tv_student_id;
    private Button btn_import;

    private Bitmap loginImage;//验证码
    private String loginNumber;//输入的验证码

    private LoginImageDialog loginImageDialog;
    private LoadingDialog loadingDialog;

    private AddCourseContract.Presenter mPresenter;
    private AddCoursePresenter courcePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        courcePresenter = new AddCoursePresenter(this);
    }

    protected void initView() {
        setContentView(R.layout.activity_add_cource);

        tv_student_id = findViewById(R.id.student_id);
        tv_student_name = findViewById(R.id.student_name);
        btn_import = findViewById(R.id.btn_import);
        btn_import.setOnClickListener(this);

        loadingDialog = new LoadingDialog.Builder(AddCourseActivity.this).create();

        infoPicker = findViewById(R.id.info_picker);
        if (infoPicker == null) {
            Log.e(TAG, "initView: ----infoPicker--null-");
        }
        infoPicker.setOnDataSelectedListener(new InfoPicker.OnDataSelectedListener() {
            @Override
            public void onDataSelected(int grade, int term, int week) {
                mGrade = grade;
                mTerm = term;
                mWeek = week;
                Toast.makeText(AddCourseActivity.this, "" + grade + term + week, Toast.LENGTH_SHORT).show();

            }
        });
        infoPicker.setSelectedInfo(1, 1, 9);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();//将presenter中正在执行的任务取消，将view对象置为空。避免OOM
    }


    /**
     * view的点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            mPresenter.getCourse();
            //登录请求
            case R.id.btn_import:
                // Log.e(TAG, "onClick: ---获取课表---");
                mPresenter.getImage();//发起获取验证码
                break;
        }

    }

    /**
     * 显示加载dialog
     *
     * @param isShow
     */
    @Override
    public void showProgressBar(boolean isShow) {
        if (isShow) {
            loadingDialog.show();
        } else {
            loadingDialog.dismiss();
        }

    }

    @Override
    public void complete() {

        //获取课表成功后保存设置的当前周次
        SharedPreferencesUtil.saveInt(AddCourseActivity.this, "CurrentWeek", "curweek", mWeek + 1);//保存设置的当前周数

        //把这周日的时间戳伴随设置的当前周次保存，用于判断要周次自动增加
        CalendarDate cd = new CalendarDate();
        int date = cd.getThisSunday();
        SharedPreferencesUtil.saveInt(AddCourseActivity.this, "ThisSunday", "sunday", date);//保存当前的周日日期

        //要在获取课程之后返回到课程的界面
        Intent intent = new Intent(AddCourseActivity.this, CourseActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 显示验证码
     *
     * @param bitmap
     */
    @Override
    public void setImage(Bitmap bitmap) {
        if (bitmap != null) {
            loginImage = bitmap;
            loginImageDialog = new LoginImageDialog.Builder(AddCourseActivity.this, loginImage).create();
            loginImageDialog.show();
            loginImageDialog.setOnDismissListener(new LoginImageDialog.OnDismissListener() {
                @Override
                public void onDismiss(String content) {
                    if (content != null) {
                        mPresenter.getLogin(content);
                        // Log.e(TAG, "onDismiss: ---输入的验证码---" + content);
                    } else {
                        Toast.makeText(AddCourseActivity.this, "输入验证码为空", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {
            Toast.makeText(AddCourseActivity.this, "获取不到验证码，网络出错", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示登录结果信息
     *
     * @param isSuccess
     * @param content
     */
    @Override
    public void setLoginMassage(boolean isSuccess, String content) {
        if (isSuccess) {
            Toast.makeText(AddCourseActivity.this, content, Toast.LENGTH_SHORT).show();//显示登录结果信息
            //Log.e(TAG, "setLoginMassage:-----term---- "+HandleDataUtil.handleGrade(mGrade) + HandleDataUtil.handleTerm(mTerm) );
            mPresenter.getCourse(HandleDataUtil.handleGrade(mGrade) + HandleDataUtil.handleTerm(mTerm));//传入特定学期，获取课程

        } else {
            //Log.e(TAG, "setLoginMassage: -----" + content);
            Toast.makeText(AddCourseActivity.this, content, Toast.LENGTH_SHORT).show();//显示登录结果信息
        }


    }

    /**
     * 显示错误信息
     *
     * @param e
     */
    @Override
    public void showError(int eCode, String e) {
        switch (eCode) {
            case getImageError:
                Toast.makeText(AddCourseActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                break;
            case getLoginError:
                Toast.makeText(AddCourseActivity.this, "登录教务系统失败", Toast.LENGTH_SHORT).show();
                break;
            case getCourseError:
                Toast.makeText(AddCourseActivity.this, "获取课程信息失败", Toast.LENGTH_SHORT).show();
                break;

        }
    }


    @Override
    public void setPresenter(AddCourseContract.Presenter presenter) {
        mPresenter = presenter;
    }
}




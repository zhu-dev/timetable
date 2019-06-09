package com.breeziness.timetable.setCurrentWeek;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.breeziness.timetable.R;
import com.breeziness.timetable.UI.BezierSeekBar.BezierSeekBar;
import com.breeziness.timetable.base.BaseActivity;
import com.breeziness.timetable.util.SharedPreferencesUtil;

public class CurrentWeekActivity extends BaseActivity {
    private static final String TAG = "CurrentWeekActivity";

    private int currentWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_current_week);

        //Toolbar 相关
        setToolbarTitle("修改当前周");
        setToolbarAlpha(0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_current_week;
    }

    @Override
    protected void initView() {

        Button btn_save = findViewById(R.id.btn_save);
        BezierSeekBar bs = findViewById(R.id.bs);
        bs.setOnSelectedValueListener(new BezierSeekBar.OnSelectedValueListener() {
            @Override
            public void onSelectedValue(int value) {
                currentWeek = value;
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.saveInt(CurrentWeekActivity.this, "CurrentWeek", "curweek", currentWeek);//保存设置的当前周数
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }
}

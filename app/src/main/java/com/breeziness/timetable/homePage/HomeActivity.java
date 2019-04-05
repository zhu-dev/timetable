package com.breeziness.timetable.homePage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.breeziness.timetable.R;
import com.breeziness.timetable.UI.floatingBar.FloatingBar;
import com.breeziness.timetable.UI.weekview.CalendarDate;
import com.breeziness.timetable.UI.weekview.DateView;
import com.breeziness.timetable.UI.weekview.WeekViewBar;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CalendarDate calendarDate = new CalendarDate();
        List<Integer> days = calendarDate.getTargetWeekDays(0);
        WeekViewBar weekViewBar = findViewById(R.id.weekbar);
        weekViewBar.setTextList(days);

        FloatingBar floatingBar = findViewById(R.id.fb_bar);
        //floatingBar.bringToFront();


    }
}

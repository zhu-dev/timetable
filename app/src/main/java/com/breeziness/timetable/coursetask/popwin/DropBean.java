package com.breeziness.timetable.coursetask.popwin;

public class DropBean {
    private boolean isCheck;
    private String weekday;

    public DropBean(String weekday) {
        this.weekday = weekday;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }
}

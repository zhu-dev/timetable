package com.breeziness.timetable.UI.popwin.weekpopwin;

public class DropBean {
    private boolean isCheck;
    private String weekday;
    private boolean curWeek;

    public boolean isCurWeek() {
        return curWeek;
    }

    public void setCurWeek(boolean curWeek) {
        this.curWeek = curWeek;
    }

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

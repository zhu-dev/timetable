package com.breeziness.timetable.data.bean;

public class CourseBean {

    private int id; //唯一的id
    private String cname;//课程名称
    private String courseno;//课号
    private String name;//老师名字
    private String term;//学期
    private String croomno;//教师
    private int startweek;//起始周次
    private int endweek;//结束周次
    private int week;//星期几
    private String seq;//节次


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCourseno() {
        return courseno;
    }

    public void setCourseno(String courseno) {
        this.courseno = courseno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getCroomno() {
        return croomno;
    }

    public void setCroomno(String croomno) {
        this.croomno = croomno;
    }

    public int getStartweek() {
        return startweek;
    }

    public void setStartweek(int startweek) {
        this.startweek = startweek;
    }

    public int getEndweek() {
        return endweek;
    }

    public void setEndweek(int endweek) {
        this.endweek = endweek;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }


}

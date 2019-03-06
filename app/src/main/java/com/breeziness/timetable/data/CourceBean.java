package com.breeziness.timetable.data;

/**
 * 课程实体类
 */
public class CourceBean {
    private String courceName;
    private String teacher;
    private int courceId;
    private int weekday;
    private int startSection;
    private int endSection;
    private String classroom;
    private String courceTime;

    public CourceBean(String courceName, String teacher, int courceId, int weekday, int startSection, int endSection, String classroom, String courceTime) {
        this.courceName = courceName;
        this.teacher = teacher;
        this.courceId = courceId;
        this.weekday = weekday;
        this.startSection = startSection;
        this.endSection = endSection;
        this.classroom = classroom;
        this.courceTime = courceTime;
    }

    public String getCourceName() {
        return courceName;
    }

    public void setCourceName(String courceName) {
        this.courceName = courceName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getCourceId() {
        return courceId;
    }

    public void setCourceId(int courceId) {
        this.courceId = courceId;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getStartSection() {
        return startSection;
    }

    public void setStartSection(int startSection) {
        this.startSection = startSection;
    }

    public int getEndSection() {
        return endSection;
    }

    public void setEndSection(int endSection) {
        this.endSection = endSection;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getCourceTime() {
        return courceTime;
    }

    public void setCourceTime(String courceTime) {
        this.courceTime = courceTime;
    }
}

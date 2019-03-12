package com.breeziness.timetable.data.bean;

/**
 * 登录成功返回的json信息
 */
public class LoginBean {

    /**
     * success : true
     * msg : 操作成功
     * data : 2
     */

    private boolean success;
    private String msg;
    private String data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

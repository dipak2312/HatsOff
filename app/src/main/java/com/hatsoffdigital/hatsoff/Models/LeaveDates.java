package com.hatsoffdigital.hatsoff.Models;

public class LeaveDates {

    private String[] holidays_leave_list;

    private String code;

    private String msg;


    public String[] getHolidays_leave_list() {
        return holidays_leave_list;
    }

    public void setHolidays_leave_list(String[] holidays_leave_list) {
        this.holidays_leave_list = holidays_leave_list;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

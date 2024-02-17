package com.hatsoffdigital.hatsoff.Models;

public class LeaveCount {

    private String code;
    private String msg;
    private String emergency_leave_count;
    private String planned_leave_count;


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

    public String getEmergency_leave_count() {
        return emergency_leave_count;
    }

    public void setEmergency_leave_count(String emergency_leave_count) {
        this.emergency_leave_count = emergency_leave_count;
    }

    public String getPlanned_leave_count() {
        return planned_leave_count;
    }

    public void setPlanned_leave_count(String planned_leave_count) {
        this.planned_leave_count = planned_leave_count;
    }
}

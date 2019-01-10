package com.hatsoffdigital.hatsoff.Models;

public class Leave_list {

    private String employee_id;
    private String fullname;
    private String leave_date_from;
    private String leave_date_to;
    private String type;
    private String days;
    private String reason;
    private String applied_date;
    private String leave_status;


    public String getApplied_date() {
        return applied_date;
    }

    public void setApplied_date(String applied_date) {
        this.applied_date = applied_date;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLeave_date_from() {
        return leave_date_from;
    }

    public void setLeave_date_from(String leave_date_from) {
        this.leave_date_from = leave_date_from;
    }

    public String getLeave_date_to() {
        return leave_date_to;
    }

    public void setLeave_date_to(String leave_date_to) {
        this.leave_date_to = leave_date_to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLeave_status() {
        return leave_status;
    }

    public void setLeave_status(String leave_status) {
        this.leave_status = leave_status;
    }
}

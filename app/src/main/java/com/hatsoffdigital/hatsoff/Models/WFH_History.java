package com.hatsoffdigital.hatsoff.Models;

public class WFH_History {
    private String from_date;
    private String to_date;
    private String applied_date;
    private String employee_reason;
    private String mgmt_reason;
    private String leave_status;
    private String days;

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getApplied_date() {
        return applied_date;
    }

    public void setApplied_date(String applied_date) {
        this.applied_date = applied_date;
    }

    public String getEmployee_reason() {
        return employee_reason;
    }

    public void setEmployee_reason(String employee_reason) {
        this.employee_reason = employee_reason;
    }

    public String getMgmt_reason() {
        return mgmt_reason;
    }

    public void setMgmt_reason(String mgmt_reason) {
        this.mgmt_reason = mgmt_reason;
    }

    public String getLeave_status() {
        return leave_status;
    }

    public void setLeave_status(String leave_status) {
        this.leave_status = leave_status;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}

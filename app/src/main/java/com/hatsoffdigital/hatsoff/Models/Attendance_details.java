package com.hatsoffdigital.hatsoff.Models;

public class Attendance_details {

    private String pm_out;

    private String total_hours;

    private String late;

    private String undertime;

    private String attendance_id;

    private String date;

    private String employee_id;

    private String am_in;

    private  String status;


    public String getPm_out() {
        return pm_out;
    }

    public void setPm_out(String pm_out) {
        this.pm_out = pm_out;
    }

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }

    public String getUndertime() {
        return undertime;
    }

    public void setUndertime(String undertime) {
        this.undertime = undertime;
    }

    public String getAttendance_id() {
        return attendance_id;
    }

    public void setAttendance_id(String attendance_id) {
        this.attendance_id = attendance_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getAm_in() {
        return am_in;
    }

    public void setAm_in(String am_in) {
        this.am_in = am_in;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_hours() {
        return total_hours;
    }

    public void setTotal_hours(String total_hours) {
        this.total_hours = total_hours;
    }
}

package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class AttendenceDetailsResponse {

    private String code;

    ArrayList<Attendance_details>Attendance_details;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<com.hatsoffdigital.hatsoff.Models.Attendance_details> getAttendance_details() {
        return Attendance_details;
    }

    public void setAttendance_details(ArrayList<com.hatsoffdigital.hatsoff.Models.Attendance_details> attendance_details) {
        Attendance_details = attendance_details;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

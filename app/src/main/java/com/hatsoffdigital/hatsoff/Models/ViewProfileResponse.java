package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class ViewProfileResponse {

    private  String code;
    private  String msg;
    ArrayList<EmployeeProfile>EmployeeProfile;

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

    public ArrayList<com.hatsoffdigital.hatsoff.Models.EmployeeProfile> getEmployeeProfile() {
        return EmployeeProfile;
    }

    public void setEmployeeProfile(ArrayList<com.hatsoffdigital.hatsoff.Models.EmployeeProfile> employeeProfile) {
        EmployeeProfile = employeeProfile;
    }
}

package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class EmployeeListResponse {

    private String code;

    ArrayList<employee_list> employee_list;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<com.hatsoffdigital.hatsoff.Models.employee_list> getEmployee_list() {
        return employee_list;
    }

    public void setEmployee_list(ArrayList<com.hatsoffdigital.hatsoff.Models.employee_list> employee_list) {
        this.employee_list = employee_list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

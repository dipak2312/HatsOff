package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class LeaveListResponse {

    ArrayList<Leave_list> Leave_list;

    private String code;

    private String msg;


    public ArrayList<com.hatsoffdigital.hatsoff.Models.Leave_list> getLeave_list() {
        return Leave_list;
    }

    public void setLeave_list(ArrayList<com.hatsoffdigital.hatsoff.Models.Leave_list> leave_list) {
        Leave_list = leave_list;
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

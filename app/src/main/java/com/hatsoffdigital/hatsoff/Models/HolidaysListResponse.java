package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class HolidaysListResponse {

    ArrayList<Holidays_list>holidays_list;

    private String code;

    private String msg;


    public ArrayList<Holidays_list> getHolidays_list() {
        return holidays_list;
    }

    public void setHolidays_list(ArrayList<Holidays_list> holidays_list) {
        this.holidays_list = holidays_list;
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

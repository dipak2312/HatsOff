package com.hatsoffdigital.hatsoff.Models;

public class HoFactsResponse {
    private  String msg;
    private  String FactDate;
    private  String FactName;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFactDate() {
        return FactDate;
    }

    public void setFactDate(String factDate) {
        FactDate = factDate;
    }

    public String getFactName() {
        return FactName;
    }

    public void setFactName(String factName) {
        FactName = factName;
    }
}

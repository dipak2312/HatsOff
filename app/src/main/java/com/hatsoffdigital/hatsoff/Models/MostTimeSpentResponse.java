package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class MostTimeSpentResponse {
    private String msg;
    private String Month;
    private String Year;
    ArrayList<MostTimeSpent>MostTimeSpent;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public ArrayList<com.hatsoffdigital.hatsoff.Models.MostTimeSpent> getMostTimeSpent() {
        return MostTimeSpent;
    }

    public void setMostTimeSpent(ArrayList<com.hatsoffdigital.hatsoff.Models.MostTimeSpent> mostTimeSpent) {
        MostTimeSpent = mostTimeSpent;
    }
}

package com.hatsoffdigital.hatsoff.Models;

public class HolidayPopupResponse {
    private String msg;
    private String date_notification;
    private String holidays_img;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate_notification() {
        return date_notification;
    }

    public void setDate_notification(String date_notification) {
        this.date_notification = date_notification;
    }

    public String getHolidays_img() {
        return holidays_img;
    }

    public void setHolidays_img(String holidays_img) {
        this.holidays_img = holidays_img;
    }
}

package com.hatsoffdigital.hatsoff.Models;

public class ScanDateTime {

    private String msg;
    private String In;
    private String Out;
    private String time;
    private String Hours;
    private  String Late;
    private  String early;
    private String half_day_or_meeting;



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIn() {
        return In;
    }

    public void setIn(String in) {
        In = in;
    }

    public String getOut() {
        return Out;
    }

    public void setOut(String out) {
        Out = out;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHours() {
        return Hours;
    }

    public void setHours(String hours) {
        Hours = hours;
    }

    public String getLate() {
        return Late;
    }

    public void setLate(String late) {
        Late = late;
    }

    public String getEarly() {
        return early;
    }

    public void setEarly(String early) {
        this.early = early;
    }

    public String getHalf_day_or_meeting() {
        return half_day_or_meeting;
    }

    public void setHalf_day_or_meeting(String half_day_or_meeting) {
        this.half_day_or_meeting = half_day_or_meeting;
    }
}

package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class AnnouncementListResponse {

    private String code;
    private String msg;
    ArrayList<Announcement_list>Announcement_list;

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

    public ArrayList<com.hatsoffdigital.hatsoff.Models.Announcement_list> getAnnouncement_list() {
        return Announcement_list;
    }

    public void setAnnouncement_list(ArrayList<com.hatsoffdigital.hatsoff.Models.Announcement_list> announcement_list) {
        Announcement_list = announcement_list;
    }
}

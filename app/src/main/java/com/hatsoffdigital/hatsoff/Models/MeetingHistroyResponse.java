package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class MeetingHistroyResponse {

    private String msg;

    ArrayList<MettingHistoryList>MettingHistoryList;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<com.hatsoffdigital.hatsoff.Models.MettingHistoryList> getMettingHistoryList() {
        return MettingHistoryList;
    }

    public void setMettingHistoryList(ArrayList<com.hatsoffdigital.hatsoff.Models.MettingHistoryList> mettingHistoryList) {
        MettingHistoryList = mettingHistoryList;
    }
}

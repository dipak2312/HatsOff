package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class WfhHistoryListResponse {

    private  String msg;
    ArrayList<WFH_History>WFH_History;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<com.hatsoffdigital.hatsoff.Models.WFH_History> getWFH_History() {
        return WFH_History;
    }

    public void setWFH_History(ArrayList<com.hatsoffdigital.hatsoff.Models.WFH_History> WFH_History) {
        this.WFH_History = WFH_History;
    }
}

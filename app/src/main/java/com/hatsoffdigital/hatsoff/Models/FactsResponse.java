package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class FactsResponse {

    ArrayList<FactName> FactName;

    private String code;

    private String msg;


    public ArrayList<com.hatsoffdigital.hatsoff.Models.FactName> getFactName() {
        return FactName;
    }

    public void setFactName(ArrayList<com.hatsoffdigital.hatsoff.Models.FactName> factName) {
        FactName = factName;
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

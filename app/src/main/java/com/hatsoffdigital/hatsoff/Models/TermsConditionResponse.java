package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class TermsConditionResponse {

    private String msg;
    ArrayList<Termsandcondition>Termsandcondition;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<com.hatsoffdigital.hatsoff.Models.Termsandcondition> getTermsandcondition() {
        return Termsandcondition;
    }

    public void setTermsandcondition(ArrayList<com.hatsoffdigital.hatsoff.Models.Termsandcondition> termsandcondition) {
        Termsandcondition = termsandcondition;
    }
}

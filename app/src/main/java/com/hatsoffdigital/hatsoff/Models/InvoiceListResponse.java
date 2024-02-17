package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class InvoiceListResponse {
    private String code;
    private String msg;
    ArrayList<InvoiceList>InvoiceList;

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

    public ArrayList<com.hatsoffdigital.hatsoff.Models.InvoiceList> getInvoiceList() {
        return InvoiceList;
    }

    public void setInvoiceList(ArrayList<com.hatsoffdigital.hatsoff.Models.InvoiceList> invoiceList) {
        InvoiceList = invoiceList;
    }
}

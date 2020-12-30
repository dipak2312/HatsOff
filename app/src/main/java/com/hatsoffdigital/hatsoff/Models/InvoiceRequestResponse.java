package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class InvoiceRequestResponse {

    private String msg;
    ArrayList<InvoiceRequest>InvoiceRequest;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<com.hatsoffdigital.hatsoff.Models.InvoiceRequest> getInvoiceRequest() {
        return InvoiceRequest;
    }

    public void setInvoiceRequest(ArrayList<com.hatsoffdigital.hatsoff.Models.InvoiceRequest> invoiceRequest) {
        InvoiceRequest = invoiceRequest;
    }
}

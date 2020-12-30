package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class InvoicePendingRequestResponse {
    private String msg;
    ArrayList<PendingInvoiceRequestList>PendingInvoiceRequestList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<com.hatsoffdigital.hatsoff.Models.PendingInvoiceRequestList> getPendingInvoiceRequestList() {
        return PendingInvoiceRequestList;
    }

    public void setPendingInvoiceRequestList(ArrayList<com.hatsoffdigital.hatsoff.Models.PendingInvoiceRequestList> pendingInvoiceRequestList) {
        PendingInvoiceRequestList = pendingInvoiceRequestList;
    }
}

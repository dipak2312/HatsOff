package com.hatsoffdigital.hatsoff.Models;

public class InvoiceRequest {
    private String request_date;
    private String currency;
    private String coasting;
    private String project_name;
    private String category_name;
    private String subject;
    private String bdm;
    private String status;
    private String request_update_date;
    private String invoice_id;
    private String cancel_reason;

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCoasting() {
        return coasting;
    }

    public void setCoasting(String coasting) {
        this.coasting = coasting;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBdm() {
        return bdm;
    }

    public void setBdm(String bdm) {
        this.bdm = bdm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest_update_date() {
        return request_update_date;
    }

    public void setRequest_update_date(String request_update_date) {
        this.request_update_date = request_update_date;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }
}

package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class MeetingAuthModel {

    private String employee_id;
    private String status;
    private String meeting;
    private String am_in;
    private String pm_out;
    ArrayList<CompanyDetails> CompanyDetails;


    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public void setMeeting(String meeting) {
        this.meeting = meeting;
    }

    public void setAm_in(String am_in) {
        this.am_in = am_in;
    }

    public void setPm_out(String pm_out) {
        this.pm_out = pm_out;
    }

    public void setCompanyDetails(ArrayList<com.hatsoffdigital.hatsoff.Models.CompanyDetails> companyDetails) {
        CompanyDetails = companyDetails;
    }
}

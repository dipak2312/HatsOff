package com.hatsoffdigital.hatsoff.Models;

public class CompanyDetails {

    private String date_cur;
    private String time;
    private String company_name;
    private String services;
    private String address;
    private String team_member;

    public CompanyDetails(String date_cur, String time, String company_name, String services, String address, String team_member) {
        this.date_cur = date_cur;
        this.time = time;
        this.company_name = company_name;
        this.services = services;
        this.address = address;
        this.team_member = team_member;
    }

    public String getDate_cur() {
        return date_cur;
    }

    public void setDate_cur(String date_cur) {
        this.date_cur = date_cur;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTeam_member() {
        return team_member;
    }

    public void setTeam_member(String team_member) {
        this.team_member = team_member;
    }
}

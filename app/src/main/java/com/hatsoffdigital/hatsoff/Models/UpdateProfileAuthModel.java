package com.hatsoffdigital.hatsoff.Models;

public class UpdateProfileAuthModel {

    private String employee_id;
    private String date_of_birth;
    private String department;
    private String position;
    private String firstname;
    private String surname;
    private String bloodtype;
    private String ra_zip_code;
    private String permanent_address;
    private String email_address;
    private String cellphone_no;
    private String joining_date;
    private String file;

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }

    public void setRa_zip_code(String ra_zip_code) {
        this.ra_zip_code = ra_zip_code;
    }

    public void setPermanent_address(String permanent_address) {
        this.permanent_address = permanent_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public void setCellphone_no(String cellphone_no) {
        this.cellphone_no = cellphone_no;
    }

    public void setJoining_date(String joining_date) {
        this.joining_date = joining_date;
    }

    public void setFile(String file) {
        this.file = file;
    }
}

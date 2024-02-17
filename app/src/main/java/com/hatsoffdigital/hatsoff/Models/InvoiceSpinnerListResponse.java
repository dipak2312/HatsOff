package com.hatsoffdigital.hatsoff.Models;

import java.util.ArrayList;

public class InvoiceSpinnerListResponse {
    private String msg;
    ArrayList<projectName>projectName;
    ArrayList<categoryName>categoryName;
    ArrayList<concernPerson>concernPerson;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<com.hatsoffdigital.hatsoff.Models.projectName> getProjectName() {
        return projectName;
    }

    public void setProjectName(ArrayList<com.hatsoffdigital.hatsoff.Models.projectName> projectName) {
        this.projectName = projectName;
    }

    public ArrayList<com.hatsoffdigital.hatsoff.Models.categoryName> getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(ArrayList<com.hatsoffdigital.hatsoff.Models.categoryName> categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<com.hatsoffdigital.hatsoff.Models.concernPerson> getConcernPerson() {
        return concernPerson;
    }

    public void setConcernPerson(ArrayList<com.hatsoffdigital.hatsoff.Models.concernPerson> concernPerson) {
        this.concernPerson = concernPerson;
    }
}

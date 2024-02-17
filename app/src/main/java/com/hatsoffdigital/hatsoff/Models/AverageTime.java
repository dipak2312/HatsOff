package com.hatsoffdigital.hatsoff.Models;

public class AverageTime {

    private String code;
    private String msg;
    private String averageTime;
    private String dateOfBirth;
    private String aboveNinehrsCount;
    private String belowNinehrsCount;
    private String averageStatus;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(String averageTime) {
        this.averageTime = averageTime;
    }

    public String getAboveNinehrsCount() {
        return aboveNinehrsCount;
    }

    public void setAboveNinehrsCount(String aboveNinehrsCount) {
        this.aboveNinehrsCount = aboveNinehrsCount;
    }

    public String getBelowNinehrsCount() {
        return belowNinehrsCount;
    }

    public void setBelowNinehrsCount(String belowNinehrsCount) {
        this.belowNinehrsCount = belowNinehrsCount;
    }


    public String getAverageStatus() {
        return averageStatus;
    }

    public void setAverageStatus(String averageStatus) {
        this.averageStatus = averageStatus;
    }
}

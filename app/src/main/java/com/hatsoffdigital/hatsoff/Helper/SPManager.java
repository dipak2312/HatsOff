package com.hatsoffdigital.hatsoff.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SPManager {

    public static final String EMPLOYEE_ID = "employeeid";
    public static final String NAME = "name";
    public static final String OTP = "Otp";
    public static final String LOGGEDIN = "login";
    public static final String HOLIDAY = "holiday";
    public static final String ANNOUNCEMENTS = "announcements";
    public static final String ATTENDENCE = "attendence";
    public static final String PREF_NAME = "MYPROJECT";
    public static final String FCM_TOKEN = "fcmtoken";
    public static final String PROFILE_IMAGE = "image";
    public static final String EXPERIENCE = "experience";
    public static final String AVERAGE = "average";
    public static final String BDAYSCREEN = "bday";

    public static final String ELAVAILABLE = "el";
    public static final String PLAVAILABLE = "pl";


    int PRIVATE_MODE = 0;
    String employee_id;
    String name;
    String Otp;
    String holiday, announcements, attendence;
    String LoggedIn;
    String Fcm_Token;
    String total_experience;
    String Profile_Image;
    String Average_time;

    String EL_Available;
    String PL_Available;

    String Bday_Check;
    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    public SPManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }


    public String getEL_Available() {
        EL_Available = pref.getString(ELAVAILABLE, " ");
        return EL_Available;
    }

    public void setEL_Available(String EL_Available) {
        editor.putString(ELAVAILABLE, EL_Available);
        editor.commit();
    }

    public String getPL_Available() {
        PL_Available = pref.getString(PLAVAILABLE, " ");
        return PL_Available;
    }

    public void setPL_Available(String PL_Available) {
        editor.putString(PLAVAILABLE, PL_Available);
        editor.commit();
    }

    public String getBday_Check() {
        Bday_Check = pref.getString(BDAYSCREEN, " ");
        return Bday_Check;
    }

    public void setBday_Check(String bday_Check) {
        editor.putString(BDAYSCREEN, bday_Check);
        editor.commit();
    }

    public String getEmployee_id() {
        employee_id = pref.getString(EMPLOYEE_ID, " ");
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        editor.putString(EMPLOYEE_ID, employee_id);
        editor.commit();
    }


    public String getName() {
        name = pref.getString(NAME, " ");
        return name;
    }

    public void setName(String name) {
        editor.putString(NAME, name);
        editor.commit();
    }

    public String getOtp() {
        Otp = pref.getString(OTP, " ");
        return Otp;
    }

    public void setOtp(String otp) {
        editor.putString(OTP, otp);
        editor.commit();
    }


    public String getLoggedIn() {
        LoggedIn = pref.getString(LOGGEDIN, " ");
        return LoggedIn;
    }

    public void setLoggedIn(String loggedIn) {
        editor.putString(LOGGEDIN, loggedIn);
        editor.commit();
    }


    public String getHoliday() {
        holiday = pref.getString(HOLIDAY, " ");
        return holiday;
    }

    public void setHoliday(String holiday) {
        editor.putString(HOLIDAY, holiday);
        editor.commit();
    }

    public String getAnnouncements() {
        announcements = pref.getString(ANNOUNCEMENTS, " ");
        return announcements;
    }

    public void setAnnouncements(String announcements) {
        editor.putString(ANNOUNCEMENTS, announcements);
        editor.commit();
    }

    public String getAttendence() {
        attendence = pref.getString(ATTENDENCE, " ");
        return attendence;
    }

    public void setAttendence(String attendence) {
        editor.putString(ATTENDENCE, attendence);
        editor.commit();
    }

    public String getFcm_Token() {
        Fcm_Token = pref.getString(FCM_TOKEN, " ");
        return Fcm_Token;
    }

    public void setFcm_Token(String fcm_Token) {
        editor.putString(FCM_TOKEN, fcm_Token);
        editor.commit();
    }


    public String getTotal_experience() {
        total_experience = pref.getString(EXPERIENCE, " ");
        return total_experience;
    }

    public void setTotal_experience(String total_experience) {
        editor.putString(EXPERIENCE, total_experience);
        editor.commit();
    }


    public String getProfile_Image() {
        Profile_Image = pref.getString(PROFILE_IMAGE, " ");
        return Profile_Image;
    }

    public void setProfile_Image(String profile_Image) {
        editor.putString(PROFILE_IMAGE, profile_Image);
        editor.commit();
    }


    public String getAverage_time() {
        Average_time = pref.getString(AVERAGE, " ");
        return Average_time;
    }

    public void setAverage_time(String average_time) {
        editor.putString(AVERAGE, average_time);
        editor.commit();
    }
}

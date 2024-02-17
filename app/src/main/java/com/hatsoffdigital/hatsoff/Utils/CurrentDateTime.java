package com.hatsoffdigital.hatsoff.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CurrentDateTime {

    public static String getDatetime()
    {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        String inputDateStr = String.format("%s/%s/%s", day, month, year);
        Date inputDate = null;
        try {
            inputDate = new SimpleDateFormat("dd/MM/yyyy").parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
        String dayOfMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);

        String date = dayOfWeek + "," + " " + day + " " + dayOfMonth + "," + " " + year;

        return date;
    }

}

package com.hatsoffdigital.hatsoff.Activity.Attendence;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Activity.Profile.UpdateProfileActivity;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AttendenceActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    LinearLayout lin_attendec, lin_scan;
    ImageView img_attend_toggel, img_back_attend;
    TextView attend_date;
    SPManager spManager;
    ImageView img_inst;


    String[] permissions = new String[]{

            Manifest.permission.CAMERA,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        context = AttendenceActivity.this;
        getSupportActionBar().hide();

        spManager = new SPManager(context);

        lin_attendec = (LinearLayout) findViewById(R.id.lin_attendec);
        lin_attendec.setOnClickListener(this);
        lin_scan = (LinearLayout) findViewById(R.id.lin_scan);
        lin_scan.setOnClickListener(this);

        img_attend_toggel = (ImageView) findViewById(R.id.img_attend_toggel);
        img_attend_toggel.setOnClickListener(this);

        img_back_attend = (ImageView) findViewById(R.id.img_back_attend);
        img_back_attend.setOnClickListener(this);

        attend_date = (TextView) findViewById(R.id.attend_date);

        img_inst = (ImageView) findViewById(R.id.img_inst);
        img_inst.setOnClickListener(this);

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
        attend_date.setText(date);

        checkPermissions();

        String attendence = spManager.getAttendence();

        if (attendence.equals(" ")) {
            attendencePopup();
            spManager.setAttendence("attendence");
        }

    }

    private void attendencePopup() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Lamba section hai but dhyan se padhna");
        builder1.setMessage("LOL!! We know this would be a big turn off but a very necessary & crucial step from the company's perspective. Here in this section you will compulsory have to SCAN the IN & OUT TIME daily which will help the system recognize the duration of time spent by you at office. People making bahanas ki bhul gaya/gayi will soon know the circumstances as this will be handled with utmost seriousness. \n" +
                "\n" +
                "Office timings i.e from 10.30AM to 7.30PM. All are informed to complete minimum of 9 hours in the office. Aage piche of entry exit wouldn't be a issue but if 9 hrs arent completed then outcome toh accha aayega yeh waada hai :D\n" +
                "\n" +
                "Non-completion of minimum number of hours in the office will result in salary adjustments.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes I have Read",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);

        Button positiveButton = alert11.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout parent = (LinearLayout) positiveButton.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);
    }


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(context, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == lin_attendec.getId()) {

            Intent intent = new Intent(context, CheckAttendenceActivity.class);
            startActivity(intent);

        } else if (id == img_inst.getId()) {
            attendencePopup();
        } else if (id == lin_scan.getId()) {

            Intent intent = new Intent(context, ScanAttendenceActivity.class);
            startActivity(intent);

        } else if (id == img_attend_toggel.getId()) {
            Intent intent = new Intent(context, UpdateProfileActivity.class);
            startActivity(intent);

        } else if (id == img_back_attend.getId()) {
            finish();
        }

    }
}

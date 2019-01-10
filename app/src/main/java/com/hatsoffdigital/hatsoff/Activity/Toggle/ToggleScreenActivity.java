package com.hatsoffdigital.hatsoff.Activity.Toggle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Activity.Attendence.AttendenceActivity;
import com.hatsoffdigital.hatsoff.Activity.Holiday.HolidayActivity;
import com.hatsoffdigital.hatsoff.Activity.Notification.NotificationActivity;
import com.hatsoffdigital.hatsoff.Activity.Profile.UpdateProfileActivity;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.R;

public class ToggleScreenActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    LinearLayout update_profile,toggle_attendance,holiday_toggel,announc_toggel,Leavetrack,salary_toggel;
    ImageView toggle_close;
    TextView toggle_name,toggle_emp_id;

    SPManager spManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppBlackTheme);
        setContentView(R.layout.activity_toggle_screen);

        context=ToggleScreenActivity.this;

        getSupportActionBar().hide();


        spManager=new SPManager(context);

        update_profile=(LinearLayout)findViewById(R.id.update_profile);
        update_profile.setOnClickListener(this);

        toggle_close=(ImageView)findViewById(R.id.toggle_close);
        toggle_close.setOnClickListener(this);


        toggle_attendance=(LinearLayout) findViewById(R.id.toggle_attendance);
        toggle_attendance.setOnClickListener(this);
        holiday_toggel=(LinearLayout) findViewById(R.id.holiday_toggel);
        holiday_toggel.setOnClickListener(this);


        Leavetrack=(LinearLayout) findViewById(R.id.Leavetrack);
        Leavetrack.setOnClickListener(this);

        salary_toggel=(LinearLayout) findViewById(R.id.salary_toggel);
        salary_toggel.setOnClickListener(this);

        announc_toggel=(LinearLayout) findViewById(R.id.announc_toggel);
        announc_toggel.setOnClickListener(this);

        toggle_name=(TextView)findViewById(R.id.toggle_name);
        toggle_name.setText(spManager.getName());
        toggle_emp_id=(TextView)findViewById(R.id.toggle_emp_id);
        toggle_emp_id.setText(spManager.getEmployee_id());
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

        if(id==update_profile.getId())
        {
//            Intent intent=new Intent(context, UpdateProfileActivity.class);
//            startActivity(intent);

            showPopup();

        }
        else if(id==toggle_close.getId())
        {
            finish();
        }
        else  if(id==toggle_attendance.getId())

        {
            Intent intent=new Intent(context, AttendenceActivity.class);
            startActivity(intent);
        }

        else if(id==holiday_toggel.getId())
        {
            Intent intent=new Intent(context, HolidayActivity.class);
            startActivity(intent);

        }

        else if(id==announc_toggel.getId())
        {
            Intent intent=new Intent(context, NotificationActivity.class);
            startActivity(intent);

        }
        else  if(id==salary_toggel.getId())
        {
            showPopup();
        }
        else  if(id==Leavetrack.getId())
        {
            showPopup();
        }

    }

    private void showPopup() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Coming soon");
        builder1.setMessage("Sabar marlo thoda. Woh kya kehte hai \" Shanti ki ****** pakdo \"");

        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "ok",
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
}

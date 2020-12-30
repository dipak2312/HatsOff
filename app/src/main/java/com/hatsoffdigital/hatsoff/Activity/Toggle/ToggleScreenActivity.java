package com.hatsoffdigital.hatsoff.Activity.Toggle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hatsoffdigital.hatsoff.Activity.Profile.UpdateProfileActivity;
import com.hatsoffdigital.hatsoff.Activity.TermsConditions.TermsAndConditionsActivity;
import com.hatsoffdigital.hatsoff.BuildConfig;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.R;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class ToggleScreenActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    SPManager spManager;
    LinearLayout lin_update_profile, lin_terms_cond, lin_rete_this_app, lin_contact_us;
    // ImageView img_toggle_back;
    RelativeLayout rel_minus_toggel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle_screen);
        context = ToggleScreenActivity.this;
        getSupportActionBar().hide();
        spManager = new SPManager(context);

        lin_update_profile = (LinearLayout) findViewById(R.id.lin_update_profile);
        lin_update_profile.setOnClickListener(this);
        lin_terms_cond = (LinearLayout) findViewById(R.id.lin_terms_cond);
        lin_terms_cond.setOnClickListener(this);
        lin_rete_this_app = (LinearLayout) findViewById(R.id.lin_rete_this_app);
        lin_rete_this_app.setOnClickListener(this);
        lin_contact_us = (LinearLayout) findViewById(R.id.lin_contact_us);
        lin_contact_us.setOnClickListener(this);

        rel_minus_toggel = (RelativeLayout) findViewById(R.id.rel_minus_toggel);
        rel_minus_toggel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == lin_update_profile.getId()) {
            Intent intent = new Intent(context, UpdateProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == lin_terms_cond.getId()) {
            Intent intent = new Intent(context, TermsAndConditionsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == lin_rete_this_app.getId()) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
            }
        } else if (id == lin_contact_us.getId()) {
            String employee_id = "Employee Id :" + spManager.getEmployee_id();
            String fullName = "User Name :" + spManager.getUser_full_name();
            String devicename = "Device :" + " " + Build.MODEL;
            String appversion = "App Version :" + " " + BuildConfig.VERSION_NAME;
            String androidversion = "Android Version :" + " " + Build.VERSION.RELEASE;
            String device_lang = "Device Language :" + " " + Locale.getDefault().getDisplayLanguage();

            String alltext = "<br/>" + "<br/>" + "<br/>" + "<br/>" + "<br/>" + "<br/>" + "===============================" + "<br/>" + employee_id + "<br/>" + fullName + "<br/>" + appversion + "<br/>" + devicename + "<br/>" + androidversion + "<br/>" + device_lang;
            Intent it = new Intent(Intent.ACTION_SEND);
            it.putExtra(Intent.EXTRA_EMAIL, new String[]{"abdeali@hatsoff.co.in", "zainab@hatsoffdigital.com", "taher@hatsoff.co.in", "shabbir@hatsoff.co.in", "husain@hatsoff.co.in"});
            it.putExtra(Intent.EXTRA_SUBJECT, "Hats-Off Feedback");
            it.putExtra(Intent.EXTRA_TEXT, String.valueOf(Html.fromHtml(alltext)));//String.valueOf(Html.fromHtml(alltext))
            it.setType("message/rfc822");
            startActivity(Intent.createChooser(it, "Choose Mail App"));
        } else if (id == rel_minus_toggel.getId()) {

            finish();
        }


    }


}

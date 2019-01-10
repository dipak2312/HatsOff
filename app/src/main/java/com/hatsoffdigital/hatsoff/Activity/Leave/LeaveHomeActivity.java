package com.hatsoffdigital.hatsoff.Activity.Leave;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hatsoffdigital.hatsoff.Activity.Profile.UpdateProfileActivity;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.CurrentDate;
import com.hatsoffdigital.hatsoff.Models.LeaveCount;
import com.hatsoffdigital.hatsoff.Models.SendTokenResponse;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LeaveHomeActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView img_leave_back,img_Leave_setting;
    RelativeLayout rel_apply_leave,rel_history_leave;
    Context context;
    CurrentDate currentdate;
    TextView leave_date;
    String emp_id;
    CustomProgressDialog dialog;
    SPManager spManager;
    TextView text_el_available,text_pl_available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_home);

        getSupportActionBar().hide();

        context=LeaveHomeActivity.this;

        dialog=new CustomProgressDialog(context);
        spManager=new SPManager(context);
        emp_id=spManager.getEmployee_id();

        text_el_available=(TextView)findViewById(R.id.text_el_available);
        text_pl_available=(TextView)findViewById(R.id.text_pl_available);

        img_Leave_setting=(ImageView)findViewById(R.id.img_Leave_setting);
        img_Leave_setting.setOnClickListener(this);
        img_leave_back=(ImageView)findViewById(R.id.img_leave_back);
        img_leave_back.setOnClickListener(this);

        leave_date=(TextView)findViewById(R.id.leave_date);

        rel_apply_leave=(RelativeLayout)findViewById(R.id.rel_apply_leave);
        rel_apply_leave.setOnClickListener(this);
        rel_history_leave=(RelativeLayout)findViewById(R.id.rel_history_leave);
        rel_history_leave.setOnClickListener(this);


        currentdate=new CurrentDate();
        String date=currentdate.current_date();
        leave_date.setText(date);

        text_el_available.setText(spManager.getEL_Available());
        text_pl_available.setText(spManager.getPL_Available());




    }

    @Override
    protected void onResume() {
        super.onResume();

        getLeaveCount();
    }

    private void getLeaveCount() {

        dialog.show(" ");

        WebServiceModel.getRestApi().getLeaveCount(emp_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LeaveCount>() {
                    @Override
                    public void onNext(LeaveCount leavecount) {
                        dialog.dismiss(" ");

                        String el_date=leavecount.getEmergency_leave_count()+ " "+"Days";
                        String pl_date=leavecount.getPlanned_leave_count()+" "+"Days";

                        spManager.setEL_Available(el_date);
                        spManager.setPL_Available(pl_date);

                        text_el_available.setText(el_date);
                        text_pl_available.setText(pl_date);

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss(" ");
                        NetworkPopup.ShowPopup(context);

                       // Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });



    }

    @Override
    public void onClick(View view) {

        int id=view.getId();

        if(id==img_leave_back.getId())
        {
            finish();
        }
        else if(id==img_Leave_setting.getId())
        {
            Intent intent=new Intent(context,UpdateProfileActivity.class);
            startActivity(intent);
        }
        else  if(id==rel_apply_leave.getId())
        {
            Intent intent=new Intent(context,LeaveApplyActivity.class);
            startActivity(intent);
        }
        else if(id==rel_history_leave.getId())
        {
            Intent intent=new Intent(context,LeaveHistroyActivity.class);
            startActivity(intent);

        }

    }
}

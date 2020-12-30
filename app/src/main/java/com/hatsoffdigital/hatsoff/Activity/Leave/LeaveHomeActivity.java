package com.hatsoffdigital.hatsoff.Activity.Leave;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Adapters.LeaveHistoryAdapter;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.CurrentDate;
import com.hatsoffdigital.hatsoff.Models.LeaveCount;
import com.hatsoffdigital.hatsoff.Models.LeaveListResponse;
import com.hatsoffdigital.hatsoff.Models.Leave_list;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CheckInternetBroadcast;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;
import com.hatsoffdigital.hatsoff.Utils.ServerPopup;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LeaveHomeActivity extends AppCompatActivity implements View.OnClickListener {

    // ImageView img_leave_back,img_Leave_setting;
    RelativeLayout rel_leave_home_toggel;
    RelativeLayout rel_apply_leave;
    Context context;
    CurrentDate currentdate;
    String emp_id;
    CustomProgressDialog dialog;
    SPManager spManager;
    TextView text_el_available, text_pl_available;
    ArrayList<Leave_list> leaveList;
    LeaveHistoryAdapter adapter;
    RecyclerView recy_history;

    LinearLayout lin_pl_avi,lin_el_avi;
    ImageView img_leave_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_home);

        getSupportActionBar().hide();

        context = LeaveHomeActivity.this;

        dialog = new CustomProgressDialog(context);
        spManager = new SPManager(context);
        emp_id = spManager.getEmployee_id();
        leaveList = new ArrayList<>();

        text_el_available = (TextView) findViewById(R.id.text_el_available);
        text_pl_available = (TextView) findViewById(R.id.text_pl_available);

        rel_leave_home_toggel = (RelativeLayout) findViewById(R.id.rel_leave_home_toggel);
        rel_leave_home_toggel.setOnClickListener(this);

        lin_pl_avi=(LinearLayout)findViewById(R.id.lin_pl_avi);
        lin_el_avi=(LinearLayout)findViewById(R.id.lin_el_avi);

        img_leave_home=(ImageView)findViewById(R.id.img_leave_home);
        img_leave_home.setOnClickListener(this);

        rel_apply_leave = (RelativeLayout) findViewById(R.id.rel_apply_leave);
        rel_apply_leave.setOnClickListener(this);

        text_el_available.setText(spManager.getEL_Available());
        text_pl_available.setText(spManager.getPL_Available());

        recy_history = (RecyclerView) findViewById(R.id.recy_history);
        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recy_history.setLayoutManager(lm);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getLeaveHistroy();
        getLeaveCount();
    }

    private void getLeaveHistroy() {
        dialog.show(" ");

        WebServiceModel.getRestApi().getLeaveList(emp_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LeaveListResponse>() {
                    @Override
                    public void onNext(LeaveListResponse leavelist) {
                        dialog.dismiss(" ");

                        String msg = leavelist.getMsg();

                        if (msg.equals("success")) {


                            leaveList = leavelist.getLeave_list();

                            adapter = new LeaveHistoryAdapter(context, leaveList);
                            recy_history.setAdapter(adapter);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(CheckInternetBroadcast.isNetworkAvilable(context))
                        {
                            ServerPopup.showPopup(context);
                            dialog.dismiss(" ");
                        }
                        else
                        {
                            NetworkPopup.ShowPopup(context);
                            dialog.dismiss(" ");


                        }

//                        dialog.dismiss(" ");
//
//                        Toast.makeText(context, "Please Check Your Network..Unable to Connect Server!!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getLeaveCount() {

        // dialog.show(" ");

        WebServiceModel.getRestApi().getLeaveCount(emp_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LeaveCount>() {
                    @Override
                    public void onNext(LeaveCount leavecount) {
                        //dialog.dismiss(" ");

                        String el_date = leavecount.getEmergency_leave_count();
                        String pl_date = leavecount.getPlanned_leave_count();

                        double el=0, pl=0;

                        if(!el_date.equals(""))
                        {
                            el = Double.parseDouble(el_date);
                        }

                        if(!pl_date.equals(""))
                        {
                            pl = Double.parseDouble(pl_date);
                        }

                        if(el <0)
                        {
                            lin_el_avi.setBackground(context.getResources().getDrawable(
                                    R.drawable.corner_below_avgtime));
                        }else if(el>=0)
                        {
                            lin_el_avi.setBackground(context.getResources().getDrawable(
                                    R.drawable.corner_avgtime));
                        }

                        if(pl<0)
                        {
                            lin_pl_avi.setBackground(context.getResources().getDrawable(
                                    R.drawable.corner_below_avgtime));
                        }else if(pl>=0)
                        {
                            lin_pl_avi.setBackground(context.getResources().getDrawable(
                                    R.drawable.corner_avgtime));
                        }

                        if(el_date.equals(""))
                        {
                            spManager.setEL_Available("0");
                        }
                        else
                        {
                            spManager.setEL_Available(el_date);
                        }

                        if(pl_date.equals(""))
                        {
                            spManager.setPL_Available("0");
                        }
                        else
                        {
                            spManager.setPL_Available(pl_date);
                        }

                        text_el_available.setText(spManager.getEL_Available());
                        text_pl_available.setText(spManager.getPL_Available());

                    }

                    @Override
                    public void onError(Throwable e) {
//                        dialog.dismiss(" ");
//                        NetworkPopup.ShowPopup(context);

                        // Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == rel_leave_home_toggel.getId()) {
            Intent intent = new Intent(context, ToggleScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == rel_apply_leave.getId()) {
            Intent intent = new Intent(context, LeaveApplyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        else if(id==img_leave_home.getId())
        {
            finish();
        }


    }
}

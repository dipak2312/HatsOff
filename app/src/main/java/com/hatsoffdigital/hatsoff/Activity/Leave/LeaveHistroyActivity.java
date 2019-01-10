package com.hatsoffdigital.hatsoff.Activity.Leave;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hatsoffdigital.hatsoff.Activity.Profile.UpdateProfileActivity;
import com.hatsoffdigital.hatsoff.Adapters.LeaveHistoryAdapter;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.CurrentDate;
import com.hatsoffdigital.hatsoff.Models.LeaveCount;
import com.hatsoffdigital.hatsoff.Models.LeaveListResponse;
import com.hatsoffdigital.hatsoff.Models.Leave_list;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LeaveHistroyActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    LeaveHistoryAdapter adapter;
    RecyclerView recy_history;
    CurrentDate currentDate;

    TextView leave_histroy_date;
    SPManager spManager;
    TextView txt_el_avai,txt_pl_avai;
    CustomProgressDialog dialog;

    ArrayList<Leave_list>leaveList;

    ImageView img_leave_his_back,img_Leave_his_setting;

    String emp_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_histroy);
        context=LeaveHistroyActivity.this;
        getSupportActionBar().hide();

        dialog=new CustomProgressDialog(context);

        spManager=new SPManager(context);

        emp_id=spManager.getEmployee_id();

        recy_history=(RecyclerView)findViewById(R.id.recy_history);
        LinearLayoutManager lm=new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recy_history.setLayoutManager(lm);

        txt_el_avai=(TextView)findViewById(R.id.txt_el_avai);
        txt_pl_avai=(TextView)findViewById(R.id.txt_pl_avai);

        txt_el_avai.setText(spManager.getEL_Available());
        txt_pl_avai.setText(spManager.getPL_Available());
        leave_histroy_date=(TextView)findViewById(R.id.leave_histroy_date);

        img_leave_his_back=(ImageView)findViewById(R.id.img_leave_his_back);
        img_leave_his_back.setOnClickListener(this);
        img_Leave_his_setting=(ImageView)findViewById(R.id.img_Leave_his_setting);
        img_Leave_his_setting.setOnClickListener(this);

        currentDate=new CurrentDate();
        String date=currentDate.current_date();
        leave_histroy_date.setText(date);

        leaveList=new ArrayList<>();

        getLeaveHistroy();


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

                        String msg=leavelist.getMsg();

                        if(msg.equals("success")) {


                            leaveList=leavelist.getLeave_list();

                            adapter=new LeaveHistoryAdapter(context,leaveList);
                            recy_history.setAdapter(adapter);

                        }




                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss(" ");

                        Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View view) {

        int id=view.getId();

        if(id==img_leave_his_back.getId())
        {
            finish();
        }
        else if(id==img_Leave_his_setting.getId())
        {
            Intent intent=new Intent(context,UpdateProfileActivity.class);
            startActivity(intent);
        }

    }
}

package com.hatsoffdigital.hatsoff.Activity.Meeting;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Adapters.MeetingHistoryAdapter;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.CurrentDate;
import com.hatsoffdigital.hatsoff.Models.MeetingHistroyResponse;
import com.hatsoffdigital.hatsoff.Models.MettingHistoryList;
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

public class MeetingActivity extends AppCompatActivity implements View.OnClickListener {

   // ImageView metting_instruction;
    RelativeLayout rel_metting_toggel;
    //TextView metting_date;
    //LinearLayout lin_histroy;
    Context context;
    LinearLayout lin_outside_pune;
    TextView text_total_meetings;
    ArrayList<MettingHistoryList> mettingHistoryLists;
    MeetingHistoryAdapter adapter;
    RecyclerView recy_meeting_his;
    CustomProgressDialog dialog;
    SPManager spManager;
    String employee_id;
    ImageView img_meeting_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        getSupportActionBar().hide();
        context=MeetingActivity.this;

        mettingHistoryLists=new ArrayList<>();
        dialog=new CustomProgressDialog(context);

        spManager=new SPManager(context);
        employee_id=spManager.getEmployee_id();

        rel_metting_toggel=(RelativeLayout) findViewById(R.id.rel_metting_toggel);
        rel_metting_toggel.setOnClickListener(this);
        lin_outside_pune=(LinearLayout)findViewById(R.id.lin_outside_pune);
        lin_outside_pune.setOnClickListener(this);
//        metting_instruction=(ImageView)findViewById(R.id.metting_instruction);
//        metting_instruction.setOnClickListener(this);


        text_total_meetings=(TextView)findViewById(R.id.text_total_meetings);
        text_total_meetings.setText(spManager.getTotal_mettings());

        img_meeting_back=(ImageView)findViewById(R.id.img_meeting_back);
        img_meeting_back.setOnClickListener(this);

        recy_meeting_his=(RecyclerView)findViewById(R.id.recy_meeting_his);
        LinearLayoutManager lm=new LinearLayoutManager(getApplicationContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recy_meeting_his.setLayoutManager(lm);



    }


    @Override
    protected void onResume() {
        super.onResume();
        getHistroyList();
    }

    private void getHistroyList() {

        dialog.show(" ");

        WebServiceModel.getRestApi().getMeetinghistroy(employee_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<MeetingHistroyResponse>() {
                    @Override
                    public void onNext(MeetingHistroyResponse histroyResponse) {
                        dialog.dismiss(" ");

                        String msg = histroyResponse.getMsg();


                        if (msg.equals("success")) {
                            mettingHistoryLists=histroyResponse.getMettingHistoryList();
                            text_total_meetings.setText(String.valueOf(mettingHistoryLists.size()));
                            spManager.setTotal_mettings(String.valueOf(mettingHistoryLists.size()));
                            adapter=new MeetingHistoryAdapter(context,mettingHistoryLists);
                            recy_meeting_his.setAdapter(adapter);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
//                        dialog.dismiss(" ");
//                        NetworkPopup.ShowPopup(context);
//                        System.out.println(e.toString());
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


                    }

                    @Override
                    public void onComplete() {

                    }
                });



    }



    @Override
    public void onClick(View view) {

        int id=view.getId();

         if(id==rel_metting_toggel.getId())
        {
            Intent intent = new Intent(context, ToggleScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        }



         else if(id==lin_outside_pune.getId())
         {
             Intent intent=new Intent(context,MettingcompanyDetailsActivity.class);
             intent.putExtra("OutSide","outside");
             intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
             startActivity(intent);
         }

         else if(id==img_meeting_back.getId())
         {
             finish();
         }
//         else if(id==metting_instruction.getId())
//         {
//             Intent intent=new Intent(context, TermsAndConditionsActivity.class);
//             intent.putExtra("Meeting","MEETING:");
//             intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//             startActivity(intent);
//         }

    }





}





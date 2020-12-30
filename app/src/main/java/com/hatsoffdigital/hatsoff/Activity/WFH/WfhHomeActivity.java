package com.hatsoffdigital.hatsoff.Activity.WFH;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Adapters.WfhHistroyAdapter;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.CurrentDate;
import com.hatsoffdigital.hatsoff.Models.WFH_History;
import com.hatsoffdigital.hatsoff.Models.WfhHistoryListResponse;
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

public class WfhHomeActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout lin_wfh_apply;
    Context context;
    CurrentDate currentdate;
    WfhHistroyAdapter adapter;
    RecyclerView recy_wfh_histroy;
    ArrayList<WFH_History> WfhHistroyList;
    RelativeLayout rel_wfh_toggel;
    CustomProgressDialog dialog;
    SPManager spManager;
    TextView text_applied_wfh_count;
    ImageView img_home_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wfh_home);

        context = WfhHomeActivity.this;
        getSupportActionBar().hide();

        dialog = new CustomProgressDialog(context);
        WfhHistroyList = new ArrayList<>();
        spManager = new SPManager(context);

        lin_wfh_apply = (LinearLayout) findViewById(R.id.lin_wfh_apply);
        lin_wfh_apply.setOnClickListener(this);

        img_home_back=(ImageView)findViewById(R.id.img_home_back);
        img_home_back.setOnClickListener(this);

        text_applied_wfh_count = (TextView) findViewById(R.id.text_applied_wfh_count);
        text_applied_wfh_count.setText(spManager.getWfh_applied_size());

//        img_instruction=(ImageView)findViewById(R.id.img_instruction);
//        img_instruction.setOnClickListener(this);
        recy_wfh_histroy = (RecyclerView) findViewById(R.id.recy_wfh_histroy);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recy_wfh_histroy.setLayoutManager(manager);


        rel_wfh_toggel = (RelativeLayout) findViewById(R.id.rel_wfh_toggel);
        rel_wfh_toggel.setOnClickListener(this);

        currentdate = new CurrentDate();
        String date = currentdate.current_date();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getHistroyList();
    }

    private void getHistroyList() {
        dialog.show(" ");
        WebServiceModel.getRestApi().getWFHHistoryList(spManager.getEmployee_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<WfhHistoryListResponse>() {
                    @Override
                    public void onNext(WfhHistoryListResponse histroyResponse) {
                        dialog.dismiss(" ");

                        String msg = histroyResponse.getMsg();

                        if (msg.equals("success")) {

                            WfhHistroyList = histroyResponse.getWFH_History();
                            text_applied_wfh_count.setText(String.valueOf(WfhHistroyList.size()));
                            spManager.setWfh_applied_size(String.valueOf(WfhHistroyList.size()));
                            adapter = new WfhHistroyAdapter(context, WfhHistroyList);
                            recy_wfh_histroy.setAdapter(adapter);


                        }

                    }

                    @Override
                    public void onError(Throwable e) {
//                        dialog.dismiss(" ");
//                        NetworkPopup.ShowPopup(context);
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
                        //Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == lin_wfh_apply.getId()) {
            Intent intent = new Intent(context, WfhApplyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == rel_wfh_toggel.getId()) {
            Intent intent = new Intent(context, ToggleScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        else if(id==img_home_back.getId())
        {
            finish();
        }
//        else if(id==img_instruction.getId())
//        {
//            Intent intent=new Intent(context, TermsAndConditionsActivity.class);
//            intent.putExtra("WorkFromHome","WORK FROM HOME:");
//            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(intent);
//            //openWFHInstructPopup();
//        }

    }
}

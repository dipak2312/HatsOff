package com.hatsoffdigital.hatsoff.Activity.Landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hatsoffdigital.hatsoff.Models.CheckEmployeeId;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class GetOtpActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_getotp;
    Context context;
    EditText edit_get_emp;
    String emp_id;
    String otp;
    String main_Otp;

    CustomProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_otp);
        getSupportActionBar().hide();
        context = GetOtpActivity.this;

        btn_getotp = (Button) findViewById(R.id.btn_getotp);
        btn_getotp.setOnClickListener(this);

        edit_get_emp = (EditText) findViewById(R.id.edit_get_emp);

        dialog = new CustomProgressDialog(context);


        Random rnd = new Random();
        otp = String.valueOf(rnd.nextInt(10000));


    }

    private void checkemployeeId() {

        dialog.show(" ");
        emp_id = edit_get_emp.getText().toString().trim();
        main_Otp = emp_id + otp;
        WebServiceModel.getRestApi().CheckEmployeeid(emp_id, main_Otp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<CheckEmployeeId>() {
                    @Override
                    public void onNext(CheckEmployeeId checkemployeeid) {
                        dialog.dismiss(" ");

                        String msg = checkemployeeid.getMsg();

                        if (msg.equals("success")) {

                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.putExtra("employeeid", emp_id);
                            intent.putExtra("Otp", main_Otp);
                            startActivity(intent);
                            finish();
                        } else {

                            Toast.makeText(context, "Invalid Employee Id", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss(" ");
                        NetworkPopup.ShowPopup(context);
                        //Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void onClick(View view) {


        emp_id = edit_get_emp.getText().toString().trim();

        if (emp_id.equalsIgnoreCase("")) {

            edit_get_emp.requestFocus();
            edit_get_emp.setError("Employee Id can't be blank");
        } else {

            checkemployeeId();
        }


    }
}

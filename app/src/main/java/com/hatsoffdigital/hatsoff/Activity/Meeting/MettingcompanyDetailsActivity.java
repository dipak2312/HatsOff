package com.hatsoffdigital.hatsoff.Activity.Meeting;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Activity.home.HomeScreenActivity;
import com.hatsoffdigital.hatsoff.Adapters.AllCompanyDetailsAdapter;
import com.hatsoffdigital.hatsoff.Adapters.companyAllUserAdapter;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.AddMeetingDetailsResponse;
import com.hatsoffdigital.hatsoff.Models.CompanyDetails;
import com.hatsoffdigital.hatsoff.Models.CurrentDate;
import com.hatsoffdigital.hatsoff.Models.EmployeeListResponse;
import com.hatsoffdigital.hatsoff.Models.MeetingAuthModel;
import com.hatsoffdigital.hatsoff.Models.employee_list;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CheckInternetBroadcast;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.ItemEmployeeClickListener;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;
import com.hatsoffdigital.hatsoff.Utils.ServerPopup;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MettingcompanyDetailsActivity extends AppCompatActivity implements View.OnClickListener, ItemEmployeeClickListener, DatePickerDialog.OnDateSetListener {

    String in_Time, Out_time;

    RelativeLayout rel_metting_toggel;
    Button btn_metting_in_time, btn_metting_out_time, btn_metting_submit;
    RelativeLayout rel_add_company;
    RecyclerView recy_all_company_list;
    Context context;
    //TextView txt_select_date;

    RelativeLayout rel_add_company_toggel, rel_select_date;

    TextView txt_select_employee;
    EditText edit_company_name, edit_services, edit_address;
    RelativeLayout rel_any_team_member;
    Button btn_comapny_add;

    Dialog dialog;
    companyAllUserAdapter adapter;
    CustomProgressDialog dialog1;
    ArrayList<employee_list> employeeList;
    ArrayList<String> selectedemployee;
    String selectemployee = "";
    ArrayList<CompanyDetails> companyDetails;
    AllCompanyDetailsAdapter ComDetailsadapter;
    SPManager spManager;
    String employee_id;

    CurrentDate currentdate;
    String current_date = "";
    String current_user_name;
    String current_name;
    String OutSide = "";
    LinearLayout lin_in_out_visible;
    String attendence_status;
    String current_time;
    TextView txt_select_date;

    TextView text_total_meetings;
    ImageView img_metting_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_mettingcompany_details);
        in_Time = getIntent().getStringExtra("inTime");
        Out_time = getIntent().getStringExtra("outTime");
        attendence_status = getIntent().getStringExtra("Status");

        OutSide = getIntent().getStringExtra("OutSide");

        context = MettingcompanyDetailsActivity.this;
        dialog1 = new CustomProgressDialog(context);


        spManager = new SPManager(context);
        employee_id = spManager.getEmployee_id();

        selectedemployee = new ArrayList<>();
        companyDetails = new ArrayList<>();
        currentdate = new CurrentDate();

        lin_in_out_visible = (LinearLayout) findViewById(R.id.lin_in_out_visible);

        rel_metting_toggel = (RelativeLayout) findViewById(R.id.rel_metting_toggel);
        rel_metting_toggel.setOnClickListener(this);

        btn_metting_in_time = (Button) findViewById(R.id.btn_metting_in_time);
        btn_metting_out_time = (Button) findViewById(R.id.btn_metting_out_time);

        text_total_meetings = (TextView) findViewById(R.id.text_total_meetings);
        text_total_meetings.setText(spManager.getTotal_mettings());

        img_metting_back=(ImageView)findViewById(R.id.img_meeting_back);
        img_metting_back.setOnClickListener(this);

        btn_metting_in_time.setText(in_Time);
        btn_metting_out_time.setText(Out_time);
        btn_metting_submit = (Button) findViewById(R.id.btn_metting_submit);
        btn_metting_submit.setOnClickListener(this);

        rel_add_company = (RelativeLayout) findViewById(R.id.rel_add_company);
        rel_add_company.setOnClickListener(this);
        recy_all_company_list = (RecyclerView) findViewById(R.id.recy_all_company_list);
        employeeList = new ArrayList<>();
        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recy_all_company_list.setLayoutManager(lm);

        if (OutSide != null) {

            lin_in_out_visible.setVisibility(View.GONE);

        }

        Date date = new Date();
        String strDateFormat = "hh:mm a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        current_time = dateFormat.format(date);


        getEmployeeList();

    }

    private void getEmployeeList() {
        dialog1.show(" ");

        WebServiceModel.getRestApi().employeeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<EmployeeListResponse>() {
                    @Override
                    public void onNext(EmployeeListResponse employeelist) {
                        dialog1.dismiss(" ");

                        employeeList = employeelist.getEmployee_list();

                        for (int i = 0; i < employeeList.size(); i++) {
                            if (spManager.getEmployee_id().equals(employeeList.get(i).getEmployee_id())) {
                                current_user_name = employeeList.get(i).getName();
                                current_name = employeeList.get(i).getName();
                                employeeList.remove(i);
                            }

                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                       // NetworkPopup.ShowPopup(context);
                        //Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();

                        if(CheckInternetBroadcast.isNetworkAvilable(context))
                        {
                            ServerPopup.showPopup(context);
                            dialog1.dismiss(" ");
                        }
                        else
                        {
                            NetworkPopup.ShowPopup(context);
                            dialog1.dismiss(" ");


                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == rel_metting_toggel.getId()) {
            Intent intent = new Intent(context, ToggleScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == rel_add_company.getId()) {

            openAddCompanyPopup();
        } else if (id == btn_metting_submit.getId()) {
            if (companyDetails.size() != 0) {

                SubmitCompanyDetails();

            } else {
                Toast.makeText(context, "Add Company Details", Toast.LENGTH_SHORT).show();
         }

        }
        else if(id==img_metting_back.getId())
        {
            Intent intent = new Intent(context, HomeScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    }

    private void ShowDatePopup() {
        Calendar now1 = Calendar.getInstance();
        int year1 = now1.get(Calendar.YEAR);
        int month1 = now1.get(Calendar.MONTH); // Note: zero based!
        int day1 = now1.get(Calendar.DAY_OF_MONTH);

        new SpinnerDatePickerDialogBuilder()
                .context(context)
                .callback(MettingcompanyDetailsActivity.this)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(false)
                .defaultDate(year1, month1, day1)
                .maxDate(2050, 0, 1)
                .minDate(1950, 0, 1)
                .build()
                .show();

    }

    private void SubmitCompanyDetails() {
        dialog1.show(" ");
        MeetingAuthModel model = new MeetingAuthModel();
        model.setEmployee_id(employee_id);
        model.setStatus(attendence_status);
        model.setAm_in(in_Time);
        model.setPm_out(Out_time);
        model.setMeeting("meeting");
        model.setCompanyDetails(companyDetails);

        WebServiceModel.getRestApi().mettingListResponse(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<AddMeetingDetailsResponse>() {
                    @Override
                    public void onNext(AddMeetingDetailsResponse mettinglist) {
                        dialog1.dismiss(" ");

                        String msg = mettinglist.getMsg();

                        if (msg.equals("success")) {

                            successMettingPopup();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
//                        dialog1.dismiss(" ");
//                       // System.out.println("saggieror" + e.toString());
//                        NetworkPopup.ShowPopup(context);
                        //Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();
                        if(CheckInternetBroadcast.isNetworkAvilable(context))
                        {
                            ServerPopup.showPopup(context);
                            dialog1.dismiss(" ");
                        }
                        else
                        {
                            NetworkPopup.ShowPopup(context);
                            dialog1.dismiss(" ");


                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void successMettingPopup() {

        Dialog popupdialog = new Dialog(context);
        popupdialog.setContentView(R.layout.meeting_success_popup);
        popupdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btn_okay = (Button) popupdialog.findViewById(R.id.btn_okay);
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HomeScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        popupdialog.show();

    }

    private void selectAnyTeamMember() {

        final Dialog dialog1 = new Dialog(context);
        dialog1.setContentView(R.layout.all_company_user_popup);
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        RecyclerView recy_all_name = (RecyclerView) dialog1.findViewById(R.id.recy_all_name);
        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
        recy_all_name.setLayoutManager(lm);

        if (employeeList != null) {

            selectedemployee.clear();
            for (int i = 0; i < employeeList.size(); i++) {
                employeeList.get(i).setSelected(false);
            }

            adapter = new companyAllUserAdapter(context, employeeList, (ItemEmployeeClickListener) this);
            recy_all_name.setAdapter(adapter);

        }

        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recy_all_name.setLayoutManager(lm);
        RelativeLayout rel_ok = (RelativeLayout) dialog1.findViewById(R.id.rel_ok);
        rel_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog1.dismiss();


                current_user_name = "";

                if (selectedemployee.size() != 0) {
                    current_user_name = current_name + "," + " " + selectedemployee.toString().substring(1, selectedemployee.toString().length() - 1).replace(", ", ", ");
                } else {
                    current_user_name = current_name;
                }


                txt_select_employee.setText(current_user_name);
                txt_select_employee.setTextColor(Color.parseColor("#000000"));

            }
        });

        dialog1.show();

    }

    private void addCompany() {

        String company_name = edit_company_name.getText().toString().trim();
        String services = edit_services.getText().toString().trim();
        String address = edit_address.getText().toString().trim();

        if (company_name.equalsIgnoreCase("")) {

            edit_company_name.requestFocus();
            edit_company_name.setError("Company Name can't be blank");
        } else if (services.equalsIgnoreCase("")) {

            edit_services.requestFocus();
            edit_services.setError("Services can't be blank");
        } else if (address.equalsIgnoreCase("")) {

            edit_address.requestFocus();
            edit_address.setError("address can't be blank");
        } else if (current_date.equals("")) {
            Toast.makeText(context, "Select Date", Toast.LENGTH_SHORT).show();
        } else {
            current_user_name = "";

            if (selectedemployee.size() != 0) {
                current_user_name = current_name + "," + " " + selectedemployee.toString().substring(1, selectedemployee.toString().length() - 1).replace(", ", ", ");
            } else {
                current_user_name = current_name;
            }

            CompanyDetails details = new CompanyDetails(current_date, current_time, company_name, services, address, current_user_name);
            companyDetails.add(details);

            ComDetailsadapter = new AllCompanyDetailsAdapter(context, companyDetails);
            recy_all_company_list.setAdapter(ComDetailsadapter);

            dialog.dismiss();
        }

    }

    private void openAddCompanyPopup() {

        selectedemployee.clear();

        dialog = new Dialog(context, R.style.AppTheme);
        dialog.setContentView(R.layout.activity_add_company_details);

        txt_select_employee = (TextView) dialog.findViewById(R.id.txt_select_employee);


        rel_select_date = (RelativeLayout) dialog.findViewById(R.id.rel_select_date);
        rel_select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDatePopup();
            }
        });

        txt_select_date = (TextView) dialog.findViewById(R.id.txt_select_date);

        rel_add_company_toggel = (RelativeLayout) dialog.findViewById(R.id.rel_add_company_toggel);
        rel_add_company_toggel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ToggleScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        TextView text_add_total_meetings = (TextView) dialog.findViewById(R.id.text_add_total_meetings);
        text_add_total_meetings.setText(spManager.getTotal_mettings());

        edit_company_name = (EditText) dialog.findViewById(R.id.edit_company_name);
        edit_services = (EditText) dialog.findViewById(R.id.edit_services);
        edit_address = (EditText) dialog.findViewById(R.id.edit_address);


        rel_any_team_member = (RelativeLayout) dialog.findViewById(R.id.rel_any_team_member);
        rel_any_team_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAnyTeamMember();
            }
        });

        btn_comapny_add = (Button) dialog.findViewById(R.id.btn_comapny_add);
        btn_comapny_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCompany();
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);


        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();

    }

    @Override
    public void onemployeeClick(String str, String status) {

        String user_name = str;
        String sattusemployee = status;
        if (sattusemployee.equals("check")) {
            selectedemployee.add(user_name);
        } else {
            for (int i = 0; i < selectedemployee.size(); i++) {

                if (selectedemployee.get(i).equals(user_name)) {

                    selectedemployee.remove(i);
                }
            }
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {

        String month = String.valueOf(monthOfYear + 1);
        String selectdate = dayOfMonth + "-" + month + "-" + year1;


        try {
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = format1.parse(selectdate);
            current_date = format2.format(date);

            txt_select_date.setText(current_date);


            //Toast.makeText(context,localsdate,Toast.LENGTH_SHORT).show();


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}

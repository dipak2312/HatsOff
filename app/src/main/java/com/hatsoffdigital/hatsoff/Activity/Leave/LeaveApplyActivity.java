package com.hatsoffdigital.hatsoff.Activity.Leave;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hatsoffdigital.hatsoff.Activity.Profile.UpdateProfileActivity;
import com.hatsoffdigital.hatsoff.Adapters.HolidayViewAdapter;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.CurrentDate;
import com.hatsoffdigital.hatsoff.Models.HolidaysListResponse;
import com.hatsoffdigital.hatsoff.Models.LeaveCount;
import com.hatsoffdigital.hatsoff.Models.LeaveDates;
import com.hatsoffdigital.hatsoff.Models.LeaveStatus;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LeaveApplyActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    Context context;
    TextView under_line;
    RelativeLayout rel_halfday_leave,rel_planned_leave;
    RadioGroup radiogroup;
    TextView text_planned_from_date,text_planned_to_date,planned_total_days;
    TextView text_halfday_outtime,text_halfday_in_time,text_half_day_date;
    int year,month,day;
    String plannedfromdate="0",plannedtodate="0",halfdaydate="0";
    RelativeLayout rel_intime,rel_outtime;
    String half_Intime="0",half_outtime="0";
    String day_append;
    int allshowdate;
    String formatedPlannedForDate;
    String formatedPlannedToDate;
    String halfleaveDays="0";
    String emergencyleavecount="0",emergencyplannedleavecount="0";




    String planned_fromdate="",planned_todate="";

    CustomProgressDialog dialog;

    String leave_type=" ";
    String emp_id;
    String full_name;
    String date;

    EditText attend_bahana;

    CurrentDate currentdate;
    TextView leave_apply_date;
    TextView txt_el_available,txt_pl_available;
    SPManager spManager;

    Button btn_apply_leave;
    String currentdate1;
    String current_date2;

    String[] getDateList;


    ImageView img_leave_inst;
    String currenemergencytdate;

    String half_day_intime1="0",half_day_outtime1="0";

    ImageView img_leave_send_back,img_Leave_send_setting;
    int swoallEmergencyDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_apply);
        getSupportActionBar().hide();
        context=LeaveApplyActivity.this;
        spManager=new SPManager(context);

        dialog=new CustomProgressDialog(context);
        emp_id=spManager.getEmployee_id();
        full_name=spManager.getName();
        under_line=(TextView)findViewById(R.id.under_line);
        under_line.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        rel_planned_leave=(RelativeLayout)findViewById(R.id.rel_planned_leave);
        rel_halfday_leave=(RelativeLayout)findViewById(R.id.rel_halfday_leave);

        txt_el_available=(TextView)findViewById(R.id.txt_el_available);
        txt_pl_available=(TextView)findViewById(R.id.txt_pl_available);

        txt_el_available.setText(spManager.getEL_Available());
        txt_pl_available.setText(spManager.getPL_Available());

        leave_apply_date=(TextView)findViewById(R.id.leave_apply_date);
        text_planned_from_date=(TextView)findViewById(R.id.text_planned_from_date);
        text_planned_from_date.setOnClickListener(this);

        text_planned_to_date=(TextView)findViewById(R.id.text_planned_to_date);
        text_planned_to_date.setOnClickListener(this);

        planned_total_days=(TextView)findViewById(R.id.planned_total_days);

        text_halfday_outtime=(TextView)findViewById(R.id.text_halfday_outtime);
        text_halfday_in_time=(TextView)findViewById(R.id.text_halfday_in_time);


        text_half_day_date=(TextView)findViewById(R.id.text_half_day_date);
        text_half_day_date.setOnClickListener(this);

        btn_apply_leave=(Button)findViewById(R.id.btn_apply_leave);
        btn_apply_leave.setOnClickListener(this);

        rel_intime=(RelativeLayout)findViewById(R.id.rel_intime);
        rel_intime.setOnClickListener(this);
        rel_outtime=(RelativeLayout)findViewById(R.id.rel_outtime);
        rel_outtime.setOnClickListener(this);

        img_leave_inst=(ImageView)findViewById(R.id.img_leave_inst);
        img_leave_inst.setOnClickListener(this);

        attend_bahana=(EditText)findViewById(R.id.attend_bahana);

        img_leave_send_back=(ImageView)findViewById(R.id.img_leave_send_back);
        img_leave_send_back.setOnClickListener(this);
        img_Leave_send_setting=(ImageView)findViewById(R.id.img_Leave_send_setting);
        img_Leave_send_setting.setOnClickListener(this);





//        attend_bahana.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//                String str = editable.toString();
//                btn_apply_leave.setVisibility(View.VISIBLE);
//
//
//                if(plannedfromdate.equals("0") && plannedtodate.equals("0"))
//                {
//                    btn_apply_leave.setVisibility(View.GONE);
//
//                }
//                else
//                {
//                    btn_apply_leave.setVisibility(View.VISIBLE);
//                }
//
//                if(leave_type.equals("Halfday"))
//                {
//                    if(half_Intime.equals("0")  && half_outtime.equals("0"))
//                    {
//                        btn_apply_leave.setVisibility(View.GONE);
//
//                    }
//                    else
//                    {
//                        btn_apply_leave.setVisibility(View.VISIBLE);
//                    }
//
//                }
//
//                    if (str.equals("")) {
//
//                    btn_apply_leave.setVisibility(View.GONE);
//
//                }
//
//            }
//        });


          getHolidaysLeave();


         currentdate=new CurrentDate();
         currentdate1=currentdate.current_date();
         leave_apply_date.setText(currentdate1);

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);

        current_date2=day+"-"+month+"-"+year;


        radiogroup=(RadioGroup)findViewById(R.id.radiogroup);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_plan_leave:
                        rel_planned_leave.setVisibility(View.VISIBLE);
                        rel_halfday_leave.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.radio_half_leave:
                        rel_halfday_leave.setVisibility(View.VISIBLE);
                        rel_planned_leave.setVisibility(View.INVISIBLE);
                        leave_type="HL";
                        break;
                }
            }
        });




    }

    private void getHolidaysLeave() {

        dialog.show(" ");

        WebServiceModel.getRestApi().getLeaveDates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LeaveDates>() {
                    @Override
                    public void onNext(LeaveDates leavedates) {
                        dialog.dismiss(" ");

                        String msg=leavedates.getMsg();

                        if(msg.equals("success")) {


                          getDateList=leavedates.getHolidays_leave_list();

                          System.out.println(getDateList);

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

        int id=view.getId();

        if(id==text_planned_from_date.getId())
        {

            plannedfromdate="1";
            ShowDatePopup();
        }
        else  if(id==text_planned_to_date.getId())
        {

            ShowDatePopup();
            plannedtodate="2";

        }
        else  if(id==text_half_day_date.getId())
        {
            halfdaydate="3";

            ShowDatePopup();

        }
        else  if(id==rel_intime.getId())
        {

            half_Intime="1";
            getTimePicker();

        }
        else  if(id==rel_outtime.getId())
        {
            half_outtime="2";
            getTimePicker();

        }
        else if(id==img_leave_inst.getId())
        {
            attendencePopup();
        }
        else if(id==img_leave_send_back.getId())
        {
            finish();
        }
        else if(id==img_Leave_send_setting.getId())
        {
            Intent intent=new Intent(context,UpdateProfileActivity.class);
            startActivity(intent);
        }
        else if (id==btn_apply_leave.getId())
        {
            if(leave_type.equals("PL"))
            {
                if(plannedfromdate.equals("0"))
                {
                  Toast.makeText(context,"Insert Leave From Date",Toast.LENGTH_SHORT).show();
                }
                else if(plannedtodate.equals("0"))
                {
                    Toast.makeText(context,"Insert Leave To Date",Toast.LENGTH_SHORT).show();
                }
                else if(attend_bahana.getText().toString().trim().equals(""))
                {
                    attend_bahana.requestFocus();
                    attend_bahana.setError("bahana dalo");
                }
                else
                {
                    emergencyplannedleavecount= String.valueOf(allshowdate);
                    sendPlannedEmergencyLeave();
                }


                }
                else if(leave_type.equals("EL"))
              {

                if(plannedfromdate.equals("0"))
                {
                    Toast.makeText(context,"Insert Leave From Date",Toast.LENGTH_SHORT).show();
                }
                else if(plannedtodate.equals("0"))
                {
                    Toast.makeText(context,"Insert Leave To Date",Toast.LENGTH_SHORT).show();
                }
                else if(attend_bahana.getText().toString().trim().equals(""))
                {
                    attend_bahana.requestFocus();
                    attend_bahana.setError("bahana dalo");
                }
                else
                {
                    emergencyleavecount= String.valueOf(allshowdate);

                    sendPlannedEmergencyLeave();
                }

                }

                else if(leave_type.equals("EL - PL"))
            {

                if(plannedfromdate.equals("0"))
                {
                    Toast.makeText(context,"Insert Leave From Date",Toast.LENGTH_SHORT).show();
                }
                else if(plannedtodate.equals("0"))
                {
                    Toast.makeText(context,"Insert Leave To Date",Toast.LENGTH_SHORT).show();
                }
                else if(attend_bahana.getText().toString().trim().equals(""))
                {
                    attend_bahana.requestFocus();
                    attend_bahana.setError("bahana dalo");
                }
                else
                {
                    sendPlannedEmergencyLeave();
                }

            }
            else if(leave_type.equals("HL"))
            {

                if(half_day_intime1.equals("0"))
                {
                    Toast.makeText(context,"Insert Halfday InTime",Toast.LENGTH_SHORT).show();
                }
                else if(half_day_outtime1.equals("0"))
                {
                    Toast.makeText(context,"Insert Halfday OutTime",Toast.LENGTH_SHORT).show();
                }
                else if(date==null)
                {
                    Toast.makeText(context,"Insert Halfday Date",Toast.LENGTH_SHORT).show();
                }
                else if(attend_bahana.getText().toString().trim().equals(""))
                {
                    attend_bahana.requestFocus();
                    attend_bahana.setError("bahana dalo");
                }
                else
                {
                    sendHalfDayLeave();
                }


                }
        }

    }


    private void attendencePopup() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Lamba section hai but dhyan se padhna");
        builder1.setMessage("Hello Team,\n" +
                "A new update in the official HO app under the \"Leave Tracker Section\":\n" +
                "\n" +
                "Starting from today you can apply for all your leaves via the app. \n" +
                "You are entitled for \"21 leaves\" in a year out of which only 7 you can apply as \"Emergency Leaves\". \n" +
                "What is Emergency Leaves - Leaves which are taken with less then 7 days spare. So ensure you plan your leaves atleast a week in advance.\n" +
                "\n" +
                "Process:\n" +
                "You can apply for leaves online via the app. System will detect the type of leave & will be sent to the admin for approval. If approved it will be notified via the app & same if rejected.\n" +
                "Even you'll can apply for half days via the app hereon. You can also track your pending leaves easily via the app. As per the app the leaves will be adjusted & affect your remuneration.\n" +
                "\n" +
                "Contact HR for more clarity.\n" +
                "\n" +
                "Thankyou!");
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

    private void  sendPlannedEmergencyLeave() {

       dialog.show(" ");

        String resone=attend_bahana.getText().toString().trim();

        DateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");

        Date d1 = null;
        try {
            d1 = formatter.parse(plannedfromdate);

            Date d2 = formatter.parse(plannedtodate);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(d1);

            Calendar endCalendar = new GregorianCalendar();
            endCalendar.setTime(d2);

            Date resultFrom = calendar.getTime();
            Date resultTo=endCalendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            formatedPlannedForDate = dateFormat.format(resultFrom);
             formatedPlannedToDate = dateFormat.format(resultTo);

            //System.out.println("saggi"+formatedPlannedForDate);
           // System.out.println("saggi"+formatedPlannedToDate);


        } catch (ParseException e) {
            e.printStackTrace();
        }



        WebServiceModel.getRestApi().SendLeaveStatus(emp_id,full_name,formatedPlannedForDate,formatedPlannedToDate,leave_type,resone,current_date2,emergencyleavecount,emergencyplannedleavecount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LeaveStatus>() {
                    @Override
                    public void onNext(LeaveStatus leavestatus) {
                        dialog.dismiss(" ");

                        String status=leavestatus.getMsg();

                        if(status.equals("success"))
                        {
                            Toast.makeText(context,"Leave Applied Successfully",Toast.LENGTH_SHORT).show();
                            finish();
                        }

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


    private void sendHalfDayLeave() {
       dialog.show(" ");

        String resone=attend_bahana.getText().toString().trim();

        String half_in_time=date+","+half_day_intime1;
        String half_out_time=date+","+half_day_outtime1;


        WebServiceModel.getRestApi().SendLeaveStatus(emp_id,full_name,half_in_time,half_out_time,leave_type,resone,current_date2,emergencyleavecount,emergencyplannedleavecount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LeaveStatus>() {
                    @Override
                    public void onNext(LeaveStatus leavestatus) {
                        dialog.dismiss(" ");

                        String status=leavestatus.getMsg();

                        if(status.equals("success"))
                        {
                            Toast.makeText(context,"Leave Applied Successfully",Toast.LENGTH_SHORT).show();
                            finish();
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

    public  void getTimePicker()
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.time_picker_dialog);
        dialog.show();
         final  TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.simpleTimePicker);

        TextView text_cancel=(TextView)dialog.findViewById(R.id.text_cancel);
        text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView text_ok=(TextView)dialog.findViewById(R.id.text_ok);
        text_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm a");

                    String strTime = timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();
                    Date time = sdf24.parse(strTime);
                    String formattedTime = sdf12.format(time);

                    if(half_Intime.equals("1")) {
                        text_halfday_in_time.setText(formattedTime);
                        under_line.setText("HALFDAY LEAVES");
                        half_day_intime1=formattedTime;
                        half_Intime="0";

                    }

                    if(half_outtime.equals("2"))
                    {
                        text_halfday_outtime.setText(formattedTime);
                        half_day_outtime1=formattedTime;
                    }

                    dialog.dismiss();

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });


    }


    private void ShowDatePopup() {


        Calendar now1 = Calendar.getInstance();
        int year1 = now1.get(Calendar.YEAR);
        int month1 = now1.get(Calendar.MONTH); // Note: zero based!
        int day1 = now1.get(Calendar.DAY_OF_MONTH);

        new SpinnerDatePickerDialogBuilder()
                .context(context)
                .callback(LeaveApplyActivity.this)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(false)
                .defaultDate(year1, month1, day1)
                .maxDate(2050, 0, 1)
                .minDate(1950, 0, 1)
                .build()
                .show();

    }

    @Override
    public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {

        String month= String.valueOf(monthOfYear+1);
        date=dayOfMonth+"-"+month+"-"+year1;


        if(plannedfromdate.equals("1"))
        {

            text_planned_from_date.setText(date);
            plannedfromdate=date;



            if(!plannedfromdate.equals(""))
            {

                Calendar now = Calendar.getInstance();

                int year3 = now.get(Calendar.YEAR);
                int  month3 = now.get(Calendar.MONTH) + 1; // Note: zero based!
                int day3 = now.get(Calendar.DAY_OF_MONTH);

                currenemergencytdate=day3+"-"+month3+"-"+year3;

                SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
                String inputString1 = currenemergencytdate;
                String inputString2 = plannedfromdate;

                try {
                    Date date1 = myFormat.parse(inputString1);
                    Date date2 = myFormat.parse(inputString2);
                    long diff = date2.getTime() - date1.getTime();
                    String day1= String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                    int day2= Integer.parseInt(day1);
                    int day4=day2+1;


                    if(day4 <=7)
                    {
                        under_line.setText("EMERGENCY LEAVES");
                        leave_type="EL";


                    }
                    else
                    {
                        under_line.setText("PLANNED LEAVES");
                        leave_type="PL";
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            if(!plannedfromdate.equals("") && !plannedtodate.equals(""))
            {
                SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
                String inputString1 = plannedfromdate;
                String inputString2 = plannedtodate;

                try {
                    Date date1 = myFormat.parse(inputString1);
                    Date date2 = myFormat.parse(inputString2);
                    long diff = date2.getTime() - date1.getTime();
                    String day1= String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                    int day2= Integer.parseInt(day1);
                    int day3=day2+1;

                    int count= showsundaycount(plannedfromdate,plannedtodate);
                    int showday=day3-count;
                    int holidaydate=getLeavedate(plannedfromdate,plannedtodate);

                   allshowdate=showday-holidaydate;

                    day_append=allshowdate +" "+"Days";
                    planned_total_days.setText(day_append);

                    if(leave_type.equals("EL")) {

                        SimpleDateFormat datefind = new SimpleDateFormat("dd-MM-yyyy");
                        String currentdatefind = currenemergencytdate;
                        String todatefind = plannedtodate;


                        Date dd1 = myFormat.parse(currentdatefind);
                        Date dd2 = myFormat.parse(todatefind);
                        long diff1 = dd2.getTime() - dd1.getTime();
                        String daydd1 = String.valueOf(TimeUnit.DAYS.convert(diff1, TimeUnit.MILLISECONDS));
                        int ddday = Integer.parseInt(daydd1);
                        int daycount = ddday + 1;

                        System.out.println("daycount"+daycount);

                        if (daycount > 7) {

                            under_line.setText("EMERGENCY & PLANNED LEAVES");
                            leave_type="EL - PL";
                            Calendar now = Calendar.getInstance();

                            int curyear = now.get(Calendar.YEAR);
                            int curmonth = now.get(Calendar.MONTH) + 1; // Note: zero based!
                            int curday = now.get(Calendar.DAY_OF_MONTH) + 6;
                            int curdayplusone = now.get(Calendar.DAY_OF_MONTH) + 7;

                            String currentAddSevenDate = curday + "-" + curmonth + "-" + curyear;

                            System.out.println("saggidate" + currentAddSevenDate);

                            int emergencycurrent = getemergencgyDate(plannedfromdate, currentAddSevenDate);

                            String currentAddSevenDateplusone = curdayplusone + "-" + curmonth + "-" + curyear;

                            int emergencyplanned = getemergencgyDate(currentAddSevenDateplusone, plannedtodate);

                            emergencyleavecount= String.valueOf(emergencycurrent);
                            emergencyplannedleavecount= String.valueOf(emergencyplanned);


//                            System.out.println("saggicurrent=" + emergencycurrent);
//                            System.out.println("saggiplanned=" + emergencyplanned);


                        }

                    }



                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }



        }

        if(plannedtodate.equals("2"))
        {

            text_planned_to_date.setText(date);
            plannedtodate=date;

            if(!plannedfromdate.equals("") && !plannedtodate.equals(""))
            {
                SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
                String inputString1 = plannedfromdate;
                String inputString2 = plannedtodate;

                try {
                    Date date1 = myFormat.parse(inputString1);
                    Date date2 = myFormat.parse(inputString2);
                    long diff = date2.getTime() - date1.getTime();
                    String day1= String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                    int day2= Integer.parseInt(day1);
                    int day3=day2+1;

                    int count= showsundaycount(plannedfromdate,plannedtodate);
                    int showday=day3-count;

                    int holidaydate=getLeavedate(plannedfromdate,plannedtodate);

                     allshowdate=showday-holidaydate;

                    day_append=allshowdate +" "+"Days";
                    planned_total_days.setText(day_append);


                    //getSeventDate(plannedfromdate,plannedtodate);

                    if(leave_type.equals("EL")) {

                        SimpleDateFormat datefind = new SimpleDateFormat("dd-MM-yyyy");
                        String currentdatefind = currenemergencytdate;
                        String todatefind = plannedtodate;


                        Date dd1 = myFormat.parse(currentdatefind);
                        Date dd2 = myFormat.parse(todatefind);
                        long diff1 = dd2.getTime() - dd1.getTime();
                        String daydd1 = String.valueOf(TimeUnit.DAYS.convert(diff1, TimeUnit.MILLISECONDS));
                        int ddday = Integer.parseInt(daydd1);
                        int daycount = ddday + 1;

                        System.out.println("daycount"+daycount);

                        if (daycount > 7) {

                            under_line.setText("EMERGENCY & PLANNED LEAVES");
                            leave_type="EL - PL";
                            Calendar now = Calendar.getInstance();

                            int curyear = now.get(Calendar.YEAR);
                            int curmonth = now.get(Calendar.MONTH) + 1; // Note: zero based!
                            int curday = now.get(Calendar.DAY_OF_MONTH) + 6;
                            int curdayplusone = now.get(Calendar.DAY_OF_MONTH) + 7;

                            String currentAddSevenDate = curday + "-" + curmonth + "-" + curyear;

                            System.out.println("saggidate" + currentAddSevenDate);

                            int emergencycurrent = getemergencgyDate(plannedfromdate, currentAddSevenDate);

                            String currentAddSevenDateplusone = curdayplusone + "-" + curmonth + "-" + curyear;

                            int emergencyplanned = getemergencgyDate(currentAddSevenDateplusone, plannedtodate);

                            emergencyleavecount= String.valueOf(emergencycurrent);
                            emergencyplannedleavecount= String.valueOf(emergencyplanned);


                            //System.out.println("saggicurrent=" + emergencycurrent);
                            //System.out.println("saggiplanned=" + emergencyplanned);


                        }

                    }



                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        if(halfdaydate.equals("3"))
        {

            text_half_day_date.setText(date);

            SimpleDateFormat datefind = new SimpleDateFormat("dd-MM-yyyy");
            Calendar now = Calendar.getInstance();
            int currentyear = now.get(Calendar.YEAR);
            int currentmonth = now.get(Calendar.MONTH) + 1; // Note: zero based!
            int currentday = now.get(Calendar.DAY_OF_MONTH);

            String curenntdate=currentday+"-"+currentmonth+"-"+currentyear;
            String todatefind = date;


            Date dd1 = null;
            try {
                dd1 = datefind.parse(curenntdate);
                Date dd2 = datefind.parse(todatefind);
                long diff1 = dd2.getTime() - dd1.getTime();
                String daydd1 = String.valueOf(TimeUnit.DAYS.convert(diff1, TimeUnit.MILLISECONDS));
                int ddday = Integer.parseInt(daydd1);
                int daycount = ddday + 1;

                if(daycount <=7)
                {
                     emergencyleavecount="0.5";
                     under_line.setText("EMERGENCY LEAVES");

                }
                else
                {
                    emergencyplannedleavecount="0.5";
                    under_line.setText("PLANNED LEAVES");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }



        }

    }

    public  int getemergencgyDate(String datecurrent1, String datecurrent2) {


        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        String inputString1 = datecurrent1;
        String inputString2 = datecurrent2;
        int allemergencyplanned=0;

        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();
            String day1= String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            int day2= Integer.parseInt(day1);
            int day3=day2+1;

            int count= showsundaycount(datecurrent1,datecurrent2);
            int showday=day3-count;

            int holidaydate=getLeavedate(datecurrent1,datecurrent2);

            allemergencyplanned=showday-holidaydate;




            //getSeventDate(plannedfromdate,plannedtodate);

        } catch (ParseException e) {
            e.printStackTrace();

        }

        return  allemergencyplanned;



        }


        private  int getLeavedate(String plannedfromdate, String plannedtodate) {

        int count=0;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
            //String date44="5-1-2019";
            Date d1 = formatter.parse(plannedfromdate);
            Date d2 = formatter.parse(plannedtodate);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(d1);

            Calendar endCalendar = new GregorianCalendar();
            endCalendar.setTime(d2);
            endCalendar.add(Calendar.DATE, 1);

            if (getDateList != null) {

            while (calendar.before(endCalendar)) {
                Date result = calendar.getTime();

                DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
                String strDate = dateFormat.format(result);
                //System.out.println("saggi"+strDate);



                    for (int i = 0; i < getDateList.length; i++) {
                        if (getDateList[i].equals(strDate)) {
                            count++;
                        }
                    }

                    calendar.add(Calendar.DATE, 1);
                }
            }
            else
            {
                getHolidaysLeave();
            }


            //System.out.println("saggiiii"+datesInRange);

            } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            }

        return count;
    }


    public static int showsundaycount(String plannedfromdate, String plannedtodate) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
        int count = 0;
        try {
            Date d1 = formatter.parse(plannedfromdate);
            Date d2 = formatter.parse(plannedtodate);
            //count = saturdaysundaycount(d1,d2);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(d1);

            Calendar c2 = Calendar.getInstance();
            c2.setTime(d2);

            int sundays = 0;
            int saturday = 0;

            while (! c1.after(c2)) {
                if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ){
                    saturday++;
                }
                if(c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                    sundays++;
                }

                c1.add(Calendar.DATE, 1);
            }

            //System.out.println("Saturday Count = "+saturday);
            //System.out.println("Sunday Count = "+sundays);
            count=sundays;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("sunday"+count);

        return count;
    }


}

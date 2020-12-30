package com.hatsoffdigital.hatsoff.Retrofit;

import com.hatsoffdigital.hatsoff.Models.AddMeetingDetailsResponse;
import com.hatsoffdigital.hatsoff.Models.AnnouncementListResponse;
import com.hatsoffdigital.hatsoff.Models.AttendenceDetailsResponse;
import com.hatsoffdigital.hatsoff.Models.AverageTime;
import com.hatsoffdigital.hatsoff.Models.CheckEmployeeId;
import com.hatsoffdigital.hatsoff.Models.DeleteInvoiceMessage;
import com.hatsoffdigital.hatsoff.Models.EmployeeListResponse;
import com.hatsoffdigital.hatsoff.Models.FactsResponse;
import com.hatsoffdigital.hatsoff.Models.GetExperience;
import com.hatsoffdigital.hatsoff.Models.HoFactsResponse;
import com.hatsoffdigital.hatsoff.Models.HolidayPopupResponse;
import com.hatsoffdigital.hatsoff.Models.HolidaysListResponse;
import com.hatsoffdigital.hatsoff.Models.InsertInOutTime;
import com.hatsoffdigital.hatsoff.Models.InvoiceListResponse;
import com.hatsoffdigital.hatsoff.Models.InvoicePendingRequestResponse;
import com.hatsoffdigital.hatsoff.Models.InvoiceRequestResponse;
import com.hatsoffdigital.hatsoff.Models.InvoiceSpinnerListResponse;
import com.hatsoffdigital.hatsoff.Models.LeaveCount;
import com.hatsoffdigital.hatsoff.Models.LeaveDates;
import com.hatsoffdigital.hatsoff.Models.LeaveListResponse;
import com.hatsoffdigital.hatsoff.Models.LeaveStatus;
import com.hatsoffdigital.hatsoff.Models.LocationResponse;
import com.hatsoffdigital.hatsoff.Models.MeetingAuthModel;
import com.hatsoffdigital.hatsoff.Models.MeetingHistroyResponse;
import com.hatsoffdigital.hatsoff.Models.MostTimeSpentResponse;
import com.hatsoffdigital.hatsoff.Models.RequestInvoiceResponse;
import com.hatsoffdigital.hatsoff.Models.ScanDateTime;
import com.hatsoffdigital.hatsoff.Models.SendTokenResponse;
import com.hatsoffdigital.hatsoff.Models.TermsConditionResponse;
import com.hatsoffdigital.hatsoff.Models.UpdateProfileAuthModel;
import com.hatsoffdigital.hatsoff.Models.UpdateProfileResponse;
import com.hatsoffdigital.hatsoff.Models.ViewProfileResponse;
import com.hatsoffdigital.hatsoff.Models.WfhHistoryListResponse;
import com.hatsoffdigital.hatsoff.Models.WfhSucessResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface WebServices {

    @FormUrlEncoded
    @POST("web_services/login.php")
    Observable<CheckEmployeeId> CheckEmployeeid(@Field("employee_id") String employeeid, @Field("OTP") String otp);

    @FormUrlEncoded
    @POST("web_services/scan.php")
    Observable<ScanDateTime> Scandatetime(@Field("employee_id") String employeeid);

    @FormUrlEncoded
    @POST("web_services/attendance_entery.php")
    Observable<InsertInOutTime> InsertTime(@Field("employee_id") String employeeid,@Field("am_in") String in,@Field("pm_out") String out,@Field("status") String status,@Field("late_mark_comment") String latemark,@Field("leave_comment") String leave);

    @FormUrlEncoded
    @POST("web_services/attendance_details.php")
    Observable<AttendenceDetailsResponse> AttendenceDetails(@Field("employee_id") String employeeid);


    @FormUrlEncoded
    @POST("web_services/experience.php")
    Observable<GetExperience> GetAllExperience(@Field("employee_id") String employeeid);


    @GET("web_services/holidays.php")
    Observable<HolidaysListResponse> HolidaysList();



    @GET("web_services/holidays_leave.php")
    Observable<LeaveDates> getLeaveDates();

    @GET("web_services/announcement.php")
    Observable<AnnouncementListResponse> AnnouncementList();

    @GET("web_services/employee_list.php")
    Observable<EmployeeListResponse> employeeList();

    @GET("web_services/most_time_spent.php")
    Observable<MostTimeSpentResponse> MostTimeSpentList();

    @GET("web_services/ho_facts.php")
    Observable<HoFactsResponse> getHoFacts();

    @GET("web_services/terms_con.php")
    Observable<TermsConditionResponse> getTermsConditon();

    @GET("web_services/holiday_popup.php")
    Observable<HolidayPopupResponse> getHolidayPopup();

    @GET("web_services/ho_facts_list.php")
    Observable<FactsResponse> getHoFactsList();


    @GET("web_services/invoice_list.php")
    Observable<InvoiceListResponse> getInvoiceList();

    @Headers("Content-Type: application/json")
    @POST("web_services/add_metting_details.php")
    Observable<AddMeetingDetailsResponse> mettingListResponse(@Body MeetingAuthModel model);


    @FormUrlEncoded
    @POST("web_services/token.php")
    Observable<SendTokenResponse> SendToken(@Field("employee_id") String employeeid,@Field("token") String token);


    @FormUrlEncoded
    @POST("web_services/view_profile.php")
    Observable<ViewProfileResponse> ViewProfile(@Field("employee_id") String employeeid);

    @FormUrlEncoded
    @POST("web_services/average_time.php")
    Observable<AverageTime> getAverageTime(@Field("employee_id") String employeeid);


    @FormUrlEncoded
    @POST("web_services/leave-count.php")
    Observable<LeaveCount> getLeaveCount(@Field("employee_id") String employeeid);


    @FormUrlEncoded
    @POST("web_services/leave_list.php")
    Observable<LeaveListResponse> getLeaveList(@Field("employee_id") String employeeid);



    @FormUrlEncoded
    @POST("web_services/leave-tracker.php")
    Observable<LeaveStatus> SendLeaveStatus(@Field("employee_id") String employeeid,@Field("fullname") String fullname,@Field("leave_date_from") String leavedatefrom,@Field("leave_date_to") String leavedateto,@Field("type") String type,@Field("reason") String resone,@Field("applied_date") String current_date,@Field("emergency_leave_days") String emergencycount,@Field("planned_leave_days") String emergencyplanned);


    @FormUrlEncoded
    @POST("web_services/insert_profile.php")
    Observable<UpdateProfileResponse> UpdateProfile(@Field("employee_id") String employeeid,@Field("date_of_birth") String dob,@Field("firstname") String firstname,@Field("surname") String lastname,@Field("bloodtype") String bloudgroup,@Field("permanent_address") String address,@Field("email_address") String email,@Field("cellphone_no") String mobno,@Field("joining_date") String join_date,@Field("file") String encodedstring,@Field("pan_card") String panCard,@Field("aadhar_card") String aadharCard);

    @FormUrlEncoded
    @POST("web_services/meeting_history.php")
    Observable<MeetingHistroyResponse> getMeetinghistroy(@Field("employee_id") String employeeid);


    @FormUrlEncoded
    @POST("web_services/wfh_apply.php")
    Observable<WfhSucessResponse> ApplyWFH(@Field("employee_id") String employeeid, @Field("fullname") String fullname, @Field("wfh_date_from") String datefrom, @Field("wfh_date_to") String dateto, @Field("employee_reason") String employeereson, @Field("applied_date") String date, @Field("days") String daysString );


    @FormUrlEncoded
    @POST("web_services/work_from_home_list.php")
    Observable<WfhHistoryListResponse> getWFHHistoryList(@Field("employee_id") String employeeid);


    @FormUrlEncoded
    @POST("web_services/invoice_request.php")
    Observable<RequestInvoiceResponse> AddInvoice(@Field("employee_id") String employee_id, @Field("employee_name") String employee_name, @Field("project_name") String project_name, @Field("category_name") String category_name, @Field("coasting") String coasting, @Field("currency") String currency,@Field("subject") String subject,@Field("bdm") String bdm,@Field("invoice_gst") String Invoicegst);



    @FormUrlEncoded
    @POST("web_services/invoice_request_list.php")
    Observable<InvoiceRequestResponse> getInvoiceHistroy(@Field("employee_id") String employeeid);


    @GET("web_services/invoice_dynamic_list.php")
    Observable<InvoiceSpinnerListResponse> getInvoiceSpinnerList();

    @FormUrlEncoded
    @POST("web_services/delete_invoice_request.php")
    Observable<DeleteInvoiceMessage> deleteInvoice(@Field("invoice_id") String invoice_id);

    @GET("web_services/invoice_pending_request_list.php")
    Observable<InvoicePendingRequestResponse> getInvoicePendingRequest();


}

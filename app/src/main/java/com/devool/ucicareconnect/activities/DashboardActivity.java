package com.devool.ucicareconnect.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.devool.ucicareconnect.R;
import com.devool.ucicareconnect.utils.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DashboardActivity extends Activity implements View.OnClickListener {

    public static final String USER_INFO = "user_info";
    RelativeLayout relScheduing, relQuickAnswers, relMyChart, relRefrrals, relPopup;
    SharedPreferences sharedpreferences;
    String currentDateandTime, strInterActionTypeID;
    Button btnEventDetails, btnViewReferralDetails;

    String strRequest, strReltionship;

    TextView tvHeading, tvSubHeading, tvWelcomeTitle;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        relScheduing = (RelativeLayout) findViewById(R.id.rel_scheduling);
        relQuickAnswers = findViewById(R.id.rel_quick_answer);
        relMyChart = findViewById(R.id.rel_my_chart);
        relRefrrals = findViewById(R.id.rel_referrals);
        relPopup = findViewById(R.id.rel_pop_up);
        btnEventDetails = findViewById(R.id.btn_view_event_details);
        btnViewReferralDetails = findViewById(R.id.btn_view_referral_details);
        tvHeading = findViewById(R.id.tv_heading);
        tvSubHeading = findViewById(R.id.tv_subheading);
        tvWelcomeTitle = findViewById(R.id.tv_welcome_tittle);

        relScheduing.setOnClickListener(this);
        relQuickAnswers.setOnClickListener(this);
        relMyChart.setOnClickListener(this);
        relRefrrals.setOnClickListener(this);
        btnEventDetails.setOnClickListener(this);
        btnViewReferralDetails.setOnClickListener(this);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentDateandTime = sdf.format(new Date());

        sharedpreferences = getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);

        i = getIntent();
        strRequest = i.getStringExtra("request");
        strReltionship = i.getStringExtra("Relationship");

        if (getIntent().hasExtra("flag")) {
            btnViewReferralDetails.setVisibility(View.VISIBLE);
            btnEventDetails.setVisibility(View.GONE);
            relPopup.setVisibility(View.VISIBLE);
            tvHeading.setText("Referral Submitted");
            tvSubHeading.setText("Thank you for the referral. We will contact them shortly.");
            btnEventDetails.setText("View Referral Details");
        }

        if (getIntent().hasExtra("signup_flag")) {
           tvWelcomeTitle.setText("We're happy to assist you with anything at UCI.");
        }

        if (getIntent().hasExtra("sigin_flag")) {
            tvWelcomeTitle.setText("How can we help?");
        }

        if (getIntent().hasExtra("is_lab_requested_completed")) {
            relPopup.setVisibility(View.VISIBLE);
            btnViewReferralDetails.setVisibility(View.GONE);
            btnEventDetails.setVisibility(View.VISIBLE);
        }
        if (getIntent().hasExtra("is_physician_requested_completed")) {
            relPopup.setVisibility(View.VISIBLE);
            btnViewReferralDetails.setVisibility(View.GONE);
            btnEventDetails.setVisibility(View.VISIBLE);
        }
        if (getIntent().hasExtra("is_radiology_requested_completed")) {
            relPopup.setVisibility(View.VISIBLE);
            btnViewReferralDetails.setVisibility(View.GONE);
            btnEventDetails.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_scheduling:
                strInterActionTypeID = "2";
                createDonorInteraction();
                break;
            case R.id.rel_quick_answer:
                strInterActionTypeID = "1";
                Toast.makeText(this, "WIP", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rel_my_chart:
                strInterActionTypeID = "3";
                Toast.makeText(this, "WIP", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rel_referrals:
                strInterActionTypeID = "4";
                //createReferral();
                /*Intent intent = new Intent(DashboardActivity.this, SchedulingActivity.class);
                intent.putExtra("Interaction_Type_ID", strInterActionTypeID);
                startActivity(intent);*/
                createDonorInteraction();
                break;
            case R.id.btn_view_event_details:
                if (strRequest.equalsIgnoreCase("Lab")) {
                    Intent i = new Intent(DashboardActivity.this, CancelAppointmentActivity.class);
                    i.putExtra("request", "Lab");
                    i.putExtra("appointment_type", getIntent().getExtras().getString("appointment_type"));
                    i.putExtra("Speciality", getIntent().getExtras().getString("Speciality"));
                    i.putExtra("Availability", getIntent().getExtras().getString("Availability"));
                    i.putExtra("Time_of_day", getIntent().getExtras().getString("Time_of_day"));
                    i.putExtra("Any_Specific_Request", getIntent().getExtras().getString("Any_Specific_Request"));
                    startActivity(i);
                    break;
                } else if (strRequest.equalsIgnoreCase("Physician")) {
                    Intent i = new Intent(DashboardActivity.this, CancelAppointmentActivity.class);
                    i.putExtra("request", "Physician");
                    i.putExtra("appointment_type", getIntent().getExtras().getString("appointment_type"));
                    i.putExtra("Speciality", "Physician");
                    i.putExtra("Physicians_Name", getIntent().getExtras().getString("Physicians_Name"));
                    i.putExtra("Physicians_Specialty", getIntent().getExtras().getString("Physicians_Specialty"));
                    i.putExtra("Availability", getIntent().getExtras().getString("Availability"));
                    i.putExtra("Time_of_day", getIntent().getExtras().getString("Time_of_day"));
                    i.putExtra("Any_Specific_Request", getIntent().getExtras().getString("Any_Specific_Request"));
                    startActivity(i);
                    break;
                } else if (strRequest.equalsIgnoreCase("Radiology / Diagnostics")) {
                    Intent i = new Intent(DashboardActivity.this, CancelAppointmentActivity.class);
                    i.putExtra("request", "Radiology / Diagnostics");
                    i.putExtra("appointment_type", getIntent().getExtras().getString("appointment_type"));
                    i.putExtra("Speciality", "Radiology / Diagnostics");
                    i.putExtra("Availability", getIntent().getExtras().getString("Availability"));
                    i.putExtra("Time_of_day", getIntent().getExtras().getString("Time_of_day"));
                    i.putExtra("Requested_By", getIntent().getExtras().getString("Requested_By"));
                    i.putExtra("OutsideProvider_number", getIntent().getExtras().getString("OutsideProvider_number"));
                    i.putExtra("Radiology_Type", getIntent().getExtras().getString("Radiology_Type"));
                    i.putExtra("Any_Specific_Request", getIntent().getExtras().getString("Any_Specific_Request"));
                    startActivity(i);
                    break;
                }
                break;

            case R.id.btn_view_referral_details:
                if(strReltionship.equalsIgnoreCase("Friend")){
                Intent intent = new Intent(DashboardActivity.this, MakeAnotherReferralActivity.class);
                intent.putExtra("Referral_name", getIntent().getExtras().getString("Referral_name"));
                intent.putExtra("Relationship", getIntent().getExtras().getString("Relationship"));
                intent.putExtra("referal_phone", getIntent().getExtras().getString("referal_phone"));
                intent.putExtra("referal_email", getIntent().getExtras().getString("referal_email"));
                startActivity(intent);
                break;
            }else if(strReltionship.equalsIgnoreCase("Family")){
                Intent intent = new Intent(DashboardActivity.this, MakeAnotherReferralActivity.class);
                intent.putExtra("family_relation", getIntent().getExtras().getString("family_relation"));
                intent.putExtra("Referral_name", getIntent().getExtras().getString("Referral_name"));
                intent.putExtra("Relationship", getIntent().getExtras().getString("Relationship"));
                intent.putExtra("referal_phone", getIntent().getExtras().getString("referal_phone"));
                intent.putExtra("referal_email", getIntent().getExtras().getString("referal_email"));
                startActivity(intent);
                break;
            }else if(strReltionship.equalsIgnoreCase("Other Associates")){
                Intent intent = new Intent(DashboardActivity.this, MakeAnotherReferralActivity.class);
                intent.putExtra("Association", getIntent().getExtras().getString("Association"));
                intent.putExtra("Referral_name", getIntent().getExtras().getString("Referral_name"));
                intent.putExtra("Relationship", getIntent().getExtras().getString("Relationship"));
                intent.putExtra("referal_phone", getIntent().getExtras().getString("referal_phone"));
                intent.putExtra("referal_email", getIntent().getExtras().getString("referal_email"));
                startActivity(intent);
                break;
            }
            break;
        }

    }

    public void createDonorInteraction() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
            String URL = AppConfig.BASE_URL + AppConfig.CREATE_SCHEDULING;
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("UserID", sharedpreferences.getString("USER_ID", ""));
            jsonBody.put("Interaction_ID", "0");
            if (strInterActionTypeID.equalsIgnoreCase("4")) {
                jsonBody.put("Interaction_Type_ID", "4");
                Log.e("Interaction_Type_ID", "4");
            } else {
                jsonBody.put("Interaction_Type_ID", "2");
                Log.e("Interaction_Type_ID", "2");
            }
            jsonBody.put("Status_ID", "0");
            jsonBody.put("Chat_ID", "0");
            jsonBody.put("Interaction_Start_Time", currentDateandTime);
            jsonBody.put("Interaction_DTL_ID", "0");
            jsonBody.put("Created_By_User_ID", sharedpreferences.getString("USER_ID", ""));

            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Dashboaard_response", response);
                        JSONArray array = new JSONArray(response);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(0);
                            if (object.getString("inMsg").equalsIgnoreCase("Interaction Saved!!!")) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("interaction_DTL_ID", object.getString("interaction_DTL_ID"));
                                editor.putString("interaction_ID", object.getString("interaction_ID"));
                                editor.putString("referral_ID", object.getString("referral_ID"));
                                editor.commit();
                                Intent intent = new Intent(DashboardActivity.this, SchedulingActivity.class);
                                intent.putExtra("Interaction_Type_ID", strInterActionTypeID);
                                startActivity(intent);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);

                    }
                    return super.parseNetworkResponse(response);
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package com.devool.ucicareconnect.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class UsersAccountActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    public static final String USER_INFO = "user_info";
    SharedPreferences sharedpreferences;
    LinearLayout llName, llPhoneNumber;
    SwitchCompat switchCompat;
    TextView tvName, tvEmailAddress, tvAddress, tvPhoneNumber;
    String strUserId;
    Button btnSignOut, btnChangePassword;
    ImageView imgCloseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_account);
        llName = findViewById(R.id.ll_switcher_name);
        llPhoneNumber = findViewById(R.id.ll_switcher_phone_number);
        switchCompat = findViewById(R.id.switcher_assistant_name);
        tvName = findViewById(R.id.tv_user_name);
        tvAddress = findViewById(R.id.tv_address);
        tvEmailAddress = findViewById(R.id.tv_email_address);
        tvPhoneNumber = findViewById(R.id.tv_phone_number);
        btnSignOut = findViewById(R.id.btn_sign_out);
        imgCloseButton = findViewById(R.id.img_close_button);
        btnChangePassword = findViewById(R.id.btn_change_password);

        switchCompat.setOnCheckedChangeListener(this);
        btnSignOut.setOnClickListener(this);
        imgCloseButton.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
        tvEmailAddress.setOnClickListener(this);

        sharedpreferences = getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        strUserId = sharedpreferences.getString("USER_ID", "");

        getProfileInfo();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId())
        {
            case R.id.switcher_assistant_name:
                if(isChecked){
                    llName.setVisibility(View.VISIBLE);
                    llPhoneNumber.setVisibility(View.VISIBLE);
                    break;
                }else {
                    llName.setVisibility(View.GONE);
                    llPhoneNumber.setVisibility(View.GONE);
                    break;
                }
        }
    }

    public void getProfileInfo() {
        RequestQueue requestQueue = Volley.newRequestQueue(UsersAccountActivity.this);
        String URL = AppConfig.BASE_URL + AppConfig.GET_USER_PROFILE+ "/"+strUserId;
        JSONObject jsonBody = new JSONObject();

        final String requestBody = jsonBody.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("getUserInfo", response);
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(0);
                        tvName.setText(object.getString("firstName") + " "+ object.getString("lastName"));
                        tvEmailAddress.setText(object.getString("email"));
                        tvPhoneNumber.setText(object.getString("phNo"));
                        tvAddress.setText(object.getString("addLn1"));
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign_out:
                Intent i = new Intent(UsersAccountActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                break;
            case R.id.img_close_button:
                Intent intent = new Intent(UsersAccountActivity.this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_change_password:
                Intent intent1 = new Intent(UsersAccountActivity.this, CreatePasswordActivity.class);
                intent1.putExtra("flag","from_account_screen");
                intent1.putExtra("userId", strUserId);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                finish();
                break;
            case R.id.tv_email_address:
                Toast.makeText(this, "WIP", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

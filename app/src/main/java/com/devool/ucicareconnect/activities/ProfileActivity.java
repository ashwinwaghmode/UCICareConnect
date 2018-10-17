package com.devool.ucicareconnect.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.devool.ucicareconnect.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSkip;
    TextView tvHeading, tvAddPhoto;
    Typeface tf;
    ImageView imgBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnSkip = findViewById(R.id.btn_skip);
        tvHeading = findViewById(R.id.tv_heading);
        tvAddPhoto = findViewById(R.id.tv_add_photo);
        imgBackArrow = findViewById(R.id.img_back_arrow);

        imgBackArrow.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_skip:
                Intent intent = new Intent(ProfileActivity.this,WelcomeScreensActivity.class);
                //intent.putExtra("password", edtCreatePassword.getText().toString());
                intent.putExtra("user_name", getIntent().getExtras().getString("user_name"));
                startActivity(intent);
                //startActivity(new Intent(ProfileActivity.this, WelcomeScreensActivity.class));
                break;
            case R.id.img_back_arrow:
                onBackPressed();
                break;
        }

    }

    private void appyfontForAllViews() {
        tf = Typeface.createFromAsset(getAssets(), "KievitSlabOT-Bold.otf");
        tvHeading.setTypeface(tf,Typeface.BOLD);

        tf = Typeface.createFromAsset(getAssets(), "KievitSlabOT-Bold.otf");
        tvAddPhoto.setTypeface(tf,Typeface.BOLD);

        tf = Typeface.createFromAsset(getAssets(), "KievitSlabOT-Medium.otf");
        btnSkip.setTypeface(tf);


    }
}

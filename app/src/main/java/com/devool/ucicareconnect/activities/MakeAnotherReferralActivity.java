package com.devool.ucicareconnect.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devool.ucicareconnect.R;

public class MakeAnotherReferralActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvReferralName, tvRelationship, tvAssociateName, tvFamiyRelation, tvPhoneNumber, tvEmailAddress;
    LinearLayout llAssociateName, llFamilyRelation;
    ImageView imgCloseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_another_refferal);

        llAssociateName = findViewById(R.id.ll_associate_name);
        llFamilyRelation = findViewById(R.id.ll_family_relation);

        tvReferralName = findViewById(R.id.tv_referral_name);
        tvRelationship = findViewById(R.id.tv_relationship);
        tvAssociateName = findViewById(R.id.tv_associate_name);
        tvFamiyRelation = findViewById(R.id.tv_family_relation);
        tvPhoneNumber = findViewById(R.id.tv_phone_number);
        tvEmailAddress = findViewById(R.id.tv_email_address);
        imgCloseButton = findViewById(R.id.img_close_button);

        imgCloseButton.setOnClickListener(this);

        if (getIntent().getExtras().getString("Relationship").equalsIgnoreCase("Friend")) {
            tvReferralName.setText(getIntent().getExtras().getString("Referral_name"));
            tvRelationship.setText(getIntent().getExtras().getString("Relationship"));
            tvPhoneNumber.setText(getIntent().getExtras().getString("referal_phone"));
            tvEmailAddress.setText(getIntent().getExtras().getString("referal_email"));
            llFamilyRelation.setVisibility(View.GONE);
            llAssociateName.setVisibility(View.GONE);
        } else if (getIntent().getExtras().getString("Relationship").equalsIgnoreCase("Family")) {
            tvReferralName.setText(getIntent().getExtras().getString("Referral_name"));
            tvRelationship.setText(getIntent().getExtras().getString("Relationship"));
            llFamilyRelation.setVisibility(View.VISIBLE);
            tvFamiyRelation.setText(getIntent().getExtras().getString("family_relation"));
            tvPhoneNumber.setText(getIntent().getExtras().getString("referal_phone"));
            tvEmailAddress.setText(getIntent().getExtras().getString("referal_email"));
        } else if (getIntent().getExtras().getString("Relationship").equalsIgnoreCase("Other Associates")) {
            tvReferralName.setText(getIntent().getExtras().getString("Referral_name"));
            tvRelationship.setText(getIntent().getExtras().getString("Relationship"));
            tvPhoneNumber.setText(getIntent().getExtras().getString("referal_phone"));
            tvEmailAddress.setText(getIntent().getExtras().getString("referal_email"));
            llFamilyRelation.setVisibility(View.GONE);
            llAssociateName.setVisibility(View.VISIBLE);
            tvAssociateName.setText(getIntent().getExtras().getString("Association"));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_close_button:
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }
}

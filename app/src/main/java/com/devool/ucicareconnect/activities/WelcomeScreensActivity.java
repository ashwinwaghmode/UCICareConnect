package com.devool.ucicareconnect.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.devool.ucicareconnect.R;
import com.devool.ucicareconnect.fragments.AppoinmentTypeFragment;
import com.devool.ucicareconnect.fragments.OnDemandServiceFragment;

public class WelcomeScreensActivity extends AppCompatActivity {



    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screens);

        userName = getIntent().getExtras().getString("user_name");

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OnDemandServiceFragment fragment = new OnDemandServiceFragment();
        Bundle args = new Bundle();
        args.putString("user_name", userName);
        fragment.setArguments(args);
        fragmentTransaction.add(R.id.myContainer, fragment);
        fragmentTransaction.commit();
    }
}

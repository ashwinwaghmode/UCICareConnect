package com.devool.ucicareconnect.activities;

import android.app.KeyguardManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devool.ucicareconnect.R;
import com.devool.ucicareconnect.utils.PasswordValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreatePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnNext;
    EditText edtCreatePassword;
    TextView tvUpLowChar, tvMinimumChar, tvNumber, tvSpecialCharacters, tvCharacterPair, tvCreatePassswordText, tvHeading, tvPasswoedRequirementHeading;
    Typeface tf;
    ImageView imgBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);

        edtCreatePassword = findViewById(R.id.edit_create_password);
        tvUpLowChar = findViewById(R.id.tv_upplow_char);
        tvMinimumChar = findViewById(R.id.tv_minimum_characters);
        tvNumber = findViewById(R.id.tv_one_number);
        tvSpecialCharacters = findViewById(R.id.tv_special_characters);
        tvCharacterPair = findViewById(R.id.tv_chaaractrs_pair);
        tvCreatePassswordText = findViewById(R.id.tv_create_password_text);
        tvHeading = findViewById(R.id.tv_heading);
        tvPasswoedRequirementHeading = findViewById(R.id.tv_password_requirement_heading);
        imgBackArrow = findViewById(R.id.img_back_arrow);

        imgBackArrow.setOnClickListener(this);

        edtCreatePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtCreatePassword.setBackgroundResource(R.drawable.edit_text_background);
                tvCreatePassswordText.setText("Create Your Password");
                if (edtCreatePassword.getText().toString().equalsIgnoreCase("")) {
                    tvPasswoedRequirementHeading.setTextColor(getResources().getColor(R.color.bacgroun_color));
                    tvCreatePassswordText.setVisibility(View.GONE);
                } else {
                    tvCreatePassswordText.setVisibility(View.VISIBLE);
                }

                if (isValidcaseSensPassword(edtCreatePassword.getText().toString().trim())) {
                    tvUpLowChar.setTextColor(getResources().getColor(R.color.error_color));
                } else {
                    tvUpLowChar.setTextColor(getResources().getColor(R.color.btn_text_color));
                }

                if (isValidcharLimitPassword(edtCreatePassword.getText().toString())) {
                    tvMinimumChar.setTextColor(getResources().getColor(R.color.error_color));
                } else {
                    tvMinimumChar.setTextColor(getResources().getColor(R.color.btn_text_color));
                }

                if (isValidateAtleastOneNumber(edtCreatePassword.getText().toString())) {
                    tvNumber.setTextColor(getResources().getColor(R.color.error_color));
                } else {
                    tvNumber.setTextColor(getResources().getColor(R.color.btn_text_color));
                }

                if (isValidateSpecialCharacters(edtCreatePassword.getText().toString())) {
                    tvSpecialCharacters.setTextColor(getResources().getColor(R.color.error_color));
                } else {
                    tvSpecialCharacters.setTextColor(getResources().getColor(R.color.btn_text_color));
                }

                if (hasConsecutiveCharacters(edtCreatePassword.getText().toString())) {
                    tvCharacterPair.setTextColor(getResources().getColor(R.color.error_color));
                } else {
                    tvCharacterPair.setTextColor(getResources().getColor(R.color.btn_text_color));
                }

                if (hasConsecutiveCharacter(edtCreatePassword.getText().toString())) {
                    tvCharacterPair.setTextColor(getResources().getColor(R.color.btn_text_color));
                } else {
                   /* if (hasConsecutiveCharacters(edtCreatePassword.getText().toString())) {
                        tvCharacterPair.setTextColor(getResources().getColor(R.color.error_color));
                    } else {
                        tvCharacterPair.setTextColor(getResources().getColor(R.color.btn_text_color));
                    }*/
                    tvCharacterPair.setTextColor(getResources().getColor(R.color.error_color));
                    if (edtCreatePassword.getText().toString().equalsIgnoreCase("")) {
                        tvCharacterPair.setTextColor(getResources().getColor(R.color.btn_text_color));
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        appyfontForAllViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                // PasswordValidator passwordValidator = new PasswordValidator();
                if (edtCreatePassword.getText().toString().equalsIgnoreCase("")) {
                     tvCreatePassswordText.setVisibility(View.VISIBLE);
                    edtCreatePassword.setBackgroundResource(R.drawable.activation_error_color_background);
                    tvCreatePassswordText.setText("Something's not right. Please check below.");
                    tvPasswoedRequirementHeading.setTextColor(getResources().getColor(R.color.text_password_error));
                    break;
                } else if (!isValidcaseSensPassword(edtCreatePassword.getText().toString().trim())) {
                    //tvCreatePassswordText.setVisibility(View.VISIBLE);
                    edtCreatePassword.setBackgroundResource(R.drawable.activation_error_color_background);
                    tvCreatePassswordText.setText("Something's not right. Please check below.");
                    tvPasswoedRequirementHeading.setTextColor(getResources().getColor(R.color.text_password_error));
                    break;
                } else if (!isValidcharLimitPassword(edtCreatePassword.getText().toString())) {
                    //tvCreatePassswordText.setVisibility(View.VISIBLE);
                    edtCreatePassword.setBackgroundResource(R.drawable.activation_error_color_background);
                    tvCreatePassswordText.setText("Something's not right. Please check below.");
                    tvPasswoedRequirementHeading.setTextColor(getResources().getColor(R.color.text_password_error));
                    break;
                } else if (!isValidateAtleastOneNumber(edtCreatePassword.getText().toString())) {
                    //tvCreatePassswordText.setVisibility(View.VISIBLE);
                    edtCreatePassword.setBackgroundResource(R.drawable.activation_error_color_background);
                    tvCreatePassswordText.setText("Something's not right. Please check below.");
                    tvPasswoedRequirementHeading.setTextColor(getResources().getColor(R.color.text_password_error));
                    break;
                } else if (!isValidateSpecialCharacters(edtCreatePassword.getText().toString())) {
                    //tvCreatePassswordText.setVisibility(View.VISIBLE);
                    edtCreatePassword.setBackgroundResource(R.drawable.activation_error_color_background);
                    tvCreatePassswordText.setText("Something's not right. Please check below.");
                    tvPasswoedRequirementHeading.setTextColor(getResources().getColor(R.color.text_password_error));
                    break;
                }/*else if(!hasConsecutiveCharacters(edtCreatePassword.getText().toString())){
                    //tvCreatePassswordText.setVisibility(View.VISIBLE);
                    edtCreatePassword.setBackgroundResource(R.drawable.activation_error_color_background);
                    tvCreatePassswordText.setText("Something's not right. Please check below.");
                    break;
                }*//*else if(hasConsecutiveCharacter(edtCreatePassword.getText().toString())){
                    //tvCreatePassswordText.setVisibility(View.VISIBLE);
                    edtCreatePassword.setBackgroundResource(R.drawable.activation_error_color_background);
                    tvCreatePassswordText.setText("Something's not right. Please check below.");
                    break;
                }*/else {
                    tvPasswoedRequirementHeading.setTextColor(getResources().getColor(R.color.bacgroun_color));
                    edtCreatePassword.setBackgroundResource(R.drawable.edit_text_background);
                    tvCreatePassswordText.setText("Create Your Password");
                    Intent i = new Intent(CreatePasswordActivity.this, ConfirmPasswordActivity.class);
                    i.putExtra("password", edtCreatePassword.getText().toString());
                    i.putExtra("user_name", getIntent().getExtras().getString("user_name"));
                    startActivity(i);
                    break;
                }

            case R.id.img_back_arrow:
                onBackPressed();
                break;
        }
    }

    private void appyfontForAllViews() {
        tf = Typeface.createFromAsset(getAssets(), "KievitSlabOT-Bold.otf");
        tvHeading.setTypeface(tf, Typeface.BOLD);

        tf = Typeface.createFromAsset(getAssets(), "KievitSlabOT-Bold.otf");
        edtCreatePassword.setTypeface(tf, Typeface.BOLD);

        tf = Typeface.createFromAsset(getAssets(), "KievitSlabOT-Medium.otf");
        btnNext.setTypeface(tf);

        tf = Typeface.createFromAsset(getAssets(), "HelveticaNeueLight.ttf");
        tvCreatePassswordText.setTypeface(tf);

        tf = Typeface.createFromAsset(getAssets(), "HelveticaNeueLight.ttf");
        tvMinimumChar.setTypeface(tf);

        tf = Typeface.createFromAsset(getAssets(), "HelveticaNeueLight.ttf");
        tvSpecialCharacters.setTypeface(tf);

        tf = Typeface.createFromAsset(getAssets(), "HelveticaNeueLight.ttf");
        tvNumber.setTypeface(tf);

        tf = Typeface.createFromAsset(getAssets(), "HelveticaNeueLight.ttf");
        tvUpLowChar.setTypeface(tf);

        tf = Typeface.createFromAsset(getAssets(), "HelveticaNeueLight.ttf");
        tvCharacterPair.setTypeface(tf);

        tf = Typeface.createFromAsset(getAssets(), "HelveticaNeueLight.ttf");
        tvPasswoedRequirementHeading.setTypeface(tf);

    }

    public static boolean isValidcharLimitPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = ("^.{8,20}$");
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidcaseSensPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = ("^(?=.*[a-z])(?=.*[A-Z]).{0,20}");
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    public static boolean isValidateAtleastOneNumber(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = ("^(?=.*\\d).{0,20}$");
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    public static boolean isValidateSpecialCharacters(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = ("^(?=.*[@#$%^&+=*]).{1,20}$");
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    public static boolean hasConsecutiveCharacters(String pwd) {
        String[] letter = pwd.split(""); // here you get each letter in to a string array

        for (int i = 0; i < letter.length - 2; i++) {
            if (letter[i].equals(letter[i + 1]) && letter[i + 1].equals(letter[i + 2])) {
                return true; //return true as it has 3 consecutive same character
            }
        }
        return false;
    }

    public static boolean hasConsecutiveCharacter(String pwd) {
        String[] letter = pwd.split(""); // here you get each letter in to a string array

        for (int i = 0; i < letter.length - 2; i++) {
            if (letter[i].equals(letter[i + 2])) {
                return true; //return true as it has 3 consecutive same character
            }
        }
        return false;
    }
}

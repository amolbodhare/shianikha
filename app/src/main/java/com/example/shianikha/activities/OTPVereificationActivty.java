package com.example.shianikha.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.shianikha.R;

public class OTPVereificationActivty extends AppCompatActivity {

    EditText otp_first,otp_second,otp_third,otp_fourth,otp_fifth,otp_sixth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        otp_first=findViewById(R.id.ot_first_digit);
        otp_second=findViewById(R.id.ot_seecond_digit);
        otp_third=findViewById(R.id.ot_third_digit);
        otp_fourth=findViewById(R.id.ot_fourth_digit);
        otp_fifth=findViewById(R.id.ot_fifth_digit);
        otp_sixth=findViewById(R.id.ot_sixth_digit);
        otp_first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                otp_second.requestFocus();
                //otp_first.setFocusable(true);
            }
        });

        otp_second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                otp_third.requestFocus();
            }
        });
        otp_third.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                otp_fourth.requestFocus();
            }
        });
        otp_fourth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                otp_fifth.requestFocus();
            }
        });

        otp_fifth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                otp_sixth.requestFocus();
            }
        });

    }
}

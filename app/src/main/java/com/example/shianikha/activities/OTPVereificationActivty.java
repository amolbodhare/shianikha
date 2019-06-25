package com.example.shianikha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shianikha.R;

public class OTPVereificationActivty extends AppCompatActivity implements View.OnClickListener {

    EditText otp_first, otp_second, otp_third, otp_fourth, otp_fifth, otp_sixth;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        otp_first = findViewById(R.id.ot_first_digit);
        otp_second = findViewById(R.id.ot_seecond_digit);
        otp_third = findViewById(R.id.ot_third_digit);
        otp_fourth = findViewById(R.id.ot_fourth_digit);
        otp_fifth = findViewById(R.id.ot_fifth_digit);
        otp_sixth = findViewById(R.id.ot_sixth_digit);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        handle6EditText();
    }

    private void handle6EditText() {
        otp_first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 1)
                    otp_second.requestFocus();
                //otp_first.setFocusable(true);
            }
        });

        otp_second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1)
                    otp_third.requestFocus();
                else
                    otp_first.requestFocus();
            }
        });

        otp_third.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1)
                    otp_fourth.requestFocus();
                else
                    otp_second.requestFocus();
            }
        });

        otp_fourth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1)
                    otp_fifth.requestFocus();
                else
                    otp_third.requestFocus();
            }
        });

        otp_fifth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1)
                    otp_sixth.requestFocus();
                else
                    otp_fourth.requestFocus();
            }
        });

        otp_sixth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1)
                    otp_sixth.requestFocus();
                else
                    otp_fifth.requestFocus();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            Intent i = new Intent(OTPVereificationActivty.this, RegSecondPageActivity.class);
            startActivity(i);
            //finish();
        }
    }
}

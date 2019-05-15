package com.example.shianikha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shianikha.OtpVerification;
import com.example.shianikha.R;
import com.example.shianikha.Registration;

public class RegSixthPageActivity extends AppCompatActivity {

    Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_sixth_page);
        btn_register=findViewById(R.id.btn_register);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(RegSixthPageActivity.this, OTPVereificationActivty.class);
                startActivity(i);
            }
        });
    }
}

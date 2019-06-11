package com.example.shianikha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.shianikha.R;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    TextView reg_here;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login=(Button)findViewById(R.id.btn_login);
        reg_here=findViewById(R.id.reg_tv);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });

        reg_here.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(LoginActivity.this,RegisterationActivity.class);
                startActivity(i);
            }
        });
    }
}

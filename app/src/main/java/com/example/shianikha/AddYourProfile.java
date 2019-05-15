package com.example.shianikha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shianikha.activities.RegFifthPageActivity;
import com.example.shianikha.fragments.RegisterationScreenFirst;

public class AddYourProfile extends AppCompatActivity {

    Button btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_your_profile);

        btn_next=(Button)findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(AddYourProfile.this, RegFifthPageActivity.class);
                startActivity(i);
            }
        });



    }
}

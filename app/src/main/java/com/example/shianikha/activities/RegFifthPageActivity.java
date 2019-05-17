package com.example.shianikha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.shianikha.R;

import java.util.ArrayList;
import java.util.List;


public class RegFifthPageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    Button btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_fifth_page);

        Spinner spinner_seeking_marriage = (Spinner) findViewById(R.id.seeking_marriage);
        Spinner spinner_interested_in = (Spinner) findViewById(R.id.interested_in);

        spinner_seeking_marriage.setOnItemSelectedListener(this);
        spinner_interested_in.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> seeking = new ArrayList<String>();
        seeking.add("Seeking Marriage");
        seeking.add("MySelf");
        seeking.add("Daughter");
        seeking.add("Son");
        seeking.add("Sister");
        seeking.add("Brother");

        List<String> interested_in = new ArrayList<String>();
        interested_in.add("Interested In");
        interested_in.add("MySelf");
        interested_in.add("Daughter");
        interested_in.add("Son");
        interested_in.add("Sister");
        interested_in.add("Brother");


        // Creating adapter for spinner
        ArrayAdapter<String> seeking_dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, seeking);
        ArrayAdapter<String> interested_dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, interested_in);

        // Drop down layout style - list view with radio button
        seeking_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interested_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // attaching data adapter to spinner
        spinner_seeking_marriage.setAdapter(seeking_dataAdapter);
        spinner_interested_in.setAdapter(interested_dataAdapter);


        btn_next=(Button)findViewById(R.id.btn_next);


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent i=new Intent(RegFifthPageActivity.this, RegSixthPageActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

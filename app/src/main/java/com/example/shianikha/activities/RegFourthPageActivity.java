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

public class RegFourthPageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_fourth_page);

        Spinner lang_spinner = (Spinner) findViewById(R.id.lang_spinner);
        Spinner habbit_spinner = (Spinner) findViewById(R.id.habbit_spinner);

        btn_next=(Button)findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(RegFourthPageActivity.this, RegFifthPageActivity.class);
                startActivity(i);
            }
        });

        lang_spinner.setOnItemSelectedListener(this);
        habbit_spinner.setOnItemSelectedListener(this);



        // Spinner Drop down elements
        List<String> cities = new ArrayList<String>();
        List<String> states = new ArrayList<String>();


        cities.add("Select languages");
        cities.add("English");


        states.add("Smoking");
        states.add("Drinking");


        setSpinner(lang_spinner,cities);
        setSpinner(habbit_spinner,states);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public  void setSpinner(Spinner spinner,List<String> categories)
    {
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }
}

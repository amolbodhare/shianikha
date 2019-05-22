package com.example.shianikha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.shianikha.R;

import java.util.ArrayList;
import java.util.List;

public class RegSixthPageActivity extends AppCompatActivity {

    Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_sixth_page);
        btn_register=findViewById(R.id.btn_register);


      /*  Spinner min_age_spinner = (Spinner) findViewById(R.id.min_age_spinner);
        Spinner max_age_spinner = (Spinner) findViewById(R.id.max_age_spinner);
        Spinner pref_match_status_spinner = (Spinner) findViewById(R.id.pref_match_status_spinner);
        Spinner pref_match_ethinicity = (Spinner) findViewById(R.id.pref_match_ethnicity_spinner);
        Spinner pref_match_min_edu_spinner = (Spinner) findViewById(R.id.pref_match_min_education_spinner);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(RegSixthPageActivity.this, OTPVereificationActivty.class);
                startActivity(i);
            }
        });

        // Spinner Drop down elements
        List<String> cities = new ArrayList<String>();
        List<String> states = new ArrayList<String>();
        List<String> country_res = new ArrayList<String>();
        List<String> country_citi = new ArrayList<String>();
        List<String> height = new ArrayList<String>();

        cities.add("18");
        cities.add("19");
        cities.add("20");
        cities.add("21");
        cities.add("22");
        cities.add("23");
        cities.add("24");
        cities.add("25");
        cities.add("26");
        cities.add("27");
        cities.add("28");
        cities.add("29");
        cities.add("30");
        cities.add("31");


        states.add("18");
        states.add("19");
        states.add("20");
        states.add("21");
        states.add("22");
        states.add("23");
        states.add("24");
        states.add("25");
        states.add("26");
        states.add("27");
        states.add("28");
        states.add("29");
        states.add("30");
        states.add("31");


        country_res.add("pref match martial status");
        country_res.add("South Africa");
        country_res.add("India");
        country_res.add("America");
        country_res.add("Singapur");
        country_res.add("UAE");
        country_res.add("Pakistan");

        country_citi.add("pref match ethinicity");
        country_citi.add("Austin");
        country_citi.add("Dallas");
        country_citi.add("Denver");
        country_citi.add("Phoenix");

        height.add("minimum education");
        height.add("B.E.");
        height.add("Doctor");
        height.add("IT");
        height.add("5.5'");

        setSpinner(min_age_spinner,cities);
        setSpinner(max_age_spinner,states);
        setSpinner(pref_match_status_spinner,country_res);
        setSpinner(pref_match_ethinicity,country_citi);
        setSpinner(pref_match_min_edu_spinner,height);*/
    }

    /*public  void setSpinner(Spinner spinner, List<String> categories)
    {
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }*/
}

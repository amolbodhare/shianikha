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

public class RegThirdPageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_third_page);
        btn_next=(Button)findViewById(R.id.btn_next);
        Spinner religion_spinner = (Spinner) findViewById(R.id.religion_spinner);
        Spinner ethinicity = (Spinner) findViewById(R.id.Ethincity_spinner);
        Spinner fathers_city_spinner = (Spinner) findViewById(R.id.fathers_city_spinner);
        Spinner mothers_city_spinner = (Spinner) findViewById(R.id.mothers_city_spinner);
        Spinner current_occupation_spinner = (Spinner) findViewById(R.id.current_occupation);
        Spinner height_level_edu_spinnner = (Spinner) findViewById(R.id.higest_level_edu_spinner);


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RegThirdPageActivity.this,RegFourthPageActivity.class);
                startActivity(i);
            }
        });

        religion_spinner.setOnItemSelectedListener(this);
        ethinicity.setOnItemSelectedListener(this);
        fathers_city_spinner.setOnItemSelectedListener(this);
        mothers_city_spinner.setOnItemSelectedListener(this);
        current_occupation_spinner.setOnItemSelectedListener(this);
        height_level_edu_spinnner.setOnItemSelectedListener(this);


        // Spinner Drop down elements
        List<String> cities = new ArrayList<String>();
        List<String> states = new ArrayList<String>();
        List<String> country_res = new ArrayList<String>();
        List<String> country_citi = new ArrayList<String>();
        List<String> height = new ArrayList<String>();
        List<String> highest_level_edu = new ArrayList<String>();

        cities.add("Select Relegion");
        cities.add("Mumbai");
        cities.add("Pune");
        cities.add("Goa");
        cities.add("Banglore");
        cities.add("Delhi");

        states.add("Ethnicity");
        states.add("Cazlifornia");
        states.add("Los Angelis");
        states.add("Indiana");
        states.add("New York");
        states.add("Texas");
        states.add("Florida");
        states.add("Alaska");

        country_res.add("Father's City/Country of origin");
        country_res.add("Brazil");
        country_res.add("South Africa");
        country_res.add("India");
        country_res.add("America");
        country_res.add("Singapur");
        country_res.add("UAE");
        country_res.add("Pakistan");

        country_citi.add("Mother's City/Country of origin");
        country_citi.add("Austin");
        country_citi.add("Austin");
        country_citi.add("Dallas");
        country_citi.add("Denver");
        country_citi.add("Phoenix");

        height.add("current occupation");
        height.add("5.2'");
        height.add("5.3'");
        height.add("5.4'");
        height.add("5.5'");

        highest_level_edu.add("Highest level Of education");
        highest_level_edu.add("5.2'");
        highest_level_edu.add("5.3'");
        highest_level_edu.add("5.4'");
        highest_level_edu.add("5.5'");

        setSpinner(religion_spinner,cities);
        setSpinner(ethinicity,states);
        setSpinner(fathers_city_spinner,country_res);
        setSpinner(mothers_city_spinner,country_citi);
        setSpinner(current_occupation_spinner,height);
        setSpinner(height_level_edu_spinnner,height);

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

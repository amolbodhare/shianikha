package com.example.shianikha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shianikha.R;

import java.util.ArrayList;
import java.util.List;

public class RegSecondPageActivity extends AppCompatActivity
{
    Button btn_next;


    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_second_page);

        btn_next=(Button)findViewById(R.id.btn_next);

        /*Spinner city_of_residence_spinner = (Spinner) findViewById(R.id.city_of_residence_spinner);
        Spinner state_spinner = (Spinner) findViewById(R.id.state_spinner);
        Spinner country_residence_spinner = (Spinner) findViewById(R.id.country_of_residence_spinner);
        Spinner country_citizenship = (Spinner) findViewById(R.id.country_of_citizenship);
        Spinner height_spinner = (Spinner) findViewById(R.id.height_spinner);


        city_of_residence_spinner.setOnItemSelectedListener(this);
        state_spinner.setOnItemSelectedListener(this);
        country_residence_spinner.setOnItemSelectedListener(this);
        country_citizenship.setOnItemSelectedListener(this);
        height_spinner.setOnItemSelectedListener(this);*/


        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(RegSecondPageActivity.this,RegThirdPageActivity.class);
                startActivity(i);
            }
        });


        setUpEditTextClickListner();
        setUpTextWatcher();

        // Spinner Drop down elements
        /*List<String> cities = new ArrayList<String>();
        List<String> states = new ArrayList<String>();
        List<String> country_res = new ArrayList<String>();
        List<String> country_citi = new ArrayList<String>();
        List<String> height = new ArrayList<String>();

        cities.add("City of Residence");
        cities.add("Mumbai");
        cities.add("Pune");
        cities.add("Goa");
        cities.add("Banglore");
        cities.add("Delhi");

        states.add("State/Province/Country");
        states.add("Cazlifornia");
        states.add("Los Angelis");
        states.add("Indiana");
        states.add("New York");
        states.add("Texas");
        states.add("Florida");
        states.add("Alaska");

        country_res.add("Country of residence");
        country_res.add("South Africa");
        country_res.add("India");
        country_res.add("America");
        country_res.add("Singapur");
        country_res.add("UAE");
        country_res.add("Pakistan");

        country_citi.add("Country of citizenship");
        country_citi.add("Austin");
        country_citi.add("Dallas");
        country_citi.add("Denver");
        country_citi.add("Phoenix");

        height.add("height");
        height.add("5.2'");
        height.add("5.3'");
        height.add("5.4'");
        height.add("5.5'");

        setSpinner(city_of_residence_spinner,cities);
        setSpinner(state_spinner,states);
        setSpinner(country_residence_spinner,country_res);
        setSpinner(country_citizenship,country_citi);
        setSpinner(height_spinner,height);
*/
    }

    /*@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    *//*public  void setSpinner(Spinner spinner,List<String> categories)
    {
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }*/

    private void setUpEditTextClickListner()
    {
        EditText ed_city = findViewById(R.id.city_ed);
        EditText ed_state = findViewById(R.id.state_ed);
        EditText ed_city_of_residence = findViewById(R.id.country_of_residence_ed);
        EditText ed_city_of_citizenship = findViewById(R.id.country_of_citizenship_ed);
        EditText ed_height = findViewById(R.id.height_ed);

        ed_city.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });


        ed_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });
        ed_city_of_residence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });
        ed_city_of_citizenship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });
        ed_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });
    }

    private void setUpTextWatcher()
    {
        ((EditText)findViewById(R.id.editText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setUpCustomSpinner(final View view)
    {
        ListView listView = findViewById(R.id.listView);
        findViewById(R.id.view).setVisibility(View.VISIBLE);
        findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideCustomSpinnerLayout();
            }
        });

        if (view.getId()==R.id.city_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Search City");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,getResources().getStringArray(R.array.city));
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.state_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Search State");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,getResources().getStringArray(R.array.state));
            listView.setAdapter(arrayAdapter);
        }
        if (view.getId()==R.id.country_of_residence_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Search Country residence");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,getResources().getStringArray(R.array.CountryofResidence));
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.country_of_citizenship_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Search Country Citizenship");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,getResources().getStringArray(R.array.CountryofCitizenship));
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.height_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Search height");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,getResources().getStringArray(R.array.Height));
            listView.setAdapter(arrayAdapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                TextView textView = v.findViewById(R.id.textView);
                if (textView!=null)
                {
                    Log.e("selectedIs",textView.getText().toString());
                    ((EditText)view).setText(textView.getText().toString());

                }
                hideCustomSpinnerLayout();
            }
        });

        findViewById(R.id.includeContainer).animate().translationX(0).setDuration(500);
    }

    private void hideCustomSpinnerLayout() {
        int i = findViewById(R.id.includeContainer).getWidth();
        findViewById(R.id.includeContainer).animate().translationX(i).setDuration(500);
        findViewById(R.id.view).setVisibility(View.GONE);
    }
}

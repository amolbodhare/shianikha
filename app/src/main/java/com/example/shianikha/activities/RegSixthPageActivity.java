package com.example.shianikha.activities;

import android.content.Context;
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

import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.Session;
import com.example.shianikha.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class RegSixthPageActivity extends AppCompatActivity {

    Button btn_register;
    private ArrayAdapter<String> arrayAdapter;

    Context context;
    ArrayList<String> pref_match_marital_status_arrayList;
    ArrayList<String> ethinicity_arrayList;
    ArrayList<String> min_education_arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_sixth_page);
        btn_register=findViewById(R.id.btn_register);
        context=RegSixthPageActivity.this;

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(RegSixthPageActivity.this, OTPVereificationActivty.class);
                startActivity(i);
            }
        });


        pref_match_marital_status_arrayList=new ArrayList<>();
        ethinicity_arrayList=new ArrayList<>();
        min_education_arrayList=new ArrayList<>();

        try
        {

            //String masterdatajsonstring=getIntent().getExtras().getString("masterDataString");
            String masterdatajsonstring = new Session(context).getString(com.example.shianikha.commen.P.masterDataString);

            //System.out.print(masterdatajsonstring);
            Json json=new Json(masterdatajsonstring);

            JSONArray jsonArray_marital_status=json.getJsonArray("marital_status");
            JSONArray jsonArray_ethinicity=json.getJsonArray("ethnicity");
            JSONArray jsonArray_min_education=json.getJsonArray("education");



            for(int i=0;i<jsonArray_marital_status.length();i++)
            {
                pref_match_marital_status_arrayList.add(jsonArray_marital_status.getJSONObject(i).getString("val"));
            }

            for(int i=0;i<jsonArray_ethinicity.length();i++)
            {
                ethinicity_arrayList.add(jsonArray_ethinicity.getJSONObject(i).getString("name"));
            }


            for(int i=0;i<jsonArray_min_education.length();i++)
            {
                min_education_arrayList.add(jsonArray_min_education.getJSONObject(i).getString("name"));
            }

           /* for(int i=0;i<jsonArray_height.length();i++)
            {
                height_arrayList.add("Hello");
            }*/

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }



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
      setUpEditTextClickListner();
      setUpTextWatcher();
    }

    private void setUpEditTextClickListner()
    {
        EditText ed_pref_match_ed = findViewById(R.id.pref_match_ed);
        EditText ed_pref_match_ethinicity_ed = findViewById(R.id.pref_match_ethnicity_spinner_ed);
        EditText ed_min_edu = findViewById(R.id.pref_match_min_education_spinner_ed);
        EditText ed_min_age = findViewById(R.id.min_age_spinner_ed);
        EditText ed_max_age = findViewById(R.id.max_age_spinner_ed);


        ed_pref_match_ed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });

        ed_pref_match_ethinicity_ed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });

        ed_min_edu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });

        ed_min_age.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });

        ed_max_age.setOnClickListener(new View.OnClickListener()
        {
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

        if (view.getId()==R.id.pref_match_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Select Pref match");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,pref_match_marital_status_arrayList);
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.pref_match_ethnicity_spinner_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Select Ethinicity");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,ethinicity_arrayList);
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.pref_match_min_education_spinner_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Select Minimum Education");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,min_education_arrayList);
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.min_age_spinner_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Select Min age");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,getResources().getStringArray(R.array.state));
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.max_age_spinner_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Select Max age");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,getResources().getStringArray(R.array.state));
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

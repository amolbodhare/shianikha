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
import android.widget.Toast;

import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.Session;
import com.example.shianikha.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class RegThirdPageActivity extends AppCompatActivity
{
    Button btn_next;
    private ArrayAdapter<String> arrayAdapter;

    ArrayList<String> religion_arraylist;
    ArrayList<String> ethinicity_arraylist;
    ArrayList<String> country_arrayList;
    ArrayList<String> current_occupation_arraylist;
    ArrayList<String> highest_level_ediucation_arraylist;
    Context context;
    Json reg_json;

    EditText ed_religion;
    EditText ed_ethinicity;
    EditText ed_fathers_city;
    EditText ed_mothers_city;
    EditText ed_current_occupation;
    EditText ed_highest_level_edu;
    EditText ed_other_detaiils_occupation ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_third_page);
        btn_next=(Button)findViewById(R.id.btn_next);
        context=RegThirdPageActivity.this;

        ed_other_detaiils_occupation = findViewById(R.id.other_details_occu_tiet);
        /*Spinner religion_spinner = (Spinner) findViewById(R.id.religion_spinner);
        Spinner ethinicity = (Spinner) findViewById(R.id.Ethincity_spinner);
        Spinner fathers_city_spinner = (Spinner) findViewById(R.id.fathers_city_spinner);
        Spinner mothers_city_spinner = (Spinner) findViewById(R.id.mothers_city_spinner);
        Spinner current_occupation_spinner = (Spinner) findViewById(R.id.current_occupation);
        Spinner height_level_edu_spinnner = (Spinner) findViewById(R.id.higest_level_edu_spinner);
*/

        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(RegThirdPageActivity.this,RegFourthPageActivity.class);
                startActivity(i);

      /*  if(validation())
        {
            reg_json.addString("religion", ed_religion.getTag().toString());
            reg_json.addString("ethnicity", ed_ethinicity.getTag().toString());
            reg_json.addString("father_city",ed_fathers_city.getTag().toString() );
            reg_json.addString("mother_city",ed_mothers_city.getTag().toString() );
            reg_json.addString("occupation_id",ed_current_occupation.getTag().toString());
            reg_json.addString("about_occupation",ed_other_detaiils_occupation.getText().toString());
            reg_json.addString("edulevel_id",ed_highest_level_edu.getTag().toString());


            //String city=reg_json.getString("city");
            //String state=reg_json.getString("state");

            //Toast.makeText(context, reg_json.getString("religion"), Toast.LENGTH_SHORT).show();

            new Session(context).addString("reg_data",reg_json.toString());
            startActivity(i);
            //finish();
        }*/
    }
});
        setUpEditTextClickListner();
        setUpTextWatcher();

        religion_arraylist=new ArrayList<>();
        ethinicity_arraylist=new ArrayList<>();
        country_arrayList=new ArrayList<>();
        current_occupation_arraylist=new ArrayList<>();
        highest_level_ediucation_arraylist=new ArrayList<>();

        try
        {

            //String masterdatajsonstring=getIntent().getExtras().getString("masterDataString");
            String masterdatajsonstring = new Session(context).getString(com.example.shianikha.commen.P.masterDataString);

            String regdatajsonstring = new Session(context).getString("reg_data");

            //System.out.print(masterdatajsonstring);
            Json master_json=new Json(masterdatajsonstring);
            reg_json=new Json(regdatajsonstring);

            JSONArray jsonArray_religion=master_json.getJsonArray("religion");
            JSONArray jsonArray_ethnicity=master_json.getJsonArray("ethnicity");
            JSONArray jsonArray_country=master_json.getJsonArray("country");
            JSONArray jsonArray_occupation=master_json.getJsonArray("occupation");
            JSONArray jsonArray_education=master_json.getJsonArray("education");


            for(int i=0;i<jsonArray_religion.length();i++)
            {
                religion_arraylist.add(jsonArray_religion.getJSONObject(i).getString("name"));
            }

            for(int i=0;i<jsonArray_ethnicity.length();i++)
            {
                ethinicity_arraylist.add(jsonArray_ethnicity.getJSONObject(i).getString("name"));
            }

            for(int i=0;i<jsonArray_country.length();i++)
            {
                country_arrayList.add(jsonArray_country.getJSONObject(i).getString("name"));
            }

            for(int i=0;i<jsonArray_occupation.length();i++)
            {
                current_occupation_arraylist.add(jsonArray_occupation.getJSONObject(i).getString("name"));
            }

            for(int i=0;i<jsonArray_education.length();i++)
            {
                highest_level_ediucation_arraylist.add(jsonArray_education.getJSONObject(i).getString("name"));;
            }

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        /*religion_spinner.setOnItemSelectedListener(this);
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
*/
    }

    /*@Override
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

    }*/

    private void setUpEditTextClickListner()
    {
         ed_religion = findViewById(R.id.religion_ed);
         ed_ethinicity = findViewById(R.id.ethinicity_ed);
         ed_fathers_city = findViewById(R.id.fathers_city_ed);
         ed_mothers_city = findViewById(R.id.mothers_city_ed);
         ed_current_occupation = findViewById(R.id.current_occupation_ed);


         ed_highest_level_edu = findViewById(R.id.higest_level_edu_ed);
         ed_religion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });


        ed_ethinicity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });
        ed_fathers_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });
        ed_mothers_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });
        ed_current_occupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });
        ed_highest_level_edu.setOnClickListener(new View.OnClickListener() {
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

        if (view.getId()==R.id.religion_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Select Religion");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,religion_arraylist);
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.ethinicity_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Ethinicity");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,ethinicity_arraylist);
            listView.setAdapter(arrayAdapter);
        }
        if (view.getId()==R.id.fathers_city_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Father's city/country of origin");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,country_arrayList);
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.mothers_city_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Mother's city/country of origin");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,country_arrayList);
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.current_occupation_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Current Occupation");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,current_occupation_arraylist);
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.higest_level_edu_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Highest level of education");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,highest_level_ediucation_arraylist);
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
                    ((EditText)view).setTag(position);

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
   /* public  boolean validation()
    {
        if(ed_religion.getText().toString().trim().equalsIgnoreCase(""))
        {
            ed_religion.setError("Select the City");

            return false;
        }

        else if(ed_ethinicity.getText().toString().trim().equalsIgnoreCase(""))
        {
            ed_ethinicity.setError("Select the ethincity");
            return false;
        }
        else if(ed_fathers_city.getText().toString().trim().equalsIgnoreCase(""))
        {
            ed_fathers_city.setError("Select the father's city");
            return false;
        }
        else if(ed_mothers_city.getText().toString().trim().equalsIgnoreCase(""))
        {
            ed_mothers_city.setError("Select the mothers city");
            return false;
        }
        else if(ed_current_occupation.getText().toString().trim().equalsIgnoreCase(""))
        {
            ed_current_occupation.setError("Select the country of citizenship");
            return false;
        }
        else if(ed_other_detaiils_occupation.getText().toString().trim().equalsIgnoreCase(""))
        {
            ed_other_detaiils_occupation.setError("Enter the occupation");
            return false;
        }
        else if(ed_highest_level_edu.getText().toString().trim().equalsIgnoreCase(""))
        {
            ed_highest_level_edu.setError("Enter the occupation");
            return false;
        }

        return  true;
    }*/
}

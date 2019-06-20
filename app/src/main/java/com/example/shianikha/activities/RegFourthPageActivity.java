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

public class RegFourthPageActivity extends AppCompatActivity
{
    Button btn_next;
    private ArrayAdapter<String> arrayAdapter;

    ArrayList<String> language_arraylist;
    ArrayList<String> smoking_arraylist;

    Context context;
    private long l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_fourth_page);
        context=RegFourthPageActivity.this;

        /*Spinner lang_spinner = (Spinner) findViewById(R.id.lang_spinner);
        Spinner habbit_spinner = (Spinner) findViewById(R.id.habbit_spinner);*/

        btn_next=(Button)findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(RegFourthPageActivity.this, RegFifthPageActivity.class);
                startActivity(i);
                finish();
            }
        });

        setUpEditTextClickListner();
        setUpTextWatcher();

        language_arraylist=new ArrayList<>();
        smoking_arraylist=new ArrayList<>();

        try
            {

            //String masterdatajsonstring=getIntent().getExtras().getString("masterDataString");
            String masterdatajsonstring = new Session(context).getString(com.example.shianikha.commen.P.masterDataString);

         //System.out.print(masterdatajsonstring);
            Json json=new Json(masterdatajsonstring);

            JSONArray jsonArray_language=json.getJsonArray("mothertongue");
            JSONArray jsonArray_smoking=json.getJsonArray("smoking");



            for(int i=0;i<jsonArray_language.length();i++)
            {
                language_arraylist.add(jsonArray_language.getJSONObject(i).getString("name"));
            }

            for(int i=0;i<jsonArray_smoking.length();i++)
            {
                smoking_arraylist.add(jsonArray_smoking.getJSONObject(i).getString("val"));
            }

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }



        /*lang_spinner.setOnItemSelectedListener(this);
        habbit_spinner.setOnItemSelectedListener(this);*/



        /*// Spinner Drop down elements
        List<String> cities = new ArrayList<String>();
        List<String> states = new ArrayList<String>();


        cities.add("Select languages");
        cities.add("English");


        states.add("Smoking");
        states.add("Drinking");


        setSpinner(lang_spinner,cities);
        setSpinner(habbit_spinner,states);*/

    }

   /* @Override
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
       EditText ed_lang = findViewById(R.id.lang_ed);
       EditText ed_habbit = findViewById(R.id.habbit_ed);

       ed_lang.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {
               setUpCustomSpinner(v);
           }
       });

       ed_habbit.setOnClickListener(new View.OnClickListener()
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

        if (view.getId()==R.id.lang_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Select Religion");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,language_arraylist);
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.habbit_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Ethinicity");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,smoking_arraylist);
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

    private void hideCustomSpinnerLayout()
    {
        int i = findViewById(R.id.includeContainer).getWidth();
        findViewById(R.id.includeContainer).animate().translationX(i).setDuration(500);
        findViewById(R.id.view).setVisibility(View.GONE);
    }
}


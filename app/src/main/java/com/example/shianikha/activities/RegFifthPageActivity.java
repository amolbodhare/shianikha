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

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.example.shianikha.R;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class RegFifthPageActivity extends AppCompatActivity
{

    Button btn_next;
    private ArrayAdapter<String> arrayAdapter;
    ArrayList<String> seeking_marriage_arrayList;
    ArrayList<String> interested_in_arraylist;
    Context context;
    Json reg_json;
    private  LoadingDialog loadingDialog;
    EditText ed_seeking_marriage;
    EditText ed_interesterd_in;
    EditText ed_religion_exception;
    EditText ed_about_one;
    EditText ed_about_two;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_fifth_page);
        context=RegFifthPageActivity.this;
        loadingDialog=new LoadingDialog(this);

       /* Spinner spinner_seeking_marriage = (Spinner) findViewById(R.id.seeking_marriage);
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
*/

        btn_next=(Button)findViewById(R.id.btn_next);

        ed_religion_exception=findViewById(R.id.religious_exception_ed);
        ed_about_one=findViewById(R.id.about_one_ed);
        ed_about_two=findViewById(R.id.about_two_ed);



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                /*Intent i=new Intent(RegFifthPageActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
*/
                hitRegisterApi();
               /* if(validation())
                {
                    hitRegisterApi();
                }*/


            }
        });

        setUpEditTextClickListner();
        setUpTextWatcher();

        seeking_marriage_arrayList=new ArrayList<>();
        interested_in_arraylist=new ArrayList<>();

        try
        {

            //String masterdatajsonstring=getIntent().getExtras().getString("masterDataString");
            String masterdatajsonstring = new Session(context).getString(com.example.shianikha.commen.P.masterDataString);
            String regdatajsonstring = new Session(context).getString("reg_data");
            //System.out.print(masterdatajsonstring);
            Json json=new Json(masterdatajsonstring);
            reg_json=new Json(regdatajsonstring);

            //System.out.print(masterdatajsonstring);



            JSONArray jsonArray_seeking_marriage=json.getJsonArray("seeking_marriage");

            JSONArray jsonArray_intreasted_in=json.getJsonArray("intreasted_in");



            for(int i=0;i<jsonArray_seeking_marriage.length();i++)
            {
                seeking_marriage_arrayList.add(jsonArray_seeking_marriage.getJSONObject(i).getString("val"));
            }


            for(int i=0;i<jsonArray_intreasted_in.length();i++)
            {
                interested_in_arraylist.add(jsonArray_intreasted_in.getJSONObject(i).getString("name"));
            }

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void setUpEditTextClickListner()
    {
         ed_seeking_marriage = findViewById(R.id.seeking_marriage_ed);
         ed_interesterd_in = findViewById(R.id.interesterd_in_ed);

        ed_seeking_marriage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });

        ed_interesterd_in.setOnClickListener(new View.OnClickListener()
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
            public void afterTextChanged(Editable s)
            {

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

        if (view.getId()==R.id.seeking_marriage_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Select value");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,seeking_marriage_arrayList);
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.interesterd_in_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Interested In");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,interested_in_arraylist);
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
    private void hitRegisterApi()
    {

        reg_json.addString("seeking_marriage",ed_seeking_marriage.getTag().toString());
        reg_json.addString("intreasted_in",ed_interesterd_in.getTag().toString());
        reg_json.addString("religion_expectations",ed_religion_exception.getText().toString());
        reg_json.addString("about_1",ed_about_one.getText().toString());
        reg_json.addString("about_2",ed_about_two.getText().toString());

        //added but not taken from the user

        reg_json.addString("password","password@123");
        reg_json.addString("fcm_token","FCMToken");



        RequestModel requestModel = RequestModel.newRequestModel("register");
        requestModel.addJSON(P.data, reg_json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(RegFifthPageActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener()
                {
                    @Override
                    public void onSuccess(Json json)
                    {
                        if (json.getInt(P.status) == 1)
                        {
                            //JsonList jsonList=json.getJsonList("data");
                            //Json logged_in_json=jsonList.get(0);
                           // new Session(context).addString("loggedinjsonstring",logged_in_json.toString());
                            Intent i=new Intent(RegFifthPageActivity.this,HomeActivity.class);
                            H.showMessage(RegFifthPageActivity.this, json.getString(P.msg));
                            //i.putExtra("email",reg_json.getString("email"));
                            //i.putExtra("pass",reg_json.getString("password"));
                            //i.putExtra("registered","1");

                            startActivity(i);
                            //finish();
                        } else
                            H.showMessage(RegFifthPageActivity.this, json.getString(P.msg));
                    }
                }).onLoading(new Api.OnLoadingListener() {
            @Override
            public void onLoading(boolean isLoading)
            {
                if (isLoading)
                    loadingDialog.show("Please wait...");
                else
                    loadingDialog.dismiss();
            }
        })
                .run("hitLoginApi");
    }
   /* public  boolean validation()
    {
        if(ed_seeking_marriage.getText().toString().trim().equalsIgnoreCase(""))
        {
            ed_seeking_marriage.setError("Select the Seeking marriage");

            return false;
        }

        else if(ed_interesterd_in.getText().toString().trim().equalsIgnoreCase(""))
        {
            ed_interesterd_in.setError("Select the interested in");
            return false;
        }
        else if(ed_religion_exception.getText().toString().trim().equalsIgnoreCase(""))
        {
            ed_religion_exception.setError("Enter the religious exception");
            return false;
        }
        else if(ed_about_one.getText().toString().trim().equalsIgnoreCase(""))
        {
            ed_about_one.setError("Enter about one");
            return false;
        }
        else if(ed_about_two.getText().toString().trim().equalsIgnoreCase(""))
        {
            ed_about_two.setError("Enter about two");
            return false;
        }

        return  true;
    }*/
}

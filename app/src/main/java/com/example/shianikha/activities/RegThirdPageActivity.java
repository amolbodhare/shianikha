package com.example.shianikha.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.Session;
import com.example.App;
import com.example.shianikha.R;
import com.example.shianikha.commen.P;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class RegThirdPageActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> educationNameList, educationCodeList, occupationNameList, occupationCodeList, countryNameList, countryIdList, cityNameList, cityIdList,
            languageNameList, languageIdList,stateeNameList, stateIdList;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_reg_third_page);

        session = new Session(this);

        findViewById(R.id.educationEditText).setOnClickListener(this);
        findViewById(R.id.occupationEditText).setOnClickListener(this);
        findViewById(R.id.countryEditText).setOnClickListener(this);
        findViewById(R.id.stateEditText).setOnClickListener(this);
        findViewById(R.id.cityEditText).setOnClickListener(this);
        findViewById(R.id.motherTongueEditText).setOnClickListener(this);
        findViewById(R.id.languageEditText).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.view).setOnClickListener(this);

        setMarginTopOfCustomSpinner();
        extractRequireList();
    }

    private void extractRequireList() {
        JsonList jsonList;

        //for education
        String string = session.getString(P.education);
        if (string != null) {
            jsonList = new JsonList(string);
            educationCodeList = new ArrayList<>();
            educationNameList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                educationNameList.add(string);

                string = j.getString(P.id);
                educationCodeList.add(string);
            }
        }

        //for occupation
        string = session.getString(P.occupation);
        if (string != null) {
            jsonList = new JsonList(string);
            occupationNameList = new ArrayList<>();
            occupationCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                occupationNameList.add(string);

                string = j.getString(P.id);
                occupationCodeList.add(string);
            }
        }

        //for country
        string = session.getString(P.country);
        if (string != null) {
            jsonList = new JsonList(string);
            countryNameList = new ArrayList<>();
            countryIdList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                countryNameList.add(string);

                string = j.getString(P.id);
                countryIdList.add(string);
            }
        }

        //for state
        string = session.getString(P.state);
        if (string != null) {
            jsonList = new JsonList(string);
             stateeNameList= new ArrayList<>();
            stateIdList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.state_name);
                stateeNameList.add(string);

                string = j.getString(P.state_id);
                stateIdList.add(string);
            }
        }


        //for city
        string = session.getString(P.city);
        if (string != null) {
            jsonList = new JsonList(string);
            cityNameList = new ArrayList<>();
            cityIdList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.city_name);
                cityNameList.add(string);

                string = j.getString(P.city_id);
                cityIdList.add(string);
            }
        }

        //for language
        string = session.getString(P.language);
        if (string != null) {
            jsonList = new JsonList(string);
            languageNameList = new ArrayList<>();
            languageIdList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                languageNameList.add(string);

                string = j.getString(P.id);
                languageIdList.add(string);
            }
        }

        setUpTextWatcher();
    }

    private void setUpTextWatcher() {
        ((EditText) findViewById(R.id.editText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (arrayAdapter != null)
                    arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setUpCustomSpinner(final View view) {
        ListView listView = findViewById(R.id.listView);
        findViewById(R.id.view).setVisibility(View.VISIBLE);

        if (view.getId() == R.id.occupationEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search Occupation");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, occupationNameList);
        } else if (view.getId() == R.id.educationEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search education");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, educationNameList);
        } else if (view.getId() == R.id.countryEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search country");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, countryNameList);
        }else if (view.getId() == R.id.stateEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search state");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, stateeNameList);
        } else if (view.getId() == R.id.cityEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search city");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, cityNameList);
        } else if (view.getId() == R.id.motherTongueEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search mother tongue");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, languageNameList);
        } else if (view.getId() == R.id.languageEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search language");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, languageNameList);
        }

        if (arrayAdapter == null)
            return;

        H.showKeyBoard(this, findViewById(R.id.editText));

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView textView = v.findViewById(R.id.textView);
                if (textView != null)
                {
                    Log.e("selectedIs", textView.getText().toString());
                    String string = textView.getText().toString().trim();
                    ((EditText) view).setText(string);
                    if (string.equalsIgnoreCase("other") || string.equalsIgnoreCase("others"))
                        showOtherEditText(view, true);
                    else
                        showOtherEditText(view, false);
                }
                hideCustomSpinnerLayout();
            }
        });

        findViewById(R.id.includeContainer).animate().translationX(0).setDuration(500);
    }

    private void showOtherEditText(View view, boolean b)
    {
        RelativeLayout relativeLayout = (RelativeLayout)view.getParent();
        LinearLayout linearLayout = (LinearLayout)relativeLayout.getParent();

        int i = linearLayout.indexOfChild(relativeLayout);
        View v = linearLayout.getChildAt(i+1);
        if (v instanceof TextInputLayout)
        {
            if (b)
                v.setVisibility(View.VISIBLE);
            else
                v.setVisibility(View.GONE);
        }
    }

    private void hideCustomSpinnerLayout() {
        int i = findViewById(R.id.includeContainer).getWidth();
        ((EditText) findViewById(R.id.editText)).setText("");
        findViewById(R.id.includeContainer).animate().translationX(i).setDuration(500);
        View view = findViewById(R.id.view);
        view.setVisibility(View.GONE);
        H.hideKeyBoard(this, view);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button)

            makeJson();
        else if (v.getId() == R.id.view)
            hideCustomSpinnerLayout();
        else
            setUpCustomSpinner(v);
    }

    private void makeJson() {


        EditText editText = findViewById(R.id.educationEditText);
        String string = editText.getText().toString();

        if (string.isEmpty())
        {
            H.showMessage(this, "Please select education");
            return;
        }
        else if(string.equalsIgnoreCase("others")||string.equalsIgnoreCase("other"))
        {
            String stringg="";

            stringg = ((EditText)findViewById(R.id.other_education_details_edt)).getText().toString();
            if (stringg.isEmpty())
            {
                H.showMessage(this,"Please enter other details about education");
                return;
            }
            App.masterJson.addString(P.other_edulevel, stringg);

        }
        int i = educationNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.edu_level, educationCodeList.get(i));



         editText = findViewById(R.id.occupationEditText);
         string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this, "Please select occupation");
            return;
        }
        else if(string.equalsIgnoreCase("others"))
        {
            String stringg="";

            stringg = ((EditText)findViewById(R.id.other_details_occupation_edt)).getText().toString();
            if (stringg.isEmpty())
            {
                H.showMessage(this,"Please enter other details about occupation");
                return;
            }
            App.masterJson.addString(P.about_occupation, stringg);

        }
         i = occupationNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.occupation_id, occupationCodeList.get(i));



        string = ((EditText)findViewById(R.id.res_add_ed)).getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter residential address");
            return;
        }
        App.masterJson.addString(P.residency_address, string);

        editText = findViewById(R.id.countryEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select country");
            return;
        }
        i = countryNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.country_res, countryIdList.get(i));

        editText = findViewById(R.id.stateEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this, "Please select state");
            return;
        }
        i = stateeNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.state, stateIdList.get(i));

        editText = findViewById(R.id.cityEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select city");
            return;
        }
        i = cityNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.city, cityIdList.get(i));

        editText = findViewById(R.id.motherTongueEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select Mothertongue");
            return;
        }
        i = languageNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.mother_tongue, languageIdList.get(i));


        editText = findViewById(R.id.languageEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select language");
            return;
        }
        i = languageNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.language, languageIdList.get(i));




        H.log("masterJsonIs", App.masterJson.toString());
        startActivity(new Intent(this, RegFourthPageActivity.class));
    }

    private void setMarginTopOfCustomSpinner() {
        LinearLayout linearLayout = findViewById(R.id.includeContainer);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        int i = new Session(this).getInt(P.statusBarHeight);
        layoutParams.topMargin = i;
        H.log("heightIs", i + "");
        linearLayout.setLayoutParams(layoutParams);
    }
}

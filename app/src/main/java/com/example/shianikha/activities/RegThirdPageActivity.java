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
    private ArrayList<String> religionNameList, religionCodeList, ethinicityNameList, ethinicityCodeList, cityNameList, cityCodeList;
    private ArrayList<String> occupationNameList, occupationCodeList, educationNameList, educationCodeList;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_third_page);

        session = new Session(this);

        findViewById(R.id.religionEditText).setOnClickListener(this);
        findViewById(R.id.ethnicityEditText).setOnClickListener(this);
        findViewById(R.id.motherCityEditText).setOnClickListener(this);
        findViewById(R.id.fatherCityEditText).setOnClickListener(this);
        findViewById(R.id.occupationEditText).setOnClickListener(this);
        findViewById(R.id.educationEditText).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.view).setOnClickListener(this);

        setMarginTopOfCustomSpinner();
        extractRequireList();
    }

    private void extractRequireList() {
        JsonList jsonList;

        //for religion
        String string = session.getString(P.religion);
        if (string != null) {
            jsonList = new JsonList(string);
            religionNameList = new ArrayList<>();
            religionCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                religionNameList.add(string);

                string = j.getString(P.id);
                religionCodeList.add(string);
            }
        }

        //for ethnicity
        string = session.getString(P.ethnicity);
        if (string != null) {
            jsonList = new JsonList(string);
            ethinicityCodeList = new ArrayList<>();
            ethinicityNameList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                ethinicityNameList.add(string);

                string = j.getString(P.id);
                ethinicityCodeList.add(string);
            }
        }

        //for city
        string = session.getString(P.city);
        if (string != null) {
            jsonList = new JsonList(string);
            cityNameList = new ArrayList<>();
            cityCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.city_name);
                cityNameList.add(string);

                string = j.getString(P.city_id);
                cityCodeList.add(string);
            }
        }

        //for city
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

        //for education
        string = session.getString(P.education);
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
        setUpTextWatcher();
    }

    private void setUpTextWatcher() {
        ((EditText) findViewById(R.id.editText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (arrayAdapter != null)
                    arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void setUpCustomSpinner(final View view) {
        ListView listView = findViewById(R.id.listView);
        findViewById(R.id.view).setVisibility(View.VISIBLE);

        if (view.getId() == R.id.religionEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search Religion");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, religionNameList);
        } else if (view.getId() == R.id.ethnicityEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Ethnicity");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, ethinicityNameList);
        }
        if (view.getId() == R.id.fatherCityEditText)
        {
            ((EditText) findViewById(R.id.editText)).setHint("Search Father's City");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, cityNameList);
        } else if (view.getId() == R.id.motherCityEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search Mother's city");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, cityNameList);
        } else if (view.getId() == R.id.occupationEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search Occupation");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, occupationNameList);
        } else if (view.getId() == R.id.educationEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search education");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, educationNameList);
        }

        if (arrayAdapter == null)
            return;

        H.showKeyBoard(this, findViewById(R.id.editText));

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView textView = v.findViewById(R.id.textView);
                if (textView != null) {
                    Log.e("selectedIs", textView.getText().toString());
                    ((EditText) view).setText(textView.getText().toString());
                }
                hideCustomSpinnerLayout();
            }
        });

        findViewById(R.id.includeContainer).animate().translationX(0).setDuration(500);
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

    private void makeJson()
    {
        EditText editText = findViewById(R.id.religionEditText);
        String string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select religion name");
            return;
        }
        int i = religionNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.religion, religionCodeList.get(i));

        editText = findViewById(R.id.ethnicityEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select ethnicity name");
            return;
        }
        i = ethinicityNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.ethnicity, ethinicityCodeList.get(i));

        editText = findViewById(R.id.fatherCityEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select father's city");
            return;
        }
        i = cityNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.father_city, cityCodeList.get(i));

        editText = findViewById(R.id.motherCityEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select mother's city");
            return;
        }
        i = cityNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.mother_city, cityCodeList.get(i));

        editText = findViewById(R.id.occupationEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select occupation");
            return;
        }
        i = occupationNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.occupation_id, occupationCodeList.get(i));

        string = ((EditText) findViewById(R.id.occupationDetailsEditText)).getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please specify occupation details");
            return;
        }
        App.masterJson.addString(P.about_occupation, string);

        editText = findViewById(R.id.educationEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select higher education");
            return;
        }
        i = educationNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.edulevel_id, educationCodeList.get(i));

        H.log("masterJsonIs",App.masterJson.toString());
        //startActivity(new Intent(this,RegThirdPageActivity.class));
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

package com.example.shianikha.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.Session;
import com.example.App;
import com.example.shianikha.R;
import com.example.shianikha.commen.P;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RegSecondPageActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayAdapter<String> arrayAdapter;

    private ArrayList<String> cityNameList, cityCodeList, stateNameList, stateCodeList, countryNameList, countryCodeList, heightList;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_reg_second_page);

        session = new Session(this);

        findViewById(R.id.cityEditText).setOnClickListener(this);
        findViewById(R.id.stateEditText).setOnClickListener(this);
        findViewById(R.id.countryEditText1).setOnClickListener(this);
        findViewById(R.id.countryEditText2).setOnClickListener(this);
        findViewById(R.id.heightEditText).setOnClickListener(this);
        findViewById(R.id.dateOfBirthEditText).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.view).setOnClickListener(this);

        setMarginTopOfCustomSpinner();
        extractRequireList();
    }

    private void extractRequireList() {
        JsonList jsonList;

        //for city
        String string = session.getString(P.city);
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

        //for state
        string = session.getString(P.state);
        if (string != null) {
            jsonList = new JsonList(string);
            stateNameList = new ArrayList<>();
            stateCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.state_name);
                stateNameList.add(string);

                string = j.getString(P.state_id);
                stateCodeList.add(string);
            }
        }

        //for country
        string = session.getString(P.country);
        if (string != null) {
            jsonList = new JsonList(string);
            countryNameList = new ArrayList<>();
            countryCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                countryNameList.add(string);

                string = j.getString(P.id);
                countryCodeList.add(string);
            }
        }

        //for height
        string = session.getString(P.height);
        if (string != null) {
            try {
                JSONArray jsonArray = new JSONArray(string);
                heightList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    string = jsonArray.getString(i);
                    heightList.add(string);
                }

            } catch (JSONException e) {
                e.printStackTrace();
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

        if (view.getId() == R.id.cityEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search City");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, cityNameList);
        } else if (view.getId() == R.id.stateEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search State");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, stateNameList);
        } else if (view.getId() == R.id.countryEditText1 || view.getId() == R.id.countryEditText2) {
            ((EditText) findViewById(R.id.editText)).setHint("Search Country residence");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, countryNameList);
        } else if (view.getId() == R.id.heightEditText)
        {   EditText editText = findViewById(R.id.editText);
            editText.setHint("Search height");
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, heightList);
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

        if (v.getId() == R.id.dateOfBirthEditText)
            handleDatePicker();
        else if (v.getId() == R.id.button)
            makeJson();
        else if (v.getId() == R.id.view)
            hideCustomSpinnerLayout();
        else
            setUpCustomSpinner(v);
    }

    private void makeJson() {
        App.masterJson.addString(P.token_id, session.getString(P.tokenData));
        H.log("tokenIs",session.getString(P.tokenData));

        EditText editText = findViewById(R.id.cityEditText);
        String string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select city name");
            return;
        }
        int i = cityNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.city, cityCodeList.get(i));

        editText = findViewById(R.id.stateEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select state name");
            return;
        }
        i = stateNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.state, stateCodeList.get(i));

        editText = findViewById(R.id.countryEditText1);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select country of residence");
            return;
        }
        i = countryNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.country_res, countryCodeList.get(i));

        editText = findViewById(R.id.countryEditText2);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select country of citizenship");
            return;
        }
        i = countryNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.country_citizen, countryCodeList.get(i));

        editText = findViewById(R.id.dateOfBirthEditText);
        string = editText.getTag().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select date of birth");
            return;
        }
        App.masterJson.addString(P.dob, string);

        editText = findViewById(R.id.heightEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select height");
            return;
        }
        App.masterJson.addString(P.height, string);
        H.log("masterJsonIs",App.masterJson.toString());
        startActivity(new Intent(this,RegThirdPageActivity.class));
    }

    private void handleDatePicker() {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };

        DatePickerDialog datePickerDialog =  new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setInverseBackgroundForced(false);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    private void updateLabel(Calendar calendar) {
        String myFormat = "dd MMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        EditText editText = findViewById(R.id.dateOfBirthEditText);
        editText.setText(sdf.format(calendar.getTime()));

        myFormat = "yyyy-MM-dd";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setTag(sdf.format(calendar.getTime()));
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

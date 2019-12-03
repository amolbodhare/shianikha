package com.nikha.shianikha.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Session;
import com.nikha.App;
import com.nikha.shianikha.R;
import com.nikha.shianikha.commen.CommonListHolder;
import com.nikha.shianikha.commen.P;

import org.json.JSONArray;


public class RegFifthPageActivity extends AppCompatActivity implements View.OnClickListener, LocationListener, CompoundButton.OnCheckedChangeListener {

    private ArrayAdapter<String> arrayAdapter;
    private Session session;

    private String lat = "", longs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_reg_fifth_page);

        session = new Session(this);

        setMarginTopOfCustomSpinner();
        setUpTextWatcher();

        findViewById(R.id.button).setOnClickListener(this);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.e("returnStatus", "return");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
        } else
            Log.e("permission_status", "not granted");
        setRequiredOnClickListener();
        makeCheckBoxInRunTime();
    }

    private void makeCheckBoxInRunTime() // making all check box of interest/hobby
    {
        int n = CommonListHolder.intrestedInNameList.size();
        H.log("nis", n + "");
        int i = n % 2 + n / 2;
        H.log("iIs", i + "");

        LinearLayout linearLayout = findViewById(R.id.checkBoxContainer1);
        for (int j = 0; j < n; j++) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.inflatable_check_box, null);
            CheckBox checkBox = (CheckBox) ll.getChildAt(0);
            checkBox.setText(CommonListHolder.intrestedInNameList.get(j));
            checkBox.setTag(CommonListHolder.intrestedInIdList.get(j));
            checkBox.setOnCheckedChangeListener(this);

            linearLayout.addView(ll);

            if (j == i - 1)
                linearLayout = findViewById(R.id.checkBoxContainer2);
        }
    }

    private void setRequiredOnClickListener() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.view).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.relocateEditText).setOnClickListener(this);
        findViewById(R.id.seekingMarriageEditText).setOnClickListener(this);

        findViewById(R.id.ethnicityEditText).setOnClickListener(this);
        findViewById(R.id.fathersCountryEditText).setOnClickListener(this);
        findViewById(R.id.mothersCountryEditText).setOnClickListener(this);
        findViewById(R.id.highestLevelEduEditText).setOnClickListener(this);
        findViewById(R.id.smokingEditText).setOnClickListener(this);
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
        EditText editText = findViewById(R.id.editText);

        if (view.getId() == R.id.ethnicityEditText) {
            editText.setHint("Search Ethnicity");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.ethnicityNameList);
        } else if (view.getId() == R.id.fathersCountryEditText || view.getId() == R.id.mothersCountryEditText) {
            editText.setHint("Search Country");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.countryNameList);
        } else if (view.getId() == R.id.highestLevelEduEditText) {
            editText.setHint("Highest level of Education");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.educationNameList);
        } else if (view.getId() == R.id.smokingEditText) {
            editText.setHint("Smoking");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.smokingNameList);
        } else if (view.getId() == R.id.relocateEditText) {
            editText.setHint("Willing to relocate");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.relocateNameList);
        } else if (view.getId() == R.id.seekingMarriageEditText) {
            editText.setHint("Seeking Marriage");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.seekingForMarriageNameList);
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

    private void hideCustomSpinnerLayout() {
        int i = findViewById(R.id.includeContainer).getWidth();
        ((EditText) findViewById(R.id.editText)).setText("");
        findViewById(R.id.includeContainer).animate().translationX(i).setDuration(500);
        View view = findViewById(R.id.view);
        view.setVisibility(View.GONE);
        H.hideKeyBoard(this, view);
    }

    private void setMarginTopOfCustomSpinner() {
        LinearLayout linearLayout = findViewById(R.id.includeContainer);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        int i = new Session(this).getInt(P.statusBarHeight);
        layoutParams.topMargin = i;
        H.log("heightIs", i + "");
        linearLayout.setLayoutParams(layoutParams);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view)
            hideCustomSpinnerLayout();
        else if (v.getId() == R.id.button)
            makeJson();
        else
            setUpCustomSpinner(v);
    }

    private void makeJson() {

        EditText editText = findViewById(R.id.ethnicityEditText);
        String string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select Ethnicity");
            return;
        } else if (string.equalsIgnoreCase("others") || string.equalsIgnoreCase("other")) {
            String stringg = "";

            stringg = ((EditText) findViewById(R.id.other_ethincity_edittext)).getText().toString();
            if (stringg.isEmpty()) {
                H.showMessage(this, "Please enter other ethnicity details");
                return;
            }
            App.masterJson.addString(P.other_ethnicity, stringg);

        }
        int i = CommonListHolder.ethnicityNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.ethnicity, CommonListHolder.ethnicityIdList.get(i));


        editText = findViewById(R.id.fathersCountryEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select fathers country ");
            return;
        }
        i = CommonListHolder.countryNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.father_country, CommonListHolder.countryIdList.get(i));


        editText = findViewById(R.id.mothersCountryEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select mothers country ");
            return;
        }
        i = CommonListHolder.countryNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.mother_country, CommonListHolder.countryIdList.get(i));

        editText = findViewById(R.id.highestLevelEduEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select highest level of education");
            return;
        } else if (string.equalsIgnoreCase("others") || string.equalsIgnoreCase("other")) {
            String stringg = "";

            stringg = ((EditText) findViewById(R.id.other_education_edittext)).getText().toString();
            if (stringg.isEmpty()) {
                H.showMessage(this, "Please enter other education details");
                return;
            }
            App.masterJson.addString(P.other_edulevel, stringg);

        }
        i = CommonListHolder.educationNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.edulevel_id, CommonListHolder.educationIdList.get(i));


        editText = findViewById(R.id.smokingEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select smoking habbit ");
            return;
        }
        i = CommonListHolder.smokingNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.smoke_id, CommonListHolder.smokingIdList.get(i));


        editText = findViewById(R.id.relocateEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select relocation ");
            return;
        }
        i = CommonListHolder.relocateNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.relocate_id, CommonListHolder.relocateIdList.get(i));


        editText = findViewById(R.id.seekingMarriageEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select seeking marriage ");
            return;
        }
        i = CommonListHolder.seekingForMarriageNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.seeking_marriage, CommonListHolder.seekingForMarriageIdList.get(i));

        editText = findViewById(R.id.otherHobbyEditText);
        string = editText.getText().toString();
        App.masterJson.addString(P.other_intreasted_in, string);


        JSONArray jsonArray = addValueToJsonArray();
        App.masterJson.addJSONArray(P.intreasted_in, jsonArray);

        App.masterJson.addString(P.lat, lat);
        App.masterJson.addString(P.lng, longs);
        H.log("masterJsonIs", App.masterJson.toString());

        //hitRegisterDetailsApi();
        startActivity(new Intent(this, RegSixthPageActivity.class));
    }

    private JSONArray addValueToJsonArray() {
        JSONArray jsonArray = new JSONArray();

        LinearLayout linearLayout = findViewById(R.id.checkBoxContainer1);
        LinearLayout ll;
        CheckBox checkBox;
        String string = "";
        Object object;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ll = (LinearLayout) linearLayout.getChildAt(i);
            checkBox = (CheckBox) ll.getChildAt(0);
            if (checkBox.isChecked()) {
                object = checkBox.getTag();
                if (object != null)
                    string = object.toString();

                jsonArray.put(string);
            }
        }
        H.log("firstJsonArrayIs", jsonArray.toString());

        linearLayout = findViewById(R.id.checkBoxContainer2);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ll = (LinearLayout) linearLayout.getChildAt(i);
            checkBox = (CheckBox) ll.getChildAt(0);
            if (checkBox.isChecked()) {
                object = checkBox.getTag();
                if (object != null)
                    string = object.toString();

                jsonArray.put(string);
            }
        }

        return jsonArray;
    }


    private void showOtherEditText(View view, boolean b) {
        RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
        LinearLayout linearLayout = (LinearLayout) relativeLayout.getParent();

        int i = linearLayout.indexOfChild(relativeLayout);
        View v = linearLayout.getChildAt(i + 1);
        if (v instanceof TextInputLayout) {
            if (b)
                v.setVisibility(View.VISIBLE);
            else
                v.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (lat.isEmpty())
            lat = location.getLatitude() + "";
        if (longs.isEmpty())
            longs = location.getLongitude() + "";
        H.log("latLong",lat+"     "+longs+"");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        String string = compoundButton.getText().toString();
        if (string.equalsIgnoreCase("other"))
        {
            if (b)
                findViewById(R.id.otherHobbyInputLayout).setVisibility(View.VISIBLE);
            else
                findViewById(R.id.otherHobbyInputLayout).setVisibility(View.GONE);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        App.mPlayer.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        App.mPlayer.pause();
    }
}

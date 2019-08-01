package com.example.shianikha.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.example.App;
import com.example.shianikha.R;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;

import java.util.ArrayList;


public class RegFifthPageActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private ArrayAdapter<String> arrayAdapter;
    private Session session;
    private ArrayList<String> cityNameList,seekingMarriageList, cityIdList,seekingMarriageCodeList, stateNameList, stateIdList, countryNameList, ethnicityNameList,relocateNameList,relocateCodeList,educationCodeList,
            ethnicityIdList, occupationNameList, occupationIdList, educationNameList,countryCodeList,smokingNameList, smokingIdList,ethincityCodeList;
    private String lat= "",longs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_reg_fifth_page);

        session = new Session(this);

        //setRequiredOnClickListener();
        setMarginTopOfCustomSpinner();
        extractRequireList();

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
    }

    private void setRequiredOnClickListener() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.view).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.relocateEditText).setOnClickListener(this);
        findViewById(R.id.seekingMarriageEditText).setOnClickListener(this);
        //findViewById(R.id.cityOfResidenceEditText).setOnClickListener(this);
        //findViewById(R.id.stateEditText).setOnClickListener(this);
        //findViewById(R.id.countryOfResidenceEditText).setOnClickListener(this);
        //findViewById(R.id.countryOfCitizenshipEditText).setOnClickListener(this);
        findViewById(R.id.ethnicityEditText).setOnClickListener(this);
        findViewById(R.id.fathersCountryEditText).setOnClickListener(this);
        findViewById(R.id.mothersCountryEditText).setOnClickListener(this);
        //findViewById(R.id.currentOccupationEditText).setOnClickListener(this);
        findViewById(R.id.highestLevelEduEditText).setOnClickListener(this);
        findViewById(R.id.smokingEditText).setOnClickListener(this);
    }

    private void extractRequireList()
    {
        JsonList jsonList;

        //for city
        String string = session.getString(P.city);
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

        //for smoking
        string = session.getString(P.smoking);
        if (string != null) {
            jsonList = new JsonList(string);
            smokingNameList = new ArrayList<>();
            smokingIdList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                smokingNameList.add(string);

                string = j.getString(P.id);
                smokingIdList.add(string);
            }
        }

        //ethnicity
        string = session.getString(P.ethnicity);
        if (string != null) {
            jsonList = new JsonList(string);
            ethnicityNameList = new ArrayList<>();
            ethincityCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                ethnicityNameList.add(string);

                string = j.getString(P.id);
                ethincityCodeList.add(string);
            }
        }

        //for relocate
        string = session.getString(P.relocate);
        if (string != null) {
            jsonList = new JsonList(string);
            relocateNameList = new ArrayList<>();
            relocateCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                relocateNameList.add(string);

                string = j.getString(P.id);
                relocateCodeList.add(string);
            }
        }

        //education
        string = session.getString(P.education);
        if (string != null) {
            jsonList = new JsonList(string);
            educationNameList = new ArrayList<>();
            educationCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                educationNameList.add(string);

                string = j.getString(P.id);
                educationCodeList.add(string);
            }
        }

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

        //for seeking marriage
        string = session.getString(P.seeking_marriage);
        if (string != null) {
            jsonList = new JsonList(string);
            seekingMarriageList = new ArrayList<>();
            seekingMarriageCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                seekingMarriageList.add(string);

                string = j.getString(P.id);
                seekingMarriageCodeList.add(string);
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

    private void setUpCustomSpinner(final View view)
    {
        ListView listView = findViewById(R.id.listView);
        findViewById(R.id.view).setVisibility(View.VISIBLE);
        EditText editText = findViewById(R.id.editText);

        if(view.getId()==R.id.ethnicityEditText)
        {
            editText.setHint("Search Ethnicity");
            arrayAdapter= new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, ethnicityNameList);
        }
        else if(view.getId()==R.id.fathersCountryEditText)
        {
            editText.setHint("Father's Country/City of Origin");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, countryNameList);
        }

        else if(view.getId()==R.id.mothersCountryEditText)
        {
            editText.setHint("Mother's Country/City of Origin");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, countryNameList);
        }
        else if(view.getId()==R.id.highestLevelEduEditText)
        {
            editText.setHint("Highest level of Education");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, educationNameList);
        }
        else if(view.getId()==R.id.smokingEditText)
        {
            editText.setHint("Smoking");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, smokingNameList);
        }
        else if(view.getId()==R.id.relocateEditText)
        {
            editText.setHint("Willing to relocate");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, relocateNameList);
        }
        else if(view.getId()==R.id.seekingMarriageEditText)
        {
            editText.setHint("Seeking Marriage");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, seekingMarriageList);
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
    public void onClick(View v)
    {
        if (v.getId() == R.id.view)
            hideCustomSpinnerLayout();
        else if (v.getId() == R.id.button)
            makeJson();
        else
            setUpCustomSpinner(v);
    }

    private void makeJson()
    {

        EditText editText = findViewById(R.id.ethnicityEditText);
        String string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select Ehincity");
            return;
        } else if (string.equalsIgnoreCase("others")||string.equalsIgnoreCase("other")) {
            String stringg = "";

            stringg = ((EditText) findViewById(R.id.other_ethincity_edittext)).getText().toString();
            if (stringg.isEmpty()) {
                H.showMessage(this, "Please enter other ethincity details");
                return;
            }
            App.masterJson.addString(P.other_ethnicity, stringg);

        }
        int i = ethnicityNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.father_occupation_id, ethincityCodeList.get(i));



        editText = findViewById(R.id.fathersCountryEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select fathers country ");
            return;
        }
        i = countryNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.father_country, countryCodeList.get(i));


        editText = findViewById(R.id.mothersCountryEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select mothers country ");
            return;
        }
        i = countryNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.mother_country, countryCodeList.get(i));



         editText = findViewById(R.id.highestLevelEduEditText);
         string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select highest level of education");
            return;
        } else if (string.equalsIgnoreCase("others")||string.equalsIgnoreCase("other")) {
            String stringg = "";

            stringg = ((EditText) findViewById(R.id.other_education_edittext)).getText().toString();
            if (stringg.isEmpty()) {
                H.showMessage(this, "Please enter other education details");
                return;
            }
            App.masterJson.addString(P.other_edulevel, stringg);

        }
         i = educationNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.edulevel_id, educationCodeList.get(i));


        editText = findViewById(R.id.smokingEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select smoking habbit ");
            return;
        }
        i = smokingNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.smoke_id, smokingIdList.get(i));


        editText = findViewById(R.id.relocateEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select relocation ");
            return;
        }
        i = relocateNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.relocate_id, relocateCodeList.get(i));


        editText = findViewById(R.id.seekingMarriageEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this, "Please select seeking marriage ");
            return;
        }
        i = seekingMarriageList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.seeking_marriage, seekingMarriageCodeList.get(i));


        ArrayList<String> arrayList=new ArrayList<>();

        if(((CheckBox)findViewById(R.id.boardGames)).isChecked())
        {
            arrayList.add(((CheckBox)findViewById(R.id.boardGames)).getTag().toString());

        }

        if(((CheckBox)findViewById(R.id.cooking)).isChecked())
        {
            arrayList.add(((CheckBox)findViewById(R.id.cooking)).getTag().toString());
        }

        if(((CheckBox)findViewById(R.id.camping)).isChecked())
        {
            arrayList.add(((CheckBox)findViewById(R.id.camping)).getTag().toString());
        }

        if(((CheckBox)findViewById(R.id.music)).isChecked())
        {
            arrayList.add(((CheckBox)findViewById(R.id.music)).getTag().toString());
        }

        if(((CheckBox)findViewById(R.id.currentEvent)).isChecked())
        {
            arrayList.add(((CheckBox)findViewById(R.id.currentEvent)).getTag().toString());
        }

        if(((CheckBox)findViewById(R.id.diningOut)).isChecked())
        {
            arrayList.add(((CheckBox)findViewById(R.id.diningOut)).getTag().toString());
        }
        if(((CheckBox)findViewById(R.id.financialMarket)).isChecked())
        {
            arrayList.add(((CheckBox)findViewById(R.id.financialMarket)).getTag().toString());
        }

        if(((CheckBox)findViewById(R.id.volunteering)).isChecked())
        {
            arrayList.add(((CheckBox)findViewById(R.id.volunteering)).getTag().toString());
        }
        if(((CheckBox)findViewById(R.id.fishing)).isChecked())
        {
            arrayList.add(((CheckBox)findViewById(R.id.fishing)).getTag().toString());
        }
        if(((CheckBox)findViewById(R.id.readingBooks)).isChecked())
        {
            arrayList.add(((CheckBox)findViewById(R.id.readingBooks)).getTag().toString());
        }
        if(((CheckBox)findViewById(R.id.watchingSports)).isChecked())
        {
            arrayList.add(((CheckBox)findViewById(R.id.watchingSports)).getTag().toString());
        }
        if(((CheckBox)findViewById(R.id.styleAndFashion)).isChecked())
        {
            arrayList.add(((CheckBox)findViewById(R.id.styleAndFashion)).getTag().toString());
        }

        if(((CheckBox)findViewById(R.id.playingSports)).isChecked())
        {
            arrayList.add(((CheckBox)findViewById(R.id.playingSports)).getTag().toString());
        }
        if(((CheckBox)findViewById(R.id.politicalInterest)).isChecked())
        {
            arrayList.add(((CheckBox)findViewById(R.id.politicalInterest)).getTag().toString());
        }


        App.masterJson.addString(P.intreasted_in, arrayList.toString());


        App.masterJson.addString(P.lat, lat);
        App.masterJson.addString(P.lng, longs);
        H.log("masterJsonIs",App.masterJson.toString());

        //hitRegisterDetailsApi();
        startActivity(new Intent(this, RegSixthPageActivity.class));
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

    @Override
    public void onLocationChanged(Location location)
    {
        if (lat.isEmpty())
            lat = location.getLatitude()+"";
        if (longs.isEmpty())
            longs = location.getLongitude()+"";
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }
}

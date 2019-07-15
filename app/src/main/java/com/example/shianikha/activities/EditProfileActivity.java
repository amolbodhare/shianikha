package com.example.shianikha.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
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
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    LoadingDialog loadingDialog;
    ImageView gender_male_imv;
    ImageView gender_female_imv;
    private Bitmap bitmap;
    private String base64String = "";
    MyBounceInterpolator interpolator;
    String gender = "";
    String imageId = "";
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> profileForList, cityOfResidenceList, stateList, countryOfResidenceList, countryOfCitizenshipList, heightList;
    private ArrayList<String> religionList, ethincityList, fathersCityList, mothersCityList, currentOccupationList, highestLevelOfEducationList;
    private ArrayList<String> languageList, habbitList, relocateList, seekingMarriageList, intrestedInList;

    private ArrayList<String> countryNameList;
    private ArrayList<String> countryCodeList;


    private ArrayList<String> profileForCodeList, cityOfResidenceCodeList, stateCodeList, countryOfResidenceCodeList, countryOfCitizenshipCodeList;
    private ArrayList<String> religionCodeList, ethincityCodeList, fathersCityCodeList, mothersCityCodeList, currentOccupationCodeList, highestLevelOfEducationCodeList;
    private ArrayList<String> languageCodeList, habbitCodeList, relocateCodeList, seekingMarriageCodeList, intrestedInCodeList;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        loadingDialog = new LoadingDialog(this);

        session = new Session(this);

        findViewById(R.id.add_img_imv).setOnClickListener(this);
        findViewById(R.id.profile_for_ed).setOnClickListener(this);
        findViewById(R.id.countryCodeEditText).setOnClickListener(this);
        findViewById(R.id.city_ed).setOnClickListener(this);
        findViewById(R.id.state_ed).setOnClickListener(this);
        findViewById(R.id.country_of_residence_ed).setOnClickListener(this);
        findViewById(R.id.country_of_citizenship_ed).setOnClickListener(this);
        findViewById(R.id.ed_dob).setOnClickListener(this);
        findViewById(R.id.height_ed).setOnClickListener(this);
        findViewById(R.id.religion_ed).setOnClickListener(this);
        findViewById(R.id.ethinicity_ed).setOnClickListener(this);

        findViewById(R.id.fathers_city_ed).setOnClickListener(this);
        findViewById(R.id.mothers_city_ed).setOnClickListener(this);
        findViewById(R.id.current_occupation_ed).setOnClickListener(this);
        findViewById(R.id.higest_level_edu_ed).setOnClickListener(this);
        findViewById(R.id.lang_ed).setOnClickListener(this);
        findViewById(R.id.habbit_ed).setOnClickListener(this);
        findViewById(R.id.relocate_ed).setOnClickListener(this);
        findViewById(R.id.seeking_marriage_ed).setOnClickListener(this);
        findViewById(R.id.interesterd_in_ed).setOnClickListener(this);

        findViewById(R.id.view).setOnClickListener(this);

        //setMarginTopOfCustomSpinner(view);
        extractRequireList();
        handleGenderClickListner();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ed_dob)
            handleDatePicker();

        else if (v.getId() == R.id.add_img_imv) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                callImageCropper();
                return;
            } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                callImageCropper();
                return;
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 47);
        }
        else if (v.getId() == R.id.view)
            hideCustomSpinnerLayout();
        else
            setUpCustomSpinner( v);
    }

    public void onBack(View view) {
        finish();
    }

    public void onSave(View view) {
        makeJson();
    }

    class MyBounceInterpolator implements android.view.animation.Interpolator {
        private double mAmplitude = 1;
        private double mFrequency = 10;

        MyBounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) *
                    Math.cos(mFrequency * time) + 1);
        }
    }

    private void makeJson()
    {
        Json submit_json=new Json();

        EditText editText = findViewById(R.id.profile_for_ed);
        String string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this, "Please select profile for");
            return;
        }
        int i = profileForList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.profile_for, profileForCodeList.get(i));



        //token id is settled  here
        String check_token_id=session.getString(P.tokenData);

        if ((session.getString(P.tokenData)).isEmpty())
        {
            H.showMessage(this, "token id not found");
            return;
        }
        submit_json.addString(P.token_id, session.getString(P.tokenData));


        string = ((TextInputEditText) findViewById(R.id.full_name_edt)).getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please specify Full Name");
            return;
        }
        submit_json.addString(P.name, string);


        string = ((TextInputEditText)findViewById(R.id.email_tie)).getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter email!");
            return;
        }
        if (!string.contains("@") || !string.contains(".") || (string.indexOf("@")-string.indexOf(".")==1))
        {
            H.showMessage(this,"Please enter valid email!");
            return;
        }
        submit_json.addString(P.email,string);

        string = ((EditText)findViewById(R.id.countryCodeEditText)).getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter country code!");
            return;
        }
        submit_json.addString(P.country_code,string);

        string = ((EditText)findViewById(R.id.mobile_no)).getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter mobile number!");
            return;
        }
        submit_json.addString(P.ph_number,string);

        if (gender.isEmpty())
        {
            H.showMessage(this,"Please select gender!");
            return;
        }
        submit_json.addString(P.gender,gender);


        editText = findViewById(R.id.city_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select City of residence");
            return;
        }
        i = cityOfResidenceList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.city, cityOfResidenceCodeList.get(i));

        editText = findViewById(R.id.state_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select state");
            return;
        }
        i = stateList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.state, stateCodeList.get(i));

        editText = findViewById(R.id.country_of_residence_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select country of residence");
            return;
        }
        i = countryOfResidenceList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.country_res, countryOfResidenceCodeList.get(i));

        editText = findViewById(R.id.country_of_citizenship_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select country of citizenship");
            return;
        }
        i = countryOfCitizenshipList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.country_citizen, countryOfCitizenshipCodeList.get(i));


        string = ((EditText) findViewById(R.id.ed_dob)).getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this, "Please select date of birth");
            return;
        }
        submit_json.addString(P.dob, string);

        string = ((EditText) findViewById(R.id.height_ed)).getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select height");
            return;
        }
        submit_json.addString(P.height, string);

        editText = findViewById(R.id.religion_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select religion");
            return;
        }
        i = religionList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.religion, religionCodeList.get(i));

        editText = findViewById(R.id.ethinicity_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select ethincity");
            return;
        }
        i = ethincityList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.ethnicity, ethincityCodeList.get(i));

        editText = findViewById(R.id.fathers_city_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select Father city");
            return;
        }
        i = fathersCityList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.father_city, fathersCityCodeList.get(i));

        editText = findViewById(R.id.mothers_city_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select Mother city");
            return;
        }
        i = mothersCityList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.mother_city, mothersCityCodeList.get(i));

        editText = findViewById(R.id.current_occupation_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select current occupation");
            return;
        }

        i = currentOccupationList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.occupation_id, currentOccupationCodeList.get(i));


        string = ((TextInputEditText)findViewById(R.id.other_details_occu_tiet)).getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter about few lines about current occupation!");
            return;
        }
        submit_json.addString(P.about_occupation,string);


        editText = findViewById(R.id.higest_level_edu_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select highest education");
            return;
        }
        i = highestLevelOfEducationList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.edulevel_id, highestLevelOfEducationCodeList.get(i));


        editText = findViewById(R.id.lang_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select language");
            return;
        }

        i = languageList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.language, languageCodeList.get(i));


        editText = findViewById(R.id.habbit_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select smoking habbit");
            return;
        }
        i = habbitList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.smoke_id, habbitCodeList.get(i));

        editText = findViewById(R.id.relocate_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select relocation");
            return;
        }
        i = relocateList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.relocate_id, relocateCodeList.get(i));


        //radiogroups and radiobuttons

        i = ((RadioGroup)findViewById(R.id.convertedRadioGroup)).getCheckedRadioButtonId();
        if (i == R.id.yesConverted)
            string = "1";
        else if (i == R.id.notConverted)
            string = "0";
        submit_json.addString(P.cvt_islam, string);

        i = ((RadioGroup)findViewById(R.id.syedRadioGroup)).getCheckedRadioButtonId();
        if (i == R.id.yesSyed)
            string = "1";
        else if (i == R.id.notSyed)
            string = "0";
        else if (i == R.id.donnKnowSyed)
            string = "2";
        submit_json.addString(P.syed, string);

        i = ((RadioGroup)findViewById(R.id.handicapRadioGroup)).getCheckedRadioButtonId();
        if (i == R.id.yesHandicap)
            string = "1";
        else if (i == R.id.notHandicap)
            string = "0";
        else if (i == R.id.donnKnowHandicap)
            string = "2";
        submit_json.addString(P.handicap, string);

        i = ((RadioGroup)findViewById(R.id.childrenRadioGroup)).getCheckedRadioButtonId();
        if (i == R.id.yesChildren)
            string = "1";
        else if (i == R.id.notChildren)
            string = "0";
        submit_json.addString(P.children, string);

        editText = findViewById(R.id.seeking_marriage_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select seeking marriage option");
            return;
        }

        i = seekingMarriageList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.seeking_marriage, seekingMarriageCodeList.get(i));


        editText = findViewById(R.id.interesterd_in_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select intrested in");
            return;
        }

        i = intrestedInList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.intrested_in, intrestedInCodeList.get(i));


        editText = findViewById(R.id.religious_exception_ed);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter religious exception");
            return;
        }
        App.masterJson.addString(P.religion_expectations, string);

        editText = findViewById(R.id.qualities_seeking_ed);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter qualities you are seeking");
            return;
        }
        App.masterJson.addString(P.qualities_seeking, string);

        editText = findViewById(R.id.more_about_you_ed);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter more about you");
            return;
        }
        App.masterJson.addString(P.more_about_you, string);

        H.log("submittedjson",submit_json.toString());
        hitEditProfileApi(submit_json);
        //startActivity(new Intent(this,RegThirdPageActivity.class));
    }

    private void setUpCustomSpinner(final View view) {
        ListView listView = findViewById(R.id.listView);
        findViewById(R.id.view).setVisibility(View.VISIBLE);

        if (view.getId() == R.id.profile_for_ed)
        {
            ((EditText) findViewById(R.id.editText)).setHint("Search profile for");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, profileForList);

        } else if (view.getId() == R.id.countryCodeEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search Country Code");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, countryCodeList);
        }
        else if (view.getId() == R.id.city_ed) {
            ((EditText) findViewById(R.id.editText)).setHint("Search City Of Residence");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, cityOfResidenceList);
        }
        else if (view.getId() == R.id.state_ed) {
            ((EditText) findViewById(R.id.editText)).setHint("Search state");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, stateList);
        }
        else if (view.getId() == R.id.country_of_residence_ed)
        {
            ((EditText) findViewById(R.id.editText)).setHint("Search Country of Residence");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, countryOfResidenceList);
        }
        else if (view.getId() == R.id.country_of_citizenship_ed) {
            ((EditText) findViewById(R.id.editText)).setHint("Search Country of Citizenship");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, countryOfCitizenshipList);
        }
        else if (view.getId() == R.id.height_ed)
        {   EditText editText = findViewById(R.id.editText);
            editText.setHint("Search height");
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, heightList);
        }
        else if (view.getId() == R.id.religion_ed) {
            ((EditText) findViewById(R.id.editText)).setHint("Search Religion");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, religionList);
        }
        else if (view.getId() == R.id.ethinicity_ed) {
            ((EditText) findViewById(R.id.editText)).setHint("Search ethincity");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, ethincityList);
        }
        else if (view.getId() == R.id.fathers_city_ed) {
            ((EditText) findViewById(R.id.editText)).setHint("Search Fathers city");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, fathersCityList);
        }
        else if (view.getId() == R.id.mothers_city_ed) {
            ((EditText) findViewById(R.id.editText)).setHint("Search Mothers city");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, mothersCityList);
        }
        else if (view.getId() == R.id.current_occupation_ed) {
            ((EditText)  findViewById(R.id.editText)).setHint("Search current occupation");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, currentOccupationList);
        }
        else if (view.getId() == R.id.higest_level_edu_ed) {
            ((EditText) findViewById(R.id.editText)).setHint("Search highest level of education");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, highestLevelOfEducationList);
        }
        else if (view.getId() == R.id.lang_ed) {
            ((EditText) findViewById(R.id.editText)).setHint("Search language");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, languageList);
        }
        else if (view.getId() == R.id.habbit_ed) {
            ((EditText) findViewById(R.id.editText)).setHint("Search smoking habbit");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, habbitList);
        }
        else if (view.getId() == R.id.relocate_ed) {
            ((EditText) findViewById(R.id.editText)).setHint("Search relocating");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, relocateList);
        }
        else if (view.getId() == R.id.seeking_marriage_ed) {
            ((EditText) findViewById(R.id.editText)).setHint("Search seeking marriage");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, seekingMarriageList);
        }
        else if (view.getId() == R.id.interesterd_in_ed) {
            ((EditText) findViewById(R.id.editText)).setHint("Search intrested in");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, intrestedInList);
        }

        if (arrayAdapter == null)
            return;

        H.showKeyBoard(this,findViewById(R.id.editText));

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

    private void extractRequireList() {
        JsonList jsonList;

        //for profile
        String string = session.getString(P.profile_for);

        if (string != null) {
            jsonList = new JsonList(string);
            profileForList = new ArrayList<>();
            profileForCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                profileForList.add(string);

                string = j.getString(P.id);
                profileForCodeList.add(string);
            }
        }


        //for country code
        string = session.getString(P.country_code);

        if (string != null) {
            jsonList = new JsonList(string);
            countryNameList = new ArrayList<>();
            countryCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                countryNameList.add(string);

                string = j.getString(P.country_code);
                countryCodeList.add(string);
            }
        }

        //for city of residence
        string = session.getString(P.city);
        if (string != null) {
            jsonList = new JsonList(string);
            cityOfResidenceCodeList = new ArrayList<>();
            cityOfResidenceList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.city_name);
                cityOfResidenceList.add(string);

                string = j.getString(P.city_id);
                cityOfResidenceCodeList.add(string);
            }
        }

        //for state
        string = session.getString(P.state);
        if (string != null) {
            jsonList = new JsonList(string);
            stateList = new ArrayList<>();
            stateCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.state_name);
                stateList.add(string);

                string = j.getString(P.state_id);
                stateCodeList.add(string);
            }
        }

        //for country of residence
        string = session.getString(P.country);
        if (string != null) {
            jsonList = new JsonList(string);
            countryOfResidenceList = new ArrayList<>();
            countryOfResidenceCodeList = new ArrayList<>();

            for (Json j : jsonList) {
                string = j.getString(P.name);
                countryOfResidenceList.add(string);

                string = j.getString(P.country_code);
                countryOfResidenceCodeList.add(string);
            }
        }

        //for country of citizenship
        string = session.getString(P.country);
        if (string != null) {
            jsonList = new JsonList(string);
            countryOfCitizenshipList = new ArrayList<>();
            countryOfCitizenshipCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                countryOfCitizenshipList.add(string);

                string = j.getString(P.country_code);
                countryOfCitizenshipCodeList.add(string);
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


        //for religion
        string = session.getString(P.religion);
        if (string != null) {
            jsonList = new JsonList(string);
            religionList = new ArrayList<>();
            religionCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                religionList.add(string);

                string = j.getString(P.id);
                religionCodeList.add(string);
            }
        }

        //for ethincity
        string = session.getString(P.ethnicity);
        if (string != null) {
            jsonList = new JsonList(string);
            ethincityList = new ArrayList<>();
            ethincityCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                ethincityList.add(string);

                string = j.getString(P.id);
                ethincityCodeList.add(string);
            }
        }


        //for fathers country
        string = session.getString(P.country);
        if (string != null) {
            jsonList = new JsonList(string);
            fathersCityList = new ArrayList<>();
            fathersCityCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                fathersCityList.add(string);

                string = j.getString(P.id);
                fathersCityCodeList.add(string);
            }
        }

        //for mothers country
        string = session.getString(P.country);
        if (string != null) {
            jsonList = new JsonList(string);
            mothersCityList = new ArrayList<>();
            mothersCityCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                mothersCityList.add(string);

                string = j.getString(P.id);
                mothersCityCodeList.add(string);
            }
        }

        //for current occupation
        string = session.getString(P.occupation);
        if (string != null) {
            jsonList = new JsonList(string);
            currentOccupationList = new ArrayList<>();
            currentOccupationCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                currentOccupationList.add(string);

                string = j.getString(P.id);
                currentOccupationCodeList.add(string);
            }
        }

        //for education
        string = session.getString(P.education);
        if (string != null) {
            jsonList = new JsonList(string);
            highestLevelOfEducationList = new ArrayList<>();
            highestLevelOfEducationCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                highestLevelOfEducationList.add(string);

                string = j.getString(P.id);
                highestLevelOfEducationCodeList.add(string);
            }
        }

        //for language
        string = session.getString(P.language);
        if (string != null) {
            jsonList = new JsonList(string);
            languageList = new ArrayList<>();
            languageCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                languageList.add(string);

                string = j.getString(P.id);
                languageCodeList.add(string);
            }
        }

        //for smoking
        string = session.getString(P.smoking);
        if (string != null) {
            jsonList = new JsonList(string);
            habbitList = new ArrayList<>();
            habbitCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                habbitList.add(string);

                string = j.getString(P.id);
                habbitCodeList.add(string);
            }
        }


        //for relocate
        string = session.getString(P.relocate);
        if (string != null) {
            jsonList = new JsonList(string);
            relocateList = new ArrayList<>();
            relocateCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                relocateList.add(string);

                string = j.getString(P.id);
                relocateCodeList.add(string);
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

        //for intrested in
        string = session.getString(P.intrested_in);
        if (string != null) {
            jsonList = new JsonList(string);
            intrestedInList = new ArrayList<>();
            intrestedInCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                intrestedInList.add(string);

                string = j.getString(P.id);
                intrestedInCodeList.add(string);
            }
        }

        setUpTextWatcher();
    }

    private void setUpTextWatcher()
    {
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
    private void handleGenderClickListner() {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        gender_male_imv = findViewById(R.id.genderr_male_imv);
        gender_male_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender_male_imv.setBackground(getDrawable(R.drawable.reg_img_bg_selected));
                gender_female_imv.setBackground(getDrawable(R.drawable.reg_img_bg));
                gender = "male";
                myAnim.setInterpolator(interpolator);
                gender_male_imv.startAnimation(myAnim);
                gender_female_imv.clearAnimation();

            }
        });

        gender_female_imv = findViewById(R.id.genderr_female_imv);
        gender_female_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender_female_imv.setBackground(getDrawable(R.drawable.reg_img_bg_selected));
                gender_male_imv.setBackground(getDrawable(R.drawable.reg_img_bg));
                gender = "female";
                myAnim.setInterpolator(interpolator);
                gender_female_imv.startAnimation(myAnim);
                gender_male_imv.clearAnimation();
            }
        });
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

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel(Calendar calendar) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ((EditText) findViewById(R.id.ed_dob)).setText(sdf.format(calendar.getTime()));
    }


    private void hitEditProfileApi(Json json)
    {
        final Json j = json;
        final LoadingDialog loadingDialog = new LoadingDialog(this);

        RequestModel requestModel = RequestModel.newRequestModel("edit_profile");
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener()
                {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (isLoading)
                            loadingDialog.show();
                        else
                            loadingDialog.dismiss();
                    }
                })
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(EditProfileActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1)
                        {
                            /*Intent intent = new Intent(context, OTPVerificationActivity.class);
                            intent.putExtra(P.registrationJson,j.toString());
                            startActivity(intent);*/
                            H.showMessage(EditProfileActivity.this, json.getString(P.msg));
                            setResult(Activity.RESULT_OK);
                            finish();

                        } else
                            H.showMessage(EditProfileActivity.this, json.getString(P.msg));
                    }
                })
                .run("edit_profile");
    }

    private void callImageCropper() {
        CropImage.activity()
                //.setCropShape(CropImageView.CropShape.RECTANGLE)//method is deprecated for P and causes crash
                .setAllowCounterRotation(false)
                .setAllowFlipping(true)
                .setAllowRotation(true)
                .setAspectRatio(1, 1)
                .start(this);
    }
    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.d("resultUriIs", "" + resultUri);

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    Bitmap.createScaledBitmap(bitmap, 300, 300, false);
                    base64String = getStringImage(bitmap);
                    hitImageUploadApi(base64String);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void hitImageUploadApi(String base64String) {
        Json json = new Json();
        //json.addString(P.token, new Session(context).getString(P.tokenData));
        json.addString(P.image, base64String);

        RequestModel requestModel = RequestModel.newRequestModel("upload_profile_pic");
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (isLoading)
                            loadingDialog.show("Please wait...");
                        else
                            loadingDialog.dismiss();
                    }
                })
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(EditProfileActivity.this, "Connection failed.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {
                        if (json.getInt(P.status) == 1) {
                            ((CircleImageView)findViewById(R.id.image_profile_pic)).setImageBitmap(bitmap);
                            json = json.getJson(P.data);
                            String string = json.getString("file_name");
                            if (string != null)
                                imageId = string;

                        } else
                            H.showMessage(EditProfileActivity.this, json.getString(P.msg));
                    }
                })
                .run("uploadImage");
    }

}
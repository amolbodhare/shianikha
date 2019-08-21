package com.example.shianikha.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.example.shianikha.R;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.CommonListHolder;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, Api.OnLoadingListener, Api.OnErrorListener {
    LoadingDialog loadingDialog;
    ImageView gender_male_imv;
    ImageView gender_female_imv;
    private Bitmap bitmap;
    private String base64String = "";
    MyBounceInterpolator interpolator;
    String gender = "";
    String imageId = "";
    private ArrayAdapter<String> arrayAdapter;

    private Map<String, String> countryList = new TreeMap<>();

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        loadingDialog = new LoadingDialog(this);

        session = new Session(this);
        String profileDetailsString = getIntent().getStringExtra("profile_details_string");

        //Toast.makeText(this, "" + profileDetailsString, Toast.LENGTH_SHORT).show();

        findViewById(R.id.view).setOnClickListener(this);
        setAllRequiredClickListener((LinearLayout) (findViewById(R.id.linearLayout)));
        findViewById(R.id.add_img_imv).setOnClickListener(this);

        //setMarginTopOfCustomSpinner(view);
        handleGenderClickListner();

        makeCountryCodeList();

        makeInterestedInCheckBoxList();

        try {
            Json json = new Json(profileDetailsString);
            setAllData(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void makeInterestedInCheckBoxList() {
        int n = CommonListHolder.intrestedInNameList.size();
        int i = n%2 + n/2;

        LinearLayout linearLayout = findViewById(R.id.hobbyContainer1);
        for (int j = 0; j < n; j++) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.inflatable_check_box, null);
            CheckBox checkBox = (CheckBox) ll.getChildAt(0);
            checkBox.setText(CommonListHolder.intrestedInNameList.get(j));
            checkBox.setTag(CommonListHolder.intrestedInIdList.get(j));
            checkBox.setOnCheckedChangeListener(this);

            linearLayout.addView(ll);

            if (j == i - 1)
                linearLayout = findViewById(R.id.hobbyContainer2);
        }
    }

    private void setAllRequiredClickListener(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup)
                setAllRequiredClickListener((ViewGroup) view);
            else if (view instanceof EditText && !view.isFocusable())
                view.setOnClickListener(this);
        }
    }

    private void makeCountryCodeList() {
        String string = new Session(this).getString(P.country);
        if (string != null) {
            countryList.clear();
            JsonList jsonList = new JsonList(string);
            String s1, s2;

            for (Json json : jsonList) {
                s1 = json.getString(P.name);
                s2 = json.getString(P.country_code);
                if (s1 != null && s2 != null)
                    countryList.put(s1, s2);
            }
            H.log("countryNameAndListIs", countryList.toString());
        }
    }

    @Override
    public void onClick(View v) {
        H.log("iAm", "Clicked");

        if (v.getId() == R.id.dateOfBirthEditText)
            handleDatePicker();
        else if (v.getId() == R.id.add_img_imv)
        {
            H.log("clickEventIs","Triggered");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                callImageCropper();
                return;
            } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                callImageCropper();
                return;
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 47);
        } else if (v.getId() == R.id.view)
            hideCustomSpinnerLayout();
        else if (v.getId() == R.id.countryCodeEditText)
        {
            final Dialog dialog = new Dialog(this);
            final EditText editText = (EditText)v;
            dialog.setContentView(R.layout.country_code_layout);
            ListView listView = dialog.findViewById(R.id.listView);
            CountryCodeListAdapter countryCodeListAdapter = new CountryCodeListAdapter();
            listView.setAdapter(countryCodeListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    editText.setText(((TextView)view.findViewById(R.id.countryCode)).getText().toString());
                    dialog.hide();
                }
            });
            dialog.show();
        } else
            setUpCustomSpinner(v);

        setUpTextWatcher();
    }

    public void onBack(View view) {
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
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
    public void onLoading(boolean isLoading) {
        if (isLoading)
            loadingDialog.show();
        else
            loadingDialog.dismiss();
    }

    @Override
    public void onError() {
        H.showMessage(EditProfileActivity.this, "Something went wrong.");
    }

    public void onSaveOrSubmit(View view) {
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

    private void makeJson() {
        Json submit_json = new Json();
        submit_json.addString(P.token_id, session.getString(P.tokenData));

        EditText editText = findViewById(R.id.firstNameEditText);
        String string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please enter first name");
            editText.requestFocus();
            return;
        }
        submit_json.addString(P.first_name, string);

        editText = findViewById(R.id.middleNameEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please enter middle name");
            editText.requestFocus();
            return;
        }
        submit_json.addString(P.middle_name, string);

        editText = findViewById(R.id.lastNameEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please enter last name");
            editText.requestFocus();
            return;
        }
        submit_json.addString(P.last_name, string);

        editText = findViewById(R.id.dateOfBirthEditText);
        string = "";
        Object object =  editText.getTag();
        if (object!=null) {
            string =object.toString();
            if (string.isEmpty()) {
                H.showMessage(this, "Please enter DOB");
                return;
            }
        }
        submit_json.addString(P.dob, string);

        /*editText = findViewById(R.id.emailEditText);
        string = editText.getText().toString();
        int i = string.indexOf("@") - string.indexOf(".");
        if (string.isEmpty()) {
            H.showMessage(this, "Please enter email");
            editText.requestFocus();
            return;
        }
        else if (!string.contains("@") || !string.contains(".") || string.contains(" ") || i==1 || i==-1)
        {
            H.showMessage(this,"Please enter valid email address");
            editText.requestFocus();
            return;
        }
        submit_json.addString(P.email, string);*/

        /*editText = findViewById(R.id.countryCodeEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please enter country code!");
            return;
        }
        i = CommonListHolder.countryCodeList.indexOf(string);
        string = i==-1?"" : CommonListHolder.countryIdList.get(i);
        submit_json.addString(P.country_code, string);*/

        /*editText = findViewById(R.id.mobileEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please enter mobile number");
            return;
        }
        else if (string.length() != 10)
        {
            H.showMessage(this,"Please enter valid mobile number");
            return;
        }
        submit_json.addString(P.ph_number, string);*/

        if (gender.isEmpty()) {
            H.showMessage(this, "Please select gender!");
            return;
        }
        submit_json.addString(P.gender, gender);


        editText = findViewById(R.id.complexionEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select the complexion");
            editText.requestFocus();
            return;
        }
        int i = CommonListHolder.complexionNameList.indexOf(string);
        string = i==-1?"" : CommonListHolder.complexionIdList.get(i);
        submit_json.addString(P.skin_tone, string);

       editText = findViewById(R.id.heightEditText);
       string = editText.getText().toString();
       if (string.isEmpty())
       {
           H.showMessage(this,"Please select your height");
           return;
       }
       i = CommonListHolder.heightList.indexOf(string);
       string = i==-1 ? "" : CommonListHolder.heightList.get(i);
       submit_json.addString(P.height,string);

       editText =findViewById(R.id.bodyTypeEditText);
       string = editText.getText().toString();
       if (string.isEmpty())
       {
           H.showMessage(this,"Please select your body type");
           return;
       }
       i = CommonListHolder.physicalStatusNameList.indexOf(string);
       string = i==-1 ? "" : CommonListHolder.physicalStatusIdList.get(i);
       submit_json.addString(P.body_type,string);

        editText = findViewById(R.id.communityEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select community");
            return;
        }
        i = CommonListHolder.religionNameList.indexOf(string);
        string = i==-1? "" : CommonListHolder.religionIdList.get(i);
        submit_json.addString(P.religion,string);

        /*View view = findViewById(R.id.otherCommunityInputLayout);
        editText = findViewById(R.id.otherCommunityEditText);
        string = editText.getText().toString();
        if (view.getVisibility() == View.VISIBLE && string.isEmpty())
        {
            H.showMessage(this,"Please enter other community's name");
            editText.requestFocus();
            return;
        }
        submit_json.addString(P.);*/

        editText = findViewById(R.id.maritalStatusEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter marital status");
            return;

        }
        i = CommonListHolder.maritalStatusNameList.indexOf(string);
        string = i==-1? "" : CommonListHolder.maritalStatusIdList.get(i);
        submit_json.addString(P.marital_status,string);

        editText = findViewById(R.id.educationEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select your education");
            return;
        }
        i = CommonListHolder.educationNameList.indexOf(string);
        string = i==-1 ? "" : CommonListHolder.educationIdList.get(i);
        submit_json.addString(P.edulevel_id,string);

        View view = findViewById(R.id.otherEducationInputLayout);
        editText = findViewById(R.id.otherEducationEditText);
        string = editText.getText().toString();
        if (view.getVisibility() == View.VISIBLE && string.isEmpty())
        {
            H.showMessage(this,"Please enter other community's name");
            editText.requestFocus();
            return;
        }
        submit_json.addString(P.other_high_edulevel,string);

        editText = findViewById(R.id.occupationEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select current occupation");
            return;
        }
        i = CommonListHolder.occupationNameList.indexOf(string);
        string = i==-1?"" : CommonListHolder.occupationIdList.get(i);
        submit_json.addString(P.occupation_id,string);

        view = findViewById(R.id.otherOccupationInputLayout);
        editText = findViewById(R.id.otherOccupationEditText);
        string = editText.getText().toString();
        if (view.getVisibility() == View.VISIBLE && string.isEmpty())
        {
            H.showMessage(this,"Please enter other community's name");
            editText.requestFocus();
            return;
        }
        submit_json.addString(P.other_occupation,string);

        editText = findViewById(R.id.monthlyIncomeEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please select your monthly income");
            return;
        }
        i = CommonListHolder.monthlyIncomeNameList.indexOf(string);
        string = i==-1? "" : CommonListHolder.monthlyIncomeIdList.get(i);
        submit_json.addString(P.monthly_income,string);

        editText = findViewById(R.id.resAddEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter your residential address.");
            editText.requestFocus();
            return;
        }
        submit_json.addString(P.residency_address,string);

        editText = findViewById(R.id.countryEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please select your country");
            return;
        }
        i = CommonListHolder.countryNameList.indexOf(string);
        string = i==-1? "" : CommonListHolder.countryIdList.get(i);
        submit_json.addString(P.country_res,string);

        editText = findViewById(R.id.stateEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please select your state");
            return;
        }
        i = CommonListHolder.stateNameList.indexOf(string);
        string = i==-1? "" : CommonListHolder.stateIdList.get(i);
        submit_json.addString(P.state,string);

        editText = findViewById(R.id.cityEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please select your city");
            return;
        }
        i = CommonListHolder.cityNameList.indexOf(string);
        string = i==-1? "" : CommonListHolder.cityIdList.get(i);
        submit_json.addString(P.city,string);

        editText = findViewById(R.id.motherTongueEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please select your city");
            return;
        }
        i = CommonListHolder.languageNameList.indexOf(string);
        string = i== -1 ? "" : CommonListHolder.languageIdList.get(i);
        submit_json.addString(P.mother_tongue,string);

        editText = findViewById(R.id.selectLanguagesEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please select known languages");
            return;
        }
        JSONArray jsonArray = H.extractJsonArray(string,CommonListHolder.languageNameList,CommonListHolder.languageIdList);
        submit_json.addJSONArray(P.language,jsonArray);

        view = findViewById(R.id.otherLanguageInputLayout);
        editText = findViewById(R.id.otherLanguageEditText);
        string = editText.getText().toString();
        if (view.getVisibility() == View.VISIBLE && string.isEmpty())
        {
            H.showMessage(this,"Please enter other language's name");
            editText.requestFocus();
            return;
        }
        submit_json.addString(P.other_language,string);

        editText = findViewById(R.id.fathersNameEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter father's name");
            editText.requestFocus();
            return;
        }
        submit_json.addString(P.father_name,string);

        editText = findViewById(R.id.fathersOccupationEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please select father's occupation");
            return;
        }
        i = CommonListHolder.occupationNameList.indexOf(string);
        string = i == -1 ? "" : CommonListHolder.occupationIdList.get(i);
        submit_json.addString(P.father_occupation_id,string);

        view = findViewById(R.id.fathersOtherOccupationInputLayout);
        editText = findViewById(R.id.fathersOtherOccupationEditText);
        string = editText.getText().toString();
        if (view.getVisibility() == View.VISIBLE && editText.getText().toString().isEmpty())
        {
            H.showMessage(this,"Please enter father's other occupation");
            editText.requestFocus();
            return;
        }
        submit_json.addString(P.father_other_occupation,string);

        editText = findViewById(R.id.mothersNameEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter mother's name");
            editText.requestFocus();
            return;
        }
        submit_json.addString(P.mother_name,string);

        editText = findViewById(R.id.mothersOccupationEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please select mother's occupation");
            return;
        }
        i = CommonListHolder.occupationNameList.indexOf(string);
        string = i == -1 ? "" : CommonListHolder.occupationIdList.get(i);
        submit_json.addString(P.mother_occupation_id,string);

        view = findViewById(R.id.mothersOtherOccupationInputLayout);
        editText = findViewById(R.id.mothersOtherOccupationEdiText);
        string = editText.getText().toString();
        if (view.getVisibility() == View.VISIBLE && editText.getText().toString().isEmpty())
        {
            H.showMessage(this,"Please enter mother's other occupation");
            editText.requestFocus();
            return;
        }
        submit_json.addString(P.mother_other_occupation,string);

        editText = findViewById(R.id.numberOfSiblings);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter number of siblings.");
            editText.requestFocus();
            return;
        }
        submit_json.addString(P.siblings,string);

        editText = findViewById(R.id.parentsNumberEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter parent's of alternate mobile number");
            editText.requestFocus();
            return;
        }
        submit_json.addString(P.alternate_contact_no,string);

        editText = findViewById(R.id.ethnicityEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select ethnicity");
            return;
        }
        i = CommonListHolder.ethnicityNameList.indexOf(string);
        string = i==-1 ?"" : CommonListHolder.educationIdList.get(i);
        submit_json.addString(P.ethnicity,string);

        editText = findViewById(R.id.fathersCountryEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please select father's country.");
            return;
        }
        i = CommonListHolder.countryNameList.indexOf(string);
        string = i==-1 ?"" : CommonListHolder.countryIdList.get(i);
        submit_json.addString(P.father_country,string);

        editText = findViewById(R.id.mothersCountryEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please select mother's country.");
            return;
        }
        i = CommonListHolder.countryNameList.indexOf(string);
        string = i==-1 ?"" : CommonListHolder.countryIdList.get(i);
        submit_json.addString(P.mother_country,string);

        editText = findViewById(R.id.smokingEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please select smoking status");
            return;
        }
        i = CommonListHolder.smokingNameList.indexOf(string);
        string = i==-1 ? "": CommonListHolder.smokingIdList.get(i);
        submit_json.addString(P.smoke_id,string);

        jsonArray = makeInterestedInJsonArray();
        submit_json.addJSONArray(P.intreasted_in,jsonArray);

        editText = findViewById(R.id.otherHobbyEditText);
        string = editText.getText().toString();
        submit_json.addString(P.other_intreasted,string);

        H.log("submittedjson", submit_json.toString());
        hitEditProfileApi(submit_json);
        //startActivity(new Intent(this,RegThirdPageActivity.class));
    }

    private JSONArray makeInterestedInJsonArray()
    {
        JSONArray jsonArray = new JSONArray();

        LinearLayout linearLayout = findViewById(R.id.hobbyContainer1);
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

        linearLayout = findViewById(R.id.hobbyContainer2);
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

    private void setUpCustomSpinner(final View view) {
        ListView listView = findViewById(R.id.listView);
        EditText editText = findViewById(R.id.editText);
        int i = view.getId();

        if (i == R.id.complexionEditText) {
            editText.setHint("Search Complexion ");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.complexionNameList);
        } else if (i == R.id.heightEditText) {
            editText.setHint("Search Height ");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.heightList);
        } else if (i == R.id.bodyTypeEditText) {
            editText.setHint("Search body Type ");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.physicalStatusNameList);
        } else if (i == R.id.communityEditText) {
            editText.setHint("Search Shia Community ");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.religionNameList);
        } else if (i == R.id.maritalStatusEditText) {
            editText.setHint("Search Marital Status ");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.maritalStatusNameList);
        } else if (i == R.id.educationEditText) {
            editText.setHint("Search Education ");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.educationNameList);
        } else if (i == R.id.occupationEditText || i == R.id.fathersOccupationEditText || i == R.id.mothersOccupationEditText) {
            editText.setHint("Search Occupation ");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.occupationNameList);
        } else if (i == R.id.monthlyIncomeEditText) {
            editText.setHint("Search Monthly Income ");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.monthlyIncomeNameList);
        } else if (i == R.id.countryEditText || i == R.id.fathersCountryEditText || i == R.id.mothersCountryEditText) {
            editText.setHint("Search Country Name ");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.countryNameList);
        } else if (i == R.id.stateEditText) {
            editText.setHint("Search State Name ");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.stateNameList);
        } else if (i == R.id.cityEditText) {
            editText.setHint("Search City Name ");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.cityNameList);
        }
        else if (i == R.id.motherTongueEditText)
        {
            editText.setHint("Search Language ");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.languageNameList);
        }
        else if (i == R.id.selectLanguagesEditText)
        {
            String[] stringArray = new String[CommonListHolder.languageNameList.size()];
            stringArray = CommonListHolder.languageNameList.toArray(stringArray);
            showLanguageMultiChoiceList(stringArray,view);
            return;
        }
        else if (i == R.id.ethnicityEditText) {
            editText.setHint("Search Ethnicity");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.ethnicityNameList);
        } else if (i == R.id.smokingEditText) {
            editText.setHint("Search Smoking Status");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.smokingNameList);
        }

        findViewById(R.id.view).setVisibility(View.VISIBLE);

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

    private ArrayList<String> tempLanguageList;
    private boolean[] languageCheckedArray;
    private void showLanguageMultiChoiceList(final String[] stringArray,final View view) {
        if (languageCheckedArray == null) {
            languageCheckedArray = new boolean[stringArray.length];
            for (int j = 0; j < stringArray.length; j++)
                languageCheckedArray[j] = false;
            tempLanguageList = new ArrayList<>();
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMultiChoiceItems(stringArray, languageCheckedArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    tempLanguageList.add(stringArray[i]);
                    languageCheckedArray[i] = true;
                } else {
                    tempLanguageList.remove(stringArray[i]);
                    languageCheckedArray[i] = false;
                }
            }
        }).setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (tempLanguageList != null) {
                    String string = tempLanguageList.toString();
                    string = string.replace("[", "");
                    string = string.replace("]", "");

                    ((EditText) view).setText(string);
                    string = string.toLowerCase();
                    if (string.contains("other"))
                        showOtherEditText(view, true);
                    else
                        showOtherEditText(view, false);
                }
            }
        }).show();
    }

    private void showOtherEditText(View view, boolean b) {
        RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
        LinearLayout linearLayout = (LinearLayout) relativeLayout.getParent();

        int i = linearLayout.indexOfChild(relativeLayout);
        View v = linearLayout.getChildAt(i + 1);
        if (v instanceof TextInputLayout) {
            if (b)
                v.setVisibility(View.VISIBLE);
            else if (view.getId()!=R.id.monthlyIncomeEditText)
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

        DatePickerDialog datePickerDialog =  new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setInverseBackgroundForced(false);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime() - (365*18-4)*24*60*60*1000l);
        datePickerDialog.show();
    }

    private void updateLabel(Calendar calendar) {
        String myFormat = "dd MMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        EditText editText = findViewById(R.id.dateOfBirthEditText);
        editText.setText(sdf.format(calendar.getTime()));

        myFormat = "yyyy-MM-dd";
        sdf = new SimpleDateFormat(myFormat,Locale.US);
        editText.setTag(sdf.format(calendar.getTime()));
    }

    private void hitEditProfileApi(Json json) {

        RequestModel requestModel = RequestModel.newRequestModel("edit_profile");
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1) {

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
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {
                        if (json.getInt(P.status) == 1) {
                            ((CircleImageView) findViewById(R.id.image_profile_pic)).setImageBitmap(bitmap);
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

    private void setAllData(Json json) {
        ((EditText) findViewById(R.id.firstNameEditText)).setText(json.getString(P.first_name));
        ((EditText) findViewById(R.id.middleNameEditText)).setText(json.getString(P.middle_name));
        ((EditText) findViewById(R.id.lastNameEditText)).setText(json.getString(P.last_name));

        String string = json.getString(P.dob);
        EditText editText = findViewById(R.id.dateOfBirthEditText);
        editText.setTag(string);
        android.icu.text.SimpleDateFormat simpleDateFormat1 = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");
        android.icu.text.SimpleDateFormat simpleDateFormat2 = new android.icu.text.SimpleDateFormat("dd MMM yyyy");
        try {
            Date date = simpleDateFormat1.parse(string);
            String str = simpleDateFormat2.format(date);
            editText.setText(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*((EditText) findViewById(R.id.emailEditText)).setText(json.getString(P.email));
        ((EditText) findViewById(R.id.countryCodeEditText)).setText("+"+json.getString(P.country_code));
        ((EditText) findViewById(R.id.mobileEditText)).setText(json.getString(P.ph_number));*/

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        if (json.getString(P.gender).equalsIgnoreCase("male")) {

            gender_male_imv.setBackground(getDrawable(R.drawable.reg_img_bg_selected));
            gender_female_imv.setBackground(getDrawable(R.drawable.reg_img_bg));
            gender = "male";
            myAnim.setInterpolator(interpolator);
            gender_male_imv.startAnimation(myAnim);
            gender_female_imv.clearAnimation();
        } else {
            gender_female_imv.setBackground(getDrawable(R.drawable.reg_img_bg_selected));
            gender_male_imv.setBackground(getDrawable(R.drawable.reg_img_bg));
            gender = "female";
            myAnim.setInterpolator(interpolator);
            gender_female_imv.startAnimation(myAnim);
            gender_male_imv.clearAnimation();
        }

        ((EditText) findViewById(R.id.complexionEditText)).setText(json.getString(P.skin_tone));

        ((EditText) findViewById(R.id.heightEditText)).setText(json.getString(P.height));
        ((EditText) findViewById(R.id.bodyTypeEditText)).setText(json.getString(P.body_type));
        ((EditText) findViewById(R.id.communityEditText)).setText(json.getString(P.religion));

        ((EditText) findViewById(R.id.maritalStatusEditText)).setText(json.getString(P.marital_status));
        ((EditText) findViewById(R.id.educationEditText)).setText(json.getString(P.educationlevel));
        ((EditText) findViewById(R.id.occupationEditText)).setText(json.getString(P.occupation_name));

        ((EditText) findViewById(R.id.monthlyIncomeEditText)).setText(json.getString(P.monthly_income));
        ((EditText) findViewById(R.id.resAddEditText)).setText(json.getString(P.residency_address));

        ((EditText) findViewById(R.id.countryEditText)).setText(json.getString(P.country_name));
        ((EditText) findViewById(R.id.stateEditText)).setText(json.getString(P.state_name));
        ((EditText) findViewById(R.id.cityEditText)).setText(json.getString(P.city_name));

        ((EditText) findViewById(R.id.motherTongueEditText)).setText(json.getString(P.mothertongue));
        //((EditText) findViewById(R.id.selectLanguagesEditText)).setText(json.getString(P.language));
        JSONArray jsonArray = json.getJsonArray(P.language_id);
        if (jsonArray!=null)
        {
            tempLanguageList = new ArrayList<>();
            languageCheckedArray = new boolean[CommonListHolder.languageNameList.size()];

            for (int i=0; i<jsonArray.length(); i++)
            {
                try {
                    string = jsonArray.getString(i);
                    for (int j=0; j<CommonListHolder.languageNameList.size(); j++)
                    {
                        if (string.equals(CommonListHolder.languageIdList.get(j))) {
                            tempLanguageList.add(CommonListHolder.languageNameList.get(j));
                            languageCheckedArray[j] = true;
                        }
                        else if (!languageCheckedArray[j])
                            languageCheckedArray[j] = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            string = tempLanguageList.toString();
            string = string.replace("[", "");
            string = string.replace("]", "");
            editText = findViewById(R.id.selectLanguagesEditText);
            editText.setText(string);
        }

        jsonArray = json.getJsonArray(P.intreasted_in);
        if (jsonArray!=null)
        {
            LinearLayout linearLayout = findViewById(R.id.interest_layout);
            View view;
            for (int i=0; i<jsonArray.length(); i++)
            {
                try {
                    string = jsonArray.getString(i);
                    view = linearLayout.findViewWithTag(string);
                    if (view instanceof CheckBox)
                        ((CheckBox)view).setChecked(true);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (findViewById(R.id.otherHobbyInputLayout).getVisibility() == View.VISIBLE)
            {
                string = json.getString(P.other_intreasted);
                ((EditText)findViewById(R.id.otherHobbyEditText)).setText(string);
            }
        }

        ((EditText) findViewById(R.id.fathersNameEditText)).setText(json.getString(P.father_name));
        ((EditText) findViewById(R.id.fathersOccupationEditText)).setText(json.getString(P.father_occupation));
        ((EditText) findViewById(R.id.fathersOtherOccupationEditText)).setText(json.getString(P.father_other_occupation));

        ((EditText) findViewById(R.id.mothersNameEditText)).setText(json.getString(P.mother_name));
        ((EditText) findViewById(R.id.mothersOccupationEditText)).setText(json.getString(P.mother_occupation));
        ((EditText) findViewById(R.id.mothersOtherOccupationEdiText)).setText(json.getString(P.mother_other_occupation));


        ((EditText) findViewById(R.id.numberOfSiblings)).setText(json.getString(P.siblings));
        ((EditText) findViewById(R.id.parentsNumberEditText)).setText(json.getString(P.alternate_contact_no));

        ((EditText) findViewById(R.id.ethnicityEditText)).setText(json.getString(P.ethnicity_name));

        ((EditText) findViewById(R.id.fathersCountryEditText)).setText(json.getString(P.father_country));
        ((EditText) findViewById(R.id.mothersCountryEditText)).setText(json.getString(P.mother_country));

        ((EditText) findViewById(R.id.smokingEditText)).setText(json.getString(P.smoke_id));


        string = json.getString(P.profile_pic);
        try {
            Picasso.get().load(string).into(((ImageView)findViewById(R.id.image_profile_pic)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    class CountryCodeListAdapter extends BaseAdapter {
        ArrayList<String> countryCodeList;
        ArrayList<String> countryNameList;
        String string;

        CountryCodeListAdapter() {
            countryCodeList = new ArrayList<>();
            countryNameList = new ArrayList<>();

            for (Map.Entry<String, String> entry : countryList.entrySet()) {
                string = entry.getKey();
                countryNameList.add(string);
                string = entry.getValue();
                countryCodeList.add(string);
            }
        }

        @Override
        public int getCount() {
            return countryList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(EditProfileActivity.this).inflate(R.layout.country_code_item, null, false);

            ((TextView) convertView.findViewById(R.id.countryCode)).setText(countryCodeList.get(position));
            ((TextView) convertView.findViewById(R.id.countryName)).setText(countryNameList.get(position));

            return convertView;
        }
    }
}

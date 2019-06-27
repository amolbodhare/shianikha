package com.example.shianikha.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayAdapter<String> arrayAdapter;
    //public  static FragmentManager fragmentManager;

    ImageView gender_male_imv;
    ImageView gender_female_imv;
    EditText profile_for_ed;
    // String masterdatajsonstring;
    Context context;
    MyBounceInterpolator interpolator;

    String gender = "";

    private ArrayList<String> profileForNameList = new ArrayList<>();
    private ArrayList<String> profileForCodeList = new ArrayList<>();
    private Map<String, String> countryList = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        context = RegistrationActivity.this;


        profile_for_ed = findViewById(R.id.profile_for_ed);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        interpolator = new MyBounceInterpolator(0.2, 20);

        findViewById(R.id.btn_next).setOnClickListener(this);
        findViewById(R.id.login_here).setOnClickListener(this);

        extractRequiredList();
        handleGenderClickListner();
        setUpEditTextClickListner();//for custom spinner
        setMargintopOfCustomSpinner();
        setUpCountryCodePopUP();
    }

    private void setUpCountryCodePopUP()
    {
        final EditText editText = findViewById(R.id.countryCodeEditText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = new Dialog(RegistrationActivity.this);
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
            }
        });
    }

    private void setMargintopOfCustomSpinner() {
        LinearLayout linearLayout = findViewById(R.id.includeContainer);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        int i = new Session(this).getInt(P.statusBarHeight);
        layoutParams.topMargin = i;
        H.log("heightIs", i + "");
        linearLayout.setLayoutParams(layoutParams);
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

    private void extractRequiredList() {
        String string = new Session(this).getString(P.profile_for);
        JsonList jsonList;
        if (string != null) {
            jsonList = new JsonList(string);
            profileForNameList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                profileForNameList.add(string);
            }

            profileForCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.id);
                profileForCodeList.add(string);
            }

            setUpTextWatcher();
        }

        string = new Session(this).getString(P.country_code);
        if (string != null) {
            countryList.clear();
            jsonList = new JsonList(string);
            String s1,s2;

            for (Json json : jsonList) {
                s1 = json.getString(P.name);
                s2 = json.getString(P.country_code);
                if (s1 != null && s2 != null)
                    countryList.put(s1, s2);
            }
            H.log("countryNameAndListIs",countryList.toString());
        }
    }

    private void setUpEditTextClickListner() {

        EditText editText = findViewById(R.id.profile_for_ed);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpCustomSpinner(v);
            }
        });
    }

    private void setUpTextWatcher()// this is for edit text aboseve list view
    {
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
        findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideCustomSpinnerLayout();
            }
        });

        if (view.getId() == R.id.profile_for_ed) {
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, profileForNameList);
            listView.setAdapter(arrayAdapter);
        }

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
        findViewById(R.id.includeContainer).animate().translationX(i).setDuration(500);
        findViewById(R.id.view).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btn_next)
            makeJson();
        else if(v.getId() == R.id.login_here)
        {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
    }

    private void makeJson()
    {
        Json json = new Json();

        String string = profile_for_ed.getText().toString().trim();
        if (string.isEmpty())
        {
            H.showMessage(context,"Please select your profile for!");
            return;
        }
        int i= profileForNameList.indexOf(string);
        if (i!=-1)
        {
            string = profileForCodeList.get(i);
            json.addString(P.profile_for,string);
        }

        string = ((EditText)findViewById(R.id.full_name)).getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(context,"Please enter Name!");
            return;
        }
        json.addString(P.name,string);

        string = ((EditText)findViewById(R.id.email)).getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(context,"Please enter email!");
            return;
        }
        if (!string.contains("@") || !string.contains(".") || (string.indexOf("@")-string.indexOf(".")==1))
        {
            H.showMessage(context,"Please enter valid email!");
            return;
        }
        json.addString(P.email,string);

        string = ((EditText)findViewById(R.id.countryCodeEditText)).getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(context,"Please enter country code!");
            return;
        }
        json.addString(P.country_code,string);

        string = ((EditText)findViewById(R.id.mobile_no)).getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(context,"Please enter mobile number!");
            return;
        }
        json.addString(P.ph_number,string);

        if (gender.isEmpty())
        {
            H.showMessage(context,"Please select gender!");
            return;
        }
        json.addString(P.gender,gender);

        hitRegisterApi(json);
    }

    private void hitRegisterApi(Json json)
    {
        final Json j = json;
        final LoadingDialog loadingDialog = new LoadingDialog(this);

        RequestModel requestModel = RequestModel.newRequestModel("register");
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
                        H.showMessage(RegistrationActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1)
                        {
                            Intent intent = new Intent(RegistrationActivity.this, OTPVereificationActivty.class);
                            intent.putExtra(P.registrationJson,j.toString());
                            startActivity(intent);
                        } else
                            H.showMessage(RegistrationActivity.this, json.getString(P.msg));
                    }
                })
                .run("hitRegisterApi");
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

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    class CountryCodeListAdapter extends BaseAdapter
    {
        ArrayList<String> countryCodeList;
        ArrayList<String> countryNameList;
        String string;

        CountryCodeListAdapter()
        {
            countryCodeList = new ArrayList<>();
            countryNameList = new ArrayList<>();

            for (Map.Entry<String,String> entry : countryList.entrySet())
            {
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
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView==null)
                convertView = LayoutInflater.from(RegistrationActivity.this).inflate(R.layout.country_code_item,null,false);

            ((TextView)convertView.findViewById(R.id.countryCode)).setText(countryCodeList.get(position));
            ((TextView)convertView.findViewById(R.id.countryName)).setText(countryNameList.get(position));

            return convertView;
        }
    }
}
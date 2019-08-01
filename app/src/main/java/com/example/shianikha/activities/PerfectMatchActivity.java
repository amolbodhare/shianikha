package com.example.shianikha.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.text.HtmlCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.text.Html.fromHtml;


public class PerfectMatchActivity extends AppCompatActivity implements View.OnClickListener
{

    private ArrayAdapter<String> arrayAdapter;
    private Session session;
    private ArrayList<String> ageList, maritalStatusNameList, maritalStatusCodeList, ethnicityNameList, ethincityCodeList;
    private ArrayList<String> educationNameList, educationCodeList;
    ;private ArrayList<String> marital_status_arraylist;
    ;private ArrayList<String> ethincity_arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_perfect_match);

        session = new Session(this);
        findViewById(R.id.minAgeEditText).setOnClickListener(this);
        findViewById(R.id.maxAgeEditText).setOnClickListener(this);
        findViewById(R.id.educationEditText).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.view).setOnClickListener(this);

        setMarginTopOfCustomSpinner();
        extractRequireList();

        setOnCheckChangeListener();

        CheckBox checkBox = findViewById(R.id.checkBox3);
        checkBox.setMovementMethod(LinkMovementMethod.getInstance());
        checkBox.append(HtmlCompat.fromHtml("<font color=\"blue\"> Terms of Use </font>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        checkBox.append(getString(R.string.appendedString));
        Linkify.addLinks(checkBox, Linkify.WEB_URLS);

        /*((CheckBox)findViewById(R.id.other)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                findViewById(R.id.other).setVisibility(View.VISIBLE);
                else
                    findViewById(R.id.otherEthnicity_layout).setVisibility(View.INVISIBLE);
            }
        });*/




    }

    private void setOnCheckChangeListener() {
       /* LinearLayout linearLayout= findViewById(R.id.linearLayout);
        attachListener(linearLayout);*/

        ((CheckBox)findViewById(R.id.neverMarriedBefore)).setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        ((CheckBox)findViewById(R.id.divorced)).setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        ((CheckBox)findViewById(R.id.married)).setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        ((CheckBox)findViewById(R.id.widowed)).setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        ((CheckBox)findViewById(R.id.separated)).setOnCheckedChangeListener(new myCheckBoxChnageClicker());

        ((CheckBox)findViewById(R.id.arab)).setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        ((CheckBox)findViewById(R.id.african)).setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        ((CheckBox)findViewById(R.id.asian)).setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        ((CheckBox)findViewById(R.id.caucasian)).setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        ((CheckBox)findViewById(R.id.hispanic)).setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        ((CheckBox)findViewById(R.id.parsian)).setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        ((CheckBox)findViewById(R.id.southAsian_indian)).setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        ((CheckBox)findViewById(R.id.southAsian_pakistani)).setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        ((CheckBox)findViewById(R.id.other)).setOnCheckedChangeListener(new myCheckBoxChnageClicker());

    }

  /*  private void attachListener(ViewGroup viewGroup)
    {
        for (int i=0; i<viewGroup.getChildCount(); i++)
        {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup)
                attachListener(viewGroup);
            else if (view instanceof CheckBox)
                ((CheckBox)view).setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        }
    }*/

    private void extractRequireList() {
        JsonList jsonList;

        //for age
        String string = session.getString(P.age);
        try {
            JSONArray jsonArray = new JSONArray(string);
            ageList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                string = jsonArray.getString(i);
                ageList.add(string);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //for marital status
        string = session.getString(P.marital_status);
        if (string != null) {
            jsonList = new JsonList(string);
            maritalStatusNameList = new ArrayList<>();
            maritalStatusCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                maritalStatusNameList.add(string);

                string = j.getString(P.id);
                maritalStatusCodeList.add(string);
            }
        }

        //for ethnicity
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

        //for education
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

        if (view.getId() == R.id.minAgeEditText || view.getId() == R.id.maxAgeEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search age");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, ageList);
        } else if (view.getId() == R.id.educationEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search edit text");
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
        if (v.getId() == R.id.view)
            hideCustomSpinnerLayout();
        else if (v.getId() == R.id.button)
            makeJson();
        else if (v.getId() == R.id.sub_drawerMenu) {
            onMethodClick(v);
        } else
            setUpCustomSpinner(v);
    }

    private void makeJson() {
        Json json = new Json();
        json.addString(P.token_id, session.getString(P.tokenData));

        EditText editText = findViewById(R.id.minAgeEditText);
        String string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select minimum age");
            return;
        }
        int i = H.getInt(string);

        editText = findViewById(R.id.maxAgeEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select maximum age");
            return;
        }
        int j = H.getInt(string);

        if (j - i < 1) {
            H.showMessage(this, "Maximum age must be greater than minimum age");
            return;
        }
        json.addString(P.min_age, i + "");
        json.addString(P.max_age, j + "");


        i = ethnicityNameList.indexOf(string);
        if (i != -1)
            json.addString(P.ethnicitys_id, ethincityCodeList.get(i));

        editText = findViewById(R.id.educationEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select education");
            return;
        }
        i = educationNameList.indexOf(string);
        if (i != -1)
            json.addString(P.education_id, educationCodeList.get(i));

        string = ((CheckBox) findViewById(R.id.checkBox1)).isChecked() ? "1" : "0";
        json.addString(P.request_from_anyone, string);

        string = ((CheckBox) findViewById(R.id.checkBox2)).isChecked() ? "1" : "0";
        json.addString(P.request_from_preferred_match, string);

        string = ((CheckBox) findViewById(R.id.checkBox3)).isChecked() ? "1" : "0";
        json.addString(P.sancity_of_the_holy_quran, string);

        hitPerfectMatchApi(json);
    }

    private void hitPerfectMatchApi(Json json) {
        final LoadingDialog loadingDialog = new LoadingDialog(this);

        RequestModel requestModel = RequestModel.newRequestModel("perfect_match");
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (isLoading)
                            loadingDialog.show("Please wait submitting your data...");
                        else
                            loadingDialog.dismiss();
                    }
                })
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(PerfectMatchActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1) {
                            new Session(PerfectMatchActivity.this).addInt(P.full_register, 1);
                            Intent intent = new Intent(PerfectMatchActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else
                            H.showMessage(PerfectMatchActivity.this, json.getString(P.msg));
                    }
                })
                .run("hitPerfectMatchApi");
    }

    private void setMarginTopOfCustomSpinner() {
        LinearLayout linearLayout = findViewById(R.id.includeContainer);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        int i = new Session(this).getInt(P.statusBarHeight);
        layoutParams.topMargin = i;
        H.log("heightIs", i + "");
        linearLayout.setLayoutParams(layoutParams);
    }

    public void onMethodClick(View v) {

        finish();
        ((PerfectMatchActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }


    class myCheckBoxChnageClicker implements CheckBox.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            // Toast.makeText(CheckBoxCheckedDemo.this, &quot;Checked =&gt; &quot;+isChecked, Toast.LENGTH_SHORT).show();

            if(buttonView==((CheckBox)findViewById(R.id.neverMarriedBefore)) || buttonView==((CheckBox)findViewById(R.id.divorced)) || buttonView==((CheckBox)findViewById(R.id.married))
                    || buttonView==((CheckBox)findViewById(R.id.married))|| buttonView==((CheckBox)findViewById(R.id.widowed))|| buttonView==((CheckBox)findViewById(R.id.separated)))
            {
                if(isChecked)
                {
                    Toast.makeText(PerfectMatchActivity.this, buttonView.getTag().toString()+"selected", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(PerfectMatchActivity.this, buttonView.getTag().toString()+"unselected", Toast.LENGTH_SHORT).show();

                }

            }



            if(buttonView==((CheckBox)findViewById(R.id.arab)) || buttonView==((CheckBox)findViewById(R.id.african)) || buttonView==((CheckBox)findViewById(R.id.asian))
                    || buttonView==((CheckBox)findViewById(R.id.caucasian))|| buttonView==((CheckBox)findViewById(R.id.hispanic))|| buttonView==((CheckBox)findViewById(R.id.mixedRace))
                    || buttonView==((CheckBox)findViewById(R.id.parsian))|| buttonView==((CheckBox)findViewById(R.id.southAsian_indian)) || buttonView==((CheckBox)findViewById(R.id.southAsian_pakistani))
            )
            {
                if(isChecked)
                {
                    Toast.makeText(PerfectMatchActivity.this, buttonView.getTag().toString()+"selected", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(PerfectMatchActivity.this, buttonView.getTag().toString()+"unselected", Toast.LENGTH_SHORT).show();

                }

            }


            if(buttonView==((CheckBox)findViewById(R.id.neverMarriedBefore)) || buttonView==((CheckBox)findViewById(R.id.divorced)) || buttonView==((CheckBox)findViewById(R.id.married))
                    || buttonView==((CheckBox)findViewById(R.id.married))|| buttonView==((CheckBox)findViewById(R.id.widowed))|| buttonView==((CheckBox)findViewById(R.id.separated)))
            {
                if(isChecked)
                {
                    Toast.makeText(PerfectMatchActivity.this, buttonView.getTag().toString()+"selected", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(PerfectMatchActivity.this, buttonView.getTag().toString()+"unselected", Toast.LENGTH_SHORT).show();

                }

            }

            if(buttonView==(CheckBox)findViewById(R.id.other))
            {
                if(isChecked)
                {
                    findViewById(R.id.otherEthnicity_layout).setVisibility(View.VISIBLE);

                }
                else
                {
                    findViewById(R.id.otherEthnicity_layout).setVisibility(View.GONE);
                }


            }

            if(buttonView==(CheckBox)findViewById(R.id.checkBox1)||buttonView==(CheckBox)findViewById(R.id.checkBox2)|buttonView==(CheckBox)findViewById(R.id.checkBox3))
            {
                if(isChecked)
                {


                }
                else
                {

                }


            }



        }
    }
}
package com.nikha.shianikha.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.shianikha.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.CommonListHolder;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;

import org.json.JSONArray;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    ImageView back_navigation_icon;
    RelativeLayout annual_income_link_layout;
    RelativeLayout marital_status_link_layout;
    RelativeLayout shia_community_link_layout;
    RelativeLayout mother_tongue_link_layout;
    RelativeLayout country_living_in_link_layout;
    RelativeLayout state_living_in_link_layout;


    RelativeLayout education_link_layout;
    RelativeLayout working_with_link_layout;
    RelativeLayout smoking_link_layout;

    ExpandableRelativeLayout annual_income_exp_layout;
    ExpandableRelativeLayout marital_status_exp_layout;
    ExpandableRelativeLayout shia_comunity_exp_layout;
    ExpandableRelativeLayout mothertongue_exp_layout;
    ExpandableRelativeLayout country_living_in_exp_layout;
    ExpandableRelativeLayout state_living_in_exp_layout;
    ExpandableRelativeLayout education_exp_layout;
    ExpandableRelativeLayout working_with_exp_layout;
    ExpandableRelativeLayout smoking_exp_layout;

    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        back_navigation_icon = findViewById(R.id.back_navigation_icon);

        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));

        session = new Session(this);

        //annual_income_item_layout = findViewById(R.id.annual_income_container_layout);

        annual_income_link_layout = (RelativeLayout) findViewById(R.id.annual_income_link_layout);
        marital_status_link_layout = (RelativeLayout) findViewById(R.id.marital_status_link_layout);
        shia_community_link_layout = (RelativeLayout) findViewById(R.id.shia_community_layout);
        mother_tongue_link_layout = (RelativeLayout) findViewById(R.id.mother_tongue_link_layout);
        country_living_in_link_layout = (RelativeLayout) findViewById(R.id.country_living_in_layout);
        state_living_in_link_layout = (RelativeLayout) findViewById(R.id.state_living_in_link_layout);
        education_link_layout = (RelativeLayout) findViewById(R.id.education_link_layout);
        working_with_link_layout = (RelativeLayout) findViewById(R.id.working_with_layout);
        smoking_link_layout = (RelativeLayout) findViewById(R.id.smoking_link_layout);


        annual_income_link_layout.setOnClickListener(this);
        marital_status_link_layout.setOnClickListener(this);
        shia_community_link_layout.setOnClickListener(this);
        mother_tongue_link_layout.setOnClickListener(this);
        country_living_in_link_layout.setOnClickListener(this);
        state_living_in_link_layout.setOnClickListener(this);
        education_link_layout.setOnClickListener(this);
        working_with_link_layout.setOnClickListener(this);
        smoking_link_layout.setOnClickListener(this);

        findViewById(R.id.btn_apply).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);


        annual_income_exp_layout = findViewById(R.id.annual_income_exp_layout);
        marital_status_exp_layout = findViewById(R.id.marital_status_exp_layout);
        shia_comunity_exp_layout = findViewById(R.id.shia_community_exp_layout);
        mothertongue_exp_layout = findViewById(R.id.mother_tongue_exp_layout);
        country_living_in_exp_layout = findViewById(R.id.country_living_in_exp_layout);
        state_living_in_exp_layout = findViewById(R.id.state_living_in_exp_layout);
        education_exp_layout = findViewById(R.id.educatoin_exp_layout);
        working_with_exp_layout = findViewById(R.id.working_with_exp_layout);
        smoking_exp_layout = findViewById(R.id.smoking_exp_layout);


        annual_income_exp_layout.collapse();
        marital_status_exp_layout.collapse();
        shia_comunity_exp_layout.collapse();
        mothertongue_exp_layout.collapse();
        country_living_in_exp_layout.collapse();
        state_living_in_exp_layout.collapse();
        education_exp_layout.collapse();
        working_with_exp_layout.collapse();
        smoking_exp_layout.collapse();


        annual_income_exp_layout.setOnClickListener(this);
        marital_status_exp_layout.setOnClickListener(this);
        shia_comunity_exp_layout.setOnClickListener(this);
        mothertongue_exp_layout.setOnClickListener(this);
        country_living_in_exp_layout.setOnClickListener(this);
        state_living_in_exp_layout.setOnClickListener(this);
        education_exp_layout.setOnClickListener(this);
        working_with_exp_layout.setOnClickListener(this);
        smoking_exp_layout.setOnClickListener(this);

        back_navigation_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        annualIncomeCheckBox();
        maritalStatusCheckBox();
        shiaCommunityCheckBox();
        mothertongueCheckBox();
        countryLivingInCheckBox();
        stateLivingInCheckBox();
        educationLivingInCheckBox();
        workingWithCheckBox();
        smokingCheckBox();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_apply)
            makeJson();
        else if(v.getId() == R.id.btn_cancel)
        {
            finish();
        }

        else if (v.getId() == R.id.annual_income_link_layout) {
            annual_income_exp_layout.toggle();
            /*Intent i=new Intent(FilterActivity.this,RefineSearchItemActivity.class);
            i.putExtra("filter_title","Active Members");
            startActivity(i);
            finish();*/
        } else if (v.getId() == R.id.marital_status_link_layout) {
            marital_status_exp_layout.toggle();
        } else if (v.getId() == R.id.shia_community_layout) {
            shia_comunity_exp_layout.toggle();
        } else if (v.getId() == R.id.mother_tongue_link_layout) {
            mothertongue_exp_layout.toggle();
        } else if (v.getId() == R.id.country_living_in_layout) {
            country_living_in_exp_layout.toggle();
        } else if (v.getId() == R.id.state_living_in_link_layout) {
            state_living_in_exp_layout.toggle();
        } else if (v.getId() == R.id.education_link_layout) {
            education_exp_layout.toggle();
        } else if (v.getId() == R.id.working_with_layout) {
            working_with_exp_layout.toggle();
        } else if (v.getId() == R.id.smoking_link_layout) {
            smoking_exp_layout.toggle();
        }
    }

    private void annualIncomeCheckBox() {
        int n = CommonListHolder.monthlyIncomeNameList.size();

        LinearLayout linearLayout = findViewById(R.id.annual_income_container_layout);
        for (int j = 0; j < n; j++) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.inflatable_check_box, null);
            CheckBox checkBox = (CheckBox) ll.getChildAt(0);
            checkBox.setText(CommonListHolder.monthlyIncomeNameList.get(j));
            checkBox.setTag(CommonListHolder.monthlyIncomeIdList.get(j));
            checkBox.setOnCheckedChangeListener(this);
            linearLayout.addView(ll);
        }
    }

    private void maritalStatusCheckBox() {
        int n = CommonListHolder.maritalStatusNameList.size();

        LinearLayout linearLayout = findViewById(R.id.marital_status_container_layout);
        for (int j = 0; j < n; j++) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.inflatable_check_box, null);
            CheckBox checkBox = (CheckBox) ll.getChildAt(0);
            checkBox.setText(CommonListHolder.maritalStatusNameList.get(j));
            checkBox.setTag(CommonListHolder.maritalStatusIdList.get(j));
            checkBox.setOnCheckedChangeListener(this);
            linearLayout.addView(ll);
        }
    }

    private void shiaCommunityCheckBox() {
        int n = CommonListHolder.religionNameList.size();

        LinearLayout linearLayout = findViewById(R.id.shia_community_container_layout);
        for (int j = 0; j < n; j++) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.inflatable_check_box, null);
            CheckBox checkBox = (CheckBox) ll.getChildAt(0);
            checkBox.setText(CommonListHolder.religionNameList.get(j));
            checkBox.setTag(CommonListHolder.religionIdList.get(j));
            checkBox.setOnCheckedChangeListener(this);
            linearLayout.addView(ll);
        }
    }

    private void mothertongueCheckBox() {
        int n = CommonListHolder.languageNameList.size();

        LinearLayout linearLayout = findViewById(R.id.mothertongue_container_layout);
        for (int j = 0; j < n; j++) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.inflatable_check_box, null);
            CheckBox checkBox = (CheckBox) ll.getChildAt(0);
            checkBox.setText(CommonListHolder.languageNameList.get(j));
            checkBox.setTag(CommonListHolder.languageIdList.get(j));
            checkBox.setOnCheckedChangeListener(this);
            linearLayout.addView(ll);
        }
    }

    private void countryLivingInCheckBox() {
        int n = CommonListHolder.countryNameList.size();

        LinearLayout linearLayout = findViewById(R.id.country_living_in_container_layout);
        for (int j = 0; j < n; j++) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.inflatable_check_box, null);
            CheckBox checkBox = (CheckBox) ll.getChildAt(0);
            checkBox.setText(CommonListHolder.countryNameList.get(j));
            checkBox.setTag(CommonListHolder.countryIdList.get(j));
            checkBox.setOnCheckedChangeListener(this);
            linearLayout.addView(ll);
        }
    }

    private void stateLivingInCheckBox() {
        int n = CommonListHolder.stateNameList.size();

        LinearLayout linearLayout = findViewById(R.id.state_living_in_container_layout);
        for (int j = 0; j < n; j++) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.inflatable_check_box, null);
            CheckBox checkBox = (CheckBox) ll.getChildAt(0);
            checkBox.setText(CommonListHolder.stateNameList.get(j));
            checkBox.setTag(CommonListHolder.stateIdList.get(j));
            checkBox.setOnCheckedChangeListener(this);
            linearLayout.addView(ll);
        }
    }

    private void educationLivingInCheckBox() {
        int n = CommonListHolder.educationNameList.size();

        LinearLayout linearLayout = findViewById(R.id.education_container_layout);
        for (int j = 0; j < n; j++) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.inflatable_check_box, null);
            CheckBox checkBox = (CheckBox) ll.getChildAt(0);
            checkBox.setText(CommonListHolder.educationNameList.get(j));
            checkBox.setTag(CommonListHolder.educationIdList.get(j));
            checkBox.setOnCheckedChangeListener(this);
            linearLayout.addView(ll);
        }
    }

    private void workingWithCheckBox() {
        int n = CommonListHolder.occupationNameList.size();

        LinearLayout linearLayout = findViewById(R.id.working_with_container_layout);
        for (int j = 0; j < n; j++) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.inflatable_check_box, null);
            CheckBox checkBox = (CheckBox) ll.getChildAt(0);
            checkBox.setText(CommonListHolder.occupationNameList.get(j));
            checkBox.setTag(CommonListHolder.occupationIdList.get(j));
            checkBox.setOnCheckedChangeListener(this);
            linearLayout.addView(ll);
        }
    }

    private void smokingCheckBox() {

        int n = CommonListHolder.smokingNameList.size();

        LinearLayout linearLayout = findViewById(R.id.smoking_container_layout);

        for (int j = 0; j < n; j++)
        {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.inflatable_check_box, null);
            CheckBox checkBox = (CheckBox) ll.getChildAt(0);
            checkBox.setText(CommonListHolder.smokingNameList.get(j));
            checkBox.setTag(CommonListHolder.smokingIdList.get(j));
            checkBox.setOnCheckedChangeListener(this);
            linearLayout.addView(ll);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    private void makeJson() {

        Json json = new Json();
        json.addString(P.token_id, session.getString(P.tokenData));


        JSONArray jsonArray = new JSONArray();
        annualIncomeJsonArray(jsonArray);
        json.addJSONArray(P.monthly_income_id, jsonArray);


        jsonArray = new JSONArray();
        makeMaritalStatusJsonArray(jsonArray);
        json.addJSONArray(P.marital_status, jsonArray);

        jsonArray = new JSONArray();
        shiaCommunityJsonArray(jsonArray);
        json.addJSONArray(P.occupation_id, jsonArray);

        jsonArray = new JSONArray();
        shiaCommunityJsonArray(jsonArray);
        json.addJSONArray(P.religion_id, jsonArray);

        jsonArray = new JSONArray();
        motherTongueJsonArray(jsonArray);
        json.addJSONArray(P.mother_tongue_id, jsonArray);

        jsonArray = new JSONArray();
        countryLivingInJsonArray(jsonArray);
        json.addJSONArray(P.country, jsonArray);

        jsonArray = new JSONArray();
        stateLivingInJsonArray(jsonArray);
        json.addJSONArray(P.state, jsonArray);

        jsonArray = new JSONArray();
        educatoinJsonArray(jsonArray);
        json.addJSONArray(P.edulevel_id, jsonArray);

        jsonArray = new JSONArray();
        workingWithJsonArray(jsonArray);
        json.addJSONArray(P.occupation_id, jsonArray);

        jsonArray = new JSONArray();
        smokingJsonArray(jsonArray);
        json.addJSONArray(P.smoke_id, jsonArray);

        hitRefineSearchhApi(json);

    }

    private void annualIncomeJsonArray(JSONArray jsonArray)
    {
        LinearLayout linearLayout = findViewById(R.id.annual_income_container_layout);
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
    }
    private void makeMaritalStatusJsonArray(JSONArray jsonArray)
    {
        LinearLayout linearLayout = findViewById(R.id.marital_status_container_layout);
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
    }

    private void shiaCommunityJsonArray(JSONArray jsonArray)
    {
        LinearLayout linearLayout = findViewById(R.id.shia_community_container_layout);
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
    }
    private void motherTongueJsonArray(JSONArray jsonArray)
    {
        LinearLayout linearLayout = findViewById(R.id.mothertongue_container_layout);
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
    }

    private void countryLivingInJsonArray(JSONArray jsonArray)
    {
        LinearLayout linearLayout = findViewById(R.id.country_living_in_container_layout);
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
    }
    private void stateLivingInJsonArray(JSONArray jsonArray)
    {
        LinearLayout linearLayout = findViewById(R.id.state_living_in_container_layout);
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
    }
    private void educatoinJsonArray(JSONArray jsonArray)
    {
        LinearLayout linearLayout = findViewById(R.id.education_container_layout);
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
    }

    private void workingWithJsonArray(JSONArray jsonArray)
    {
        LinearLayout linearLayout = findViewById(R.id.working_with_container_layout);
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
    }

    private void smokingJsonArray(JSONArray jsonArray)
    {
        LinearLayout linearLayout = findViewById(R.id.smoking_container_layout);
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
    }

    private void hitRefineSearchhApi(Json json) {
        final LoadingDialog loadingDialog = new LoadingDialog(this);

        RequestModel requestModel = RequestModel.newRequestModel("search");
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
                        H.showMessage(FilterActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1) {

                        } else
                            H.showMessage(FilterActivity.this, json.getString(P.msg));
                    }
                })
                .run("hitPerfectMatchApi");
    }

}

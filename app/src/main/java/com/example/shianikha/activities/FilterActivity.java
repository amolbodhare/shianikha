package com.example.shianikha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.shianikha.R;
import com.example.shianikha.commen.CommonListHolder;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener

{

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
    ExpandableRelativeLayout mothertongue__exp_layout;
    ExpandableRelativeLayout country_living_in__exp_layout;
    ExpandableRelativeLayout state_living_in_exp_layout;
    ExpandableRelativeLayout education_exp_layout;
    ExpandableRelativeLayout working_with__exp_layout;
    ExpandableRelativeLayout smoking_exp_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        back_navigation_icon=findViewById(R.id.back_navigation_icon);





        //annual_income_item_layout = findViewById(R.id.annual_income_container_layout);


        annual_income_link_layout=(RelativeLayout)findViewById(R.id.annual_income_link_layout);
        marital_status_link_layout=(RelativeLayout)findViewById(R.id.marital_status_link_layout);
        shia_community_link_layout=(RelativeLayout)findViewById(R.id.shia_community_layout);
        mother_tongue_link_layout=(RelativeLayout)findViewById(R.id.mother_tongue_link_layout);
        country_living_in_link_layout=(RelativeLayout)findViewById(R.id.country_living_in_layout);
        state_living_in_link_layout=(RelativeLayout)findViewById(R.id.state_layout);
        education_link_layout=(RelativeLayout)findViewById(R.id.education_link_layout);
        working_with_link_layout=(RelativeLayout)findViewById(R.id.working_with_layout);
        smoking_link_layout=(RelativeLayout)findViewById(R.id.smoking_link_layout);



        annual_income_exp_layout.collapse();
        marital_status_exp_layout.collapse();
        shia_comunity_exp_layout.collapse();
        mothertongue__exp_layout.collapse();
        country_living_in__exp_layout.collapse();
        state_living_in_exp_layout.collapse();
        education_exp_layout.collapse();
        working_with__exp_layout.collapse();
        smoking_exp_layout.collapse();


        //edit_password_tv_btn.setOnClickListener(this);

        annual_income_link_layout.setOnClickListener(this);
        marital_status_link_layout.setOnClickListener(this);
        shia_community_link_layout.setOnClickListener(this);
        mother_tongue_link_layout.setOnClickListener(this);
        country_living_in_link_layout.setOnClickListener(this);
        state_living_in_link_layout.setOnClickListener(this);
        education_link_layout.setOnClickListener(this);
        working_with_link_layout.setOnClickListener(this);
        smoking_link_layout.setOnClickListener(this);


        annual_income_exp_layout.setOnClickListener(this);
        marital_status_exp_layout.setOnClickListener(this);
        shia_comunity_exp_layout.setOnClickListener(this);
        mothertongue__exp_layout.setOnClickListener(this);
        country_living_in__exp_layout.setOnClickListener(this);
        state_living_in_exp_layout.setOnClickListener(this);
        education_exp_layout.setOnClickListener(this);
        working_with__exp_layout.setOnClickListener(this);
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
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.annual_income_link_layout)
        {
            annual_income_exp_layout.toggle();
            /*Intent i=new Intent(FilterActivity.this,RefineSearchItemActivity.class);
            i.putExtra("filter_title","Active Members");
            startActivity(i);
            finish();*/
        }
        else if(v.getId()==R.id.marital_status_link_layout)
        {
           marital_status_exp_layout.toggle();
        }
    }

    private void annualIncomeCheckBox()
    {
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

    private void maritalStatusCheckBox()
    {
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

    private void shiaCommunityCheckBox()
    {
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

    private void mothertongueCheckBox()
    {
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

    private void countryLivingInCheckBox()
    {
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


    private void stateLivingInCheckBox()
    {
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
    private void educationLivingInCheckBox()
    {
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
    private void workingWithCheckBox()
    {
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

    private void smokingCheckBox()
    {
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}

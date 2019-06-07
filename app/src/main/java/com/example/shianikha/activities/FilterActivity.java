package com.example.shianikha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.shianikha.R;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener

{

    ImageView back_navigation_icon;
    RelativeLayout active_members_link_layout;
    RelativeLayout annual_income_layout;
    RelativeLayout marital_status_link_layout;
    RelativeLayout religion_link_layout;
    RelativeLayout mother_tongue_link_layout;
    RelativeLayout country_living_in_link_layout;
    RelativeLayout state_living_in_link_layout;
    RelativeLayout education_link_layout;
    RelativeLayout working_with_link_layout;
    RelativeLayout profession_area_link_layout;
    RelativeLayout profile_created_by_link_layout;
    RelativeLayout smoking_link_layout;
    RelativeLayout drinking_link_layout;
    RelativeLayout eating_habits_link_layout;
    RelativeLayout community_link_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        back_navigation_icon=findViewById(R.id.back_navigation_icon);
        active_members_link_layout=(RelativeLayout)findViewById(R.id.active_members_link_layout);
        annual_income_layout=(RelativeLayout)findViewById(R.id.annual_income_link_layout);
        marital_status_link_layout=(RelativeLayout)findViewById(R.id.marital_status_link_layout);
        religion_link_layout=(RelativeLayout)findViewById(R.id.religion_link_layout);
        mother_tongue_link_layout=(RelativeLayout)findViewById(R.id.mother_tongue_link_layout);
        country_living_in_link_layout=(RelativeLayout)findViewById(R.id.country_living_in_layout);

        state_living_in_link_layout=(RelativeLayout)findViewById(R.id.state_layout);
        education_link_layout=(RelativeLayout)findViewById(R.id.education_link_layout);
        working_with_link_layout=(RelativeLayout)findViewById(R.id.working_with_layout);

        profession_area_link_layout=(RelativeLayout)findViewById(R.id.professional_area_link_layout);
        profile_created_by_link_layout=(RelativeLayout)findViewById(R.id.profile_created_by_link_layout);
        smoking_link_layout=(RelativeLayout)findViewById(R.id.smoking_link_layout);

        drinking_link_layout=(RelativeLayout)findViewById(R.id.drinking_link_layout);
        eating_habits_link_layout=(RelativeLayout)findViewById(R.id.eating_habits_layout);
        community_link_layout=(RelativeLayout)findViewById(R.id.community_layout);


        active_members_link_layout.setOnClickListener(this);

        back_navigation_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.active_members_link_layout)
        {
            Intent i=new Intent(FilterActivity.this,RefineSearchItemActivity.class);
            i.putExtra("filter_title","Active Members");
            startActivity(i);
        }
    }
}

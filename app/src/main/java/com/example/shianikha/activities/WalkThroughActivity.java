package com.example.shianikha.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.shianikha.R;
import com.example.shianikha.adapters.SliderAdapter;

public class WalkThroughActivity extends AppCompatActivity {

    private ViewPager slideViewPager;
    private SliderAdapter sliderAdapter;
    Button btn_login,btn_reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        slideViewPager=(ViewPager)findViewById(R.id.slideViewPager);
        sliderAdapter=new SliderAdapter(this);
        slideViewPager.setAdapter(sliderAdapter);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_reg=(Button)findViewById(R.id.btn_reg);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layoutt);
        tabLayout.setupWithViewPager(slideViewPager, true);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(WalkThroughActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(WalkThroughActivity.this,RegisterationActivity.class);
                startActivity(i);
            }
        });

    }
}

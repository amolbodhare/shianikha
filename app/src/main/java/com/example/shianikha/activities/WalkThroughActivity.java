package com.example.shianikha.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.example.shianikha.R;
import com.example.shianikha.adapters.SliderAdapter;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;

public class WalkThroughActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager slideViewPager;
    private SliderAdapter sliderAdapter;
    Button btn_login, btn_reg;
    private LoadingDialog loadingDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);

        context = WalkThroughActivity.this;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        slideViewPager = findViewById(R.id.slideViewPager);
        sliderAdapter = new SliderAdapter(context);
        slideViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position)
            {

                page.setCameraDistance(20000);


                if (position < -1){
                    page.setAlpha(0);
                }
                else if (position <= 0)
                {
                    page.setAlpha(1);
                    page.setPivotX(page.getWidth());
                    page.setRotationY(90*Math.abs(position));
                }
                else if (position <= 1)
                {
                    page.setAlpha(1);
                    page.setPivotX(0);
                    page.setRotationY(-90*Math.abs(position));
                }
                else{
                    page.setAlpha(0);
                }


                if (Math.abs(position) <= 0.5){
                    page.setScaleY(Math.max(.4f,1-Math.abs(position)));
                }
                else if (Math.abs(position) <= 1){
                    page.setScaleY(Math.max(.4f,1-Math.abs(position)));

                }
            }
        });

        slideViewPager.setAdapter(sliderAdapter);
        btn_login = findViewById(R.id.btn_login);
        btn_reg = findViewById(R.id.btn_reg);


        loadingDialog = new LoadingDialog(this);

        TabLayout tabLayout = findViewById(R.id.tab_layoutt);
        tabLayout.setupWithViewPager(slideViewPager, true);
        btn_login.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btn_login)
        {
            Intent i = new Intent(WalkThroughActivity.this, LoginActivity.class);
            startActivity(i);
        }
        else if (v.getId() == R.id.btn_reg)
        {
            Intent i = new Intent(WalkThroughActivity.this, RegistrationActivity.class);
            startActivity(i);
        }
        //finish();
    }
}

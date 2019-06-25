package com.example.shianikha.activities;

import android.content.Context;
import android.content.Intent;
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
        slideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        sliderAdapter = new SliderAdapter(this);
        slideViewPager.setAdapter(sliderAdapter);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_reg = (Button) findViewById(R.id.btn_reg);


        loadingDialog = new LoadingDialog(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layoutt);
        tabLayout.setupWithViewPager(slideViewPager, true);
        btn_login.setOnClickListener(this);
        btn_reg.setOnClickListener(this);

        /*btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                *//*Intent i=new Intent(WalkThroughActivity.this,LoginActivity.class);
                startActivity(i);*//*
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                *//*Intent i=new Intent(WalkThroughActivity.this,RegistrationActivity.class);
                startActivity(i);*//*
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            Intent i = new Intent(WalkThroughActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        } else if (v.getId() == R.id.btn_reg) {
            hitMastersApi();
        }

    }

    private void hitMastersApi() {
        Json json = new Json();

        RequestModel requestModel = RequestModel.newRequestModel("masters");
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(WalkThroughActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {
                        if (json.getInt(P.status) == 1)
                        {
                            json = json.getJson(P.data);
                            makeSeperateJsonList(json);
                            Intent i = new Intent(WalkThroughActivity.this, RegistrationActivity.class);
                            startActivity(i);
                            finish();
                        } else
                            H.showMessage(WalkThroughActivity.this, json.getString(P.msg));
                    }
                }).onLoading(new Api.OnLoadingListener() {
            @Override
            public void onLoading(boolean isLoading) {
                if (isLoading)
                    loadingDialog.show("Please wait...");
                else
                    loadingDialog.dismiss();
            }
        })
                .run("hitMastersApi");
    }

    private void makeSeperateJsonList(Json json)
    {
        if (json==null)
            return;

        Session session = new Session(this);

        JsonList profileForList = json.getJsonList(P.profile_for);
        if (profileForList!=null)
            session.addString(P.profile_for,profileForList.toString());

        JsonList countryPinCodeList = json.getJsonList(P.country_code);
        if (countryPinCodeList!=null)
            session.addString(P.country_code,countryPinCodeList.toString());

    }
}

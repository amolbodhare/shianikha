package com.nikha.shianikha.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.shianikha.R;
import com.nikha.shianikha.adapters.SubscriptionSliderAdapter;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;

import org.json.JSONArray;
import org.json.JSONException;

public class SubscriptionActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));

        findViewById(R.id.prev_imv).setOnClickListener(this);
        findViewById(R.id.next_imv).setOnClickListener(this);

        hitSubscriptionPlanApi();
    }

    private void hitSubscriptionPlanApi() {
        final LoadingDialog loadingDialog = new LoadingDialog(this);

        RequestModel requestModel = RequestModel.newRequestModel("plan_list");
        Json json = new Json();
        json.addString(P.token_id, new Session(this).getString(P.tokenData));
        requestModel.addJSON(P.data, json);
        //5XtinfoxR0jl2d4JQGKvzVwNc

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (!isDestroyed()) {
                            if (isLoading)
                                loadingDialog.show("Please wait...");
                            else
                                loadingDialog.dismiss();
                        }
                    }
                })
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(SubscriptionActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {
                        if (json.getInt(P.status) == 1) {

                            try {
                                showDropDown(json);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            /*int i = json.getInt(P.currency_type);
                            JsonList jsonList = json.getJsonList(P.data);
                            if (jsonList != null) {
                                subPlanSliderAdapter = new SubscriptionSliderAdapter(SubscriptionActivity.this, jsonList, i);
                                subPlanSlideViewPager.setAdapter(subPlanSliderAdapter);
                            }*/
                        }
                    }
                })
                .run("hitSubscriptionPlanApi");
    }

    private void showDropDown(Json json) throws JSONException {
        String string = json.getString(P.default_currency);
        final Json j = json.getJson(P.data);
        JSONArray jsonArray = j.getJsonArray(P.currency_list);
        String[] strings = new String[jsonArray.length()];
        int k = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            strings[i] = jsonArray.getString(i);
            if (strings[i].equalsIgnoreCase(string))
                k = i;
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strings);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(k);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Json json1 = j.getJson(P.plan_list);
                String str = ((TextView) view).getText().toString() + "";
                JsonList jsonList = json1.getJsonList(str);
                H.log("jsonListIs", jsonList + "");
                if (str.equalsIgnoreCase("inr"))
                    ((ViewPager) findViewById(R.id.sub_plan_viewpager)).setAdapter(new SubscriptionSliderAdapter(SubscriptionActivity.this, jsonList, 1));
                else
                    ((ViewPager) findViewById(R.id.sub_plan_viewpager)).setAdapter(new SubscriptionSliderAdapter(SubscriptionActivity.this, jsonList, 0));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.prev_imv) {
            ((ViewPager) findViewById(R.id.sub_plan_viewpager)).setCurrentItem(getItem(-1), true);
        } else if (v.getId() == R.id.next_imv) {
            ((ViewPager) findViewById(R.id.sub_plan_viewpager)).setCurrentItem(getItem(+1), true);
        } else if (v.getId() == R.id.sub_drawerMenu) {

            onMethodClick(v);
        }
    }

    public void onMethodClick(View v) {

        finish();
        ((SubscriptionActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }

    private int getItem(int i) {
        return ((ViewPager) findViewById(R.id.sub_plan_viewpager)).getCurrentItem() + i;
    }
}

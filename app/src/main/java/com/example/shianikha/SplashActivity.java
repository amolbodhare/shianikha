package com.example.shianikha;

import android.Manifest;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.Session;
import com.example.shianikha.activities.HomeActivity;
import com.example.shianikha.activities.RegThirdPageActivity;
import com.example.shianikha.activities.WalkThroughActivity;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.CommonListHolder;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;

import org.json.JSONArray;

public class SplashActivity extends AppCompatActivity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        session = new Session(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, 31);

        //to get height of status bar
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Rect rectangle = new Rect();
                Window window = getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
                int statusBarHeight = rectangle.top;
                if (statusBarHeight == 0)
                    statusBarHeight = 70;
                new Session(SplashActivity.this).addInt(P.statusBarHeight, statusBarHeight);
            }
        }, 987);

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
                        H.showMessage(SplashActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {
                        if (json.getInt(P.status) == 1) {
                            json = json.getJson(P.data);
                            makeSeperateJsonList(json);

                        } else
                            H.showMessage(SplashActivity.this, json.getString(P.msg));
                    }
                })
                .run("hitMastersApi");


    }

    private void makeSeperateJsonList(Json json) {
        if (json == null)
            return;

        CommonListHolder commonListHolder = new CommonListHolder();

        JsonList jsonList = json.getJsonList(P.profile_for);//for whom
        if (jsonList != null) {
            session.addString(P.profile_for, jsonList.toString());
            commonListHolder.makeProfileForList(jsonList);
        }

        jsonList = json.getJsonList(P.city);// city name and code
        if (jsonList != null) {
            session.addString(P.city, jsonList.toString());
            commonListHolder.makeCityList(jsonList);
        }

        jsonList = json.getJsonList(P.state);// state name and code
        if (jsonList != null) {
            session.addString(P.state, jsonList.toString());
            commonListHolder.makeStateList(jsonList);
        }

        jsonList = json.getJsonList(P.country);// country name and code
        if (jsonList != null) {
            session.addString(P.country, jsonList.toString());
            commonListHolder.makeCountryList(jsonList);
        }

        JSONArray jsonArray = json.getJsonArray(P.height);// height array
        if (jsonArray != null) {
            session.addString(P.height, jsonArray.toString());
            commonListHolder.makeHeightList(jsonArray);
        }

        jsonList = json.getJsonList(P.religion);// religion name and code
        if (jsonList != null) {
            session.addString(P.religion, jsonList.toString());
            commonListHolder.makeReligionList(jsonList);
        }

        jsonList = json.getJsonList(P.ethnicity);// ethnicity name and code
        if (jsonList != null) {
            session.addString(P.ethnicity, jsonList.toString());
            commonListHolder.makeEthnicityList(jsonList);
        }

        jsonList = json.getJsonList(P.occupation);// occupation name and code
        if (jsonList != null) {
            session.addString(P.occupation, jsonList.toString());
            commonListHolder.makeOccupationList(jsonList);
        }

        jsonList = json.getJsonList(P.education);// education name and code
        if (jsonList != null) {
            session.addString(P.education, jsonList.toString());
            commonListHolder.makeEducationList(jsonList);
        }

        jsonList = json.getJsonList(P.mothertongue);// language name and code
        if (jsonList != null) {
            session.addString(P.language, jsonList.toString());
            commonListHolder.makeMotherTongueList(jsonList);
        }

        jsonList = json.getJsonList(P.smoking);// smoking name and code
        if (jsonList != null) {
            session.addString(P.smoking, jsonList.toString());
            commonListHolder.makeSmokingList(jsonList);
        }

        jsonList = json.getJsonList(P.relocate);// relocate name and code
        if (jsonList != null) {
            session.addString(P.relocate, jsonList.toString());
            commonListHolder.makeRelocateList(jsonList);
        }

        jsonList = json.getJsonList(P.seeking_marriage);// marriage name and code
        if (jsonList != null) {
            session.addString(P.seeking_marriage, jsonList.toString());
            commonListHolder.makeSeekingForMarriageList(jsonList);
        }

        jsonList = json.getJsonList(P.intreasted_in);// interrest name and code
        if (jsonList != null) {
            session.addString(P.intreasted_in, jsonList.toString());
            commonListHolder.makeIntrestedInList(jsonList);
        }

        jsonList = json.getJsonList(P.complexion);// complexion name and code
        if (jsonList != null) {
            session.addString(P.complexion, jsonList.toString());
            commonListHolder.makeComplexionList(jsonList);
        }

        jsonArray = json.getJsonArray(P.min_age);// age array, note: min and max array have same values so keeping only one
        if (jsonArray != null) {
            session.addString(P.age, jsonArray.toString());
            commonListHolder.makeAgeList(jsonArray);
        }

        jsonList = json.getJsonList(P.marital_status);// marital status name and code
        if (jsonList != null) {
            session.addString(P.marital_status, jsonList.toString());
            commonListHolder.makeMaritalStatusList(jsonList);
        }

        jsonList = json.getJsonList(P.physical_status);// physical status name and code
        if (jsonList != null) {
            session.addString(P.physical_status, jsonList.toString());
            commonListHolder.makePhysicalStatusList(jsonList);
        }

        jsonList = json.getJsonList(P.monthly_income);// physical status name and code
        if (jsonList != null)
            commonListHolder.makeMonthlyIncomeList(jsonList);


        startNewActivity();
    }

    private void startNewActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent intent;
                String string = session.getString(P.tokenData);
                H.log("tokenIs", string);
                if (string == null || string.isEmpty())
                    intent = new Intent(SplashActivity.this, WalkThroughActivity.class);
                else if (session.getInt(P.full_register) == 0)
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                else
                    intent = new Intent(SplashActivity.this, HomeActivity.class);

                //intent = new Intent(SplashActivity.this, RegThirdPageActivity.class);

                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        hitMastersApi();
    }
}

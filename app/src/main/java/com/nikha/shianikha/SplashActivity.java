package com.nikha.shianikha;

import android.Manifest;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.nikha.App;
import com.nikha.shianikha.activities.HomeActivity;
import com.nikha.shianikha.activities.RegSecondPageActivity;
import com.nikha.shianikha.activities.WalkThroughActivity;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.CommonListHolder;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;

import org.json.JSONArray;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private Session session;
    private String profileId = "";
    private String sharableLink = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        session = new Session(this);
        generateFcmToken();
        int i = session.getInt(P.showName);
        App.showName = i == 1 ? true : false;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 31);

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

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        H.log("appLinkData", appLinkData + "");
        if (appLinkData != null) {
            /*String string = session.getString(P.tokenData);
            if (string==null || string.isEmpty())
            {
                H.showMessage(this,"You are not logged in");
                return;
            }*/

            String string = appLinkData.toString();
            sharableLink = string;
            /*profileId = string.substring(string.lastIndexOf("/")+1);
            H.log("profileIdIs",profileId);*/
        }

        //handleIntent(getIntent());
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
            //session.addString(P.profile_for, jsonList.toString());
            commonListHolder.makeProfileForList(jsonList);
        }

        jsonList = json.getJsonList(P.city);// city name and code
        if (jsonList != null) {
            //session.addString(P.city, jsonList.toString());
            commonListHolder.makeCityList(jsonList);
        }

        jsonList = json.getJsonList(P.state);// state name and code
        if (jsonList != null) {
            //session.addString(P.state, jsonList.toString());
            commonListHolder.makeStateList(jsonList);
        }

        jsonList = json.getJsonList(P.country);// country name and code
        if (jsonList != null) {
            session.addString(P.country, jsonList.toString());
            commonListHolder.makeCountryList(jsonList);
        }

        JSONArray jsonArray = json.getJsonArray(P.height);// height array
        if (jsonArray != null) {
            //session.addString(P.height, jsonArray.toString());
            commonListHolder.makeHeightList(jsonArray);
        }

        jsonList = json.getJsonList(P.religion);// religion name and code
        if (jsonList != null) {
            //session.addString(P.religion, jsonList.toString());
            commonListHolder.makeReligionList(jsonList);
        }

        jsonList = json.getJsonList(P.ethnicity);// ethnicity name and code
        if (jsonList != null) {
            //session.addString(P.ethnicity, jsonList.toString());
            commonListHolder.makeEthnicityList(jsonList);
        }

        jsonList = json.getJsonList(P.occupation);// occupation name and code
        if (jsonList != null) {
            //session.addString(P.occupation, jsonList.toString());
            commonListHolder.makeOccupationList(jsonList);
        }

        jsonList = json.getJsonList(P.education);// education name and code
        if (jsonList != null) {
            //session.addString(P.education, jsonList.toString());
            commonListHolder.makeEducationList(jsonList);
        }

        jsonList = json.getJsonList(P.mothertongue);// language name and code
        if (jsonList != null) {
            //session.addString(P.language, jsonList.toString());
            commonListHolder.makeMotherTongueList(jsonList);
        }

        jsonList = json.getJsonList(P.smoking);// smoking name and code
        if (jsonList != null) {
            //session.addString(P.smoking, jsonList.toString());
            commonListHolder.makeSmokingList(jsonList);
        }

        jsonList = json.getJsonList(P.relocate);// relocate name and code
        if (jsonList != null) {
            //session.addString(P.relocate, jsonList.toString());
            commonListHolder.makeRelocateList(jsonList);
        }

        jsonList = json.getJsonList(P.seeking_marriage);// marriage name and code
        if (jsonList != null) {
            //session.addString(P.seeking_marriage, jsonList.toString());
            commonListHolder.makeSeekingForMarriageList(jsonList);
        }

        jsonList = json.getJsonList(P.intreasted_in);// interrest name and code
        if (jsonList != null) {
            //session.addString(P.intreasted_in, jsonList.toString());
            commonListHolder.makeIntrestedInList(jsonList);
        }

        jsonList = json.getJsonList(P.complexion);// complexion name and code
        if (jsonList != null) {
            //session.addString(P.complexion, jsonList.toString());
            commonListHolder.makeComplexionList(jsonList);
        }

        jsonArray = json.getJsonArray(P.min_age);// age array, note: min and max array have same values so keeping only one
        if (jsonArray != null) {
            //session.addString(P.age, jsonArray.toString());
            commonListHolder.makeAgeList(jsonArray);
        }

        jsonList = json.getJsonList(P.marital_status);// marital status name and code
        if (jsonList != null) {
            //session.addString(P.marital_status, jsonList.toString());
            commonListHolder.makeMaritalStatusList(jsonList);
        }

        jsonList = json.getJsonList(P.physical_status);// physical status name and code
        if (jsonList != null) {
            //session.addString(P.physical_status, jsonList.toString());
            commonListHolder.makePhysicalStatusList(jsonList);
        }

        jsonList = json.getJsonList(P.monthly_income);
        if (jsonList != null)
            commonListHolder.makeMonthlyIncomeList(jsonList);

        jsonList = json.getJsonList(P.type_of_issue);
        if (jsonList != null)
            commonListHolder.makeIssueList(jsonList);


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
                if (!sharableLink.isEmpty()) {
                    intent = new Intent(SplashActivity.this, WebViewActivity.class);
                    intent.putExtra("url", sharableLink);
                } else if (string == null || string.isEmpty())
                    intent = new Intent(SplashActivity.this, WalkThroughActivity.class);
                else if (session.getInt(P.full_register) == 0)
                    intent = new Intent(SplashActivity.this, RegSecondPageActivity.class);
                else
                    intent = new Intent(SplashActivity.this, HomeActivity.class);


                //for deep linking
                /*if (!profileId.isEmpty())
                    intent.putExtra(P.profile_id,profileId);*/

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

    private void generateFcmToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e(getLocalClassName(), "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = Objects.requireNonNull(task.getResult()).getToken();

                        //H.log(getLocalClassName(), FirebaseInstanceId.getInstance().getId());
                        String id = FirebaseInstanceId.getInstance().getId();

                        new Session(SplashActivity.this).addString(P.fcmToken, token);
                        H.log("fcmTokenIs", token);
                        App.fcmToken = token+"";
                        H.log("idIs", id);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


}

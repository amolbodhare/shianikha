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
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.example.shianikha.activities.HomeActivity;
import com.example.shianikha.activities.RegSecondPageActivity;
import com.example.shianikha.activities.WalkThroughActivity;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;

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
        hitMastersApi();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 31);

        //to get height of status bar
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Rect rectangle = new Rect();
                Window window = getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
                int statusBarHeight = rectangle.top;
                new Session(SplashActivity.this).addInt(P.statusBarHeight, statusBarHeight);
            }
        }, 987);

    }

    private void hitMastersApi() {
        final LoadingDialog loadingDialog = new LoadingDialog(this);
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

        JsonList profileForList = json.getJsonList(P.profile_for);
        if (profileForList != null)
            session.addString(P.profile_for, profileForList.toString());

        JsonList countryPinCodeList = json.getJsonList(P.country_code);
        if (countryPinCodeList != null)
            session.addString(P.country_code, countryPinCodeList.toString());

        startNewActivity();
    }

    private void startNewActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent intent;
                String string = session.getString(P.tokenData);
                //if (string == null)
                    intent = new Intent(SplashActivity.this, WalkThroughActivity.class);
                /*else if (session.getInt(P.full_register)==0)
                    intent = new Intent(SplashActivity.this, RegSecondPageActivity.class);
                else
                    intent = new Intent(SplashActivity.this, HomeActivity.class);*/

                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}

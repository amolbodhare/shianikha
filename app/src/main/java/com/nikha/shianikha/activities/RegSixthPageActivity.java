package com.nikha.shianikha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.App;
import com.nikha.shianikha.R;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;

public class RegSixthPageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_reg_sixth_page);

        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button)
            //startActivity(new Intent(this,PerfectMatchActivity.class));
            makeJson();

    }

    private void makeJson() {
        String string = ((EditText) findViewById(R.id.descriptionOneEditText)).getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please enter religious expectations");
            return;
        }
        App.masterJson.addString(P.religion_expectations, string);

        string = ((EditText) findViewById(R.id.descriptionTwoEditText)).getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please enter preferences or qualities you are seeking");
            return;
        }
        App.masterJson.addString(P.qualities_seeking, string);

        string = ((EditText) findViewById(R.id.descriptionThreeEditText)).getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please enter more about you");
            return;
        }

        App.masterJson.addString(P.more_about_you, string);


        H.log("masterJsonIs", App.masterJson.toString());
        hitRegisterDetailsApi();
    }

    private void hitRegisterDetailsApi() {
        final LoadingDialog loadingDialog = new LoadingDialog(this);

        RequestModel requestModel = RequestModel.newRequestModel("register_details");
        requestModel.addJSON(P.data, App.masterJson);

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
                        H.showMessage(RegSixthPageActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1) {
                            new Session(RegSixthPageActivity.this).addInt(P.full_register, 1);
                            Intent intent = new Intent(RegSixthPageActivity.this, PerfectMatchActivity.class);
                            startActivity(intent);
                        } else
                            H.showMessage(RegSixthPageActivity.this, json.getString(P.msg));
                    }
                })
                .run("hitRegisterDetailsApi");
    }
}

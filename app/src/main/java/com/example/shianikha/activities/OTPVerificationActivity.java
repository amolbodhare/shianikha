package com.example.shianikha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.LoadingDialog;
import com.example.shianikha.R;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;

import org.json.JSONException;

public class OTPVerificationActivity extends AppCompatActivity implements View.OnClickListener, Api.OnLoadingListener, Api.OnErrorListener, TextView.OnEditorActionListener {

    private EditText otp_first, otp_second, otp_third, otp_fourth, otp_fifth, otp_sixth;
    private Json json = new Json();
    private LoadingDialog loadingDialog;
    private int from;//if from =2 hai tho login se if 6 hai tho registration se hai

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        loadingDialog = new LoadingDialog(this);

        otp_first = findViewById(R.id.ot_first_digit);
        otp_second = findViewById(R.id.ot_seecond_digit);
        otp_third = findViewById(R.id.ot_third_digit);
        otp_fourth = findViewById(R.id.ot_fourth_digit);
        otp_fifth = findViewById(R.id.ot_fifth_digit);
        otp_sixth = findViewById(R.id.ot_sixth_digit);

        setOnEditorActionListener();

        //previous json for resend otp
        String string = getIntent().getStringExtra(P.registrationJson);
        if (string!=null)
        {
            try
            {
                json = new Json(string);
                H.log("json length is ",json.length()+"");
                String s1 = json.getString(P.country_code);
                if (s1.equals("+91")) {
                    String s2 = json.getString(P.ph_number);
                    string = s1 + "-" + s2;
                    ((TextView) findViewById(R.id.mobile_no)).setText("OTP sent to " + string);
                }
                else
                {
                    s1 = json.getString(P.email);
                    ((TextView) findViewById(R.id.mobile_no)).setText("OTP sent to " + s1);
                    ((TextView)findViewById(R.id.textView)).setText("Email Id Verification");
                }

                findViewById(R.id.btn_submit).setOnClickListener(this);
                findViewById(R.id.resendText).setOnClickListener(this);

                handle6EditText();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void setOnEditorActionListener()
    {
        //otp_first.setOnEditorActionListener(this);
        otp_second.setOnEditorActionListener(this);
        otp_third.setOnEditorActionListener(this);
        otp_fourth.setOnEditorActionListener(this);
        otp_fifth.setOnEditorActionListener(this);
        otp_sixth.setOnEditorActionListener(this);
    }

    private void handle6EditText()
    {
        otp_first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 1)
                    otp_second.requestFocus();
                //otp_first.setFocusable(true);
            }
        });

        otp_second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1)
                    otp_third.requestFocus();
                else
                    otp_first.requestFocus();
            }
        });

        otp_third.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1)
                    otp_fourth.requestFocus();
                else
                    otp_second.requestFocus();
            }
        });

        otp_fourth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1)
                    otp_fifth.requestFocus();
                else
                    otp_third.requestFocus();
            }
        });

        otp_fifth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1)
                    otp_sixth.requestFocus();
                else
                    otp_fourth.requestFocus();
            }
        });

        otp_sixth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1)
                    otp_sixth.requestFocus();
                else
                    otp_fifth.requestFocus();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btn_submit)
            hitOtpVerificationApi();

        else if (v.getId() == R.id.resendText)
            hitRegisterApi();
    }

    private void hitOtpVerificationApi()
    {
        String string = otp_first.getText().toString() + otp_second.getText().toString() + otp_third.getText().toString() +
                otp_fourth.getText().toString() + otp_fifth.getText().toString() + otp_sixth.getText().toString();
        string = string.replace(" ","");
        if (string.length()<6)
        {
            H.showMessage(this,"Please enter valid otp");
            return;
        }

        if (json==null)
            return;

        json.addString(P.otp, string);

        string= from==2?"validate_login_otp": "validate_otp";

        RequestModel requestModel = RequestModel.newRequestModel(string);
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1)
                        {
                            Intent i = new Intent(OTPVerificationActivity.this, RegSecondPageActivity.class);
                            startActivity(i);
                            finish();
                        } else
                            H.showMessage(OTPVerificationActivity.this, json.getString(P.msg));
                    }
                })
                .run("hitOtpVerificationApi");
    }

    private void hitRegisterApi()
    {
        RequestModel requestModel = RequestModel.newRequestModel("register");
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1)
                        {
                            H.showMessage(OTPVerificationActivity.this,"OTP sent successfully, please wait for some time...");
                        } else
                            H.showMessage(OTPVerificationActivity.this, json.getString(P.msg));
                    }
                })
                .run("hitRegisterApi");
    }

    @Override
    public void onLoading(boolean isLoading) {
        if (isLoading)
            loadingDialog.show();
        else
            loadingDialog.dismiss();
    }

    @Override
    public void onError() {
        H.showMessage(OTPVerificationActivity.this, "Something went wrong.");
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {


        return false;
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_CLEAR)
        {
            H.log("iAm","Fired");
            if (otp_sixth.isFocused())
                otp_fifth.requestFocus();
            else if (otp_fifth.isFocused())
                otp_fourth.requestFocus();
            else if (otp_fourth.isFocused())
                otp_third.requestFocus();
            else if (otp_third.isFocused())
                otp_second.requestFocus();
            else if (otp_second.isFocused())
                otp_first.requestFocus();

            //return  true;
        }
        return false; //super.onKeyDown(keyCode, event);
    }*/
}

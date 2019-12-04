package com.nikha.shianikha.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONException;

public class OTPVerificationActivity extends AppCompatActivity implements View.OnClickListener, Api.OnLoadingListener, Api.OnErrorListener {

    private EditText otp_first, otp_second, otp_third, otp_fourth, otp_fifth, otp_sixth;
    private Json json = new Json();
    private LoadingDialog loadingDialog;
    private int from;//if from =3 hai tho login se if 6 hai tho registration se hai

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_otp_verification);

        App.app.startSmsListener(this);

        loadingDialog = new LoadingDialog(this);

        otp_first = findViewById(R.id.ot_first_digit);
        otp_second = findViewById(R.id.ot_seecond_digit);
        otp_third = findViewById(R.id.ot_third_digit);
        otp_fourth = findViewById(R.id.ot_fourth_digit);
        otp_fifth = findViewById(R.id.ot_fifth_digit);
        otp_sixth = findViewById(R.id.ot_sixth_digit);

        //previous json for resend otp
        String string = getIntent().getStringExtra(P.registrationJson);
        if (string != null) {
            try {
                json = new Json(string);
                from = json.length();
                H.log("json length is ", json.length() + "");
                String s1 = json.getString(P.country_code);
                if (s1.equals("+91")) {
                    String s2 = json.getString(P.ph_number);
                    string = s1 + "-" + s2;
                    ((TextView) findViewById(R.id.mobile_no)).setText("OTP sent to " + string);
                    ((TextView) findViewById(R.id.message)).setText("The Validation Code That\n Get From SMS");
                } else {
                    ((TextView) findViewById(R.id.textView)).setText("Email Id Verification");
                    ((TextView) findViewById(R.id.message)).setText("The Validation Code That\n Get On Email");
                    s1 = json.getString(P.email);
                    if (s1 != null && !s1.isEmpty())
                        ((TextView) findViewById(R.id.mobile_no)).setText("OTP sent to " + s1);
                }

                findViewById(R.id.btn_submit).setOnClickListener(this);
                findViewById(R.id.resendText).setOnClickListener(this);

                //addTextWatcherOnAllEditText();
                setOnKeyListnerOnAllEditText();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        setSixDigitOtp();
    }

    private void setOnKeyListnerOnAllEditText() {
        otp_first.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && isValidKey(keyCode))
                    otp_second.requestFocus();
                return false;
            }
        });

        otp_second.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (isValidKey(keyCode))
                        otp_third.requestFocus();
                    else if (keyCode == 67 && otp_second.getText().length() == 0) {
                        otp_first.requestFocus();
                        H.log("keyCodeIs", keyCode + "");
                    }
                }

                return false;
            }
        });

        otp_third.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (isValidKey(keyCode))
                        otp_fourth.requestFocus();
                    else if (keyCode == 67 && otp_third.getText().length() == 0)
                        otp_second.requestFocus();
                }
                return false;
            }
        });

        otp_fourth.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (isValidKey(keyCode))
                        otp_fifth.requestFocus();
                    else if (keyCode == 67 && otp_fourth.getText().length() == 0)
                        otp_third.requestFocus();
                }
                return false;
            }
        });

        otp_fifth.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (isValidKey(keyCode))
                        otp_sixth.requestFocus();
                    else if (keyCode == 67 && otp_fifth.getText().length() == 0)
                        otp_fourth.requestFocus();
                }
                return false;
            }
        });

        otp_sixth.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == 67 && otp_sixth.getText().length() == 0)
                    otp_fifth.requestFocus();
                return false;
            }
        });
    }

    private boolean isValidKey(int keyCode) {
        for (int i = 7; i < 17; i++) {
            if (i == keyCode)
                return true;
        }
        return false;
    }

    private void addTextWatcherOnAllEditText() {
        otp_first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 1)
                    otp_second.requestFocus();
                //otp_first.setFocusable(true);
            }
        });

        otp_second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

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
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit)
            hitOtpVerificationApi();

        else if (v.getId() == R.id.resendText) {
            json.remove(P.otp);

            if (from == 3)
                hitLoginApi();
            else if (from > 3)
                hitRegisterApi();
        }
    }

    private void hitOtpVerificationApi() {
        String string = otp_first.getText().toString() + otp_second.getText().toString() + otp_third.getText().toString() +
                otp_fourth.getText().toString() + otp_fifth.getText().toString() + otp_sixth.getText().toString();
        string = string.replace(" ", "");
        if (string.length() < 6) {
            H.showMessage(this, "Please enter valid otp");
            return;
        }

        if (json == null)
            return;

        json.addString(P.otp, string);

        string = from == 3 ? "validate_login_otp" : "validate_otp";

        RequestModel requestModel = RequestModel.newRequestModel(string);
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1) {
                            Session session = new Session(OTPVerificationActivity.this);
                            String str = json.getString(P.tokenData);
                            if (str != null)
                                session.addString(P.tokenData, str);

                            json = json.getJson(P.data);
                            str = json.getString(P.full_register);
                            session.addInt(P.full_register, H.getInt(str));

                            Intent intent;
                            if (str.equals("1"))
                                intent = new Intent(OTPVerificationActivity.this, HomeActivity.class);
                            else
                                intent = new Intent(OTPVerificationActivity.this, RegSecondPageActivity.class);

                            str = json.getString(P.email);
                            session.addString(P.email, str);

                            int i = json.getInt(P.show);
                            session.addInt(P.showName, i);
                            App.showName = i == 1 ? true : false;

                            startActivity(intent);
                            finish();

                        } else
                            H.showMessage(OTPVerificationActivity.this, json.getString(P.msg));
                    }
                })
                .run("hitOtpVerificationApi");
    }

    private void hitRegisterApi() {
        RequestModel requestModel = RequestModel.newRequestModel("register");
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1)
                            H.showMessage(OTPVerificationActivity.this, "OTP sent successfully, please wait for some time...");
                        else
                            H.showMessage(OTPVerificationActivity.this, json.getString(P.msg));
                    }
                })
                .run("hitRegisterApi");
    }

    private void hitLoginApi() {
        RequestModel requestModel = RequestModel.newRequestModel("login");
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {
                        if (json.getInt(P.status) == 1)
                            H.showMessage(OTPVerificationActivity.this, "OTP sent successfully, please wait for some time...");
                        else
                            H.showMessage(OTPVerificationActivity.this, json.getString(P.msg));
                    }
                })
                .run("hitLoginApi");
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

    public void setSixDigitOtp()
    {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (App.tempOTP == null || App.tempOTP.isEmpty())
                    handler.postDelayed(this, 500);
                else {
                    otp_first.setText(App.tempOTP.charAt(0)+"");
                    otp_second.setText(App.tempOTP.charAt(1)+"");
                    otp_third.setText(App.tempOTP.charAt(2)+"");
                    otp_fourth.setText(App.tempOTP.charAt(3)+"");
                    otp_fifth.setText(App.tempOTP.charAt(4)+"");
                    otp_sixth.setText(App.tempOTP.charAt(5)+"");
                    handler.removeCallbacks(this);
                }
            }
        };
        runnable.run();
    }
}

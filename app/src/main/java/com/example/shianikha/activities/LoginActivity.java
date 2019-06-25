package com.example.shianikha.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.example.shianikha.R;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;

public class LoginActivity extends AppCompatActivity
{

    Button btn_login;
    TextView reg_here;
    Context context;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        btn_login=findViewById(R.id.btn_login);
        reg_here=findViewById(R.id.reg_tv);
        loadingDialog=new LoadingDialog(this);
        context=LoginActivity.this;

        btn_login.setOnClickListener(
                new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //hitLoginApi();
                Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        reg_here.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void hitLoginApi()
    {
        Json json = new Json();
        json.addString("email","mailtobodhare@gmail.com");
        json.addString("password","123");
        json.addString("fcm_token","sdjgdsjhgdjdg");

        RequestModel requestModel = RequestModel.newRequestModel("login");
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(LoginActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener()
                {
                    @Override
                    public void onSuccess(Json json)
                    {
                        if (json.getInt(P.status) == 1)
                        {
                            JsonList jsonList=json.getJsonList("data");
                            Json logged_in_json=jsonList.get(0);
                            new Session(context).addString("loggedinjsonstring",logged_in_json.toString());
                            Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else
                            H.showMessage(LoginActivity.this, json.getString(P.msg));
                    }
                }).onLoading(new Api.OnLoadingListener() {
            @Override
            public void onLoading(boolean isLoading)
            {
                if (isLoading)
                    loadingDialog.show("Please wait...");
                else
                    loadingDialog.dismiss();
            }
        })
                .run("hitLoginApi");
    }
}

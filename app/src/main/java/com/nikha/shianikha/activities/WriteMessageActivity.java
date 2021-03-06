package com.nikha.shianikha.activities;

        import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.shianikha.R;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;

public class WriteMessageActivity extends AppCompatActivity  {

    private String profileId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_write_message);

        String string = getIntent().getStringExtra(P.profile_id);
        if (string != null) {
            profileId = string;

            findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hitSendMessageApi();
                }
            });
        }

    }

    private void hitSendMessageApi()
    {
        EditText editText = findViewById(R.id.type_msg_edt);
        String string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter your message");
            return;
        }

        Json json = new Json();
        json.addString(P.profile_id,profileId);
        json.addString(P.token_id, new Session(this).getString(P.tokenData));
        json.addString(P.message,string);

        final LoadingDialog loadingDialog = new LoadingDialog(this);

        RequestModel requestModel = RequestModel.newRequestModel("send_message");
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener()
                {
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
                        H.showMessage(WriteMessageActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json)
                    {
                        if (json.getInt(P.status) == 1)
                        {
                            Intent intent = new Intent(WriteMessageActivity.this, MessageActivity.class);
                            intent.putExtra("open","");
                            startActivity(intent);
                        }
                    }
                })
                .run("hitSendMessageApi");
    }


    public void onMethodClick(View v)
    {
        finish();
        ((WriteMessageActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }
}


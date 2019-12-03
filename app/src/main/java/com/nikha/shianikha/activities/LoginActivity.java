package com.nikha.shianikha.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.App;
import com.nikha.shianikha.R;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Map<String,String> countryList = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_login);

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.reg_tv).setOnClickListener(this);
        findViewById(R.id.countryCodeEditText).setOnClickListener(this);

        makeCountryCodeList();
        App.app.extractHashKey(this);
    }

    private void makeCountryCodeList() {
        String string = new Session(this).getString(P.country);
        if (string != null) {
            countryList.clear();
            JsonList jsonList = new JsonList(string);
            String s1,s2;

            for (Json json : jsonList) {
                s1 = json.getString(P.name);
                s2 = json.getString(P.country_code);
                if (s1 != null && s2 != null)
                    countryList.put(s1, s2);
            }
            H.log("countryNameAndListIs",countryList.toString());
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btn_login)
        {
            makeJson();
            /*startActivity(new Intent(this,HomeActivity.class));
            finish();*/
        }
        else if (v.getId() == R.id.reg_tv)
        {
            startActivity(new Intent(this,RegistrationActivity.class));
        }
        else if (v.getId() == R.id.countryCodeEditText)
        {
            final Dialog dialog = new Dialog(this);
            final EditText editText = (EditText)v;
            dialog.setContentView(R.layout.country_code_layout);
            ListView listView = dialog.findViewById(R.id.listView);
            CountryCodeListAdapter countryCodeListAdapter = new CountryCodeListAdapter();
            listView.setAdapter(countryCodeListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    editText.setText(((TextView)view.findViewById(R.id.countryCode)).getText().toString());
                    dialog.hide();
                }
            });
            dialog.show();
        }
    }

    private void makeJson()
    {
        Json json = new Json();

        String countryCode = ((EditText)findViewById(R.id.countryCodeEditText)).getText().toString();
        json.addString(P.country_code,countryCode);

        String string=((EditText)findViewById(R.id.mobile_no)).getText().toString();
        if(string.isEmpty())
        {
            H.showMessage(this,"please enter your Mobile Number");
            return;
        }
        else if (countryCode.equals("+91") && string.length()!=10)
        {
            H.showMessage(this,"please enter valid Mobile Number");
            return;
        }
        json.addString(P.ph_number,string);

        App.app.startSmsListener(this);
        json.addString(P.hash_code, App.hashKey);
        hitLoginApi(json);
    }

    private void hitLoginApi(Json json) {
        final Json j = json;

        final LoadingDialog loadingDialog = new LoadingDialog(this);

        RequestModel requestModel = RequestModel.newRequestModel("login");
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener()
                {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (isLoading)
                            loadingDialog.show();
                        else
                            loadingDialog.dismiss();
                    }
                })
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(LoginActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1)
                        {
                            Intent intent = new Intent(LoginActivity.this, OTPVerificationActivity.class);
                            intent.putExtra(P.registrationJson,j.toString());//same parameter is required in everyscreen
                            startActivity(intent);
                        } else
                            H.showMessage(LoginActivity.this, json.getString(P.msg));
                    }
                })
                .run("hitLoginApi");
    }

    class CountryCodeListAdapter extends BaseAdapter
    {
        ArrayList<String> countryCodeList;
        ArrayList<String> countryNameList;
        String string;

        CountryCodeListAdapter()
        {
            countryCodeList = new ArrayList<>();
            countryNameList = new ArrayList<>();

            for (Map.Entry<String,String> entry : countryList.entrySet())
            {
                string = entry.getKey();
                countryNameList.add(string);
                string = entry.getValue();
                countryCodeList.add(string);
            }
        }

        @Override
        public int getCount() {
            return countryList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView==null)
                convertView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.country_code_item,null,false);

            ((TextView)convertView.findViewById(R.id.countryCode)).setText(countryCodeList.get(position));
            ((TextView)convertView.findViewById(R.id.countryName)).setText(countryNameList.get(position));

            return convertView;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.mPlayer.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        App.mPlayer.pause();
    }
}

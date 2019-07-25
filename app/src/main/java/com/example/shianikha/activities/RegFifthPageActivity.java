package com.example.shianikha.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.example.App;
import com.example.shianikha.R;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class RegFifthPageActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private ArrayAdapter<String> arrayAdapter;
    private Session session;
    private ArrayList<String> marriageNameList, marriageCodeList, intrestedInNameList, intrestedInCodeList;
    private String lat= "",longs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_reg_fifth_page);

        session = new Session(this);

        /*findViewById(R.id.marriageEditText).setOnClickListener(this);
        findViewById(R.id.interestedInEditText).setOnClickListener(this);*/
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.view).setOnClickListener(this);

        setMarginTopOfCustomSpinner();
        extractRequireList();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.e("returnStatus", "return");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
        } else
            Log.e("permission_status", "not granted");
    }

    private void extractRequireList()
    {
        JsonList jsonList;

        //for language
        String string = session.getString(P.seeking_marriage);
        if (string != null) {
            jsonList = new JsonList(string);
            marriageNameList = new ArrayList<>();
            marriageCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                marriageNameList.add(string);

                string = j.getString(P.id);
                marriageCodeList.add(string);
            }
        }

        //for smoking
        string = session.getString(P.intreasted_in);
        if (string != null) {
            jsonList = new JsonList(string);
            intrestedInCodeList = new ArrayList<>();
            intrestedInNameList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                intrestedInNameList.add(string);

                string = j.getString(P.id);
                intrestedInCodeList.add(string);
            }
        }

        setUpTextWatcher();
    }

    private void setUpTextWatcher() {
        ((EditText) findViewById(R.id.editText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (arrayAdapter != null)
                    arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void setUpCustomSpinner(final View view)
    {
        ListView listView = findViewById(R.id.listView);
        findViewById(R.id.view).setVisibility(View.VISIBLE);

        /*if (view.getId() == R.id.marriageEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Select option");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, marriageNameList);
        } else if (view.getId() == R.id.interestedInEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Interested In");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, intrestedInNameList);
        }*/

        if (arrayAdapter == null)
            return;

        H.showKeyBoard(this, findViewById(R.id.editText));

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView textView = v.findViewById(R.id.textView);
                if (textView != null) {
                    Log.e("selectedIs", textView.getText().toString());
                    ((EditText) view).setText(textView.getText().toString());
                }
                hideCustomSpinnerLayout();
            }
        });

        findViewById(R.id.includeContainer).animate().translationX(0).setDuration(500);
    }

    private void hideCustomSpinnerLayout() {
        int i = findViewById(R.id.includeContainer).getWidth();
        ((EditText) findViewById(R.id.editText)).setText("");
        findViewById(R.id.includeContainer).animate().translationX(i).setDuration(500);
        View view = findViewById(R.id.view);
        view.setVisibility(View.GONE);
        H.hideKeyBoard(this, view);
    }

    private void setMarginTopOfCustomSpinner() {
        LinearLayout linearLayout = findViewById(R.id.includeContainer);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        int i = new Session(this).getInt(P.statusBarHeight);
        layoutParams.topMargin = i;
        H.log("heightIs", i + "");
        linearLayout.setLayoutParams(layoutParams);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.view)
            hideCustomSpinnerLayout();
        /*else if (v.getId() == R.id.button)
            makeJson();
*/        else
            setUpCustomSpinner(v);
    }

   /* private void makeJson()
    {
        EditText editText = findViewById(R.id.marriageEditText);
        String string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select marriage option");
            return;
        }
        int i = marriageNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.seeking_marriage, marriageCodeList.get(i));

        editText = findViewById(R.id.interestedInEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select your interest");
            return;
        }
        i = intrestedInNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.intreasted_in, intrestedInCodeList.get(i));

        editText = findViewById(R.id.descriptionOneEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter level of religion practice");
            return;
        }
        App.masterJson.addString(P.religion_expectations, string);

        editText = findViewById(R.id.descriptionTwoEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please preference or qualities you are seeking");
            return;
        }
        App.masterJson.addString(P.religion_expectations, string);

        editText = findViewById(R.id.descriptionThreeEditText);
        string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Pleas enter more about you");
            return;
        }
        App.masterJson.addString(P.about_2, string);
        App.masterJson.addString(P.lat, lat);
        App.masterJson.addString(P.lng, longs);
        H.log("masterJsonIs",App.masterJson.toString());

        hitRegisterDetailsApi();
    }
*/
    private void hitRegisterDetailsApi()
    {
        final LoadingDialog loadingDialog = new LoadingDialog(this);

        RequestModel requestModel = RequestModel.newRequestModel("register_details");
        requestModel.addJSON(P.data, App.masterJson);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener()
                {
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
                        H.showMessage(RegFifthPageActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1)
                        {
                            new Session(RegFifthPageActivity.this).addInt(P.full_register, 1);
                            Intent intent = new Intent(RegFifthPageActivity.this, RegSixthPageActivity.class);
                            startActivity(intent);
                        } else
                            H.showMessage(RegFifthPageActivity.this, json.getString(P.msg));
                    }
                })
                .run("hitRegisterDetailsApi");
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if (lat.isEmpty())
            lat = location.getLatitude()+"";
        if (longs.isEmpty())
            longs = location.getLongitude()+"";
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }
}

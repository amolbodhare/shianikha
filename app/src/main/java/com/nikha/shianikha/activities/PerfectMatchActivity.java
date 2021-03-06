package com.nikha.shianikha.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.google.android.material.textfield.TextInputLayout;
import com.nikha.shianikha.R;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.CommonListHolder;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class PerfectMatchActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ArrayAdapter<String> arrayAdapter;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_perfect_match);

        session = new Session(this);
        findViewById(R.id.minAgeEditText).setOnClickListener(this);
        findViewById(R.id.maxAgeEditText).setOnClickListener(this);
        findViewById(R.id.educationEditText).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.view).setOnClickListener(this);

        setMarginTopOfCustomSpinner();
        setUpTextWatcher();
        handleTermsOfUse();

        makeMaritalStatusCheckBox();
        makeEthnicityCheckBox();

        boolean makeVisible = getIntent().getBooleanExtra("makeVisible",false);
        if (makeVisible)// reusing activity for perfect match and my preferences.
            findViewById(R.id.toolbar_layout).setVisibility(View.VISIBLE);
    }

    private void  handleTermsOfUse() {
        TextView textView = findViewById(R.id.termsAndCondition);
        String string = textView.getText().toString();

        SpannableString spannableString = new SpannableString(string);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view)
            {
                Intent intent = new Intent(PerfectMatchActivity.this, WebViewActivity.class);
                intent.putExtra("url","https://shianikah.in/privacy-policy.html");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getColor(R.color.darkblue));
            }
        };

        spannableString.setSpan(clickableSpan,string.indexOf("T"),string.indexOf("U")+3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }


    private void makeEthnicityCheckBox()
    {
        int n = CommonListHolder.ethnicityNameList.size();
        int i = n%2 + n/2;

        LinearLayout linearLayout = findViewById(R.id.ethnicityContainer1);
        for (int j = 0; j < n; j++) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.inflatable_check_box, null);
            CheckBox checkBox = (CheckBox) ll.getChildAt(0);
            checkBox.setText(CommonListHolder.ethnicityNameList.get(j));
            checkBox.setTag(CommonListHolder.ethnicityIdList.get(j));
            checkBox.setOnCheckedChangeListener(this);

            linearLayout.addView(ll);

            if (j == i - 1)
                linearLayout = findViewById(R.id.ethnicityContainer2);
        }
    }

    private void makeMaritalStatusCheckBox()
    {
        int n = CommonListHolder.maritalStatusNameList.size();
        int i = n%2 + n/2;

        LinearLayout linearLayout = findViewById(R.id.maritalStatusContainer1);
        for (int j = 0; j < n; j++) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.inflatable_check_box, null);
            CheckBox checkBox = (CheckBox) ll.getChildAt(0);
            checkBox.setText(CommonListHolder.maritalStatusNameList.get(j));
            checkBox.setTag(CommonListHolder.maritalStatusIdList.get(j));
            checkBox.setOnCheckedChangeListener(this);

            linearLayout.addView(ll);

            if (j == i - 1)
                linearLayout = findViewById(R.id.maritalStatusContainer2);
        }
    }

    private void setUpTextWatcher() {
        ((EditText) findViewById(R.id.editText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (arrayAdapter != null)
                    arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setUpCustomSpinner(final View view) {
        ListView listView = findViewById(R.id.listView);

        if (view.getId() == R.id.minAgeEditText || view.getId() == R.id.maxAgeEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search Age");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.ageList);
        } else if (view.getId() == R.id.educationEditText)
        {
            /*((EditText) findViewById(R.id.editText)).setHint("Search Education");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.educationNameList);*/
            String[] educationArray = new String[CommonListHolder.educationNameList.size()];
            educationArray = CommonListHolder.educationNameList.toArray(educationArray);
            showMultiChoiceDialog(educationArray,view);
            return;
        }

        if (arrayAdapter == null)
            return;

        H.showKeyBoard(this, findViewById(R.id.editText));
        findViewById(R.id.view).setVisibility(View.VISIBLE);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView textView = v.findViewById(R.id.textView);
                if (textView != null) {
                    Log.e("selectedIs", textView.getText().toString());
                    String string = textView.getText().toString().trim();
                    ((EditText) view).setText(string);
                    if (string.equalsIgnoreCase("other") || string.equalsIgnoreCase("others"))
                        showOtherEditText(view, true);
                    else
                        showOtherEditText(view, false);
                }
                hideCustomSpinnerLayout();
            }
        });

        findViewById(R.id.includeContainer).animate().translationX(0).setDuration(500);
    }

    private ArrayList<String> tempList;
    private boolean[] checkedArray;

    private void showMultiChoiceDialog(final String[] languageArray, final View view) {
        if (checkedArray == null) {
            checkedArray = new boolean[languageArray.length];
            for (int j = 0; j < languageArray.length; j++)
                checkedArray[j] = false;
            tempList = new ArrayList<>();
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMultiChoiceItems(languageArray, checkedArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    tempList.add(languageArray[i]);
                    checkedArray[i] = true;
                } else {
                    tempList.remove(languageArray[i]);
                    checkedArray[i] = false;
                }
            }
        }).setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (tempList != null) {
                    String string = tempList.toString();
                    string = string.replace("[", "");
                    string = string.replace("]", "");
                    if (string.contains("other") || string.contains("Other"))
                        findViewById(R.id.otherEducationInputLayout).setVisibility(View.VISIBLE);
                    else
                        findViewById(R.id.otherEducationInputLayout).setVisibility(View.GONE);
                    ((EditText) view).setText(string);
                    hideCustomSpinnerLayout();
                }
            }
        }).show();
    }

    private void showOtherEditText(View view, boolean b) {
        RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
        LinearLayout linearLayout = (LinearLayout) relativeLayout.getParent();

        int i = linearLayout.indexOfChild(relativeLayout);
        View v = linearLayout.getChildAt(i + 1);
        if (v instanceof TextInputLayout) {
            if (b)
                v.setVisibility(View.VISIBLE);
            else
                v.setVisibility(View.GONE);
        }
    }

    private void hideCustomSpinnerLayout() {
        int i = findViewById(R.id.includeContainer).getWidth();
        ((EditText) findViewById(R.id.editText)).setText("");
        findViewById(R.id.includeContainer).animate().translationX(i).setDuration(500);
        View view = findViewById(R.id.view);
        view.setVisibility(View.GONE);
        H.hideKeyBoard(this, view);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.button)
            makeJson();
        else if (v.getId() == R.id.view)
            hideCustomSpinnerLayout();
        else
            setUpCustomSpinner(v);

    }

    private void makeJson()
    {
        Json json = new Json();
        json.addString(P.token_id, session.getString(P.tokenData));

        EditText editText = findViewById(R.id.minAgeEditText);
        String string = editText.getText().toString();
        int i = H.getInt(string);

        editText = findViewById(R.id.maxAgeEditText);
        string = editText.getText().toString();
        int j = H.getInt(string);

        if (i>=18 && j>=18 && j - i < 1) {
            H.showMessage(this, "Maximum age must be greater than minimum age");
            return;
        }
        json.addString(P.min_age, i + "");
        json.addString(P.max_age, j + "");

        JSONArray jsonArray = new JSONArray();
        makeMaritalStatusJsonArray(jsonArray);
        json.addJSONArray(P.marital_status_id,jsonArray);

        jsonArray = new JSONArray();
        makeEthnicityJsonArray(jsonArray);
        json.addJSONArray(P.ethnicitys_id,jsonArray);

        editText = findViewById(R.id.educationEditText);
        string = editText.getText().toString();
        StringTokenizer stringTokenizer = new StringTokenizer(string,",");
        jsonArray = new JSONArray();
        while (stringTokenizer.hasMoreTokens())
        {
            string = stringTokenizer.nextToken().trim();
            H.log("tokenIs",string);
            i= CommonListHolder.educationNameList.indexOf(string);
            if (i!=-1)
                jsonArray.put(CommonListHolder.educationIdList.get(i));
        }
        json.addJSONArray(P.education_id,jsonArray);

        // TODO: 07-08-2019 no id available for other education in api

        CheckBox checkBox = findViewById(R.id.checkBox1);
        string = checkBox.isChecked()? "1":"0";
        json.addString(P.request_from_anyone,string);

        CheckBox cB = findViewById(R.id.checkBox2);
        string = cB.isChecked() ? "1" : "0";
        json.addString(P.request_from_preferred_match,string);

        if (!checkBox.isChecked() && !cB.isChecked())
        {
            H.showMessage(this,"Please select any of the email option");
            return;
        }

        checkBox = findViewById(R.id.checkBox3);
        string = checkBox.isChecked() ? "1" : "0";
        if(string.equals("0")){
            H.showMessage(this,"Please check the Terms of Use");
            return;
        }
        json.addString(P.sancity_of_the_holy_quran,string);

        hitPerfectMatchApi(json);
    }

    private void makeEthnicityJsonArray(JSONArray jsonArray) {
        LinearLayout linearLayout = findViewById(R.id.ethnicityContainer1);
        LinearLayout ll;
        CheckBox checkBox;
        String string = "";
        Object object;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ll = (LinearLayout) linearLayout.getChildAt(i);
            checkBox = (CheckBox) ll.getChildAt(0);
            if (checkBox.isChecked()) {
                object = checkBox.getTag();
                if (object != null)
                    string = object.toString();

                jsonArray.put(string);
            }
        }

        linearLayout = findViewById(R.id.ethnicityContainer2);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ll = (LinearLayout) linearLayout.getChildAt(i);
            checkBox = (CheckBox) ll.getChildAt(0);
            if (checkBox.isChecked()) {
                object = checkBox.getTag();
                if (object != null)
                    string = object.toString();

                jsonArray.put(string);
            }
        }
    }

    private void makeMaritalStatusJsonArray(JSONArray jsonArray)
    {
        LinearLayout linearLayout = findViewById(R.id.maritalStatusContainer1);
        LinearLayout ll;
        CheckBox checkBox;
        String string = "";
        Object object;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ll = (LinearLayout) linearLayout.getChildAt(i);
            checkBox = (CheckBox) ll.getChildAt(0);
            if (checkBox.isChecked()) {
                object = checkBox.getTag();
                if (object != null)
                    string = object.toString();

                jsonArray.put(string);
            }
        }

        linearLayout = findViewById(R.id.maritalStatusContainer2);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ll = (LinearLayout) linearLayout.getChildAt(i);
            checkBox = (CheckBox) ll.getChildAt(0);
            if (checkBox.isChecked()) {
                object = checkBox.getTag();
                if (object != null)
                    string = object.toString();

                jsonArray.put(string);
            }
        }
    }

    private void hitPerfectMatchApi(Json json) {
        final LoadingDialog loadingDialog = new LoadingDialog(this);

        RequestModel requestModel = RequestModel.newRequestModel("perfect_match");
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (!isDestroyed()) {
                            if (isLoading)
                                loadingDialog.show("Please wait submitting your data...");
                            else
                                loadingDialog.dismiss();
                        }
                    }
                })
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(PerfectMatchActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1) {
                            new Session(PerfectMatchActivity.this).addInt(P.full_register, 1);
                            Intent intent = new Intent(PerfectMatchActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else
                            H.showMessage(PerfectMatchActivity.this, json.getString(P.msg));
                    }
                })
                .run("hitPerfectMatchApi");
    }

    private void setMarginTopOfCustomSpinner()
    {
        LinearLayout linearLayout = findViewById(R.id.includeContainer);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        int i = new Session(this).getInt(P.statusBarHeight);
        layoutParams.topMargin = i;
        H.log("heightIs", i + "");
        linearLayout.setLayoutParams(layoutParams);

        RelativeLayout relativeLayout = findViewById(R.id.toolbar_layout);
        layoutParams = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.topMargin = i;
        findViewById(R.id.toolbar_layout).setLayoutParams(layoutParams);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        String string = compoundButton.getText().toString();
        if (string.equalsIgnoreCase("other"))
        {
            if (b)
                findViewById(R.id.otherEthnicityInputLayout).setVisibility(View.VISIBLE);
            else
                findViewById(R.id.otherEthnicityInputLayout).setVisibility(View.GONE);
        }
    }
}
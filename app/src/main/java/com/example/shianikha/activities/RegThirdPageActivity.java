package com.example.shianikha.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.Session;
import com.example.App;
import com.example.shianikha.R;
import com.example.shianikha.commen.CommonListHolder;
import com.example.shianikha.commen.P;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class RegThirdPageActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayAdapter<String> arrayAdapter;

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_reg_third_page);

        session = new Session(this);

        findViewById(R.id.educationEditText).setOnClickListener(this);
        findViewById(R.id.occupationEditText).setOnClickListener(this);
        findViewById(R.id.countryEditText).setOnClickListener(this);
        findViewById(R.id.stateEditText).setOnClickListener(this);
        findViewById(R.id.cityEditText).setOnClickListener(this);
        findViewById(R.id.motherTongueEditText).setOnClickListener(this);
        findViewById(R.id.monthlyIncomeEditText).setOnClickListener(this);
        findViewById(R.id.languageEditText).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.view).setOnClickListener(this);

        setMarginTopOfCustomSpinner();
        setUpTextWatcher();
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

        if (view.getId() == R.id.occupationEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search Occupation");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.occupationNameList);
        } else if (view.getId() == R.id.educationEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search education");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.educationNameList);
        } else if (view.getId() == R.id.countryEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search country");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.countryNameList);
        } else if (view.getId() == R.id.stateEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search state");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.stateNameList);
        } else if (view.getId() == R.id.cityEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search city");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.cityNameList);
        } else if (view.getId() == R.id.motherTongueEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search mother tongue");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.languageNameList);
        } else if (view.getId() == R.id.monthlyIncomeEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search income");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.monthlyIncomeNameList);
        } else if (view.getId() == R.id.languageEditText)
        {
            ((EditText) findViewById(R.id.editText)).setHint("Search language");
            String[] languageArray = new String[CommonListHolder.languageNameList.size()];
            languageArray = CommonListHolder.languageNameList.toArray(languageArray);
            showMultiChoiceDialog(languageArray, view);
            return;
            //arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, languageNameList);
        }
        findViewById(R.id.view).setVisibility(View.VISIBLE);

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
    boolean[] checkedArray;

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
                        findViewById(R.id.otherLanguageInputLayout).setVisibility(View.VISIBLE);
                    else
                        findViewById(R.id.otherLanguageInputLayout).setVisibility(View.GONE);
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
        if (v instanceof TextInputLayout && v.getId()!=R.id.residentialAddressTextInputLayout)
        {
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
    public void onClick(View v) {
        if (v.getId() == R.id.button)

            makeJson();
        else if (v.getId() == R.id.view)
            hideCustomSpinnerLayout();
        else
            setUpCustomSpinner(v);
    }

    private void makeJson() {


        EditText editText = findViewById(R.id.educationEditText);
        String string = editText.getText().toString();

        if (string.isEmpty()) {
            H.showMessage(this, "Please select education");
            return;
        } else if (string.equalsIgnoreCase("others") || string.equalsIgnoreCase("other")) {
            String stringg = "";

            stringg = ((EditText) findViewById(R.id.other_education_details_edt)).getText().toString();
            if (stringg.isEmpty()) {
                H.showMessage(this, "Please enter other details about education");
                return;
            }
            App.masterJson.addString(P.other_edulevel, stringg);

        }
        int i = CommonListHolder.educationNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.edu_level, CommonListHolder.educationIdList.get(i));


        editText = findViewById(R.id.occupationEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select occupation");
            return;
        } else if (string.equalsIgnoreCase("others")) {
            String stringg = "";

            stringg = ((EditText) findViewById(R.id.other_details_occupation_edt)).getText().toString();
            if (stringg.isEmpty()) {
                H.showMessage(this, "Please enter other details about occupation");
                return;
            }
            App.masterJson.addString(P.about_occupation, stringg);

        }
        i = CommonListHolder.occupationNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.occupation_id, CommonListHolder.occupationIdList.get(i));

        editText = findViewById(R.id.monthlyIncomeEditText);
        string = editText.getText().toString();
        i = CommonListHolder.monthlyIncomeNameList.indexOf(string);
        if (i!=-1)
            App.masterJson.addString(P.monthly_income,CommonListHolder.monthlyIncomeIdList.get(i));


        string = ((EditText) findViewById(R.id.res_add_ed)).getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please enter residential address");
            return;
        }
        App.masterJson.addString(P.residency_address, string);

        editText = findViewById(R.id.countryEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select country");
            return;
        }
        i = CommonListHolder.countryNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.country_res, CommonListHolder.countryIdList.get(i));

        editText = findViewById(R.id.stateEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select state");
            return;
        }
        i = CommonListHolder.stateNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.state, CommonListHolder.stateIdList.get(i));

        editText = findViewById(R.id.cityEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select city");
            return;
        }
        i = CommonListHolder.cityNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.city, CommonListHolder.cityIdList.get(i));

        editText = findViewById(R.id.motherTongueEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select Mothertongue");
            return;
        }
        i = CommonListHolder.languageNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.mother_tongue, CommonListHolder.languageIdList.get(i));


        editText = findViewById(R.id.languageEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select languages");
            return;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(string,",");
        JSONArray jsonArray = new JSONArray();
        while (stringTokenizer.hasMoreTokens())
        {
            string = stringTokenizer.nextToken().trim();
            H.log("tokenIs",string);
            i= CommonListHolder.languageNameList.indexOf(string);
            if (i!=-1)
                jsonArray.put(CommonListHolder.languageIdList.get(i));
        }

        App.masterJson.addJSONArray(P.language,jsonArray);

        editText = findViewById(R.id.otherLanguageEditText);
        string = editText.getText().toString();
        App.masterJson.addString(P.other_language,string);

        H.log("jsonArrayIs",jsonArray+"");

        H.log("masterJsonIs", App.masterJson.toString());
        startActivity(new Intent(this, RegFourthPageActivity.class));
    }

    private void setMarginTopOfCustomSpinner() {
        LinearLayout linearLayout = findViewById(R.id.includeContainer);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        int i = new Session(this).getInt(P.statusBarHeight);
        layoutParams.topMargin = i;
        H.log("heightIs", i + "");
        linearLayout.setLayoutParams(layoutParams);
    }
}

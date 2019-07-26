package com.example.shianikha.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.Session;
import com.example.App;
import com.example.shianikha.R;
import com.example.shianikha.commen.P;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class RegFourthPageActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> languageNameList, languageCodeList, smokingNameList, smokingCodeList, relocateNameList, relocateCodeList;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_reg_fourth_page);

        session = new Session(this);
        findViewById(R.id.view).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);

        setMarginTopOfCustomSpinner();
        extractRequireList();
    }

    private void extractRequireList() {
        JsonList jsonList;

        //for language
        String string = session.getString(P.language);
        if (string != null) {
            jsonList = new JsonList(string);
            languageNameList = new ArrayList<>();
            languageCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                languageNameList.add(string);

                string = j.getString(P.id);
                languageCodeList.add(string);
            }
        }

        //for smoking
        string = session.getString(P.smoking);
        if (string != null) {
            jsonList = new JsonList(string);
            smokingNameList = new ArrayList<>();
            smokingCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                smokingNameList.add(string);

                string = j.getString(P.id);
                smokingCodeList.add(string);
            }
        }

        //for relocate
        string = session.getString(P.relocate);
        if (string != null) {
            jsonList = new JsonList(string);
            relocateNameList = new ArrayList<>();
            relocateCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                relocateNameList.add(string);

                string = j.getString(P.id);
                relocateCodeList.add(string);
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

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.view)
            hideCustomSpinnerLayout();
        else  if(v.getId()==R.id.yesChildren)
        {

        }
        else if (v.getId() == R.id.button)
            startActivity(new Intent(this,RegFifthPageActivity.class));
            //makeJson();
        else
            setUpCustomSpinner(v);
    }

    private void makeJson()
    {
        String string = "";
        if (string.isEmpty())
        {
            H.showMessage(this, "Please select relocation status");
            return;
        }
        int i = relocateNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.relocate_id, relocateCodeList.get(i));

        i = ((RadioGroup)findViewById(R.id.convertedRadioGroup)).getCheckedRadioButtonId();
        if (i == R.id.yesConverted)
            string = "1";
        else if (i == R.id.notConverted)
            string = "0";
        App.masterJson.addString(P.cvt_islam, string);

        i = ((RadioGroup)findViewById(R.id.syedRadioGroup)).getCheckedRadioButtonId();
        if (i == R.id.yesSyed)
            string = "1";
        else if (i == R.id.notSyed)
            string = "0";
        else if (i == R.id.donnKnowSyed)
            string = "2";
        App.masterJson.addString(P.syed, string);

        i = ((RadioGroup)findViewById(R.id.handicapRadioGroup)).getCheckedRadioButtonId();
        if (i == R.id.yesHandicap)
            string = "1";
        else if (i == R.id.notHandicap)
            string = "0";
        else if (i == R.id.donnKnowHandicap)
            string = "2";
        App.masterJson.addString(P.handicap, string);

        i = ((RadioGroup)findViewById(R.id.childrenRadioGroup)).getCheckedRadioButtonId();
        if (i == R.id.yesChildren)
            string = "1";
        else if (i == R.id.notChildren)
            string = "0";
        App.masterJson.addString(P.children, string);

        H.log("masterJsonIs",App.masterJson.toString());
        startActivity(new Intent(this,RegFifthPageActivity.class));
    }

    private void setMarginTopOfCustomSpinner() {
        LinearLayout linearLayout = findViewById(R.id.includeContainer);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        int i = new Session(this).getInt(P.statusBarHeight);
        layoutParams.topMargin = i;
        H.log("heightIs", i + "");
        linearLayout.setLayoutParams(layoutParams);
    }

    public  void OnChildrenYesChecked(View view)
    {

    }

    public  void OnChildrenNoChecked(View view)
    {

    }
}


package com.example.shianikha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.Session;
import com.example.App;
import com.example.shianikha.R;
import com.example.shianikha.commen.CommonListHolder;
import com.example.shianikha.commen.P;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class RegSecondPageActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ArrayAdapter<String> arrayAdapter;

    private Session session;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_reg_second_page);

        session = new Session(this);
        linearLayout = findViewById(R.id.linearLayout);


        findViewById(R.id.heightEditText).setOnClickListener(this);
        findViewById(R.id.bodyEditText).setOnClickListener(this);
        findViewById(R.id.shiaCommunity).setOnClickListener(this);
        findViewById(R.id.complexionEditText).setOnClickListener(this);
        findViewById(R.id.maritalStatusEditText).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.view).setOnClickListener(this);
        findViewById(R.id.logInHereTextView).setOnClickListener(this);

        findViewById(R.id.imageView).setOnClickListener(this);
        ((RadioGroup) findViewById(R.id.convertedRadioGroup)).setOnCheckedChangeListener(this);
        ((RadioGroup) findViewById(R.id.syedRadioGroup)).setOnCheckedChangeListener(this);
        ((RadioGroup) findViewById(R.id.childrenRadioGroup)).setOnCheckedChangeListener(this);


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
        findViewById(R.id.view).setVisibility(View.VISIBLE);
        EditText editText = findViewById(R.id.editText);

        if (view.getId() == R.id.heightEditText) {
            editText.setHint("Search height");
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.heightList);
        } else if (view.getId() == R.id.complexionEditText) {
            editText.setHint("Search Complexion");
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.complexionNameList);
        } else if (view.getId() == R.id.bodyEditText) {
            editText.setHint("Search Body Type");
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.physicalStatusNameList);
        } else if (view.getId() == R.id.shiaCommunity) {
            editText.setHint("Search shia Community Type");
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.religionNameList);
        } else if (view.getId() == R.id.maritalStatusEditText) {
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setHint("Search marital status");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.maritalStatusNameList);
        }

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
    public void onClick(View v) {

        if (v.getId() == R.id.imageView)
            addNewSubLayout();
        else if (v.getId() == R.id.deleteImageView)
            deleteLayout(v);
        else if (v.getId() == R.id.button)
            makeJson();
        else if (v.getId() == R.id.view)
            hideCustomSpinnerLayout();
        else if (v.getId() == R.id.logInHereTextView)
        {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        else
            setUpCustomSpinner(v);
    }

    private void deleteLayout(View v) {
        Object object = v.getTag();
        if (object != null) {
            long l = (long) object;
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                View view = linearLayout.getChildAt(i);
                Object o = view.getTag();
                if (o != null && (long) o == l) {
                    linearLayout.removeViewAt(i);
                    setSrNo();
                    break;
                }
            }
        }
    }

    private void addNewSubLayout() {
        long l = System.currentTimeMillis();
        View view = LayoutInflater.from(this).inflate(R.layout.inflatable_children_layout, null);
        view.setTag(l);

        ImageView imageView = view.findViewById(R.id.deleteImageView);
        imageView.setTag(l);
        imageView.setOnClickListener(this);
        imageView.setVisibility(View.VISIBLE);

        linearLayout.addView(view);
        setSrNo();
    }

    private void setSrNo() {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View view = linearLayout.getChildAt(i);
            ((TextView) view.findViewById(R.id.textView)).setText("Children " + (i + 1));
        }
    }

    private void makeJson() {
        App.masterJson.addString(P.token_id, session.getString(P.tokenData));
        H.log("tokenIs", session.getString(P.tokenData));

        EditText editText = findViewById(R.id.complexionEditText);
        String string = editText.getText().toString();

        if (string.isEmpty()) {
            H.showMessage(this, "Please select complexion");
            return;
        }
        int i = CommonListHolder.complexionNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.skin_tone, CommonListHolder.complexionIdList.get(i));

        editText = findViewById(R.id.heightEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select height");
            return;
        }
        App.masterJson.addString(P.height, string);

        editText = findViewById(R.id.bodyEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select body type");
            return;
        }
        i = CommonListHolder.physicalStatusNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.body_type, CommonListHolder.physicalStatusIdList.get(i));

        editText = findViewById(R.id.shiaCommunity);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select shia commmunity");
            return;
        }
        i = CommonListHolder.religionNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.religion, CommonListHolder.religionIdList.get(i));


        string = ((EditText) findViewById(R.id.otherShiaEditText)).getText().toString();
        /*if (string.isEmpty()) {
            H.showMessage(this, "Please enter other shia details");
            return;
        }*/
        App.masterJson.addString(P.other_religion, string);


        string = findViewById(((RadioGroup) findViewById(R.id.convertedRadioGroup)).getCheckedRadioButtonId()).getTag().toString();
        App.masterJson.addString(P.cvt_islam, string);

        string = findViewById(((RadioGroup) findViewById(R.id.syedRadioGroup)).getCheckedRadioButtonId()).getTag().toString();
        App.masterJson.addString(P.syed, string);

        editText = findViewById(R.id.maritalStatusEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select marital status");
            return;
        }
        i = CommonListHolder.maritalStatusNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.marital_status, CommonListHolder.maritalStatusIdList.get(i));

        string = findViewById(((RadioGroup) findViewById(R.id.childrenRadioGroup)).getCheckedRadioButtonId()).getTag().toString();
        App.masterJson.addString(P.children, string);

        JSONArray jsonArray = new JSONArray();
        Json json;
        LinearLayout childLayout;
        for (i=0; i<linearLayout.getChildCount(); i++)
        {
            json = new Json();
            childLayout =(LinearLayout) linearLayout.getChildAt(i);
            editText = childLayout.findViewById(R.id.childAgeEditText);
            string = editText.getText().toString();
            json.addString(P.children_age,string);

            editText = childLayout.findViewById(R.id.childEducationEditText);
            string = editText.getText().toString();
            json.addString(P.children_education,string);
            jsonArray.put(json);
        }
        App.masterJson.addJSONArray(P.children_details,jsonArray);

        H.log("masterJsonIs", App.masterJson.toString());
        startActivity(new Intent(this, RegThirdPageActivity.class));
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
    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        i = radioGroup.getCheckedRadioButtonId();
        if (i == R.id.notChildren)
            findViewById(R.id.relativeLayout).setVisibility(View.GONE);
        else if (i == R.id.yesChildren) {
            findViewById(R.id.relativeLayout).setVisibility(View.VISIBLE);
            if (((LinearLayout)findViewById(R.id.linearLayout)).getChildCount() == 0)
                addNewSubLayout();
        }
    }
}

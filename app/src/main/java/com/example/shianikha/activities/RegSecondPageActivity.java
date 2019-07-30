package com.example.shianikha.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.Session;
import com.example.App;
import com.example.shianikha.R;
import com.example.shianikha.commen.P;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RegSecondPageActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ArrayAdapter<String> arrayAdapter;

    private ArrayList<String> heightList, maritalStatusNameList, maritalStatusIdList,complexionNameList,complexionIdList,physicalStatusNameList,physicalStatusIdList, shiaCommunityNameList, shiaCommunityIdList;
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

        findViewById(R.id.imageView).setOnClickListener(this);
        ((RadioGroup) findViewById(R.id.convertedRadioGroup)).setOnCheckedChangeListener(this);
        ((RadioGroup) findViewById(R.id.syedRadioGroup)).setOnCheckedChangeListener(this);
        ((RadioGroup) findViewById(R.id.childrenRadioGroup)).setOnCheckedChangeListener(this);


        setMarginTopOfCustomSpinner();
        extractRequireList();
    }

    private void extractRequireList() {
        JsonList jsonList;



        //for height
        String string = session.getString(P.height);
        if (string != null) {
            try {
                JSONArray jsonArray = new JSONArray(string);
                heightList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    string = jsonArray.getString(i);
                    heightList.add(string);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //for body type
        string = session.getString(P.physical_status);
        if (string != null) {
            jsonList = new JsonList(string);
            physicalStatusNameList = new ArrayList<>();
            physicalStatusIdList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                physicalStatusNameList.add(string);

                string = j.getString(P.id);
                physicalStatusIdList.add(string);
            }
        }

        //for shia Community
        string = session.getString(P.religion);
        if (string != null) {
            jsonList = new JsonList(string);
            shiaCommunityNameList = new ArrayList<>();
            shiaCommunityIdList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                shiaCommunityNameList.add(string);

                string = j.getString(P.id);
                shiaCommunityIdList.add(string);
            }
        }

        //for complexion
        string = session.getString(P.complexion);
        if (string != null) {
            jsonList = new JsonList(string);
            complexionNameList = new ArrayList<>();
            complexionIdList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                complexionNameList.add(string);

                string = j.getString(P.id);
                complexionIdList.add(string);
            }
        }


        //  for marital status
        string = session.getString(P.marital_status);
        if (string != null) {
            jsonList = new JsonList(string);
            maritalStatusNameList = new ArrayList<>();
            maritalStatusIdList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                maritalStatusNameList.add(string);

                string = j.getString(P.id);
                maritalStatusIdList.add(string);
            }
        }

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
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, heightList);
        }

        else if (view.getId() == R.id.complexionEditText) {
            editText.setHint("Search Complexion");
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, complexionNameList);
        }
        else if (view.getId() == R.id.bodyEditText) {
            editText.setHint("Search Body Type");
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, physicalStatusNameList);
        }
        else if (view.getId() == R.id.shiaCommunity) {
            editText.setHint("Search shia Community Type");
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, shiaCommunityNameList);
        }
        else if (view.getId() == R.id.maritalStatusEditText)
        {
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setHint("Search marital status");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, maritalStatusNameList);
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
        int i = complexionNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.skin_tone, complexionIdList.get(i));



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
        i = physicalStatusNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.body_type, physicalStatusIdList.get(i));

        editText = findViewById(R.id.shiaCommunity);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select shia commmunity");
            return;
        }
        i = shiaCommunityNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.body_type, shiaCommunityIdList.get(i));


        string = ((EditText)findViewById(R.id.otherShiaEditText)).getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(this,"Please enter other shia details");
            return;
        }
        App.masterJson.addString(P.othrt_religion, shiaCommunityIdList.get(i));


        string =  findViewById(((RadioGroup)findViewById(R.id.convertedRadioGroup)).getCheckedRadioButtonId()).getTag().toString();
        App.masterJson.addString(P.cvt_islam, string);

        string =  findViewById(((RadioGroup)findViewById(R.id.syedRadioGroup)).getCheckedRadioButtonId()).getTag().toString();
        App.masterJson.addString(P.syed, string);

        string =  findViewById(((RadioGroup)findViewById(R.id.childrenRadioGroup)).getCheckedRadioButtonId()).getTag().toString();
        App.masterJson.addString(P.children, string);


        editText = findViewById(R.id.maritalStatusEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select marital status");
            return;
        }
        i = maritalStatusNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.marital_status, maritalStatusIdList.get(i));


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
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int viewId = radioGroup.getId();

         if (viewId == R.id.childrenRadioGroup)
        {
            String string="";
            string =  findViewById(((RadioGroup)findViewById(R.id.childrenRadioGroup)).getCheckedRadioButtonId()).getTag().toString();
            App.masterJson.addString(P.children, string);
        }
    }
}

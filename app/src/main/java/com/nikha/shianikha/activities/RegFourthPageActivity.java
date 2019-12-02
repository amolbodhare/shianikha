package com.nikha.shianikha.activities;

import android.content.Intent;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Session;
import com.nikha.App;
import com.nikha.shianikha.R;
import com.nikha.shianikha.commen.CommonListHolder;
import com.nikha.shianikha.commen.P;

public class RegFourthPageActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayAdapter<String> arrayAdapter;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_reg_fourth_page);

        findViewById(R.id.fatherOccupationEditText).setOnClickListener(this);
        findViewById(R.id.motherOccupationEditText).setOnClickListener(this);

        session = new Session(this);
        findViewById(R.id.view).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);

        setMarginTopOfCustomSpinner();
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

    private void setUpCustomSpinner(final View view) {
        ListView listView = findViewById(R.id.listView);
        findViewById(R.id.view).setVisibility(View.VISIBLE);

        if (view.getId() == R.id.fatherOccupationEditText || view.getId() == R.id.motherOccupationEditText)
        {
            ((EditText) findViewById(R.id.editText)).setHint("Search Occupation");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, CommonListHolder.occupationNameList);
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
        if (v.getId() == R.id.view)
            hideCustomSpinnerLayout();
        else if (v.getId() == R.id.yesChildren) {

        } else if (v.getId() == R.id.button)

            makeJson();
        else
            setUpCustomSpinner(v);
    }

    private void makeJson()
    {
        EditText editText =  findViewById(R.id.fatherName);
        String string = editText.getText().toString();

        if (string.isEmpty()) {
            H.showMessage(this, "Please enter father name");
            return;
        }
        App.masterJson.addString(P.father_name, string);


        editText = findViewById(R.id.fatherOccupationEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select Father's occupation");
            return;
        } else if (string.equalsIgnoreCase("others")) {
            String stringg = "";

            stringg = ((EditText) findViewById(R.id.other_details_fathers_occupation_edt)).getText().toString();
            if (stringg.isEmpty()) {
                H.showMessage(this, "Please enter father's other details about occupation");
                return;
            }
            App.masterJson.addString(P.father_other_occupation, stringg);

        }
        int i = CommonListHolder.occupationNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.father_occupation_id, CommonListHolder.occupationIdList.get(i));


        editText =  findViewById(R.id.motherName);
        string = editText.getText().toString();

        if (string.isEmpty()) {
            H.showMessage(this, "Please enter mother name");
            return;
        }
        App.masterJson.addString(P.mother_name, string);


        editText = findViewById(R.id.motherOccupationEditText);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select Mother's occupation");
            return;
        } else if (string.equalsIgnoreCase("others")) {
            String stringg = "";

            stringg = ((EditText) findViewById(R.id.other_details_mothers_occupation_edt)).getText().toString();
            if (stringg.isEmpty()) {
                H.showMessage(this, "Please enter mother's other details about occupation");
                return;
            }
            App.masterJson.addString(P.mother_other_occupation, stringg);

        }

        i = CommonListHolder.occupationNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.mother_occupation_id, CommonListHolder.occupationIdList.get(i));


        string = ((EditText) findViewById(R.id.numberSibilings)).getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please enter number of siblings");
            return;
        }
        App.masterJson.addString(P.siblings, string);


        string = ((EditText) findViewById(R.id.parentContact)).getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please enter parent's altername contact");
            return;
        }
        App.masterJson.addString(P.siblialternate_contact_no, string);


        string = findViewById(((RadioGroup) findViewById(R.id.disabilityGroup)).getCheckedRadioButtonId()).getTag().toString();
        App.masterJson.addString(P.handicap, string);

        string = findViewById(((RadioGroup) findViewById(R.id.namazGroup)).getCheckedRadioButtonId()).getTag().toString();
        App.masterJson.addString(P.namaz, string);

        string = findViewById(((RadioGroup) findViewById(R.id.rozaGroup)).getCheckedRadioButtonId()).getTag().toString();
        App.masterJson.addString(P.roza, string);

        string = findViewById(((RadioGroup) findViewById(R.id.hijabGroup)).getCheckedRadioButtonId()).getTag().toString();
        App.masterJson.addString(P.hijab_preference, string);

        string = findViewById(((RadioGroup) findViewById(R.id.familyDetailsGroup)).getCheckedRadioButtonId()).getTag().toString();
        App.masterJson.addString(P.family_details, string);

        H.log("masterJsonIs", App.masterJson.toString());
        startActivity(new Intent(this, RegFifthPageActivity.class));
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

    private void setMarginTopOfCustomSpinner() {
        LinearLayout linearLayout = findViewById(R.id.includeContainer);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        int i = new Session(this).getInt(P.statusBarHeight);
        layoutParams.topMargin = i;
        H.log("heightIs", i + "");
        linearLayout.setLayoutParams(layoutParams);
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
package com.example.shianikha.activities;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.RadioGroup;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.adoisstudio.helper.Api;
        import com.adoisstudio.helper.H;
        import com.adoisstudio.helper.Json;
        import com.adoisstudio.helper.JsonList;
        import com.adoisstudio.helper.LoadingDialog;
        import com.adoisstudio.helper.Session;
        import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
        import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
        import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
        import com.example.App;
        import com.example.shianikha.R;
        import com.example.shianikha.commen.C;
        import com.example.shianikha.commen.P;
        import com.example.shianikha.commen.RequestModel;

        import java.util.ArrayList;

public class PartnerPreferenceActivity extends AppCompatActivity implements View.OnClickListener
{
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> maritalStatusNameList, maritalStatusCodelist, ethincityNameList, ethincityCodeList, minEduNameList, minEduCodeList;
    private Session session;
    TextView tvMax;
    TextView tvMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_preference);
        session = new Session(this);

        findViewById(R.id.marital_status_spinner_ed).setOnClickListener(this);
        findViewById(R.id.ethincity_spinner_ed).setOnClickListener(this);
        findViewById(R.id.min_edu_spinner_ed).setOnClickListener(this);
        findViewById(R.id.view).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);

        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.age_rangeSeekbar);

        // get min and max text view
         tvMin = (TextView) findViewById(R.id.textMin1);

         tvMax = (TextView) findViewById(R.id.textMax1);

// set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText("Min " + String.valueOf(minValue) + " years");
                tvMin.setTag(minValue);
                tvMax.setText("Max " + String.valueOf(maxValue) + " years");
                tvMax.setTag(maxValue);
            }
        });

// set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });


        setMarginTopOfCustomSpinner();
        extractRequireList();
    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.view)
            hideCustomSpinnerLayout();
        else if (v.getId() == R.id.sub_drawerMenu)
        {
            onMethodClick(v);
        }
        else if (v.getId() == R.id.btn_next)
            makeJson();
        else
            setUpCustomSpinner(v);
    }
    public void  onMethodClick(View v)
    {
        finish();
        ((PartnerPreferenceActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }
    private void setMarginTopOfCustomSpinner() {
        LinearLayout linearLayout = findViewById(R.id.includeContainer);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        int i = new Session(this).getInt(P.statusBarHeight);
        layoutParams.topMargin = i;
        H.log("heightIs", i + "");
        linearLayout.setLayoutParams(layoutParams);
    }

    private void extractRequireList() {
        JsonList jsonList;

        //for language
        String string = session.getString(P.marital_status);
        if (string != null) {
            jsonList = new JsonList(string);
            maritalStatusNameList = new ArrayList<>();
            maritalStatusCodelist = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                maritalStatusNameList.add(string);

                string = j.getString(P.id);
                maritalStatusCodelist.add(string);
            }
        }

        //for smoking
        string = session.getString(P.ethnicity);
        if (string != null) {
            jsonList = new JsonList(string);
            ethincityNameList = new ArrayList<>();
            ethincityCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                ethincityNameList.add(string);

                string = j.getString(P.id);
                ethincityCodeList.add(string);
            }
        }

        //for relocate
        string = session.getString(P.education);
        if (string != null) {
            jsonList = new JsonList(string);
            minEduNameList = new ArrayList<>();
            minEduCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                minEduNameList.add(string);

                string = j.getString(P.id);
                minEduCodeList.add(string);
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

        if (view.getId()==R.id.marital_status_spinner_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Search Marital Status");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,maritalStatusNameList);
        }
        else if (view.getId() == R.id.ethincity_spinner_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Search Ethincity");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,ethincityNameList);
        }

        else if (view.getId() == R.id.min_edu_spinner_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Search Education");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,minEduNameList);
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

    private void makeJson()
    {
        Json json = new Json();
        json.addString(P.token_id,session.getString(P.tokenData));

        EditText editText = findViewById(R.id.marital_status_spinner_ed);
        String string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select your marital status");
            return;
        }
        int i = maritalStatusNameList.indexOf(string);
        if (i != -1)
            json.addString(P.marital_status_id, maritalStatusCodelist.get(i));

        editText = findViewById(R.id.ethincity_spinner_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select smoking status");
            return;
        }
        i = ethincityNameList.indexOf(string);
        if (i != -1)
            json.addString(P.ethnicitys_id, ethincityCodeList.get(i));

        editText = findViewById(R.id.min_edu_spinner_ed);
        string = editText.getText().toString();

        if (string.isEmpty())
        {
            H.showMessage(this, "Please select min Education");
            return;
        }
        i = minEduNameList.indexOf(string);
        if (i != -1)
            json.addString(P.education_id, minEduCodeList.get(i));


        string = ((CheckBox)findViewById(R.id.checkBox1)).isChecked() ? "1" : "0";
        json.addString(P.request_from_anyone,string);

        string = ((CheckBox)findViewById(R.id.checkBox2)).isChecked() ? "1" : "0";
        json.addString(P.request_from_preferred_match,string);

        string = ((CheckBox)findViewById(R.id.checkBox3)).isChecked() ? "1" : "0";
        json.addString(P.sancity_of_the_holy_quran,string);

        json.addString(P.min_age,tvMin.getTag().toString());
        json.addString(P.max_age,tvMax.getTag().toString());


        H.log("masterJsonIs",App.masterJson.toString());
        hitPerfectMatchApi(json);
        //startActivity(new Intent(this,RegFifthPageActivity.class));
    }

    private void hitPerfectMatchApi(Json json)
    {
        final LoadingDialog loadingDialog = new LoadingDialog(this);

        RequestModel requestModel = RequestModel.newRequestModel("perfect_match");
        requestModel.addJSON(P.data, json);

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
                        H.showMessage(PartnerPreferenceActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1)
                        {
                            /*new Session(PartnerPreferenceActivity.this).addInt(P.full_register, 1);
                            Intent intent = new Intent(PartnerPreferenceActivity.this, HomeActivity.class);
                            startActivity(intent);*/
                            Toast.makeText(PartnerPreferenceActivity.this, "Your Preference is Submitted", Toast.LENGTH_SHORT).show();
                        } else
                            H.showMessage(PartnerPreferenceActivity.this, json.getString(P.msg));
                    }
                })
                .run("hitPerfectMatchApi");
    }

}

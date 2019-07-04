package com.example.shianikha.subfragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.App;
import com.example.shianikha.R;
import com.example.shianikha.activities.RegSecondPageActivity;
import com.example.shianikha.activities.RegistrationActivity;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class EditProfileFragment extends Fragment implements View.OnClickListener {

    ImageView gender_male_imv;
    ImageView gender_female_imv;
    View view;
    private OnFragmentInteractionListener mListener;
    Context context;
    MyBounceInterpolator interpolator;
    String gender="";
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> profileForList, cityOfResidenceList, stateList, countryOfResidenceList, countryOfCitizenshipList, heightList;
    private ArrayList<String> religionList, ethincityList, fathersCityList, mothersCityList,currentOccupationList,highestLevelOfEducationList;
    private ArrayList<String> languageList, habbitList, relocateList, seekingMarriageList,intrestedInList;

    private ArrayList<String> countryNameList;
    private ArrayList<String> countryCodeList;


    private ArrayList<String> profileForCodeList, cityOfResidenceCodeList, stateCodeList, countryOfResidenceCodeList, countryOfCitizenshipCodeList;
    private ArrayList<String> religionCodeList, ethincityCodeList, fathersCityCodeList, mothersCityCodeList,currentOccupationCodeList,highestLevelOfEducationCodeList;
    private ArrayList<String> languageCodeList, habbitCodeList, relocateCodeList, seekingMarriageCodeList,intrestedInCodeList;
    private Session session;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        view=inflater.inflate(R.layout.fragment_edit_profile, container, false);

        session = new Session(getActivity());

        view.findViewById(R.id.profile_for_ed).setOnClickListener(this);
        view.findViewById(R.id.countryCodeEditText).setOnClickListener(this);
        view.findViewById(R.id.city_ed).setOnClickListener(this);
        view.findViewById(R.id.state_ed).setOnClickListener(this);
        view.findViewById(R.id.country_of_residence_ed).setOnClickListener(this);
        view.findViewById(R.id.country_of_citizenship_ed).setOnClickListener(this);
        view.findViewById(R.id.ed_dob).setOnClickListener(this);
        view.findViewById(R.id.height_ed).setOnClickListener(this);
        view.findViewById(R.id.religion_ed).setOnClickListener(this);
        view.findViewById(R.id.ethinicity_ed).setOnClickListener(this);

        view.findViewById(R.id.fathers_city_ed).setOnClickListener(this);
        view.findViewById(R.id.mothers_city_ed).setOnClickListener(this);
        view.findViewById(R.id.current_occupation_ed).setOnClickListener(this);
        view.findViewById(R.id.higest_level_edu_ed).setOnClickListener(this);
        view.findViewById(R.id.lang_ed).setOnClickListener(this);
        view.findViewById(R.id.habbit_ed).setOnClickListener(this);
        view.findViewById(R.id.relocate_ed).setOnClickListener(this);
        view.findViewById(R.id.seeking_marriage_ed).setOnClickListener(this);
        view.findViewById(R.id.interesterd_in_ed).setOnClickListener(this);

        view.findViewById(R.id.btn_save).setOnClickListener(this);
        view.findViewById(R.id.view).setOnClickListener(this);

        //setMarginTopOfCustomSpinner(view);
        extractRequireList();
        handleGenderClickListner();

        return  view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ed_dob)
            handleDatePicker();
        if (v.getId() == R.id.btn_save)
            makeJson(view);
        else if (v.getId() == R.id.view)
            hideCustomSpinnerLayout(view);
        else
            setUpCustomSpinner(view,v);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public  void radioButtonClick(View view)
    {
        if((view.getRootView().getId())==(R.id.islam_convert_rdg))
        {
            Toast.makeText(context, "heyyyyy", Toast.LENGTH_SHORT).show();
        }
    }
    class MyBounceInterpolator implements android.view.animation.Interpolator {
        private double mAmplitude = 1;
        private double mFrequency = 10;

        MyBounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) *
                    Math.cos(mFrequency * time) + 1);
        }
    }

    private void makeJson(View view)
    {
        Json submit_json=new Json();

        EditText editText = view.findViewById(R.id.profile_for_ed);
        String string = editText.getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(getActivity(), "Please select profile for");
            return;
        }
        int i = profileForList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.profile_for, profileForCodeList.get(i));


        /*if ((App.device_id).isEmpty())
        {
            H.showMessage(getActivity(), "Device id not met");
            return;
        }
        submit_json.addString(P.token_id, App.device_id);*/


        string = ((EditText) view.findViewById(R.id.full_name_til)).getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please specify Full Name");
            return;
        }
        submit_json.addString(P.name, string);


        string = ((EditText)view.findViewById(R.id.email_til)).getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(context,"Please enter email!");
            return;
        }
        if (!string.contains("@") || !string.contains(".") || (string.indexOf("@")-string.indexOf(".")==1))
        {
            H.showMessage(context,"Please enter valid email!");
            return;
        }
        submit_json.addString(P.email,string);

        string = ((EditText)view.findViewById(R.id.countryCodeEditText)).getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(context,"Please enter country code!");
            return;
        }
        submit_json.addString(P.country_code,string);

        string = ((EditText)view.findViewById(R.id.mobile_no)).getText().toString();
        if (string.isEmpty())
        {
            H.showMessage(context,"Please enter mobile number!");
            return;
        }
        submit_json.addString(P.ph_number,string);

        if (gender.isEmpty())
        {
            H.showMessage(context,"Please select gender!");
            return;
        }
        submit_json.addString(P.gender,gender);


        editText = view.findViewById(R.id.city_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select City of residence");
            return;
        }
        i = cityOfResidenceList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.city, cityOfResidenceCodeList.get(i));

        editText = view.findViewById(R.id.state_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select state");
            return;
        }
        i = stateList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.state, stateCodeList.get(i));

        editText = view.findViewById(R.id.country_of_residence_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select country of residence");
            return;
        }
        i = countryOfResidenceList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.country_res, countryOfResidenceCodeList.get(i));

        editText = view.findViewById(R.id.country_of_citizenship_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select country of citizenship");
            return;
        }
        i = countryOfCitizenshipList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.country_citizen, countryOfCitizenshipCodeList.get(i));


        string = ((EditText) view.findViewById(R.id.dateOfBirthEditText)).getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select date of birth");
            return;
        }
        submit_json.addString(P.dob, string);

        string = ((EditText) view.findViewById(R.id.height_ed)).getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select height");
            return;
        }
        submit_json.addString(P.height, string);

        editText = view.findViewById(R.id.religion_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select religion");
            return;
        }
        i = religionList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.religion, religionCodeList.get(i));

        editText = view.findViewById(R.id.ethinicity_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select ethincity");
            return;
        }
        i = ethincityList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.ethnicity, ethincityCodeList.get(i));

        editText = view.findViewById(R.id.fathers_city_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select Father city");
            return;
        }
        i = fathersCityList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.father_city, fathersCityCodeList.get(i));

        editText = view.findViewById(R.id.mothers_city_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select Mother city");
            return;
        }
        i = mothersCityList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.mother_city, mothersCityCodeList.get(i));

        editText = view.findViewById(R.id.current_occupation_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select current occupation");
            return;
        }

        i = currentOccupationList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.occupation_id, currentOccupationCodeList.get(i));


        string = ((EditText) view.findViewById(R.id.other_details_occu_til)).getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please specify occupation details");
            return;
        }
        submit_json.addString(P.about_occupation, string);


        editText = view.findViewById(R.id.education_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select highest education");
            return;
        }
        i = highestLevelOfEducationList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.edulevel_id, highestLevelOfEducationCodeList.get(i));


        editText = view.findViewById(R.id.lang_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select language");
            return;
        }

        i = languageList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.language, languageCodeList.get(i));


        editText = view.findViewById(R.id.habbit_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select smoking habbit");
            return;
        }
        i = habbitList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.smoke_id, habbitCodeList.get(i));



        editText = view.findViewById(R.id.relocate_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select relocation");
            return;
        }
        i = relocateList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.relocate_id, relocateCodeList.get(i));


        //radiogroups and radiobuttons

        i = ((RadioGroup)view.findViewById(R.id.convertedRadioGroup)).getCheckedRadioButtonId();
        if (i == R.id.yesConverted)
            string = "1";
        else if (i == R.id.notConverted)
            string = "0";
        submit_json.addString(P.cvt_islam, string);

        i = ((RadioGroup)view.findViewById(R.id.syedRadioGroup)).getCheckedRadioButtonId();
        if (i == R.id.yesSyed)
            string = "1";
        else if (i == R.id.notSyed)
            string = "0";
        else if (i == R.id.donnKnowSyed)
            string = "2";
        submit_json.addString(P.syed, string);

        i = ((RadioGroup)view.findViewById(R.id.handicapRadioGroup)).getCheckedRadioButtonId();
        if (i == R.id.yesHandicap)
            string = "1";
        else if (i == R.id.notHandicap)
            string = "0";
        else if (i == R.id.donnKnowHandicap)
            string = "2";
        submit_json.addString(P.handicap, string);

        i = ((RadioGroup)view.findViewById(R.id.childrenRadioGroup)).getCheckedRadioButtonId();
        if (i == R.id.yesChildren)
            string = "1";
        else if (i == R.id.notChildren)
            string = "0";
        submit_json.addString(P.children, string);

        editText = view.findViewById(R.id.seeking_marriage_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select seeking marriage option");
            return;
        }

        i = seekingMarriageList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.seeking_marriage, seekingMarriageCodeList.get(i));


        editText = view.findViewById(R.id.interesterd_in_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select intrested in");
            return;
        }

        i = intrestedInList.indexOf(string);
        if (i != -1)
            submit_json.addString(P.intrested_in, intrestedInCodeList.get(i));

        H.log("submittedjson",submit_json.toString());
        hitEditProfileApi(submit_json);
        //startActivity(new Intent(this,RegThirdPageActivity.class));
    }

    private void setMarginTopOfCustomSpinner(View view) {
        /*LinearLayout linearLayout = view.findViewById(R.id.includeContainer);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        int i = new Session(getActivity()).getInt(P.statusBarHeight);
        layoutParams.topMargin = i;
        H.log("heightIs", i + "");
        linearLayout.setLayoutParams(layoutParams);*/
    }
    private void setUpCustomSpinner(final View mainView,final View view) {
        ListView listView = mainView.findViewById(R.id.listView);
        mainView.findViewById(R.id.view).setVisibility(View.VISIBLE);

        if (view.getId() == R.id.profile_for_ed)
        {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search profile for");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, profileForList);

        } else if (view.getId() == R.id.countryCodeEditText) {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search Country Code");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, countryCodeList);
        }
        else if (view.getId() == R.id.city_ed) {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search City Of Residence");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, cityOfResidenceList);
        }
        else if (view.getId() == R.id.state_ed) {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search state");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, stateList);
        }
        else if (view.getId() == R.id.country_of_residence_ed)
        {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search Country of Residence");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, countryOfResidenceList);
        }
        else if (view.getId() == R.id.country_of_citizenship_ed) {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search Country of Citizenship");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, countryOfCitizenshipList);
        }
        else if (view.getId() == R.id.height_ed)
        {   EditText editText = mainView.findViewById(R.id.editText);
            editText.setHint("Search height");
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, heightList);
        }
        else if (view.getId() == R.id.religion_ed) {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search Religion");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, religionList);
        }
        else if (view.getId() == R.id.ethinicity_ed) {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search ethincity");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, ethincityList);
        }
        else if (view.getId() == R.id.fathers_city_ed) {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search Fathers city");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, fathersCityList);
        }
        else if (view.getId() == R.id.mothers_city_ed) {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search Mothers city");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, mothersCityList);
        }
        else if (view.getId() == R.id.current_occupation_ed) {
            ((EditText)  mainView.findViewById(R.id.editText)).setHint("Search current occupation");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, currentOccupationList);
        }
        else if (view.getId() == R.id.education_ed) {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search highest level of education");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, highestLevelOfEducationList);
        }
        else if (view.getId() == R.id.lang_ed) {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search language");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, languageList);
        }
        else if (view.getId() == R.id.habbit_ed) {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search smoking habbit");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, habbitList);
        }
        else if (view.getId() == R.id.relocate_ed) {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search relocating");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, relocateList);
        }
        else if (view.getId() == R.id.seeking_marriage_ed) {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search smoking habbit");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, seekingMarriageList);
        }
        else if (view.getId() == R.id.interesterd_in_ed) {
            ((EditText) mainView.findViewById(R.id.editText)).setHint("Search intrested in");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, intrestedInList);
        }

        if (arrayAdapter == null)
            return;

        H.showKeyBoard(context, mainView.findViewById(R.id.editText));

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView textView = v.findViewById(R.id.textView);
                if (textView != null) {
                    Log.e("selectedIs", textView.getText().toString());
                    ((EditText) view).setText(textView.getText().toString());
                }
                hideCustomSpinnerLayout(mainView);
            }
        });

        mainView.findViewById(R.id.includeContainer).animate().translationX(0).setDuration(500);
    }

    private void hideCustomSpinnerLayout(View view) {
        int i = view.findViewById(R.id.includeContainer).getWidth();
        ((EditText) view.findViewById(R.id.editText)).setText("");
        view.findViewById(R.id.includeContainer).animate().translationX(i).setDuration(500);
        View vieww = view.findViewById(R.id.view);
        vieww.setVisibility(View.GONE);
        H.hideKeyBoard(context, view);
    }
    private void extractRequireList() {
        JsonList jsonList;

        //for profile
        String string = session.getString(P.profile_for);

        if (string != null) {
            jsonList = new JsonList(string);
            profileForList = new ArrayList<>();
            profileForCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                profileForList.add(string);

                string = j.getString(P.id);
                profileForCodeList.add(string);
            }
        }


        //for country code
         string = session.getString(P.country_code);

        if (string != null) {
            jsonList = new JsonList(string);
            countryNameList = new ArrayList<>();
            countryCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                countryNameList.add(string);

                string = j.getString(P.id);
                countryCodeList.add(string);
            }
        }

        //for city of residence
        string = session.getString(P.city);
        if (string != null) {
            jsonList = new JsonList(string);
            cityOfResidenceCodeList = new ArrayList<>();
            cityOfResidenceList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.city_name);
                cityOfResidenceList.add(string);

                string = j.getString(P.city_id);
                cityOfResidenceCodeList.add(string);
            }
        }

        //for state
        string = session.getString(P.state);
        if (string != null) {
            jsonList = new JsonList(string);
            stateList = new ArrayList<>();
            stateCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.state_name);
                stateList.add(string);

                string = j.getString(P.state_id);
                stateCodeList.add(string);
            }
        }

        //for country of residence
        string = session.getString(P.country);
        if (string != null) {
            jsonList = new JsonList(string);
            countryOfResidenceList = new ArrayList<>();
            countryOfResidenceCodeList = new ArrayList<>();

            for (Json j : jsonList) {
                string = j.getString(P.name);
                countryOfResidenceList.add(string);

                string = j.getString(P.country_code);
                countryOfResidenceCodeList.add(string);
            }
        }

        //for country of citizenship
        string = session.getString(P.country);
        if (string != null) {
            jsonList = new JsonList(string);
            countryOfCitizenshipList = new ArrayList<>();
            countryOfCitizenshipCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                countryOfCitizenshipList.add(string);

                string = j.getString(P.country_code);
                countryOfCitizenshipCodeList.add(string);
            }
        }

        //for height
        string = session.getString(P.height);
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


        //for religion
        string = session.getString(P.religion);
        if (string != null) {
            jsonList = new JsonList(string);
            religionList = new ArrayList<>();
            religionCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                religionList.add(string);

                string = j.getString(P.id);
                religionCodeList.add(string);
            }
        }

        //for ethincity
        string = session.getString(P.ethnicity);
        if (string != null) {
            jsonList = new JsonList(string);
            ethincityList = new ArrayList<>();
            ethincityCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                ethincityList.add(string);

                string = j.getString(P.id);
                ethincityCodeList.add(string);
            }
        }


        //for fathers country
        string = session.getString(P.country);
        if (string != null) {
            jsonList = new JsonList(string);
            fathersCityList = new ArrayList<>();
            fathersCityCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                fathersCityList.add(string);

                string = j.getString(P.id);
                fathersCityCodeList.add(string);
            }
        }

        //for mothers country
        string = session.getString(P.country);
        if (string != null) {
            jsonList = new JsonList(string);
            mothersCityList = new ArrayList<>();
            mothersCityCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                mothersCityList.add(string);

                string = j.getString(P.id);
                mothersCityCodeList.add(string);
            }
        }

        //for current occupation
        string = session.getString(P.occupation);
        if (string != null) {
            jsonList = new JsonList(string);
            currentOccupationList = new ArrayList<>();
            currentOccupationCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                currentOccupationList.add(string);

                string = j.getString(P.id);
                currentOccupationCodeList.add(string);
            }
        }

        //for education
        string = session.getString(P.education);
        if (string != null) {
            jsonList = new JsonList(string);
            highestLevelOfEducationList = new ArrayList<>();
            highestLevelOfEducationCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                highestLevelOfEducationList.add(string);

                string = j.getString(P.id);
                highestLevelOfEducationCodeList.add(string);
            }
        }

        //for language
        string = session.getString(P.language);
        if (string != null) {
            jsonList = new JsonList(string);
            languageList = new ArrayList<>();
            languageCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                languageList.add(string);

                string = j.getString(P.id);
                languageCodeList.add(string);
            }
        }

        //for smoking
        string = session.getString(P.smoking);
        if (string != null) {
            jsonList = new JsonList(string);
            habbitList = new ArrayList<>();
            habbitCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                habbitList.add(string);

                string = j.getString(P.id);
                habbitCodeList.add(string);
            }
        }


        //for relocate
        string = session.getString(P.relocate);
        if (string != null) {
            jsonList = new JsonList(string);
            relocateList = new ArrayList<>();
            relocateCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                relocateList.add(string);

                string = j.getString(P.id);
                relocateCodeList.add(string);
            }
        }

        //for seeking marriage
        string = session.getString(P.seeking_marriage);
        if (string != null) {
            jsonList = new JsonList(string);
            seekingMarriageList = new ArrayList<>();
            seekingMarriageCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.id);
                seekingMarriageList.add(string);

                string = j.getString(P.val);
                seekingMarriageCodeList.add(string);
            }
        }

        //for intrested in
        string = session.getString(P.intrested_in);
        if (string != null) {
            jsonList = new JsonList(string);
            intrestedInList = new ArrayList<>();
            intrestedInCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                intrestedInList.add(string);

                string = j.getString(P.id);
                intrestedInCodeList.add(string);
            }
        }

        setUpTextWatcher(view);
    }

    private void setUpTextWatcher(View view)
    {
        ((EditText) view.findViewById(R.id.editText)).addTextChangedListener(new TextWatcher() {
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
    private void handleGenderClickListner() {
        final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
        gender_male_imv = view.findViewById(R.id.genderr_male_imv);
        gender_male_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender_male_imv.setBackground(context.getDrawable(R.drawable.reg_img_bg_selected));
                gender_female_imv.setBackground(context.getDrawable(R.drawable.reg_img_bg));
                gender = "male";
                myAnim.setInterpolator(interpolator);
                gender_male_imv.startAnimation(myAnim);
                gender_female_imv.clearAnimation();

            }
        });

        gender_female_imv = view.findViewById(R.id.genderr_female_imv);
        gender_female_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender_female_imv.setBackground(context.getDrawable(R.drawable.reg_img_bg_selected));
                gender_male_imv.setBackground(context.getDrawable(R.drawable.reg_img_bg));
                gender = "female";
                myAnim.setInterpolator(interpolator);
                gender_female_imv.startAnimation(myAnim);
                gender_male_imv.clearAnimation();
            }
        });
    }


    private void handleDatePicker() {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };

        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel(Calendar calendar) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ((EditText) view.findViewById(R.id.ed_dob)).setText(sdf.format(calendar.getTime()));
    }


    private void hitEditProfileApi(Json json)
    {
        final Json j = json;
        final LoadingDialog loadingDialog = new LoadingDialog(context);

        RequestModel requestModel = RequestModel.newRequestModel("edit_profile");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
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
                        H.showMessage(context, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1)
                        {
                            /*Intent intent = new Intent(context, OTPVerificationActivity.class);
                            intent.putExtra(P.registrationJson,j.toString());
                            startActivity(intent);*/
                            getFragmentManager().popBackStack();
                            H.showMessage(context, json.getString(P.msg));
                        } else
                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitRegisterApi");
    }
}

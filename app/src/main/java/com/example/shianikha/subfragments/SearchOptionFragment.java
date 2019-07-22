package com.example.shianikha.subfragments;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.Session;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.shianikha.R;
import com.example.shianikha.commen.P;

import org.json.JSONArray;

import java.util.ArrayList;

public class SearchOptionFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private View fragmentView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> maritalStatusNameList, maritalStatusCodeList, religionNameList, religionCodeList,motherToungueNameList, motherToungeCodeList,
            countryNameList, countryCodeList, stateNameList, stateCodeList, cityNameList, cityCodeList, photoSettingNameList, photoSettingCodeList;
    private Context context;
    private Session session;


    public SearchOptionFragment() {
        // Required empty public constructor
    }

    public static SearchOptionFragment newInstance() {
        SearchOptionFragment fragment = new SearchOptionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentView == null)
        {
            context = getActivity();
            session = new Session(context);
            fragmentView = inflater.inflate(R.layout.fragment_search_option, container, false);

            handleSeekBar();
            extractRequireList();

        /*height_rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue)
            {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });*/

        }
        return fragmentView;
    }

    private void extractRequireList()
    {
        JsonList jsonList;

        //for marital status
        String string = session.getString(P.marital_status);
        if (string != null) {
            jsonList = new JsonList(string);
            maritalStatusNameList = new ArrayList<>();
            maritalStatusCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                maritalStatusNameList.add(string);

                string = j.getString(P.id);
                maritalStatusCodeList.add(string);
            }
        }

        //for religion list
        string = session.getString(P.religion);
        if (string != null) {
            jsonList = new JsonList(string);
            religionNameList = new ArrayList<>();
            religionCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                religionNameList.add(string);

                string = j.getString(P.id);
                religionCodeList.add(string);
            }
        }

        //for mother tongue list
        string = session.getString(P.language);
        if (string != null) {
            jsonList = new JsonList(string);
            motherToungueNameList = new ArrayList<>();
            motherToungeCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                motherToungueNameList.add(string);

                string = j.getString(P.id);
                motherToungeCodeList.add(string);
            }
        }

        //for country list
        string = session.getString(P.country);
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

        //for state
        string = session.getString(P.state);
        if (string != null) {
            jsonList = new JsonList(string);
            stateNameList = new ArrayList<>();
            stateCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.state_name);
                stateNameList.add(string);

                string = j.getString(P.state_id);
                stateCodeList.add(string);
            }
        }

        string = session.getString(P.city);
        if (string != null) {
            jsonList = new JsonList(string);
            cityNameList = new ArrayList<>();
            cityCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.city_name);
                cityNameList.add(string);

                string = j.getString(P.city_id);
                cityCodeList.add(string);
            }
        }

        setUpTextWatcher(fragmentView);

        fragmentView.findViewById(R.id.marital_status_ed).setOnClickListener(this);
        fragmentView.findViewById(R.id.religion_ed).setOnClickListener(this);
        //fragmentView.findViewById(R.id.community_ed).setOnClickListener(this);
        fragmentView.findViewById(R.id.mother_tongue_ed).setOnClickListener(this);
        fragmentView.findViewById(R.id.country_edt).setOnClickListener(this);
        fragmentView.findViewById(R.id.state_edt).setOnClickListener(this);
        fragmentView.findViewById(R.id.city_edt).setOnClickListener(this);
        //fragmentView.findViewById(R.id.photo_setting_edt).setOnClickListener(this);
        fragmentView.findViewById(R.id.btn_search).setOnClickListener(this);
    }

    private void handleSeekBar() {
        final CrystalRangeSeekbar age_rangeSeekbar = fragmentView.findViewById(R.id.age_rangeSeekbar);
        final CrystalRangeSeekbar height_rangeSeekbar = fragmentView.findViewById(R.id.height_rangeSeekbar);

        // get min and max text view
        final TextView tvMinAge = fragmentView.findViewById(R.id.textMinAge);
        final TextView tvMaxAge = fragmentView.findViewById(R.id.textMaxAge);

        final TextView tvMinHeight = fragmentView.findViewById(R.id.textMinHeight);
        final TextView tvMaxHeight = fragmentView.findViewById(R.id.textMaxHeight);

        age_rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {

                tvMinAge.setText("Min " + minValue + " years");
                tvMaxAge.setText("Max " + maxValue + " years");

            }
        });

        height_rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                H.log("minValueIs", minValue + "");
                H.log("maxValueIs", maxValue + "");

                long minProgress = (long) minValue;

                long maxProgress = (long) maxValue;

                long minFt = minProgress / 12;
                long minIn = minProgress % 12;

                long maxFt = maxProgress / 12;
                long maxIn = maxProgress % 12;


                /*int ft = progress/12;
                int in = progress%12;*/
                float minH = ((float) minFt / 3.281f) + ((float) minIn / 39.37f);//3.281  39.37
                String text = "" + minFt + "' " + minIn + '"';
                tvMinHeight.setText("Min " + text);

                text = "" + maxFt + "' " + maxIn + '"';
                tvMaxHeight.setText("Max " + text);
            }
        });
    }

    private void setUpTextWatcher(View mainview) {
        ((EditText) mainview.findViewById(R.id.editText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }



    private void hideCustomSpinnerLayout() {
        int i = fragmentView.findViewById(R.id.includeContainer).getWidth();
        ((EditText)fragmentView. findViewById(R.id.editText)).setText("");
        fragmentView.findViewById(R.id.includeContainer).animate().translationX(i).setDuration(500);
        View view = fragmentView.findViewById(R.id.view);
        view.setVisibility(View.GONE);
        H.hideKeyBoard(context, view);
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.btn_search)
            hitSearchOptionApi();
        else
            setUpCustomSpinner(view);
    }

    private void setUpCustomSpinner(final View view) {
        ListView listView = fragmentView.findViewById(R.id.listView);
        fragmentView.findViewById(R.id.view).setVisibility(View.VISIBLE);
        EditText editText = fragmentView.findViewById(R.id.editText);

        if (view.getId() == R.id.marital_status_ed)
        {
           editText.setHint("Search Marital Status ");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, maritalStatusNameList);
        }
        else if (view.getId() == R.id.religion_ed)
        {
            editText.setHint("Search Religion");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, religionNameList);
        }
        else if (view.getId() == R.id.community_ed )
        {

        }
        else if (view.getId() == R.id.mother_tongue_ed)
        {
            editText.setHint("Search Mother Tongue");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, motherToungueNameList);
        }
        else if (view.getId() == R.id.country_edt)
        {
            editText.setHint("Search Country");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, countryNameList);
        }
        else if (view.getId() == R.id.state_edt)
        {
            editText.setHint("Search State");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, stateNameList);
        }
        else if (view.getId() == R.id.city_edt)
        {
            editText.setHint("Search City");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, cityNameList);
        }
        else if (view.getId() == R.id.photo_setting_edt)
        {

        }

        if (arrayAdapter == null)
            return;

        H.showKeyBoard(context,editText);

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

        fragmentView.findViewById(R.id.includeContainer).animate().translationX(0).setDuration(500);

    }

    private void hitSearchOptionApi() {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

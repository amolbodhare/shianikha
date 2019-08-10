package com.example.shianikha;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.App;
import com.example.shianikha.commen.P;

import java.util.ArrayList;


public class PartnerPreference extends Fragment implements View.OnClickListener
{
    private ArrayAdapter<String> arrayAdapter;
    private View fragmentView;
    private Context context;
    private LoadingDialog loadingDialog;

    public static Fragment previousFragment;
    public static String previousFragmentName;
    private Session session;

    private OnFragmentInteractionListener mListener;
    private ArrayList<String> prefMatchMaritalStatuusNameList, prefMatchhEthincityNameList, minEducationNameList;
    private ArrayList<String> prefMatchMaritalStatuusCodeList, prefMatchhEthincityCodeList, minEducationCodeList;


    public static PartnerPreference newInstance(Fragment fragment, String string) {
        PartnerPreference partnerPreference = new PartnerPreference();
        previousFragment = fragment;
        previousFragmentName = string;

        return partnerPreference;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_partner_preference, container, false);
        context=getContext();
        session = new Session(context);
        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) fragmentView.findViewById(R.id.age_rangeSeekbar);

        // get min and max text view
        final TextView tvMin = (TextView) fragmentView.findViewById(R.id.textMin1);
        final TextView tvMax = (TextView) fragmentView.findViewById(R.id.textMax1);


        fragmentView.findViewById(R.id.btn_next).setOnClickListener(this);
        fragmentView.findViewById(R.id.view).setOnClickListener(this);

        fragmentView.findViewById(R.id.marital_status_spinner_ed).setOnClickListener(this);
        fragmentView.findViewById(R.id.ethincity_spinner_ed).setOnClickListener(this);
        fragmentView.findViewById(R.id.min_edu_spinner_ed).setOnClickListener(this);

// set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText("Min " + String.valueOf(minValue) + " years");
                tvMax.setText("Max " + String.valueOf(maxValue) + " years");
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

        return fragmentView;
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btn_next)
        {
            //makeJson();
        }

        else if (v.getId() == R.id.view)
            hideCustomSpinnerLayout();
        else
            setUpCustomSpinner(v);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setUpCustomSpinner(final View view) {
        ListView listView =fragmentView.findViewById(R.id.listView);
        fragmentView.findViewById(R.id.view).setVisibility(View.VISIBLE);

        if (view.getId() == R.id.marital_status_spinner_ed)
        {
            ((EditText) fragmentView.findViewById(R.id.editText)).setHint("Search Marital Status");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, prefMatchMaritalStatuusNameList);
        } else if (view.getId() == R.id.ethincity_spinner_ed)
        {
            ((EditText) fragmentView.findViewById(R.id.editText)).setHint("Search Ethnicity");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, prefMatchhEthincityNameList);
        }
        if (view.getId() == R.id.min_edu_spinner_ed)
        {
            ((EditText) fragmentView.findViewById(R.id.editText)).setHint("Search Min Education");
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, minEducationNameList);
        }

        if (arrayAdapter == null)
            return;

        H.showKeyBoard(context, fragmentView.findViewById(R.id.editText));

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

    private void hideCustomSpinnerLayout() {
        int i =fragmentView. findViewById(R.id.includeContainer).getWidth();
        ((EditText) fragmentView.findViewById(R.id.editText)).setText("");
        fragmentView.findViewById(R.id.includeContainer).animate().translationX(i).setDuration(500);
        View view = fragmentView.findViewById(R.id.view);
        view.setVisibility(View.GONE);
        H.hideKeyBoard(context, view);
    }
    private void extractRequireList() {
        JsonList jsonList;

        //for marital status
        String string = session.getString(P.marital_status);
        if (string != null) {
            jsonList = new JsonList(string);
            prefMatchMaritalStatuusNameList = new ArrayList<>();
            prefMatchMaritalStatuusCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.val);
                prefMatchMaritalStatuusNameList.add(string);

                string = j.getString(P.id);
                prefMatchMaritalStatuusCodeList.add(string);
            }
        }

        //for ethnicity
        string = session.getString(P.ethnicity);
        if (string != null) {
            jsonList = new JsonList(string);
            prefMatchhEthincityNameList = new ArrayList<>();
            prefMatchhEthincityCodeList = new ArrayList<>();

            for (Json j : jsonList)
            {
                string = j.getString(P.name);
                prefMatchhEthincityNameList.add(string);

                string = j.getString(P.id);
                prefMatchhEthincityCodeList.add(string);
            }
        }

        //for education
        string = session.getString(P.education);
        if (string != null) {
            jsonList = new JsonList(string);
            minEducationNameList = new ArrayList<>();
            minEducationCodeList = new ArrayList<>();
            for (Json j : jsonList) {
                string = j.getString(P.name);
                minEducationNameList.add(string);

                string = j.getString(P.id);
                minEducationCodeList.add(string);
            }
        }

        setUpTextWatcher();
    }

    private void setUpTextWatcher() {
        ((EditText) fragmentView.findViewById(R.id.editText)).addTextChangedListener(new TextWatcher() {
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


    private void makeJson()
    {
        EditText editText = fragmentView.findViewById(R.id.marital_status_spinner_ed);
        String string = editText.getText().toString();

        if (string.isEmpty()) {
            H.showMessage(context, "Please select marital status");
            return;
        }

        int i = prefMatchMaritalStatuusNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.marital_status, prefMatchMaritalStatuusCodeList.get(i));



        editText = fragmentView.findViewById(R.id.ethincity_spinner_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select ethnicity name");
            return;
        }
        i = prefMatchhEthincityNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.ethnicity, prefMatchhEthincityCodeList.get(i));



        editText = fragmentView.findViewById(R.id.min_edu_spinner_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select minimum education");
            return;
        }
        i = minEducationNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.father_city, minEducationCodeList.get(i));


        H.log("masterJsonIs",App.masterJson.toString());
        //startActivity(new Intent(this,RegFourthPageActivity.class));
    }
    private void setMarginTopOfCustomSpinner() {
        LinearLayout linearLayout = fragmentView.findViewById(R.id.includeContainer);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        int i = new Session(context).getInt(P.statusBarHeight);
        layoutParams.topMargin = i;
        H.log("heightIs", i + "");
        linearLayout.setLayoutParams(layoutParams);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Object object = fragmentView.getParent();
        if (object instanceof FrameLayout)
            ((FrameLayout) object).removeAllViews();
    }
}


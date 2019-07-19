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
import org.json.JSONException;

import java.util.ArrayList;

public class SearchOptionFragment extends Fragment implements View.OnClickListener
{

    private OnFragmentInteractionListener mListener;
    SeekBar seekBar_age;
    SeekBar seekBar_height;
    View view;
    private ArrayAdapter<String> arrayAdapter;

    ArrayList<String> marital_status_name_arrayList;
    ArrayList<String> marital_status_code_arrayList;
    ArrayList<String> religion_name_arrayList;
    ArrayList<String> religion_code_arrayList;
    ArrayList<String> mothertonge_name_arrayList;
    ArrayList<String> mothertonge_code_arrayList;

    ArrayList<String> country_name_arrayList;
    ArrayList<String> country_code_arrayList;
    ArrayList<String> state_name_arrayList;
    ArrayList<String> state_code_arrayList;
    ArrayList<String> city_name_arrayList;
    ArrayList<String> citycode_arrayList;

    ArrayList<String> photo_setting_arrayList;
    Context context;
    Session session;


    public SearchOptionFragment() {
        // Required empty public constructor
    }

    public static SearchOptionFragment newInstance()
    {
        SearchOptionFragment fragment = new SearchOptionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_search_option, container, false);
        context=getContext();
        session = new Session(context);

        final CrystalRangeSeekbar age_rangeSeekbar = (CrystalRangeSeekbar)view.findViewById(R.id.age_rangeSeekbar);
        final CrystalRangeSeekbar height_rangeSeekbar = (CrystalRangeSeekbar)view.findViewById(R.id.height_rangeSeekbar);

        // get min and max text view
        final TextView tvMinAge = (TextView) view.findViewById(R.id.textMinAge);
        final TextView tvMaxAge = (TextView) view.findViewById(R.id.textMaxAge);

        final TextView tvMinHeight = (TextView) view.findViewById(R.id.textMinHeight);
        final TextView tvMaxHeight = (TextView) view.findViewById(R.id.textMaxHeight);


        view.findViewById(R.id.marital_status_ed).setOnClickListener(this);
        view.findViewById(R.id.religion_ed).setOnClickListener(this);
        view.findViewById(R.id.community_ed).setOnClickListener(this);
        view.findViewById(R.id.mother_tongue_ed).setOnClickListener(this);
        view.findViewById(R.id.country_edt).setOnClickListener(this);
        view.findViewById(R.id.state_edt).setOnClickListener(this);
        view.findViewById(R.id.city_edt).setOnClickListener(this);
        //view.findViewById(R.id.photo_setting_edt).setOnClickListener(this);

// set listener
        age_rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener()
        {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {

                tvMinAge.setText("Min "+String.valueOf(minValue)+" years");

                tvMaxAge.setText("Max "+String.valueOf(maxValue)+" years");

            }
        });

// set final value listener
        age_rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue)
            {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });


        // set listener
        height_rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMinHeight.setText("Min "+String.valueOf(minValue));
                tvMaxHeight.setText("Max "+String.valueOf(maxValue));
            }
        });

// set final value listener
        height_rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue)
            {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });


        //extractRequireList();
        /*marital_status_arrayList=new ArrayList<>();
        religion_arrayList=new ArrayList<>();
        community_arrayList=new ArrayList<>();
        mothertonge_arrayList=new ArrayList<>();
        country_arrayList=new ArrayList<>();
        state_arrayList=new ArrayList<>();
        city_arrayList=new ArrayList<>();
        photo_setting_arrayList=new ArrayList<>();

        try
        {

            //String masterdatajsonstring=getIntent().getExtras().getString("masterDataString");



            //String masterdatajsonstring=getIntent().getExtras().getString("masterDataString");
            String masterdatajsonstring = new Session(getActivity()).getString(com.example.shianikha.commen.P.masterDataString);


            //System.out.print(masterdatajsonstring);
            Json json=new Json(masterdatajsonstring);

            JSONArray jsonArray_marital_status=json.getJsonArray("marital_status");//val
            JSONArray jsonArray_religion=json.getJsonArray("religion");//name
            JSONArray jsonArray_community=json.getJsonArray("religion");//name
            JSONArray jsonArray_mothertongue=json.getJsonArray("mothertongue");//name
            JSONArray jsonArray_country=json.getJsonArray("country");//name
            JSONArray jsonArray_state=json.getJsonArray("state");//state_name
            JSONArray jsonArray_city=json.getJsonArray("city");//city_name
            JSONArray jsonArray_photo=json.getJsonArray("city");//city_name


            //System.out.print(jsonArray_marital_status);

            for(int i=0;i<jsonArray_marital_status.length();i++)
            {
                marital_status_arrayList.add(jsonArray_marital_status.getJSONObject(i).getString("val"));
            }

            for(int i=0;i<jsonArray_religion.length();i++)
            {
                religion_arrayList.add(jsonArray_religion.getJSONObject(i).getString("name"));
            }

            for(int i=0;i<jsonArray_community.length();i++)
            {
                community_arrayList.add(jsonArray_community.getJSONObject(i).getString("name"));
            }

            for(int i=0;i<jsonArray_mothertongue.length();i++)
            {
                mothertonge_arrayList.add(jsonArray_mothertongue.getJSONObject(i).getString("name"));
            }

            for(int i=0;i<jsonArray_country.length();i++)
            {
               country_arrayList.add(jsonArray_country.getJSONObject(i).getString("name"));
            }

            for(int i=0;i<jsonArray_state.length();i++)
            {
                state_arrayList.add(jsonArray_state.getJSONObject(i).getString("state_name"));
            }

            for(int i=0;i<jsonArray_city.length();i++)
            {
                city_arrayList.add(jsonArray_city.getJSONObject(i).getString("city_name"));
            }

            for(int i=0;i<jsonArray_photo.length();i++)
            {
                photo_setting_arrayList.add(jsonArray_photo.getJSONObject(i).getString("city_name"));
            }


           *//* for(int i=0;i<jsonArray_height.length();i++)
            {
                height_arrayList.add("Hello");
            }*//*

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        setUpEditTextClickListner(view);
        setUpTextWatcher(view);*/

        return  view;
    }


   /* private void setUpEditTextClickListner(final View view)
    {
        EditText ed_marital_status = view.findViewById(R.id.marital_status_ed);
        EditText ed_religion = view.findViewById(R.id.religion_ed);
        EditText ed_commmunity = view.findViewById(R.id.community_ed);
        EditText ed_mother_tongue = view.findViewById(R.id.mother_tongue_ed);
        EditText ed_country = view.findViewById(R.id.country_edt);
        EditText ed_state = view.findViewById(R.id.state_edt);
        EditText ed_city = view.findViewById(R.id.city_edt);
        EditText ed_photo_settings = view.findViewById(R.id.photo_setting_edt);



        ed_marital_status.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v,view);
            }
        });
        ed_religion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v,view);
            }
        });
        ed_commmunity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v,view);
            }
        });
        ed_mother_tongue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v,view);
            }
        });

        ed_country.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v,view);
            }
        });

        ed_state.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v,view);
            }
        });

        ed_city.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v,view);
            }
        });

        ed_photo_settings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v,view);
            }
        });



    }*/

    private void setUpTextWatcher(View mainview)
    {
        ((EditText)mainview.findViewById(R.id.editText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

   /* private void setUpCustomSpinner(final View view,final View main_view)
    {
        ListView listView = main_view.findViewById(R.id.listView);
        main_view.findViewById(R.id.view).setVisibility(View.VISIBLE);
        main_view.findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideCustomSpinnerLayout(main_view);
            }
        });

        if (view.getId()==R.id.marital_status_ed)
        {
            ((EditText)main_view.findViewById(R.id.editText)).setHint("Select Marital Status");
            arrayAdapter = new ArrayAdapter<>(context,R.layout.text_view,R.id.textView,marital_status_arrayList);
            listView.setAdapter(arrayAdapter);
        }

        else if (view.getId() == R.id.religion_ed)
        {
            ((EditText)main_view.findViewById(R.id.editText)).setHint("Select Religion");
            arrayAdapter = new ArrayAdapter<>(context,R.layout.text_view,R.id.textView,religion_arrayList);
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.community_ed)
        {
            ((EditText)main_view.findViewById(R.id.editText)).setHint("Select Community");
            arrayAdapter = new ArrayAdapter<>(context,R.layout.text_view,R.id.textView,community_arrayList);
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.mother_tongue_ed)
        {
            ((EditText)main_view.findViewById(R.id.editText)).setHint("Select Mother Tongue");
            arrayAdapter = new ArrayAdapter<>(context,R.layout.text_view,R.id.textView,mothertonge_arrayList);
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.country_edt)
        {
            ((EditText)main_view.findViewById(R.id.editText)).setHint("Select Country");
            arrayAdapter = new ArrayAdapter<>(context,R.layout.text_view,R.id.textView,country_arrayList);
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.state_edt)
        {
            ((EditText)main_view.findViewById(R.id.editText)).setHint("Select State");
            arrayAdapter = new ArrayAdapter<>(context,R.layout.text_view,R.id.textView,state_arrayList);
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.city_edt)
        {
            ((EditText)main_view.findViewById(R.id.editText)).setHint("Select City");
            arrayAdapter = new ArrayAdapter<>(context,R.layout.text_view,R.id.textView,city_arrayList);
            listView.setAdapter(arrayAdapter);
        }

        else if (view.getId() == R.id.photo_setting_edt)
        {
            ((EditText)main_view.findViewById(R.id.editText)).setHint("Select Settting");
            arrayAdapter = new ArrayAdapter<>(context,R.layout.text_view,R.id.textView,photo_setting_arrayList);
            listView.setAdapter(arrayAdapter);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                TextView textView = v.findViewById(R.id.textView);
                if (textView!=null)
                {
                    Log.e("selectedIs",textView.getText().toString());
                    ((EditText)view).setText(textView.getText().toString());

                }
                hideCustomSpinnerLayout(main_view);
            }
        });

        main_view.findViewById(R.id.includeContainer).animate().translationX(0).setDuration(500);
    }

    private void hideCustomSpinnerLayout(View main_view) {
        int i =main_view.findViewById(R.id.includeContainer).getWidth();
        main_view.findViewById(R.id.includeContainer).animate().translationX(i).setDuration(500);
        main_view.findViewById(R.id.view).setVisibility(View.GONE);
    }*/

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.btn_next)
        {
            //makeJson();
        }
        else if (v.getId() == R.id.view)
        {
            //hideCustomSpinnerLayout();
        }

        else
        {
            //setUpCustomSpinner(v);
        }


    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
 /*   private void makeJson()
    {
        Json json=new Json();

        EditText editText = view.findViewById(R.id.marital_status_ed);
        String string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select city name");
            return;
        }
        int i = cityNameList.indexOf(string);
        if (i != -1)
            json.addString(P.city, cityCodeList.get(i));

        editText = findViewById(R.id.religion_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {        i = stateNameList.indexOf(string);

            H.showMessage(this, "Please select state name");
            return;
        }
        if (i != -1)
            App.masterJson.addString(P.state, stateCodeList.get(i));

        editText = findViewById(R.id.community_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select country of residence");
            return;
        }
        i = countryNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.country_res, countryCodeList.get(i));

        editText = findViewById(R.id.mother_tongue_ed);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select country of citizenship");
            return;
        }
        i = countryNameList.indexOf(string);
        if (i != -1)
            App.masterJson.addString(P.country_citizen, countryCodeList.get(i));

        editText = findViewById(R.id.country_edt);
        string = editText.getTag().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select date of birth");
            return;
        }
        App.masterJson.addString(P.dob, string);

        editText = findViewById(R.id.state_edt);
        string = editText.getText().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select height");
            return;
        }

        App.masterJson.addString(P.height, string);

        editText = findViewById(R.id.city_edt);
        string = editText.getTag().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select date of birth");
            return;
        }
        App.masterJson.addString(P.dob, string);



                editText = findViewById(R.id.photo_setting_edt);
        string = editText.getTag().toString();
        if (string.isEmpty()) {
            H.showMessage(this, "Please select date of birth");
            return;
        }
        App.masterJson.addString(P.dob, string);

        H.log("masterJsonIs",json.toString());
        startActivity(new Intent(this,RegThirdPageActivity.class));
    }
    private void extractRequireList() {
        JsonList jsonList;

        //for city
        String string = session.getString(P.city);
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

        //for country
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

        setUpTextWatcher();
    }
    private void setUpTextWatcher() {
        ((EditText) view.findViewById(R.id.editText)).addTextChangedListener(new TextWatcher() {
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

        if (view.getId() == R.id.cityEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search City");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, cityNameList);
        } else if (view.getId() == R.id.stateEditText) {
            ((EditText) findViewById(R.id.editText)).setHint("Search State");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, stateNameList);
        } else if (view.getId() == R.id.countryEditText1 || view.getId() == R.id.countryEditText2) {
            ((EditText) findViewById(R.id.editText)).setHint("Search Country residence");
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, countryNameList);
        } else if (view.getId() == R.id.heightEditText)
        {   EditText editText = findViewById(R.id.editText);
            editText.setHint("Search height");
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            arrayAdapter = new ArrayAdapter<>(this, R.layout.text_view, R.id.textView, heightList);
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
    }*/
}

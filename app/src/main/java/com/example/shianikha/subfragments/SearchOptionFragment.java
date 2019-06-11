package com.example.shianikha.subfragments;

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
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.Session;
import com.example.shianikha.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class SearchOptionFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    SeekBar seekBar_age;
    SeekBar seekBar_height;
    View view;
    private ArrayAdapter<String> arrayAdapter;
    ArrayList<String> marital_status_arrayList;
    ArrayList<String> religion_arrayList;
    ArrayList<String> community_arrayList;
    ArrayList<String> mothertonge_arrayList;
    ArrayList<String> country_arrayList;
    ArrayList<String> state_arrayList;
    ArrayList<String> city_arrayList;
    ArrayList<String> photo_setting_arrayList;
    Context context;


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
        context=getActivity();
        seekBar_age=(SeekBar)view.findViewById(R.id.seekBar_age);
        seekBar_age.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                Toast.makeText(getActivity(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getActivity(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getActivity(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });

        marital_status_arrayList=new ArrayList<>();
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


           /* for(int i=0;i<jsonArray_height.length();i++)
            {
                height_arrayList.add("Hello");
            }*/

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        setUpEditTextClickListner(view);
        setUpTextWatcher(view);

        return  view;
    }


    private void setUpEditTextClickListner(final View view)
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



    }

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

    private void setUpCustomSpinner(final View view,final View main_view)
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
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

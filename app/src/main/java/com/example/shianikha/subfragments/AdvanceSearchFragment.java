package com.example.shianikha.subfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.shianikha.R;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

public class AdvanceSearchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    LinearLayout parent_layout;
    View view;
    Context context;


    public AdvanceSearchFragment()
    {
        // Required empty public constructor
    }

    public static AdvanceSearchFragment newInstance() {
        AdvanceSearchFragment fragment = new AdvanceSearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       view= inflater.inflate(R.layout.fragment_advance_search, container, false);
       context=getActivity();

        final CrystalRangeSeekbar age_rangeSeekbar = (CrystalRangeSeekbar)view.findViewById(R.id.age_rangeSeekbar);
        final CrystalRangeSeekbar height_rangeSeekbar = (CrystalRangeSeekbar)view.findViewById(R.id.height_rangeSeekbar);

        // get min and max text view
        final TextView tvMinAge = (TextView) view.findViewById(R.id.textMinAge);
        final TextView tvMaxAge = (TextView) view.findViewById(R.id.textMaxAge);

        final TextView tvMinHeight = (TextView) view.findViewById(R.id.textMinHeight);
        final TextView tvMaxHeight = (TextView) view.findViewById(R.id.textMaxHeight);

// set listener
        age_rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
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


       return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

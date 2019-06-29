package com.example.shianikha;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;


public class PartnerPreference extends Fragment {



    private OnFragmentInteractionListener mListener;
    View view;

    public PartnerPreference() {
        // Required empty public constructor
    }


    public static PartnerPreference newInstance() {
        PartnerPreference fragment = new PartnerPreference();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_partner_preference, container, false);
        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar)view.findViewById(R.id.age_rangeSeekbar);

        // get min and max text view
        final TextView tvMin = (TextView) view.findViewById(R.id.textMin1);
        final TextView tvMax = (TextView) view.findViewById(R.id.textMax1);

// set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText("Min "+String.valueOf(minValue)+" years");
                tvMax.setText("Max "+String.valueOf(maxValue)+" years");
            }
        });

// set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
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

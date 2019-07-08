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

import com.adoisstudio.helper.LoadingDialog;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;


public class PartnerPreference extends Fragment {

    private View fragmentView;
    private Context context;
    private LoadingDialog loadingDialog;

    public static Fragment previousFragment;
    public static String previousFragmentName;

    private OnFragmentInteractionListener mListener;

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
        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) fragmentView.findViewById(R.id.age_rangeSeekbar);

        // get min and max text view
        final TextView tvMin = (TextView) fragmentView.findViewById(R.id.textMin1);
        final TextView tvMax = (TextView) fragmentView.findViewById(R.id.textMax1);

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
        return fragmentView;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

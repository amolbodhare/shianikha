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

import com.adoisstudio.helper.H;
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
    View fragmentView;
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

        fragmentView= inflater.inflate(R.layout.fragment_advance_search, container, false);
       context=getActivity();

        handleSeekBar();

       return fragmentView;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
}

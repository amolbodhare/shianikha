package com.example.shianikha.subfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.shianikha.R;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;

public class AdvanceSearchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    LinearLayout parent_layout;
    View view;


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


        //ExpandableLayout expandableLayout = view.findViewById(R.id.expandableLayout);
        // toggle expand, collapse
        //expandableLayout.toggle();
// expand
        //expandableLayout.expand();
// collapse
        //expandableLayout.collapse();

// move position of child view
        //expandableLayout.moveChild(0);
// move optional position
        //expandableLayout.move(500);

// set base position which is close position
        //expandableLayout.setClosePosition(500);


        /*expandableLayout.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            // You can get notification that your expandable layout is going to open or close.
            // So, you can set the animation synchronized with expanding animation.
            @Override
            public void onPreOpen() {
            }

            @Override
            public void onPreClose() {
            }

            @Override
            public void onOpened() {
            }

            @Override
            public void onClosed() {

            }
        });*/


       return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package com.example.shianikha.subfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shianikha.R;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

public class AdvanceSearchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    LinearLayout parent_layout;
    View view;

    CardView location_grew_up_exp_card_layout;
    CardView education_pro_details_card_layout;
    CardView lifestyle_and_appearence_card_layout;
    CardView search_using_keywords_card_layout;

    ExpandableRelativeLayout location_grew_up_exp_layout;
    ExpandableRelativeLayout education_and_pro_exp_layout;
    ExpandableRelativeLayout lifestyle_and_appearence_exp_layout;
    ExpandableRelativeLayout search_using_keywords_exp_layout;



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


        location_grew_up_exp_card_layout=view.findViewById(R.id.loc_grew_up_details_card_layout);
        education_pro_details_card_layout=view.findViewById(R.id.edu_pro_details_card_layout);
        lifestyle_and_appearence_card_layout=view.findViewById(R.id.lifestyle_and_appearence_card_layout);
        search_using_keywords_card_layout=view.findViewById(R.id.search_using_keywords_card_layout);

        location_grew_up_exp_layout=view.findViewById(R.id.location_grew_up_exp_layout);
        education_and_pro_exp_layout=view.findViewById(R.id.education_and_pro_exp_layout);
        lifestyle_and_appearence_exp_layout=view.findViewById(R.id.lifestyle_and_appearence_exp_layout);
        search_using_keywords_exp_layout=view.findViewById(R.id.search_using_keywords_exp_layout);


       location_grew_up_exp_layout.collapse();
       education_and_pro_exp_layout.collapse();
       lifestyle_and_appearence_exp_layout.collapse();
       search_using_keywords_exp_layout.collapse();

       location_grew_up_exp_card_layout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //Toast.makeText(getActivity(), "jkgjjh", Toast.LENGTH_SHORT).show();
               location_grew_up_exp_layout.toggle();
           }
       });

       education_pro_details_card_layout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               education_and_pro_exp_layout.toggle();
           }
       });
       lifestyle_and_appearence_card_layout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               lifestyle_and_appearence_exp_layout.toggle();
           }
       });

       search_using_keywords_card_layout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               search_using_keywords_exp_layout.toggle();
           }
       });



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

package com.example.shianikha.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shianikha.R;
import com.example.shianikha.activities.HomeActivity;
import com.example.shianikha.adapters.RecentlyJoinAdapter;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<String> recently_join_Names = new ArrayList<>();
    private ArrayList<String> recently_join_ImageUrls = new ArrayList<>();
    private View fragmentView;
    private Context context;

    private OnFragmentInteractionListener mListener;

    public static HomeFragment newInstance()
    {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        context = getContext();
        if (fragmentView == null)
        {
            fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
            CircularImageView circularImageView = fragmentView.findViewById(R.id.cir_imv);

            getRecentlyJoinList();
            getRecentlyVisitedList();
            ((HomeActivity)context).makeStatusBarColorBlue(true);
        }
        return fragmentView;
    }


    private void getRecentlyJoinList() {
        //Log.d(TAG, "getSpecialOfferList: preparing bitmaps.");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        ////////////
        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        /////////

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.rec_join_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecentlyJoinAdapter adapter = new RecentlyJoinAdapter(getActivity(), recently_join_Names, recently_join_ImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getRecentlyVisitedList() {
       // Log.d(TAG, "getSpecialOfferList: preparing bitmaps.");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        ////////////
        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        /////////

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.recent_visitors_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecentlyJoinAdapter adapter = new RecentlyJoinAdapter(getActivity(), recently_join_Names, recently_join_ImageUrls);
        recyclerView.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

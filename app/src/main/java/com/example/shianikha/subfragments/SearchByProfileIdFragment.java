package com.example.shianikha.subfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shianikha.R;


public class SearchByProfileIdFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public SearchByProfileIdFragment() {
        // Required empty public constructor
    }

    public static SearchByProfileIdFragment newInstance() {
        SearchByProfileIdFragment fragment = new SearchByProfileIdFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_by_profile_id, container, false);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package com.example.shianikha.subfragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shianikha.R;


public class IAmLookingForFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public IAmLookingForFragment() {
        // Required empty public constructor
    }

    public static IAmLookingForFragment newInstance() {
        IAmLookingForFragment fragment = new IAmLookingForFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_i_am_looking_for, container, false);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package com.example.shianikha.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shianikha.R;

public class MyProfileFragment extends Fragment {

    View fragmentView;
    public static Fragment previousFragment;
    public static String previousFragmentName;

    private OnFragmentInteractionListener mListener;


    public static MyProfileFragment newInstance(Fragment fragment, String string) {
        MyProfileFragment myProfileFragment = new MyProfileFragment();
        previousFragment = fragment;
        previousFragmentName = string;

        return myProfileFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_my_profile, container, false);

        }
        return fragmentView;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

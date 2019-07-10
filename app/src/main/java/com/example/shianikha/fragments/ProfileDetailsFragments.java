package com.example.shianikha.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shianikha.R;


public class ProfileDetailsFragments extends Fragment {
    private OnFragmentInteractionListener mListener;

    public static Fragment previousFragment;
    public static String previousFragmentName;

    public static ProfileDetailsFragments newInstance(Fragment fragment, String string) {
        ProfileDetailsFragments profileDetailsFragments = new ProfileDetailsFragments();
        previousFragment = fragment;
        previousFragmentName = string;

        return profileDetailsFragments;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_details, container, false);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

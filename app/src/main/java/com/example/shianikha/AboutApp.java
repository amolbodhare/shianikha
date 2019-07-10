package com.example.shianikha;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class AboutApp extends Fragment {


    public static Fragment previousFragment;
    public static String previousFragmentName;

    private OnFragmentInteractionListener mListener;

    public AboutApp() {
        // Required empty public constructor
    }


    public static AboutApp newInstance(Fragment fragment, String string) {
        AboutApp aboutAppFragment = new AboutApp();
        previousFragment = fragment;
        previousFragmentName = string;

        return aboutAppFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_app, container, false);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

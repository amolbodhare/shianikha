package com.example.shianikha;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class HelpAndSupport extends Fragment {
    public static Fragment previousFragment;
    public static String previousFragmentName;

    private OnFragmentInteractionListener mListener;

    public HelpAndSupport() {
        // Required empty public constructor
    }

    public static HelpAndSupport newInstance() {
        HelpAndSupport fragment = new HelpAndSupport();

        return fragment;
    }

    // TODO: Rename and change types and number of parameters

    public static HelpAndSupport newInstance(Fragment fragment, String string) {
        HelpAndSupport helpAndSupportFragment = new HelpAndSupport();
        previousFragment = fragment;
        previousFragmentName = string;

        return helpAndSupportFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help_and_support, container, false);
    }





    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package com.example.shianikha;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class NotifacationDetails extends Fragment {

    public static Fragment previousFragment;
    public static String previousFragmentName;

    private OnFragmentInteractionListener mListener;

    public NotifacationDetails() {
        // Required empty public constructor
    }

    public static NotifacationDetails newInstance() {
        NotifacationDetails fragment = new NotifacationDetails();
        return fragment;
    }

    public static NotifacationDetails newInstance(Fragment fragment, String string) {
        NotifacationDetails notificationDetailsFragment = new NotifacationDetails();
        previousFragment = fragment;
        previousFragmentName = string;

        return notificationDetailsFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifacation_details, container, false);
    }



    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

}

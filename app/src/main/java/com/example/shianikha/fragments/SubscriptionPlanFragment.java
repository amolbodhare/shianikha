package com.example.shianikha.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adoisstudio.helper.LoadingDialog;
import com.example.shianikha.R;

public class SubscriptionPlanFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private View fragmentView;
    private Context context;
    private LoadingDialog  loadingDialog;

    public static Fragment previousFragment;
    public static String previousFragmentName;

    public SubscriptionPlanFragment() {
        // Required empty public constructor
    }

    public static SubscriptionPlanFragment newInstance(Fragment fragment, String string) {
        SubscriptionPlanFragment subscriptionPlanFragment = new SubscriptionPlanFragment();
        previousFragment = fragment;
        previousFragmentName = string;

        return subscriptionPlanFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentView == null)
        {
            fragmentView = inflater.inflate(R.layout.fragment_subscription_plan, container, false);
        }

        return fragmentView;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

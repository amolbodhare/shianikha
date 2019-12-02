package com.nikha.shianikha;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.adoisstudio.helper.LoadingDialog;


public class NotifacationDetails extends Fragment {
    View fragmentView;
    public static Fragment previousFragment;
    public static String previousFragmentName;
    Context context;
    LoadingDialog loadingDialog;


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
        if(fragmentView==null)
        {
            context = getContext();
            fragmentView =  inflater.inflate(R.layout.fragment_notifacation_details, container, false);
        }
        // Inflate the layout for this fragment
        return fragmentView;
    }



    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Object object = fragmentView.getParent();
        if (object instanceof FrameLayout)
            ((FrameLayout) object).removeAllViews();
    }

}

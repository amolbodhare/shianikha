package com.example.shianikha.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.shianikha.R;
import com.example.shianikha.activities.ImageViewerActivity;


public class ProfileDetailsFragments extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;
    private Context context;
    private View fragmentView;
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
        context=getContext();
        if (fragmentView == null) {

            fragmentView = inflater.inflate(R.layout.fragment_profile_details, container, false);
            ImageView image = fragmentView.findViewById(R.id.imagesView);
            LinearLayout linearLayout = fragmentView.findViewById(R.id.imageViewer1);
            image.setOnClickListener(this);
            linearLayout.setOnClickListener(this);

        }
        return fragmentView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imagesView || v.getId() == R.id.imageViewer1) {
            Intent intent = new Intent(context, ImageViewerActivity.class);
            startActivity(intent);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

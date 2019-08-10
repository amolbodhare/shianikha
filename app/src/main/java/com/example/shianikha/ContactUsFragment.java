package com.example.shianikha;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.adoisstudio.helper.LoadingDialog;

import jp.wasabeef.blurry.Blurry;


public class ContactUsFragment extends Fragment {

    ImageView imageView;
    Context context;
    View fragmentView;
    LoadingDialog loadingDialog;

    public static Fragment previousFragment;
    public static String previousFragmentName;



    private OnFragmentInteractionListener mListener;

    public ContactUsFragment() {

    }


    // TODO: Rename and change types and number of parameters

    public static ContactUsFragment newInstance(Fragment fragment, String string) {
        ContactUsFragment contactUsFragment = new ContactUsFragment();
        previousFragment = fragment;
        previousFragmentName = string;

        return contactUsFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_contact_us, container, false);
            context = getContext();
            Blurry.with(context)
                    .radius(10)
                    .sampling(8)
                    .color(Color.argb(66, 255, 255, 0))
                    .async()
                    .onto(((RelativeLayout) fragmentView.findViewById(R.id.relativeLayout)));
        }
        return fragmentView;

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
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

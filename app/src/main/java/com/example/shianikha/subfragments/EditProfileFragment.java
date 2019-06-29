package com.example.shianikha.subfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shianikha.R;
import com.example.shianikha.activities.RegistrationActivity;


public class EditProfileFragment extends Fragment {

    ImageView gender_male_imv;
    ImageView gender_female_imv;
    View view;
    private OnFragmentInteractionListener mListener;
    Context context;
   MyBounceInterpolator interpolator;
    String gender="";

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        view=inflater.inflate(R.layout.fragment_edit_profile, container, false);
        gender_male_imv = view.findViewById(R.id.genderr_male_imv);
        gender_female_imv = view.findViewById(R.id.genderr_female_imv);
        final Animation myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        interpolator = new MyBounceInterpolator(0.2, 20);
        gender_male_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender_male_imv.setBackground(getActivity().getDrawable(R.drawable.reg_img_bg_selected));
                gender_female_imv.setBackground(getActivity().getDrawable(R.drawable.reg_img_bg));
                gender = "male";
                myAnim.setInterpolator(interpolator);
                gender_male_imv.startAnimation(myAnim);
                gender_female_imv.clearAnimation();

            }
        });

        gender_female_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender_female_imv.setBackground(getResources().getDrawable(R.drawable.reg_img_bg_selected));
                gender_male_imv.setBackground(getResources().getDrawable(R.drawable.reg_img_bg));
                gender = "female";
                myAnim.setInterpolator(interpolator);
                gender_female_imv.startAnimation(myAnim);
                gender_male_imv.clearAnimation();
            }
        });
        return  view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public  void radioButtonClick(View view)
    {
        if((view.getRootView().getId())==(R.id.islam_convert_rdg))
        {
            Toast.makeText(context, "heyyyyy", Toast.LENGTH_SHORT).show();
        }
    }
    class MyBounceInterpolator implements android.view.animation.Interpolator {
        private double mAmplitude = 1;
        private double mFrequency = 10;

        MyBounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) *
                    Math.cos(mFrequency * time) + 1);
        }
    }
}

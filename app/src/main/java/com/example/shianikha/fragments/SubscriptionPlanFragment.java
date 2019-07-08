package com.example.shianikha.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.adoisstudio.helper.LoadingDialog;
import com.example.shianikha.R;
import com.example.shianikha.adapters.SubscriptionSliderAdapter;

public class SubscriptionPlanFragment extends Fragment implements View.OnClickListener
{

    private OnFragmentInteractionListener mListener;

    private View fragmentView;

    public static Fragment previousFragment;
    public static String previousFragmentName;

    private ViewPager subPlanSlideViewPager;
    private SubscriptionSliderAdapter subPlanSliderAdapter;
    Context context;
    ImageView imv_pre_btn,imv_next_btn;


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

        context = getContext();
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Inflate the layout for this fragment

        if (fragmentView == null)
        {
            fragmentView = inflater.inflate(R.layout.fragment_subscription_plan, container, false);

            subPlanSlideViewPager = fragmentView.findViewById(R.id.sub_plan_viewpager);
            imv_pre_btn=fragmentView.findViewById(R.id.prev_imv);
            imv_next_btn=fragmentView.findViewById(R.id.next_imv);

            subPlanSliderAdapter = new SubscriptionSliderAdapter(context);

            subPlanSlideViewPager.setAdapter(subPlanSliderAdapter);

        }

        return fragmentView;
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.prev_imv)
        {
            subPlanSlideViewPager.setCurrentItem(getItem(-1), true);
        }
        else  if(v.getId()==R.id.next_imv)
        {
            subPlanSlideViewPager.setCurrentItem(getItem(+1), true);
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private int getItem(int i) {
        return subPlanSlideViewPager.getCurrentItem() + i;
    }
}

package com.example.shianikha.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.LoadingDialog;
import com.example.App;
import com.example.shianikha.R;
import com.example.shianikha.activities.HomeActivity;
import com.example.shianikha.subfragments.AdvanceSearchFragment;
import com.example.shianikha.subfragments.SearchByProfileIdFragment;
import com.example.shianikha.subfragments.SearchOptionFragment;

public class SearchFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context context;
    private LoadingDialog loadingDialog;
    private View fragMentView;

    public static Fragment previousFragment;
    public static String previousFragmentName;

    private SearchOptionFragment searchOptionFragment;
    private AdvanceSearchFragment advanceSearchFragment;
    private SearchByProfileIdFragment searchByProfileIdFragment;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(Fragment fragment, String string) {
        SearchFragment searchFragment = new SearchFragment();
        previousFragment = fragment;
        previousFragmentName = string;
        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        fragMentView = inflater.inflate(R.layout.fragment_search, container, false);
        fragMentView.findViewById(R.id.searchByProfileId).setOnClickListener(this);
        fragMentView.findViewById(R.id.advanceSearch).setOnClickListener(this);
        fragMentView.findViewById(R.id.searchOption).setOnClickListener(this);

        if (App.tempFragment != null) {
            if (App.tempFragment instanceof SearchOptionFragment)
                changeTabColor(fragMentView.findViewById(R.id.searchOption));
            else if (App.tempFragment instanceof AdvanceSearchFragment)
                changeTabColor(fragMentView.findViewById(R.id.advanceSearch));
            else if (App.tempFragment instanceof SearchByProfileIdFragment)
                changeTabColor(fragMentView.findViewById(R.id.searchByProfileId));

            H.log("iAmCaught", "inIf");

            fragmentLoader(App.tempFragment);

            return fragMentView;
        }

        SearchOptionFragment searchOptionFragment = SearchOptionFragment.newInstance();
        fragmentLoader(searchOptionFragment);


        return fragMentView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.searchByProfileId || v.getId() == R.id.advanceSearch || v.getId() == R.id.searchOption) {
            changeTabColor(v);

            loadSubFragment(v);
        }
    }

    private void changeTabColor(View v) {
        LinearLayout linearLayout = (LinearLayout) v.getParent();
        linearLayout = (LinearLayout) linearLayout.getParent();
        LinearLayout ll;
        TextView textView;

        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ll = (LinearLayout) linearLayout.getChildAt(i);
            textView = (TextView) ll.getChildAt(0);
            textView.setTextColor(getContext().getColor(R.color.textview_grey_color));
            textView.setBackgroundColor(getContext().getColor(R.color.textview_back_color));
            textView.setElevation(0f);

            ll.getChildAt(1).setVisibility(View.INVISIBLE);
        }

        linearLayout = (LinearLayout) v.getParent();
        textView = (TextView) linearLayout.getChildAt(0);
        textView.setTextColor(getContext().getColor(R.color.white));
        textView.setBackgroundColor(getContext().getColor(R.color.textpurle2));
        textView.setElevation(7f);
        linearLayout.getChildAt(1).setVisibility(View.VISIBLE);
    }

    private void loadSubFragment(View v) {
        if (v.getId() == R.id.searchOption) {
            if (searchOptionFragment == null)
                searchOptionFragment = SearchOptionFragment.newInstance();
            fragmentLoader(searchOptionFragment);
        } else if (v.getId() == R.id.advanceSearch) {
            if (advanceSearchFragment == null)
                advanceSearchFragment = AdvanceSearchFragment.newInstance();
            fragmentLoader(advanceSearchFragment);
        } else if (v.getId() == R.id.searchByProfileId) {
            if (searchByProfileIdFragment == null)
                searchByProfileIdFragment = SearchByProfileIdFragment.newInstance();
            fragmentLoader(searchByProfileIdFragment);
        }
    }

    private void fragmentLoader(Fragment fragment) {
        App.tempFragment = fragment;
        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit)
                .replace(R.id.frameLayout, fragment).commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

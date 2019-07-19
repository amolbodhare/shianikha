package com.example.shianikha.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shianikha.R;
import com.example.shianikha.adapters.MatchesAdapter;
import com.example.shianikha.entities.MatchesEntity;

import java.util.List;


public class MyActivityFragment extends Fragment
{
    private View fragmentView;
    private Context context;
    public static Fragment previousFragment;
    public static String previousFragmentName;

    public MyActivityFragment() {
        // Required empty public constructor
    }

    public static MyActivityFragment newInstance(Fragment fragment, String string) {
        MyActivityFragment myActivityFragment = new MyActivityFragment();
        previousFragment = fragment;
        previousFragmentName = string;
        return myActivityFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        context = getContext();
       if (fragmentView == null)
       {
           fragmentView = inflater.inflate(R.layout.fragment_my_activity, container, false);
       }

        return fragmentView;
    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

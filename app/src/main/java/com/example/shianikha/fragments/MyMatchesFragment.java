package com.example.shianikha.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shianikha.R;
import com.example.shianikha.activities.FilterActivity;
import com.example.shianikha.activities.HomeActivity;

public class MyMatchesFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;

    private View fragMentView;
    private Context context;
    public static Fragment previousFragment;
    public static String previousFragmentName;

    public static MyMatchesFragment newInstance(Fragment fragment, String string) {
        MyMatchesFragment myMatchesFragment = new MyMatchesFragment();
        previousFragment = fragment;
        previousFragmentName = string;
        return myMatchesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        //((HomeActivity) context).makeStatusBarColorBlue(context.getColor(R.color.white));
        if (fragMentView == null) {
            fragMentView = inflater.inflate(R.layout.fragment_my_matches, container, false);
            fragMentView.findViewById(R.id.top_matches).setOnClickListener(this);
            fragMentView.findViewById(R.id.i_am_looking_for).setOnClickListener(this);
            fragMentView.findViewById(R.id.looking_for_me).setOnClickListener(this);
            fragMentView.findViewById(R.id.refine_imv).setOnClickListener(this);
            fragMentView.findViewById(R.id.edit_btn).setOnClickListener(this);

            ((ListView) fragMentView.findViewById(R.id.listView)).setAdapter(new CustomListAdapte());

        }
        return fragMentView;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.top_matches) {

        } else if (v.getId() == R.id.i_am_looking_for) {

        } else if (v.getId() == R.id.looking_for_me) {

        } else if (v.getId() == R.id.refine_imv) {
            Intent i = new Intent(getActivity(), FilterActivity.class);
            startActivity(i);
            ((HomeActivity) context).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class CustomListAdapte extends BaseAdapter {

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(context).inflate(R.layout.matches_card, null, false);

            if (position == 1)
            {
                convertView.findViewById(R.id.thumbnail).setVisibility(View.GONE);
                convertView.findViewById(R.id.linearLayout).setVisibility(View.VISIBLE);
            }

            return convertView;
        }
    }
}

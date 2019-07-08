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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shianikha.R;
import com.example.shianikha.activities.FilterActivity;
import com.example.shianikha.activities.HomeActivity;

public class MyMatchesFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;

    private View fragmentView;
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
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_my_matches, container, false);
            fragmentView.findViewById(R.id.top_matches).setOnClickListener(this);
            fragmentView.findViewById(R.id.i_am_looking_for).setOnClickListener(this);
            fragmentView.findViewById(R.id.looking_for_me).setOnClickListener(this);

            fragmentView.findViewById(R.id.edit_btn).setOnClickListener(this);

            fragmentView.findViewById(R.id.refine_imv).setOnClickListener(this);
            fragmentView.findViewById(R.id.refine_btn).setOnClickListener(this);

            ((ListView) fragmentView.findViewById(R.id.listView)).setAdapter(new CustomListAdapte());


        }
        return fragmentView;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.top_matches || i == R.id.i_am_looking_for || i == R.id.looking_for_me)
            changeColorsOfThreeTab(v);
        else if (v.getId() == R.id.refine_imv || v.getId() == R.id.refine_btn)
        {
            Intent intent = new Intent(getActivity(), FilterActivity.class);
            startActivity(intent);
            ((HomeActivity) context).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        }
    }

    private void changeColorsOfThreeTab(View v) {
        LinearLayout parentLayout = fragmentView.findViewById(R.id.threeTabContainer);
        LinearLayout childLayout;
        TextView textView;
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            childLayout = (LinearLayout) parentLayout.getChildAt(i);
            textView = ((TextView) childLayout.getChildAt(0));
            textView.setTextColor(context.getColor(R.color.textview_grey_color));
            textView.setBackgroundColor(context.getColor(R.color.textview_back_color));
            childLayout.getChildAt(1).setVisibility(View.GONE);
        }

        childLayout = (LinearLayout) v.getParent();
        ((TextView) childLayout.getChildAt(0)).setTextColor(context.getColor(R.color.white));
        childLayout.getChildAt(0).setBackgroundColor(context.getColor(R.color.textpurle2));
        childLayout.getChildAt(1).setVisibility(View.VISIBLE);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class CustomListAdapte extends BaseAdapter implements View.OnClickListener {

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

            convertView.findViewById(R.id.imageView).setOnClickListener(this);

            if (position == 1) {
                convertView.findViewById(R.id.thumbnail).setVisibility(View.GONE);
                convertView.findViewById(R.id.linearLayout).setVisibility(View.VISIBLE);
            }

            return convertView;
        }

        @Override
        public void onClick(View v)
        {
            if (v.getId() == R.id.imageView)
            {
                ImageView imageView = (ImageView)v;
                if (imageView.getDrawable() == null)
                    imageView.setImageDrawable(context.getDrawable(R.drawable.ic_check_black_24dp));
                else
                    imageView.setImageDrawable(null);
            }
        }
    }
}

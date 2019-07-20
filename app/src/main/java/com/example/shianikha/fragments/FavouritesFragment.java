package com.example.shianikha.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.shianikha.R;

public class FavouritesFragment extends Fragment
{
    private View fragmentView;
    private Context context;

    public static Fragment previousFragment;
    public static String previousFragmentName;

    private OnFragmentInteractionListener mListener;

    public FavouritesFragment() {
        // Required empty public constructor
    }


    public static FavouritesFragment newInstance(Fragment fragment, String string) {
        FavouritesFragment favouritesFragment = new FavouritesFragment();
        previousFragment = fragment;
        previousFragmentName = string;
        return favouritesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (fragmentView == null)
        {
            context = getContext();
            fragmentView = inflater.inflate(R.layout.fragment_favourites, container, false);

            ((ListView)fragmentView.findViewById(R.id.listView)).setAdapter(new CustomListAdapter());

        }
        return fragmentView;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class CustomListAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            if (view == null)
                view = LayoutInflater.from(context).inflate(R.layout.matches_card,viewGroup,false);


            return view;
        }
    }

}

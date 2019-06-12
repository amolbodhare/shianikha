package com.example.shianikha.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shianikha.R;
import com.example.shianikha.activities.HomeActivity;
import com.example.shianikha.adapters.RecentlyJoinAdapter;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static String TAG = HomeActivity.class.getSimpleName();
    CircleImageView ivUserProfilePic;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<String> recently_join_Names = new ArrayList<>();
    private ArrayList<String> recently_join_ImageUrls = new ArrayList<>();
    View v;


    private OnFragmentInteractionListener mListener;


    public HomeFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static HomeFragment newInstance(String param1, String param2)
    {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_home, container, false);
        //ivUserProfilePic = (CircleImageView) v.findViewById(R.id.image_profile_pic);
        CircularImageView circularImageView = (CircularImageView)v.findViewById(R.id.cir_imv);
// Set Border
       /* circularImageView.setBorderColor(getResources().getColor(R.color.grey));
        circularImageView.setBorderWidth(10);
// Add Shadow with default param
        circularImageView.addShadow();
// or with custom param
        circularImageView.setShadowRadius(15);
        circularImageView.setShadowColor(Color.RED);*/
        getRecentlyJoinList();
        getRecentlyVisitedList();
        //((HomeActivity)getContext()).setStatusBarBackground(getContext().getColor(R.color.textpurle2));
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void getRecentlyJoinList()
    {
        Log.d(TAG, "getSpecialOfferList: preparing bitmaps.");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        ////////////
        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        /////////

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = v.findViewById(R.id.rec_join_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecentlyJoinAdapter adapter = new RecentlyJoinAdapter(getActivity(), recently_join_Names, recently_join_ImageUrls);
        recyclerView.setAdapter(adapter);

    }

    private void getRecentlyVisitedList()
    {
        Log.d(TAG, "getSpecialOfferList: preparing bitmaps.");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        ////////////
        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        /////////

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = v.findViewById(R.id.recent_visitors_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecentlyJoinAdapter adapter = new RecentlyJoinAdapter(getActivity(), recently_join_Names, recently_join_ImageUrls);
        recyclerView.setAdapter(adapter);
    }
}

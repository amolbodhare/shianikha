package com.example.shianikha.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.shianikha.activities.FilterActivity;
import com.example.shianikha.adapters.MatchesAdapter;
import com.example.shianikha.entities.MatchesEntity;
import com.example.shianikha.subfragments.AcceptedFragment;
import com.example.shianikha.subfragments.IAmLookingForFragment;
import com.example.shianikha.subfragments.LookingForMeFragment;
import com.example.shianikha.subfragments.TopMatchesFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyActivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyActivityFragment extends Fragment implements View.OnClickListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;
    private RecyclerView recyclerView;
    private MatchesAdapter adapter;
    private List<MatchesEntity> matchesList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView top_matches_tv,i_am_looking_for_tv,looking_for_me_tv;

    private OnFragmentInteractionListener mListener;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private ProgressDialog dialog;


    private View fragMentView;
    Context context;

    public MyActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyMatchesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyActivityFragment newInstance(String param1, String param2) {
        MyActivityFragment fragment = new MyActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static MyActivityFragment newInstance() {
        MyActivityFragment fragment = new MyActivityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        //((HomeActivity) context).setStatusBarBackground(context.getColor(R.color.white));
        if (fragMentView == null)
        {
            fragMentView = inflater.inflate(R.layout.fragment_my_activity, container, false);
            fragMentView.findViewById(R.id.accepted).setOnClickListener(this);
            fragMentView.findViewById(R.id.accepted_me).setOnClickListener(this);
            fragMentView.findViewById(R.id.contacted_me).setOnClickListener(this);


            AcceptedFragment acceptedFragment = AcceptedFragment.newInstance();
            fragmentLoader(acceptedFragment);
        }
        return fragMentView;
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

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.accepted || v.getId() == R.id.accepted_me || v.getId() == R.id.contacted_me) {
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

            loadSubFragment(v);
        }
        /*if(v.getId()==R.id.refine_imv)
        {
            Intent i=new Intent(getActivity(), FilterActivity.class);
            startActivity(i);
            getActivity().overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        }*/
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
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void loadSubFragment(View v)
    {
        if (v.getId() == R.id.accepted)
        {
            AcceptedFragment acceptedFragment = AcceptedFragment.newInstance();
            fragmentLoader(acceptedFragment);
        }
        else if (v.getId() == R.id.accepted_me)
        {
            AcceptedFragment acceptedFragment = AcceptedFragment.newInstance();
            fragmentLoader(acceptedFragment);
        }
        else if (v.getId() == R.id.contacted_me)
        {
            AcceptedFragment acceptedFragment = AcceptedFragment.newInstance();
            fragmentLoader(acceptedFragment);
        }
    }

    private void fragmentLoader(Fragment fragment)
    {
        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit)
                .replace(R.id.frameLayout, fragment).commit();
    }


}

package com.example.shianikha.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shianikha.R;
import com.example.shianikha.adapters.MatchesAdapter;
import com.example.shianikha.adapters.RecentlyJoinAdapter;
import com.example.shianikha.entities.MatchesEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyMatchesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyMatchesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyMatchesFragment extends Fragment {
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

    private OnFragmentInteractionListener mListener;

    public MyMatchesFragment() {
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
    public static MyMatchesFragment newInstance(String param1, String param2) {
        MyMatchesFragment fragment = new MyMatchesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static MyMatchesFragment newInstance() {
        MyMatchesFragment fragment = new MyMatchesFragment();
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
        view=inflater.inflate(R.layout.fragment_my_matches, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        matchesList = new ArrayList<>();
        adapter = new MatchesAdapter(getActivity(), matchesList);

        /*RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);*/

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        /*LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        MatchesAdapter adapter = new MatchesAdapter(getActivity(), matchesList);
        recyclerView.setAdapter(adapter);*/

        prepareAlbums();
        return view;
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
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration
    {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private void prepareAlbums()
    {
        int[] covers = new int[]{
                R.drawable.kangna,
                R.drawable.kangna,
                R.drawable.kangna,
                R.drawable.kangna,
                R.drawable.kangna,
                R.drawable.kangna,
                R.drawable.kangna,
                R.drawable.kangna,
                R.drawable.kangna,
                R.drawable.kangna,
                R.drawable.kangna};

        MatchesEntity a = new MatchesEntity("True Romance", 13, covers[0]);
        matchesList.add(a);

        a = new MatchesEntity("Panzanella ", 8, covers[1]);
        matchesList.add(a);

        a = new MatchesEntity("Bruschetta", 11, covers[2]);
        matchesList.add(a);

        a = new MatchesEntity("Focaccia Bread", 12, covers[3]);
        matchesList.add(a);

        a = new MatchesEntity("Pasta Carbonara", 14, covers[4]);
        matchesList.add(a);

        a = new MatchesEntity("Margherita Pizza", 1, covers[5]);
        matchesList.add(a);

        a = new MatchesEntity("Mushroom Risotto", 11, covers[6]);
        matchesList.add(a);

        a = new MatchesEntity(" Pasta Con Pomodoro E Basilico", 14, covers[7]);
        matchesList.add(a);

        a = new MatchesEntity("Lasagna", 11, covers[8]);
        matchesList.add(a);

        a = new MatchesEntity("Pistachio Panna Cotta", 17, covers[9]);
        matchesList.add(a);

        adapter.notifyDataSetChanged();
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
}

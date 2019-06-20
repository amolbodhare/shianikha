package com.example.shianikha.subfragments;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shianikha.R;
import com.example.shianikha.adapters.AcceptedAdapter;
import com.example.shianikha.adapters.MatchesAdapter;
import com.example.shianikha.entities.MatchesEntity;

import java.util.ArrayList;
import java.util.List;


public class AcceptedFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private AcceptedAdapter adapter;
    private ProgressDialog dialog;
    View view;
    RecyclerView recyclerview;
    private List<MatchesEntity> albumList;

    public AcceptedFragment() {
        // Required empty public constructor
    }

    public static AcceptedFragment newInstance()
    {
        AcceptedFragment fragment = new AcceptedFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_accepted, container, false);


        recyclerview = (RecyclerView) view.findViewById(R.id.accepted_recyclerview);

        albumList = new ArrayList<>();

        adapter = new AcceptedAdapter(getActivity(), albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(15), true));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(adapter);
        acceptedList();
        return view;


    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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


    private void acceptedList()
    {
        int[] covers = new int[]
                {
                        R.drawable.kangana_ranaut,
                        R.drawable.kangana_ranaut,
                        R.drawable.kangana_ranaut,
                        R.drawable.kangana_ranaut,
                        R.drawable.kangana_ranaut,
                        R.drawable.kangana_ranaut,
                        R.drawable.kangana_ranaut,
                        R.drawable.kangana_ranaut,
                        R.drawable.kangana_ranaut,
                        R.drawable.kangana_ranaut,
                        R.drawable.kangana_ranaut,
                };

        MatchesEntity a = new MatchesEntity("Kangna Ranout", 13, covers[0]);
        albumList.add(a);

        a = new MatchesEntity("Kangna Ranout ", 8, covers[1]);
        albumList.add(a);

        a = new MatchesEntity("Kangna Ranout", 11, covers[2]);
        albumList.add(a);

        a = new MatchesEntity("Kangna Ranout", 12, covers[3]);
        albumList.add(a);

        a = new MatchesEntity("Kangna Ranout", 14, covers[4]);
        albumList.add(a);

        a = new MatchesEntity("Kangna Ranout", 1, covers[5]);
        albumList.add(a);

        a = new MatchesEntity("Kangna Ranout", 11, covers[6]);
        albumList.add(a);

        a = new MatchesEntity(" Kangna Ranout", 14, covers[7]);
        albumList.add(a);

        a = new MatchesEntity("Kangna Ranout", 11, covers[8]);
        albumList.add(a);

        a = new MatchesEntity("Kangna Ranout", 17, covers[9]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }
}

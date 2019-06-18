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
import com.example.shianikha.adapters.MatchesAdapter;
import com.example.shianikha.entities.MatchesEntity;

import java.util.ArrayList;
import java.util.List;


public class TopMatchesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private List<MatchesEntity> matchesList;
    private MatchesAdapter adapter;
    private ProgressDialog dialog;
    View view;
    RecyclerView top_matches_recyclerview;

    public TopMatchesFragment() {
        // Required empty public constructor
    }

    public static TopMatchesFragment newInstance()
    {
        TopMatchesFragment fragment = new TopMatchesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_top_mathces, container, false);


        top_matches_recyclerview = (RecyclerView) view.findViewById(R.id.top_matches_recyclerview);
        dialog = new ProgressDialog(getActivity());
        matchesList = new ArrayList<>();
        adapter = new MatchesAdapter(getActivity(), matchesList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        top_matches_recyclerview.setLayoutManager(mLayoutManager);
        top_matches_recyclerview.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(28), true));
        top_matches_recyclerview.setItemAnimator(new DefaultItemAnimator());
        top_matches_recyclerview.setAdapter(adapter);
        //recyclerView.removeAllViews();
        //recyclerView.setAdapter(null);


        top_matches_recyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        /*LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        MatchesAdapter adapter = new MatchesAdapter(getActivity(), matchesList);
        recyclerView.setAdapter(adapter);*/

        topmatches();
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


    private void topmatches()
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
        matchesList.add(a);

        a = new MatchesEntity("Kangna Ranout ", 8, covers[1]);
        matchesList.add(a);

        a = new MatchesEntity("Kangna Ranout", 11, covers[2]);
        matchesList.add(a);

        a = new MatchesEntity("Kangna Ranout", 12, covers[3]);
        matchesList.add(a);

        a = new MatchesEntity("Kangna Ranout", 14, covers[4]);
        matchesList.add(a);

        a = new MatchesEntity("Kangna Ranout", 1, covers[5]);
        matchesList.add(a);

        a = new MatchesEntity("Kangna Ranout", 11, covers[6]);
        matchesList.add(a);

        a = new MatchesEntity(" Kangna Ranout", 14, covers[7]);
        matchesList.add(a);

        a = new MatchesEntity("Kangna Ranout", 11, covers[8]);
        matchesList.add(a);

        a = new MatchesEntity("Kangna Ranout", 17, covers[9]);
        matchesList.add(a);

        adapter.notifyDataSetChanged();

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        //recyclerView.setAdapter(null);
        //recyclerView.removeAllViews();
    }
}

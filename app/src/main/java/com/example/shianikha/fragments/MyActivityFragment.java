package com.example.shianikha.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.adoisstudio.helper.H;
import com.example.shianikha.R;


public class MyActivityFragment extends Fragment implements View.OnClickListener {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_my_activity, container, false);

            ((GridView) fragmentView.findViewById(R.id.gridView)).setAdapter(new GridViewAdapter());

            setOnClickListenerOnScrollBarChild();
        }

        return fragmentView;
    }

    private void setOnClickListenerOnScrollBarChild() {
        LinearLayout linearLayout = fragmentView.findViewById(R.id.linearLayout);
        for (int i = 0; i < linearLayout.getChildCount(); i += 2)
            linearLayout.getChildAt(i).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        LinearLayout linearLayout = fragmentView.findViewById(R.id.linearLayout);
        LinearLayout childLayout;
        TextView textView;

        for (int i = 0; i < linearLayout.getChildCount(); i += 2) {
            childLayout = (LinearLayout) linearLayout.getChildAt(i);
            textView = (TextView) childLayout.getChildAt(0);
            textView.setBackgroundColor(context.getColor(R.color.textview_back_color));
            textView.setTextColor(context.getColor(R.color.textview_grey_color));
            textView.setElevation(0f);

            if ((i%4==0)&& view.getId() == childLayout.getId())
                implementAutoScroll(childLayout);

            childLayout.getChildAt(1).setVisibility(View.INVISIBLE);
        }

        childLayout = (LinearLayout) view;
        textView = (TextView) childLayout.getChildAt(0);
        textView.setBackgroundColor(context.getColor(R.color.textpurle2));
        textView.setTextColor(context.getColor(R.color.white));
        textView.setElevation(7f);
        childLayout.getChildAt(1).setVisibility(View.VISIBLE);
    }

    private void implementAutoScroll(LinearLayout linearLayout)
    {
        HorizontalScrollView horizontalScrollView = fragmentView.findViewById(R.id.horizontalScrollView);
        horizontalScrollView.smoothScrollBy((int) linearLayout.getX(),0);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class GridViewAdapter extends BaseAdapter {

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
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null)
                view = LayoutInflater.from(context).inflate(R.layout.accepted_list_card, viewGroup, false);
            return view;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Object object = fragmentView.getParent();
        if (object instanceof FrameLayout)
            ((FrameLayout) object).removeAllViews();
    }
}

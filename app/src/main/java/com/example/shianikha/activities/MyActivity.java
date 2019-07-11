package com.example.shianikha.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shianikha.R;
import com.example.shianikha.adapters.MatchesAdapter;
import com.example.shianikha.entities.MatchesEntity;
import com.example.shianikha.subfragments.AcceptedFragment;

import java.util.List;

public class MyActivity extends AppCompatActivity implements View.OnClickListener
{

    View view;
    private RecyclerView recyclerView;
    private MatchesAdapter adapter;
    private List<MatchesEntity> matchesList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView top_matches_tv,i_am_looking_for_tv,looking_for_me_tv;

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private ProgressDialog dialog;


    private View fragMentView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        context = MyActivity.this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.accepted).setOnClickListener(MyActivity.this);
        findViewById(R.id.accepted_me).setOnClickListener(this);
        findViewById(R.id.contacted_me).setOnClickListener(this);
        findViewById(R.id.i_have_contacted).setOnClickListener(this);
        findViewById(R.id.who_visited_my_profile).setOnClickListener(this);
        findViewById(R.id.profiles_viewed_by_me).setOnClickListener(this);

        AcceptedFragment acceptedFragment = AcceptedFragment.newInstance();
        fragmentLoader(acceptedFragment);
    }

    private void fragmentLoader(Fragment fragment)
     { getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit)
                .replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.accepted || v.getId() == R.id.accepted_me || v.getId() == R.id.contacted_me||
                v.getId()== R.id.i_have_contacted ||v.getId()==R.id.who_visited_my_profile||v.getId()==R.id.profiles_viewed_by_me)
        {
            LinearLayout linearLayout = (LinearLayout) v.getParent();
            linearLayout = (LinearLayout) linearLayout.getParent();
            LinearLayout ll;
            TextView textView;

            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                ll = (LinearLayout) linearLayout.getChildAt(i);
                textView = (TextView) ll.getChildAt(0);
                textView.setTextColor(context.getColor(R.color.textview_grey_color));
                textView.setBackgroundColor(context.getColor(R.color.textview_back_color));
                textView.setElevation(0f);

                ll.getChildAt(1).setVisibility(View.INVISIBLE);
            }

            linearLayout = (LinearLayout) v.getParent();
            textView = (TextView) linearLayout.getChildAt(0);
            textView.setTextColor(context.getColor(R.color.white));
            textView.setBackgroundColor(context.getColor(R.color.textpurle2));
            textView.setElevation(7f);
            linearLayout.getChildAt(1).setVisibility(View.VISIBLE);

            loadSubFragment(v);
        }
        else if (v.getId() == R.id.sub_drawerMenu)
        {
            onMethodClick(v);
        }
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
        else if (v.getId() == R.id.i_have_contacted)
        {
            AcceptedFragment acceptedFragment = AcceptedFragment.newInstance();
            fragmentLoader(acceptedFragment);
        }
        else if (v.getId() == R.id.who_visited_my_profile)
        {
            AcceptedFragment acceptedFragment = AcceptedFragment.newInstance();
            fragmentLoader(acceptedFragment);
        }
        else if (v.getId() == R.id.profiles_viewed_by_me)
        {
            AcceptedFragment acceptedFragment = AcceptedFragment.newInstance();
            fragmentLoader(acceptedFragment);
        }

    }
    public void  onMethodClick(View v)
    {
        finish();
    }

}

package com.example.shianikha.activities;

import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shianikha.R;
import com.example.shianikha.fragments.HomeFragment;
import com.example.shianikha.fragments.MyMatchesFragment;
import com.example.shianikha.fragments.MyProfileFragment;
import com.example.shianikha.fragments.SearchFragment;

public class HomeActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,MyMatchesFragment.OnFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener,MyProfileFragment.OnFragmentInteractionListener
{
    HomeFragment homeFragment;
    MyMatchesFragment myMatchesFragment;
    SearchFragment searchFragment;
    MyProfileFragment myProfileFragment;

    private long l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeFragment = HomeFragment.newInstance("Amol","amit");
        setStatusBarBackground(getColor(R.color.textpurle2));
        fragmentLoader(homeFragment, getString(R.string.MyShia));
    }

    public void setStatusBarBackground(int i)
    {
        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height = statusBarHeight;
        findViewById(R.id.view).setLayoutParams(layoutParams);
        findViewById(R.id.view).setBackgroundColor(i);
    }

    public void fragmentLoader(Fragment fragment, String title)
    {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit)
                .replace(R.id.frameLayout, fragment).addToBackStack(title).commit();

        //((TextView) findViewById(R.id.titleName)).setText(title);
    }

    public void onFragmentChange(View view)
    {
        if (view.getId() == R.id.homeButton)
        {
            fragmentLoader(homeFragment, getString(R.string.MyShia));
            //controlBackButtonVisibility(false);
        }
        else if (view.getId() == R.id.mymatchesButton) {
            if (myMatchesFragment == null)
                myMatchesFragment = MyMatchesFragment.newInstance("hi","hello");
            fragmentLoader(myMatchesFragment, getString(R.string.MyMatches));
            //controlBackButtonVisibility(true);
        }
        else if (view.getId() == R.id.searchButton)
        {
            if (searchFragment == null)
                searchFragment = SearchFragment.newInstance();
            fragmentLoader(searchFragment, getString(R.string.search));
            //controlBackButtonVisibility(true);
        }

        else if (view.getId() == R.id.myprofileButton)
        {
            if (myProfileFragment == null)
                myProfileFragment = MyProfileFragment.newInstance("hi","hello");
            fragmentLoader(myProfileFragment, getString(R.string.Myprofile));
            //controlBackButtonVisibility(true);
        }

        setSelection(view);
    }

    /*public void controlBackButtonVisibility(boolean b) {
        if (b)
            findViewById(R.id.backButton).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.backButton).setVisibility(View.GONE);
    }*/

    private void setSelection(View view) {
        LinearLayout parentLayout = findViewById(R.id.bottomLayout);
        LinearLayout childLayout;
        ImageView imageView;
        TextView textView;

        /*int scale = getResources().getDisplayMetrics().densityDpi;
        scale = (int)(24*scale+0.5f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(scale,scale);*/

        for (int i = 0; i < parentLayout.getChildCount(); i++)
        {
            childLayout = ((LinearLayout) parentLayout.getChildAt(i));

            imageView = (ImageView) childLayout.getChildAt(0);
            imageView.setColorFilter(getResources().getColor(R.color.grey));
            //imageView.setLayoutParams(layoutParams);

            textView = (TextView) childLayout.getChildAt(1);
            textView.setTextColor(getResources().getColor(R.color.grey));
            textView.setTextSize(9f);
        }

        childLayout = (LinearLayout) view;

        /*scale = (int)(26*scale+0.5f);
        layoutParams = new LinearLayout.LayoutParams(scale,scale);*/

        imageView = (ImageView) childLayout.getChildAt(0);
        imageView.setColorFilter(getResources().getColor(R.color.bluebtnback));

        //imageView.setLayoutParams(layoutParams);

        textView = (TextView) childLayout.getChildAt(1);
        textView.setTextColor(getResources().getColor(R.color.bluebtnback));
        textView.setTextSize(9.4f);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        FragmentManager fm = HomeActivity.this.getSupportFragmentManager();

        int count=fm.getBackStackEntryCount();

        //Toast.makeText(this, ""+count, Toast.LENGTH_SHORT).show();

        if(fm.getBackStackEntryCount()==1)
        {
            if (System.currentTimeMillis() - l < 1000)
                finish();
            else
                {
                l = System.currentTimeMillis();
                //H.showMessage(this, "Press again to exit");
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            for(int i = 0; i < fm.getBackStackEntryCount()-1; ++i)
            {
                fm.popBackStack();
            }

            setSelection(findViewById(R.id.homeButton));
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

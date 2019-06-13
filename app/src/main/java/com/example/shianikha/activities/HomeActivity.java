package com.example.shianikha.activities;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adoisstudio.helper.H;
import com.example.shianikha.ContactUsFragment;
import com.example.shianikha.R;
import com.example.shianikha.fragments.HomeFragment;
import com.example.shianikha.fragments.MyMatchesFragment;
import com.example.shianikha.fragments.MyProfileFragment;
import com.example.shianikha.fragments.SearchFragment;
import com.example.shianikha.subfragments.EditProfileFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,MyMatchesFragment.OnFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener,MyProfileFragment.OnFragmentInteractionListener
{
    HomeFragment homeFragment;
    MyMatchesFragment myMatchesFragment;
    SearchFragment searchFragment;
    MyProfileFragment myProfileFragment;
    private DrawerLayout.LayoutParams layoutParams;
    private RelativeLayout relativeLayout;
    private DrawerLayout drawerLayout;
    ContactUsFragment contactUsFragment;
    public EditProfileFragment editProfileFragment;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();
    RelativeLayout cardView_layout;
    public TextView textView;
    public LinearLayout homeButtonLayout, myMatchesButtonLayout, searchButtonLayout, myProfileButtonLayout;

    private long l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeFragment = HomeFragment.newInstance();

        textView = findViewById(R.id.titleName);
        textView.setSelected(true);


        contactUsFragment=ContactUsFragment.newInstance();
       // setStatusBarBackground(getColor(R.color.textpurle2));
        relativeLayout=findViewById(R.id.relativeLayout);
        drawerLayout=findViewById(R.id.drawerLayout);
        cardView_layout=findViewById(R.id.titleBar);
        //cardView.setCardBackgroundColor(getResources().getColor(R.color.dashboard_card_back_color));
        layoutParams=(DrawerLayout.LayoutParams)relativeLayout.getLayoutParams();
        fragmentLoader(homeFragment, getString(R.string.MyShia));

        homeButtonLayout = findViewById(R.id.homeButton_layout);
        myMatchesButtonLayout = findViewById(R.id.mymatchesButton_layout);
        searchButtonLayout = findViewById(R.id.searchButton_layout);
        myProfileButtonLayout = findViewById(R.id.myprofileButton_layout);

    }


   /* public void setStatusBarBackground(int i)
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
*/
    public void fragmentLoader(Fragment fragment, String title)
    {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit)
                .replace(R.id.frameLayout, fragment).addToBackStack(title).commit();


        if (fragmentList.size() > 0 && fragmentList.get(fragmentList.size() - 1) != fragment) {
            fragmentList.add(fragment);
            titleList.add(title);
        }

        title = title.replace("\n"," ");
        textView.setText(title);
        if (fragment == homeFragment)
        {
            ((ImageView) findViewById(R.id.drawerMenu)).setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_black_24dp));
            //cardView_layout.setBackgroundColor(getResources().getColor(R.color.dashboard_card_back_color));
            textView.setText("DashBoard");
        }


        else
            ((ImageView) findViewById(R.id.drawerMenu)).setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
            //cardView_layout.setBackgroundColor(getResources().getColor(R.color.white));

        //((TextView) findViewById(R.id.titleName)).setText(title);
    }

    public void onFragmentChange(View view)
    {
        if (view.getId() == R.id.homeButton_layout)
        {
            fragmentLoader(homeFragment, getString(R.string.MyShia));
            //controlBackButtonVisibility(true);
        }
        else if (view.getId() == R.id.mymatchesButton_layout) {
            //if (myMatchesFragment == null)
                myMatchesFragment = MyMatchesFragment.newInstance("hi","hello");
            fragmentLoader(myMatchesFragment, getString(R.string.MyMatches));
            //controlBackButtonVisibility(true);
        }
        else if (view.getId() == R.id.searchButton_layout)
        {
            //if (searchFragment == null)
                searchFragment = SearchFragment.newInstance();
            fragmentLoader(searchFragment, getString(R.string.search));
            //controlBackButtonVisibility(true);
        }

        else if (view.getId() == R.id.myprofileButton_layout)
        {
            //if (myProfileFragment == null)
                myProfileFragment = MyProfileFragment.newInstance("hi","hello");
            fragmentLoader(myProfileFragment, getString(R.string.Myprofile));
            //controlBackButtonVisibility(true);
        }
        else if(view.getId() == R.id.contactUs)
        {
            if (contactUsFragment == null)
                contactUsFragment = ContactUsFragment.newInstance();
            fragmentLoader(contactUsFragment, getString(R.string.contactUs));
        }

        setSelection(view);
    }

    public void controlBackButtonVisibility(boolean b)
    {
        if (b)
            findViewById(R.id.drawerMenu).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.drawerMenu).setVisibility(View.GONE);
    }

    private void setSelection(View view) {
        LinearLayout parentLayout = findViewById(R.id.bottomLayout);
        LinearLayout childLayout;
        ImageView imageView;
        TextView textView;

        /*int scale = getResources().getDisplayMetrics().densityDpi;
        scale = (int)(24*scale+0.5f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(scale,scale);*/

        for (int i = 0; i <
parentLayout.getChildCount(); i++)
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
        onBack(new View(this));
        /*FragmentManager fm = HomeActivity.this.getSupportFragmentManager();

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
        }*/

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void OnDrawerMenuClick(View view) {
        if (contactUsFragment == null)
            contactUsFragment = ContactUsFragment.newInstance();
        fragmentLoader(contactUsFragment, getString(R.string.contactUs));
        drawerLayout.closeDrawer(Gravity.START);
    }
    public void OnDrawerNavIconClick(View view)
    {
       /* if (contactUsFragment == null)
            contactUsFragment = ContactUsFragment.newInstance();
        fragmentLoader(contactUsFragment, getString(R.string.contactUs));*/
    }

    public void onDrawerMenuClick(View view) {
    }
   /* public void controlBackButtonVisibility(boolean b) {
        if (b)
            findViewById(R.id.backButton).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.backButton).setVisibility(View.INVISIBLE);
    }*/
   public void onBack(View view)
   {
       if (homeFragment!=null && !homeFragment.isVisible())
       {
           homeFragment = HomeFragment.newInstance();
           //fragmentLoader(homeFragment, getString(R.string.home));
           fragmentLoader(homeFragment, "HomeFragment");
           setSelection(homeButtonLayout);
           controlBackButtonVisibility(true);
       }

       else if (myMatchesFragment != null && myMatchesFragment.isVisible())
       {
           //myMatchesFragment = MyMatchesFragment.newInstance();
           //fragmentLoader(homeFragment, getString(R.string.home));
           //fragmentLoader(homeFragment, "MyMatchesFragment");
           //setSelection(myMatchesButtonLayout);
           //controlBackButtonVisibility(false);
       }

       else if (searchFragment != null && searchFragment.isVisible())
       {
           //searchFragment = SearchFragment.newInstance();
           //fragmentLoader(homeFragment, getString(R.string.home));
           //fragmentLoader(searchFragment, "SearchFragment");
           //setSelection(searchButtonLayout);
           //controlBackButtonVisibility(false);

       }

       else if (myProfileFragment != null && !myProfileFragment.isVisible())
       {
           //myProfileFragment = MyProfileFragment.newInstance();
           //fragmentLoader(homeFragment, getString(R.string.home));
           //fragmentLoader(myProfileFragment, "MyProfileFragment");
           //setSelection(myProfileButtonLayout);
           //controlBackButtonVisibility(false);
       }

       else
           {
           if (System.currentTimeMillis() - l < 1000)
               finish();
           else
               {
               l = System.currentTimeMillis();
               H.showMessage(this, "Press again to exit");
           }
       }
   }


}

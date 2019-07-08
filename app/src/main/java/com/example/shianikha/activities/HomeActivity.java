package com.example.shianikha.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Session;
import com.example.shianikha.AboutApp;
import com.example.shianikha.ContactUsFragment;
import com.example.shianikha.HelpAndSupport;
import com.example.shianikha.NotificationFragment;
import com.example.shianikha.PartnerPreference;
import com.example.shianikha.R;
import com.example.shianikha.commen.P;
import com.example.shianikha.fragments.AccountSettingsFragment;
import com.example.shianikha.fragments.HomeFragment;
import com.example.shianikha.fragments.MyActivityFragment;
import com.example.shianikha.fragments.MyMatchesFragment;
import com.example.shianikha.fragments.MyProfileFragment;
import com.example.shianikha.fragments.SearchFragment;
import com.example.shianikha.subfragments.EditProfileFragment;

public class HomeActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private MyMatchesFragment myMatchesFragment;
    private SearchFragment searchFragment;
    private MyProfileFragment myProfileFragment;
    public EditProfileFragment editProfileFragment;
    private NotificationFragment notificationFragment;
    private AccountSettingsFragment accountSettingsFragment;
    private PartnerPreference partnerPreference;
    private HelpAndSupport helpAndSupport;
    private AboutApp aboutApp;

    private DrawerLayout.LayoutParams layoutParams;
    private RelativeLayout relativeLayout;
    private DrawerLayout drawerLayout;
    private ContactUsFragment contactUsFragment;
    private MyActivityFragment myActivityFragment;

    private RelativeLayout cardView_layout;
    public LinearLayout homeButtonLayout, myMatchesButtonLayout, searchButtonLayout, myProfileButtonLayout;

    public static Fragment currentFragment;
    public static String currentFragmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeFragment = HomeFragment.newInstance();
        currentFragment = homeFragment;
        currentFragmentName = getString(R.string.dashboard);
        fragmentLoader(homeFragment, "dashboard");

        TextView textView = findViewById(R.id.titleName);
        textView.setSelected(true);

        relativeLayout = findViewById(R.id.relativeLayout);
        drawerLayout = findViewById(R.id.drawerLayout);
        cardView_layout = findViewById(R.id.titleBar);

        layoutParams = (DrawerLayout.LayoutParams) relativeLayout.getLayoutParams();

        homeButtonLayout = findViewById(R.id.homeButton_layout);
        myMatchesButtonLayout = findViewById(R.id.mymatchesButton_layout);
        searchButtonLayout = findViewById(R.id.searchButton_layout);
        myProfileButtonLayout = findViewById(R.id.myprofileButton_layout);

        setMarginTopOfDrawer();
    }

    private void setMarginTopOfDrawer() {
        ScrollView scrollView = findViewById(R.id.scrollView);
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) scrollView.getLayoutParams();
        int i = new Session(this).getInt(P.statusBarHeight);
        layoutParams.topMargin = i;
        H.log("heightIs", i + "");
        scrollView.setLayoutParams(layoutParams);
    }

    public void makeStatusBarColorBlue(boolean bool) {
        View view = findViewById(R.id.view);
        if (bool) {
            int statusBarHeight = new Session(this).getInt(P.statusBarHeight);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.height = statusBarHeight;
            view.setLayoutParams(layoutParams);
            view.setBackgroundColor(getColor(R.color.dashboard_card_back_color));

            findViewById(R.id.titleBar).setBackgroundColor(getColor(R.color.dashboard_card_back_color));
            //findViewById(R.id.toolbar_layout).setBackground(getDrawable(R.drawable.aaaaaa));

            ((TextView) findViewById(R.id.titleName)).setTextColor(getColor(R.color.white));

            ((ImageView) findViewById(R.id.drawerMenu)).setColorFilter(getColor(R.color.white));
            ((ImageView) findViewById(R.id.imv_noti)).setColorFilter(getColor(R.color.white));

            return;
        }

        view.setBackgroundColor(getColor(R.color.white));
        findViewById(R.id.titleBar).setBackgroundColor(getColor(R.color.white));
        //findViewById(R.id.toolbar_layout).setBackground(getDrawable(R.drawable.aaaaaa));

        ((TextView) findViewById(R.id.titleName)).setTextColor(getColor(R.color.profile_card_back_color));

        ((ImageView) findViewById(R.id.drawerMenu)).setColorFilter(getColor(R.color.profile_card_back_color));
        ((ImageView) findViewById(R.id.imv_noti)).setColorFilter(getColor(R.color.profile_card_back_color));

    }

    public void fragmentLoader(Fragment fragment, String title)
    {
        if (fragment == homeFragment)
        {
            showBackButton(false);
            makeStatusBarColorBlue(true);////change color except dashboard
        }
        else if (fragment == myProfileFragment)
        {
            showBackButton(true);
            makeStatusBarColorBlue(true);
        }
        else
        {
            showBackButton(true);
            makeStatusBarColorBlue(false);
        }

        currentFragment = fragment;
        currentFragmentName = title;

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit)
                .replace(R.id.frameLayout, fragment).commit();

        ((TextView) findViewById(R.id.titleName)).setText(title);
    }

    public void bottomFourFragmentClick(View view) {
        if (view.getId() == R.id.homeButton_layout)
        {
            fragmentLoader(homeFragment, getString(R.string.MyShia));

        }
        else if (view.getId() == R.id.mymatchesButton_layout)
        {
            myMatchesFragment = MyMatchesFragment.newInstance(currentFragment, currentFragmentName);
            fragmentLoader(myMatchesFragment, getString(R.string.MyMatches));
        }
        else if (view.getId() == R.id.searchButton_layout)
        {
            searchFragment = SearchFragment.newInstance(currentFragment, currentFragmentName);
            fragmentLoader(searchFragment, getString(R.string.search));
        }
        else if (view.getId() == R.id.myprofileButton_layout)
        {
            myProfileFragment = MyProfileFragment.newInstance(currentFragment, currentFragmentName);
            fragmentLoader(myProfileFragment, getString(R.string.Myprofile));
        }

        setSelection(view);
    }

    public void showBackButton(boolean bool) {
        if (bool)
            ((ImageView) findViewById(R.id.drawerMenu)).setImageDrawable(getDrawable(R.drawable.ic_arrow_back_black_24dp));
        else
            ((ImageView) findViewById(R.id.drawerMenu)).setImageDrawable(getDrawable(R.drawable.ic_menu_black_24dp));
    }

    private void setSelection(View view) {
        LinearLayout parentLayout = findViewById(R.id.bottomLayout);
        LinearLayout childLayout;
        ImageView imageView;
        TextView textView;


        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            childLayout = ((LinearLayout) parentLayout.getChildAt(i));

            imageView = (ImageView) childLayout.getChildAt(0);
            imageView.setColorFilter(getColor(R.color.grey));
            //imageView.setLayoutParams(layoutParams);

            textView = (TextView) childLayout.getChildAt(1);
            textView.setTextColor(getColor(R.color.grey));
            textView.setTextSize(9f);
        }
        childLayout = (LinearLayout) view;

        imageView = (ImageView) childLayout.getChildAt(0);
        imageView.setColorFilter(getColor(R.color.bluebtnback));

        textView = (TextView) childLayout.getChildAt(1);
        textView.setTextColor(getColor(R.color.bluebtnback));
        textView.setTextSize(9.4f);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        onBack(new View(this));
    }

    public void OnDrawerMenuClick(View view) {
        if (view.getId() == R.id.contactUs) {
            if (contactUsFragment == null)
                contactUsFragment = ContactUsFragment.newInstance();
            fragmentLoader(contactUsFragment, getString(R.string.contactUs));
            drawerLayout.closeDrawer(Gravity.START);
            findViewById(R.id.drawerMenu).setTag(null);
        }

        else if (view.getId() == R.id.my_activity_drawer_layout) {
            if (myActivityFragment == null)
                myActivityFragment = MyActivityFragment.newInstance();
            fragmentLoader(myActivityFragment, getString(R.string.myactivity));
            drawerLayout.closeDrawer(Gravity.START);
            findViewById(R.id.drawerMenu).setTag(null);
        }
        else if (view.getId() == R.id.drawerNoti) {
            if (notificationFragment == null)
                notificationFragment = NotificationFragment.newInstance();
            fragmentLoader(notificationFragment, getString(R.string.notificationn));
            drawerLayout.closeDrawer(Gravity.START);
            findViewById(R.id.drawerMenu).setTag(null);

        }
        else if (view.getId() == R.id.drawer_account_settings_layout) {
            if (accountSettingsFragment == null)
                accountSettingsFragment = AccountSettingsFragment.newInstance();
            fragmentLoader(accountSettingsFragment, getString(R.string.accountsettings));
            drawerLayout.closeDrawer(Gravity.START);
            findViewById(R.id.drawerMenu).setTag(null);

        }

        else if (view.getId() == R.id.partnerPreference) {
            if (partnerPreference == null)
                partnerPreference = PartnerPreference.newInstance();
            fragmentLoader(partnerPreference, getString(R.string.partpref));
            drawerLayout.closeDrawer(Gravity.START);
            findViewById(R.id.drawerMenu).setTag(null);

        }
        else if (view.getId() == R.id.help_and_support_layout) {
            if (helpAndSupport == null)
                helpAndSupport = HelpAndSupport.newInstance();
            fragmentLoader(helpAndSupport, getString(R.string.helpandsupport));
            drawerLayout.closeDrawer(Gravity.START);
            findViewById(R.id.drawerMenu).setTag(null);

        }
        else if (view.getId() == R.id.drawerAboutApp) {
            if (aboutApp == null)
                aboutApp = AboutApp.newInstance();
            fragmentLoader(aboutApp, getString(R.string.about_app));
            drawerLayout.closeDrawer(Gravity.START);
            findViewById(R.id.drawerMenu).setTag(null);

        }
        else if (view.getId() == R.id.drawer_logout_layout) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setMessage("Do you really want to exit?");
            adb.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new Session(HomeActivity.this).addString(P.tokenData, "");
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    //((HomeActivity)context).finish();
                }
            });
            adb.setNegativeButton("no", null);
            adb.show();
            drawerLayout.closeDrawer(Gravity.START);

        }

    }

    public void onNotificationIconClick(View view) {

        showBackButton(true);
        RelativeLayout relativeLayout = (RelativeLayout) view;


        //findViewById(R.id.drawerMenu).setTag("HomeDrawer");
        findViewById(R.id.noti_layout).setTag(null);
    }

    public void onBack(View view)
    {
        Fragment fragment;
        String string;

        if (myMatchesFragment != null && myMatchesFragment.isVisible())
        {
            fragment = MyMatchesFragment.previousFragment;
            string = MyMatchesFragment.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
            }
        }
        else if (searchFragment != null && searchFragment.isVisible())
        {
            fragment = SearchFragment.previousFragment;
            string = SearchFragment.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
            }
        }
        else if (myProfileFragment != null && myProfileFragment.isVisible())
        {
            fragment = MyProfileFragment.previousFragment;
            string = MyProfileFragment.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
            }
        }
        else
            drawerLayout.openDrawer(Gravity.START);
    }

    private void decideBottomSelection(String string)
    {
        if (string.equals(getString(R.string.dashboard)))
            setSelection(homeButtonLayout);
    }

}

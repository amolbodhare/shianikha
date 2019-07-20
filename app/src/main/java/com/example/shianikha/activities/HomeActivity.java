package com.example.shianikha.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
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
import com.example.shianikha.NotifacationDetails;
import com.example.shianikha.NotificationFragment;
import com.example.shianikha.R;
import com.example.shianikha.commen.P;
import com.example.shianikha.fragments.AccountSettingsFragment;
import com.example.shianikha.fragments.FavouritesFragment;
import com.example.shianikha.fragments.HomeFragment;
import com.example.shianikha.fragments.MyActivityFragment;
import com.example.shianikha.fragments.MyMatchesFragment;
import com.example.shianikha.fragments.MyProfileFragment;
import com.example.shianikha.fragments.ProfileDetailsFragments;
import com.example.shianikha.fragments.SearchFragment;

public class HomeActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private MyMatchesFragment myMatchesFragment;
    private SearchFragment searchFragment;
    private MyProfileFragment myProfileFragment;
    private NotificationFragment notificationFragment;
    public NotifacationDetails notifacationDetails;
    private AccountSettingsFragment accountSettingsFragment;
    private HelpAndSupport helpAndSupport;
    private AboutApp aboutApp;
    private FavouritesFragment favouritesFragment;
    public ProfileDetailsFragments profileDetailsFragments;

    private DrawerLayout drawerLayout;
    private ContactUsFragment contactUsFragment;
    private MyActivityFragment myActivityFragment;

    public LinearLayout homeButtonLayout, myMatchesButtonLayout, searchButtonLayout, myProfileButtonLayout;

    public static Fragment currentFragment;
    public static String currentFragmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility( 0);
        getWindow().setStatusBarColor(getColor(R.color.dashboard_card_back_color));
        setContentView(R.layout.activity_home);

        homeFragment = HomeFragment.newInstance();
        fragmentLoader(homeFragment, "dashboard");

        TextView textView = findViewById(R.id.titleName);
        textView.setSelected(true);

        drawerLayout = findViewById(R.id.drawerLayout);

        homeButtonLayout = findViewById(R.id.homeButton_layout);
        myMatchesButtonLayout = findViewById(R.id.mymatchesButton_layout);
        searchButtonLayout = findViewById(R.id.searchButton_layout);
        myProfileButtonLayout = findViewById(R.id.myprofileButton_layout);
    }

    public void makeStatusBarColorBlue(boolean bool)
    {
        if (bool) {
            int statusBarHeight = new Session(this).getInt(P.statusBarHeight);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.height = statusBarHeight;

            getWindow().getDecorView().setSystemUiVisibility( 0);
            getWindow().setStatusBarColor(getColor(R.color.dashboard_card_back_color));

            findViewById(R.id.titleBar).setBackgroundColor(getColor(R.color.dashboard_card_back_color));
            //findViewById(R.id.toolbar_layout).setBackground(getDrawable(R.drawable.aaaaaa));

            ((TextView) findViewById(R.id.titleName)).setTextColor(getColor(R.color.white));

            ((ImageView) findViewById(R.id.drawerMenu)).setColorFilter(getColor(R.color.white));
            ((ImageView) findViewById(R.id.imv_noti)).setColorFilter(getColor(R.color.white));

            return;
        }

        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));

        findViewById(R.id.titleBar).setBackgroundColor(getColor(R.color.white));
        //findViewById(R.id.toolbar_layout).setBackground(getDrawable(R.drawable.aaaaaa));

        ((TextView) findViewById(R.id.titleName)).setTextColor(getColor(R.color.titleBarColor));

        ((ImageView) findViewById(R.id.drawerMenu)).setColorFilter(getColor(R.color.backArrowColor));
        ((ImageView) findViewById(R.id.imv_noti)).setColorFilter(getColor(R.color.notificationIcnColor));

    }

    public void fragmentLoader(Fragment fragment, String title) {
        if (currentFragment != null && currentFragment.getClass().toString().equalsIgnoreCase(fragment.getClass().toString()))
            return;

        if (fragment == homeFragment) {
            showBackButton(false);
            makeStatusBarColorBlue(true);////change color except dashboard
        } else if (fragment == myProfileFragment) {
            showBackButton(true);
            makeStatusBarColorBlue(true);
        } else {
            showBackButton(true);
            makeStatusBarColorBlue(false);
        }

        if (fragment == myProfileFragment)
            changeNotificationicon(true);

        currentFragment = fragment;
        currentFragmentName = title;
        try {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit)
                    .replace(R.id.frameLayout, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((TextView) findViewById(R.id.titleName)).setText(title);
    }

    public void bottomFourFragmentClick(View view) {
        if (view.getId() == R.id.homeButton_layout) {
            fragmentLoader(homeFragment, getString(R.string.MyShia));
            changeNotificationicon(false);
        } else if (view.getId() == R.id.mymatchesButton_layout) {
            if (myMatchesFragment == null)
                myMatchesFragment = MyMatchesFragment.newInstance(currentFragment, currentFragmentName);
            fragmentLoader(myMatchesFragment, getString(R.string.MyMatches));
            changeNotificationicon(false);

        } else if (view.getId() == R.id.searchButton_layout) {
            if (searchFragment == null)
                searchFragment = SearchFragment.newInstance(currentFragment, currentFragmentName);
            fragmentLoader(searchFragment, getString(R.string.search));
            changeNotificationicon(false);
        } else if (view.getId() == R.id.myprofileButton_layout) {
            if (myProfileFragment == null)
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
        onBack(null);
    }

    public void OnDrawerMenuClick(View view) {
        if (view.getId() == R.id.drawer_logout_layout)
            showLogOutAlert();
        else if (view.getId() == R.id.drawer_partnerPreference_layout)
            showPartnerPreferenceActivity();
        else if (view.getId() == R.id.drawer_subscription_layout)
            showSubscriptionPlanActivity();
        else if (view.getId() == R.id.my_activity_drawer_layout)
            showMyActivityFragment();
        else if (view.getId() == R.id.drawer_noti_layout)
            showNotificationFragment();
        else if (view.getId() == R.id.drawer_help_and_support_layout)
            showHelpAndSupportFrgment();
        else if (view.getId() == R.id.drawer_aboutapp_layout)
            showAboutAppFragment();
        else if (view.getId() == R.id.drawer_contactus_layout)
            showContactUsFragment();
        else if (view.getId() == R.id.drawer_account_settings_layout)
            showAccountSettingFragment();
        else if (view.getId() == R.id.inbox_activity_drawer_layout) {
            Intent i = new Intent(HomeActivity.this, InboxActivity.class);
            startActivity(i);
            ((HomeActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        } else if (view.getId() == R.id.drawer_favourites_layout)
        {
            if (favouritesFragment == null)
                favouritesFragment = FavouritesFragment.newInstance(currentFragment, currentFragmentName);
            fragmentLoader(favouritesFragment, "favourite");

        }

        drawerLayout.closeDrawer(Gravity.START);
    }

    public void showAccountSettingFragment() {
        if (accountSettingsFragment == null)
            accountSettingsFragment = AccountSettingsFragment.newInstance(currentFragment, currentFragmentName);
        fragmentLoader(accountSettingsFragment, getString(R.string.accountsettings));
    }

    private void showLogOutAlert() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setMessage("Do you really want to exit?");
        adb.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Session(HomeActivity.this).addString(P.tokenData, "");
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                ((HomeActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                finish();
                //((HomeActivity)context).finish();
            }
        });
        adb.setNegativeButton("no", null);
        adb.show();
    }

    public void showContactUsFragment() {
        if (contactUsFragment == null)
            contactUsFragment = ContactUsFragment.newInstance(currentFragment, currentFragmentName);
        fragmentLoader(contactUsFragment, getString(R.string.contactUs));
    }

    public void showAboutAppFragment() {
        if (aboutApp == null)
            aboutApp = AboutApp.newInstance(currentFragment, currentFragmentName);
        fragmentLoader(aboutApp, getString(R.string.aboutapp));
    }

    public void showHelpAndSupportFrgment() {
        if (helpAndSupport == null)
            helpAndSupport = HelpAndSupport.newInstance(currentFragment, currentFragmentName);
        fragmentLoader(helpAndSupport, getString(R.string.helpandsupport));
    }

    public void showNotificationFragment() {
        if (notificationFragment == null)
            notificationFragment = NotificationFragment.newInstance(currentFragment, currentFragmentName);
        fragmentLoader(notificationFragment, getString(R.string.notificationn));
    }

    public void showMyActivityFragment() {
        if (myActivityFragment == null)
            myActivityFragment = MyActivityFragment.newInstance(currentFragment, currentFragmentName);
        fragmentLoader(myActivityFragment, getString(R.string.myactivity));
    }

    public void showSubscriptionPlanActivity() {
        Intent i = new Intent(HomeActivity.this, SubscriptionActivity.class);
        startActivity(i);
        ((HomeActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }

    public void showPartnerPreferenceActivity() {
        Intent i = new Intent(HomeActivity.this, PartnerPreferenceActivity.class);
        startActivity(i);
        ((HomeActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }

    public void onNotificationIconClick(View view) {
        H.log("Inside method", "This is method");
        ImageView imageView = view.findViewById(R.id.imv_noti);

        Object object = imageView.getTag();
        if (object == null)
            return;
        String string = object.toString();

        H.log("ObjectIs", object + "");
        if (string.equalsIgnoreCase("n")) {
            if (notificationFragment == null)
                notificationFragment = NotificationFragment.newInstance(currentFragment, currentFragmentName);
            fragmentLoader(notificationFragment, getString(R.string.notificationn));
        } else if (string.equalsIgnoreCase("e")) {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivityForResult(intent, 31);
        }
    }

    public void onBack(View view) {
        Fragment fragment;
        String string;

        if (myMatchesFragment != null && myMatchesFragment.isVisible()) {
            fragment = MyMatchesFragment.previousFragment;
            string = MyMatchesFragment.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
            }
        } else if (searchFragment != null && searchFragment.isVisible()) {
            fragment = SearchFragment.previousFragment;
            string = SearchFragment.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
            }
        } else if (myProfileFragment != null && myProfileFragment.isVisible()) {
            fragment = MyProfileFragment.previousFragment;
            string = MyProfileFragment.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
                changeNotificationicon(false);
            }
        } else if (notificationFragment != null && notificationFragment.isVisible()) {
            fragment = NotificationFragment.previousFragment;
            string = NotificationFragment.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
            }
        } else if (profileDetailsFragments != null && profileDetailsFragments.isVisible()) {
            fragment = ProfileDetailsFragments.previousFragment;
            string = ProfileDetailsFragments.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
            }
        } else if (notifacationDetails != null && notifacationDetails.isVisible()) {
            fragment = NotifacationDetails.previousFragment;
            string = NotifacationDetails.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
            }
        } else if (helpAndSupport != null && helpAndSupport.isVisible()) {
            fragment = HelpAndSupport.previousFragment;
            string = HelpAndSupport.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
            }
        } else if (aboutApp != null && aboutApp.isVisible()) {
            fragment = AboutApp.previousFragment;
            string = AboutApp.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
            }
        } else if (accountSettingsFragment != null && accountSettingsFragment.isVisible()) {
            fragment = AccountSettingsFragment.previousFragment;
            string = AccountSettingsFragment.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
            }
        } else if (contactUsFragment != null && contactUsFragment.isVisible()) {
            fragment = ContactUsFragment.previousFragment;
            string = ContactUsFragment.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
            }
        } else if (myActivityFragment != null && myActivityFragment.isVisible()) {
            fragment = MyActivityFragment.previousFragment;
            string = MyActivityFragment.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
            }
        }
        else if (favouritesFragment != null && favouritesFragment.isVisible()) {
            fragment = FavouritesFragment.previousFragment;
            string = FavouritesFragment.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
            }
        }else if (view == null) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            finish();
        } else if (view != null)
            drawerLayout.openDrawer(Gravity.START);

    }

    private void decideBottomSelection(String string) {
        if (string.equals(getString(R.string.dashboard)))
            setSelection(homeButtonLayout);
        else if (string.equals(getString(R.string.MyMatches)))
            setSelection(myMatchesButtonLayout);
        else if (string.equals(getString(R.string.search)))
            setSelection(searchButtonLayout);
        else if (string.equals(getString(R.string.Myprofile)))
            setSelection(myProfileButtonLayout);
    }

    public void changeNotificationicon(boolean b) {
        ImageView imageView = findViewById(R.id.imv_noti);
        if (b) {
            imageView.setImageDrawable(getDrawable(R.drawable.edit));
            imageView.setTag("E");
            findViewById(R.id.noti_text).setVisibility(View.INVISIBLE);
        } else {
            imageView.setImageDrawable(getDrawable(R.drawable.notification));
            findViewById(R.id.noti_text).setVisibility(View.VISIBLE);
            imageView.setTag("N");
        }
    }

    public void OnDrawerMyProfileClick(View v) {
        bottomFourFragmentClick(findViewById(R.id.myprofileButton_layout));
        drawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 31 && resultCode == RESULT_OK) {
            //if (myProfileFragment!=null )
            H.log("resultOk", "isExecuted");
        }
    }
}

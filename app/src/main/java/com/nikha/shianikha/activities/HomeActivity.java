package com.nikha.shianikha.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.Session;
import com.nikha.App;
import com.nikha.shianikha.AboutApp;
import com.nikha.shianikha.BuildConfig;
import com.nikha.shianikha.ContactUsFragment;
import com.nikha.shianikha.HelpAndSupport;
import com.nikha.shianikha.NotifacationDetails;
import com.nikha.shianikha.NotificationFragment;
import com.nikha.shianikha.R;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;
import com.nikha.shianikha.fragments.AccountSettingsFragment;
import com.nikha.shianikha.fragments.FavouritesFragment;
import com.nikha.shianikha.fragments.HomeFragment;
import com.nikha.shianikha.fragments.MyActivityFragment;
import com.nikha.shianikha.fragments.MyMatchesFragment;
import com.nikha.shianikha.fragments.MyProfileFragment;
import com.nikha.shianikha.fragments.ProfileDetailsFragments;
import com.nikha.shianikha.fragments.SearchFragment;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    public HomeFragment homeFragment;
    private MyMatchesFragment myMatchesFragment;
    private SearchFragment searchFragment;
    private MyProfileFragment myProfileFragment;
    private NotificationFragment notificationFragment;
    public NotifacationDetails notifacationDetails;
    private AccountSettingsFragment accountSettingsFragment;
    private HelpAndSupport helpAndSupport;
    private AboutApp aboutApp;
    public FavouritesFragment favouritesFragment;
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
        getWindow().getDecorView().setSystemUiVisibility(0);
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

        setVersionCodeToDrawer();

        // when user comes from shared link
        /*String string = getIntent().getStringExtra(P.profile_id);
        if (string != null && !string.isEmpty()) {
            profileDetailsFragments = ProfileDetailsFragments.newInstance(currentFragment, currentFragmentName, string);
            fragmentLoader(profileDetailsFragments, getString(R.string.profileDetails));
        }*/

        hitLastLogInDetails();
    }

    private void hitLastLogInDetails() {
        Json json = new Json();
        json.addString(P.token_id, new Session(this).getString(P.tokenData));
        json.addString(P.fcm_token, App.fcmToken);

        RequestModel requestModel = RequestModel.newRequestModel("last_login_details");
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        /*if (isLoading)s
                            loadingDialog.show();
                        else
                            loadingDialog.dismiss();*/
                    }
                })
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(HomeActivity.this, "Something went Wrong");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                    }
                })
                .run("hitLastLogInDetails");
    }

    private void setVersionCodeToDrawer() {
        TextView textView = findViewById(R.id.versionName);

        String string = App.IS_DEV ? "DEV - " : "V - ";
        textView.setText(string);

        string = BuildConfig.VERSION_NAME;
        textView.append(string);
    }

    public void makeStatusBarColorBlue(boolean bool) {
        if (bool) {
            int statusBarHeight = new Session(this).getInt(P.statusBarHeight);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.height = statusBarHeight;

            getWindow().getDecorView().setSystemUiVisibility(0);
            getWindow().setStatusBarColor(getColor(R.color.dashboard_card_back_color));

            findViewById(R.id.titleBar).setBackgroundColor(getColor(R.color.dashboard_card_back_color));
            //findViewById(R.id.toolbar_layout).setBackground(getDrawable(R.drawable.aaaaaa));

            ((TextView) findViewById(R.id.titleName)).setTextColor(getColor(R.color.white));

            ((ImageView) findViewById(R.id.drawerMenu)).setColorFilter(getColor(R.color.white));
            ((ImageView) findViewById(R.id.imv_noti)).setColorFilter(getColor(R.color.white));

            return;
        }

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));

        findViewById(R.id.titleBar).setBackgroundColor(getColor(R.color.white));
        //findViewById(R.id.toolbar_layout).setBackground(getDrawable(R.drawable.aaaaaa));

        ((TextView) findViewById(R.id.titleName)).setTextColor(getColor(R.color.titleBarColor));

        ((ImageView) findViewById(R.id.drawerMenu)).setColorFilter(getColor(R.color.backArrowColor));
        ((ImageView) findViewById(R.id.imv_noti)).setColorFilter(getColor(R.color.notificationIcnColor));

    }

    public void fragmentLoader(Fragment fragment, String title) {
        if (currentFragment != null && currentFragment.getClass().toString().equalsIgnoreCase(fragment.getClass().toString())) {
            if (fragment != homeFragment)// this condition is to avoid blank screen of home activity after logging out and logging in
                return;
        }
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
            changeNotificationIcon(true);

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
        if (tempView == view)
            return;
        tempView = view;

        if (view.getId() == R.id.homeButton_layout) {
            fragmentLoader(homeFragment, getString(R.string.dashboard));
            changeNotificationIcon(false);
        } else if (view.getId() == R.id.mymatchesButton_layout) {
            myMatchesFragment = MyMatchesFragment.newInstance(currentFragment, currentFragmentName);
            fragmentLoader(myMatchesFragment, getString(R.string.MyMatches));
            changeNotificationIcon(false);

        } else if (view.getId() == R.id.searchButton_layout) {
            searchFragment = SearchFragment.newInstance(currentFragment, currentFragmentName);
            fragmentLoader(searchFragment, getString(R.string.search));
            changeNotificationIcon(false);
        } else if (view.getId() == R.id.myprofileButton_layout) {
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

    private long l;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        drawerLayout.closeDrawer(Gravity.START);
        onBack(null);
    }

    private View tempView;

    public void onDrawerMenuClick(View view) {
        if (tempView == view)
            return;
        tempView = view;

        changeIconColor(view);

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
            Intent i = new Intent(HomeActivity.this, MessageActivity.class);
            i.putExtra("open", P.inbox);
            startActivity(i);
            ((HomeActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        } else if (view.getId() == R.id.drawer_favourites_layout) {
            favouritesFragment = FavouritesFragment.newInstance(currentFragment, currentFragmentName);
            fragmentLoader(favouritesFragment, "favourite");
        }

        drawerLayout.closeDrawer(Gravity.START);
        tempView = null;
    }

    private void changeIconColor(View view) {
        LinearLayout parentLayout = (LinearLayout) view.getParent();
        LinearLayout childLayout;
        ImageView imageView;

        for (int i = 1; i < parentLayout.getChildCount(); i++) {
            childLayout = (LinearLayout) parentLayout.getChildAt(i);
            imageView = (ImageView) childLayout.getChildAt(0);
            imageView.setColorFilter(getColor(R.color.drawerIconColor));
        }

        childLayout = (LinearLayout) view;
        imageView = (ImageView) childLayout.getChildAt(0);
        imageView.setColorFilter(getColor(R.color.goldColor));
    }

    public void showAccountSettingFragment() {
        accountSettingsFragment = AccountSettingsFragment.newInstance(currentFragment, currentFragmentName);
        fragmentLoader(accountSettingsFragment, getString(R.string.accountsettings));
    }

    private void showLogOutAlert() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setMessage("Do you really want to exit?");
        adb.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Session session = new Session(HomeActivity.this);
                session.addString(P.tokenData, "");
                App.i = 0;
                //session.addInt(P.showName, 1);

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
        contactUsFragment = ContactUsFragment.newInstance(currentFragment, currentFragmentName);
        fragmentLoader(contactUsFragment, getString(R.string.contactUs));
        //onDrawerMenuClick(findViewById(R.id.drawer_contactus_layout));
    }

    public void showAboutAppFragment() {
        aboutApp = AboutApp.newInstance(currentFragment, currentFragmentName);
        fragmentLoader(aboutApp, getString(R.string.aboutapp));
        //onDrawerMenuClick(findViewById(R.id.drawer_aboutapp_layout));
    }

    public void showHelpAndSupportFrgment() {
        helpAndSupport = HelpAndSupport.newInstance(currentFragment, currentFragmentName);
        fragmentLoader(helpAndSupport, getString(R.string.helpandsupport));
        //onDrawerMenuClick(findViewById(R.id.drawer_help_and_support_layout));
    }

    public void showNotificationFragment() {
        notificationFragment = NotificationFragment.newInstance(currentFragment, currentFragmentName);
        fragmentLoader(notificationFragment, getString(R.string.notificationn));
        //onDrawerMenuClick(findViewById(R.id.drawer_noti_layout));
    }

    public void showMyActivityFragment() {
        myActivityFragment = MyActivityFragment.newInstance(currentFragment, currentFragmentName);
        fragmentLoader(myActivityFragment, getString(R.string.myactivity));
        //onDrawerMenuClick(findViewById(R.id.my_activity_drawer_layout));
    }

    public void showSubscriptionPlanActivity() {
        Intent i = new Intent(HomeActivity.this, SubscriptionActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        ((HomeActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        //onDrawerMenuClick(findViewById(R.id.drawer_subscription_layout));
    }

    public void showPartnerPreferenceActivity() {
        Intent i = new Intent(HomeActivity.this, PerfectMatchActivity.class);
        startActivity(i);
        ((HomeActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        //onDrawerMenuClick(findViewById(R.id.drawer_partnerPreference_layout));
    }

    public void onNotificationIconClick(View view) {
        if (tempView == view)
            return;

        H.log("Inside method", "This is method");
        ImageView imageView = view.findViewById(R.id.imv_noti);

        Object object = imageView.getTag();
        if (object == null)
            return;
        String string = object.toString();

        H.log("ObjectIs", object + "");
        if (string.equalsIgnoreCase("n")) {
            notificationFragment = NotificationFragment.newInstance(currentFragment, currentFragmentName);
            fragmentLoader(notificationFragment, getString(R.string.notificationn));
            tempView = view;
        } else if (string.equalsIgnoreCase("e")) {
            Intent intent = new Intent(this, EditProfileActivity.class);
            intent.putExtra("profile_details_string", MyProfileFragment.profile_details_string);
            startActivityForResult(intent, 31);
        }
        //onDrawerMenuClick(findViewById(R.id.drawer_noti_layout));
    }

    public void onBack(View view) {
        tempView = view;
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
                changeNotificationIcon(false);
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
        } else if (favouritesFragment != null && favouritesFragment.isVisible()) {
            fragment = FavouritesFragment.previousFragment;
            string = FavouritesFragment.previousFragmentName;
            if (fragment != null && string != null) {
                fragmentLoader(fragment, string);
                decideBottomSelection(string);
            }
        } else if (!homeFragment.isVisible()) {
            fragmentLoader(homeFragment, "dashboard");
            setSelection(homeButtonLayout);
        } else if (view == null) {
            handleExit();
        } else
            drawerLayout.openDrawer(Gravity.START);
    }

    private void handleExit() {
        if (homeFragment.isVisible()) {
            if (System.currentTimeMillis() - l < 700) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
            } else {
                H.showMessage(this, "Press again to exit.");
                l = System.currentTimeMillis();
            }
        }
    }

    private void decideBottomSelection(String string) {
        //H.log("decisionIs","taken");
        if (string.equalsIgnoreCase(getString(R.string.dashboard)))
            setSelection(homeButtonLayout);
        else if (string.equalsIgnoreCase(getString(R.string.MyMatches)))
            setSelection(myMatchesButtonLayout);
        else if (string.equalsIgnoreCase(getString(R.string.search)))
            setSelection(searchButtonLayout);
        else if (string.equalsIgnoreCase(getString(R.string.Myprofile)))
            setSelection(myProfileButtonLayout);
        else
            setSelection(homeButtonLayout);
    }

    public void changeNotificationIcon(boolean b) {
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

    public void hideOrUpdateNotificationCount(boolean hide, int count) {
        if (hide || count == 0)
            findViewById(R.id.noti_text).setVisibility(View.INVISIBLE);
        else
            ((TextView) findViewById(R.id.noti_text)).setText(count + "");
    }

    public void OnDrawerMyProfileClick(View v) {
        bottomFourFragmentClick(findViewById(R.id.myprofileButton_layout));
        drawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        H.log("onActivityResult", "isExecuted");

        if (requestCode == 31 && resultCode == RESULT_OK) {
            //if (myProfileFragment!=null )
            H.log("resultOk", "isExecuted");
            if (myProfileFragment != null)
                myProfileFragment.hitMyProfileApi();

            if (homeFragment != null)
                homeFragment.hitDashboardApi();
        } else if (requestCode == 47 && resultCode == RESULT_OK) {
            H.log("result47", "isExecuted");
            if (myMatchesFragment != null && myMatchesFragment.isVisible())
                myMatchesFragment.hitApiForRefineSearchRequest();
            else if (favouritesFragment != null && favouritesFragment.isVisible())
                favouritesFragment.hitApiForRefineSearchRequest();
        }
    }

    public void startRefineSearchActivity() {
        Intent intent = new Intent(this, FilterActivity.class);
        startActivityForResult(intent, 47);
        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }

    public void setDrawerData(Json json) {
        json = json.getJson(P.profile_details);

        String string = json.getString(P.full_name);
        ((TextView) findViewById(R.id.name)).setText(string);

        string = json.getString(P.profile_id);
        ((TextView) findViewById(R.id.profileId)).setText(string);

        string = json.getString(P.profile_pic);
        try {
            Picasso.get().load(string).fit().placeholder(R.drawable.user).into(((ImageView) findViewById(R.id.circleImageView)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateNotificationCount(int i) {
        TextView textView = findViewById(R.id.noti_text);
        if (i == 0)
            textView.setVisibility(View.INVISIBLE);
        else
            textView.setText(i + "");
    }
    @Override
    protected void onResume() {
        super.onResume();
        App.mPlayer.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        App.mPlayer.pause();
    }
}

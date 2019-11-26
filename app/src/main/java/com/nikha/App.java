package com.nikha;

import android.app.Application;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v4.app.Fragment;

import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.Static;
import com.nikha.shianikha.R;

public class App extends Application
{
    public static String device_id = "";
    public static Json masterJson = new Json();
    public static Fragment tempFragment;
    public static boolean IS_DEV = false;
    public static boolean showName = false;

    public static String fcmToken = "";

    public static Json json = new Json();
    public static MediaPlayer mPlayer;

    @Override
    public void onCreate() {
        super.onCreate();

        device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.ali_ke_sath);
        mPlayer.start();
        mPlayer.setLooping(true);
        //device_id = Settings.Secure.ANDROID_ID;

        Static.show_log = false;

    }//init



}

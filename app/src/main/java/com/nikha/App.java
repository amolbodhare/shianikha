package com.nikha;

import android.app.Application;
import android.provider.Settings;
import android.support.v4.app.Fragment;

import com.adoisstudio.helper.Json;

public class App extends Application
{
    public static String device_id = "";
    public static Json masterJson = new Json();
    public static Fragment tempFragment;

    @Override
    public void onCreate() {
        super.onCreate();

        device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        //device_id = Settings.Secure.ANDROID_ID;

    }//init
}

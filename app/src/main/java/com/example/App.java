package com.example;

import android.app.Application;
import android.provider.Settings;

import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.Static;

public class App extends Application
{
    public static String device_id = "";
    public static Json masterJson = new Json();

    @Override
    public void onCreate() {
        super.onCreate();

        device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        //device_id = Settings.Secure.ANDROID_ID;

        Static.show_log = true;
    }//init
}

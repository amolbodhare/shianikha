package com.nikha;

import android.app.Application;
import android.provider.Settings;
import android.support.v4.app.Fragment;

import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.Static;

public class App extends Application {
    public static String device_id = "";
    public static Json masterJson = new Json();
    public static Fragment tempFragment;
    public static boolean IS_DEV = false;
    public static boolean showName = false;

    @Override
    public void onCreate() {
        super.onCreate();

        device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        //device_id = Settings.Secure.ANDROID_ID;

        Static.show_log = true;

    }//init
}

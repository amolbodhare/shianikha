package com.nikha;

import android.app.Application;
import android.provider.Settings;
import android.support.v4.app.Fragment;

import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.Static;
import com.nikha.shianikha.commen.P;

public class App extends Application {
    public static String device_id = "";
    public static Json masterJson = new Json();
    public static Fragment tempFragment;
    public static boolean IS_DEV = false;

    @Override
    public void onCreate() {
        super.onCreate();

        device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        //device_id = Settings.Secure.ANDROID_ID;

        if (IS_DEV) {
            Static.show_log = true;
            P.baseUrl = "http://dev.digiinterface.com/2019/shia_nikah/web_services/webservices";
        }
        else
        {
            Static.show_log = true;
            P.baseUrl = "https://shianikah.in/index.php/web_services/Webservices";
        }

    }//init
}

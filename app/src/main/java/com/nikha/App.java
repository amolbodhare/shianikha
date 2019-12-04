package com.nikha;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.Static;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class App extends Application
{
    public static String device_id = "";
    public static Json masterJson = new Json();
    public static Fragment tempFragment;
    public static boolean IS_DEV = true;
    public static boolean showName = false;

    public static String fcmToken = "";

    public static Json json = new Json();

    public static int i;
    public static App app;
    public static String hashKey;

    public static String tempOTP;

    @Override
    public void onCreate() {
        super.onCreate();

        device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
      
        //device_id = Settings.Secure.ANDROID_ID;

        app = new App();

        Static.show_log = true;


    }//init

    public void extractHashKey(Context context) {
        AppSignatureHashHelper appSignatureHashHelper = new AppSignatureHashHelper(context);

        if (appSignatureHashHelper.getAppSignatures() != null && appSignatureHashHelper.getAppSignatures().size() > 0) {
            hashKey = appSignatureHashHelper.getAppSignatures().get(0);
            H.log("hashKeyIs", hashKey);
        }
    }

    public void startSmsListener(Context context)
    {
        MySMSBroadcastReceiver mySMSBroadcastReceiver = new MySMSBroadcastReceiver(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        context.registerReceiver(mySMSBroadcastReceiver,intentFilter);

        SmsRetrieverClient client = SmsRetriever.getClient(context);

        Task<Void> task = client.startSmsRetriever();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // API successfully started
                H.log(getClass().getSimpleName(), "isExecuted");
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Fail to start API
                e.printStackTrace();
                H.log(getClass().getSimpleName(), e.toString());
            }
        });
    }

}

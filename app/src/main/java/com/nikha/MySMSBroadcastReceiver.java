package com.nikha;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.adoisstudio.helper.H;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

public class MySMSBroadcastReceiver extends BroadcastReceiver {
    private Context context;

    public MySMSBroadcastReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();

            if (extras == null)
                return;

            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
            if (status == null)
                return;

            switch (status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:

                    H.log("CommonStatusCodes", "SUCCESS");

                    //This is the full message
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                    if (message != null) {
                        Log.e("smsIs", message);
                        if (this.context != null)
                            setText(message);
                    }

                    break;
                case CommonStatusCodes.TIMEOUT:
                    // Waiting for SMS timed out (5 minutes)

                    H.log("CommonStatusCodes", "TIMEOUT");

                    break;

                case CommonStatusCodes.API_NOT_CONNECTED:

                    H.log("CommonStatusCodes", "API_NOT_CONNECTED");

                    break;

                case CommonStatusCodes.NETWORK_ERROR:

                    H.log("CommonStatusCodes", "NETWORK_ERROR");

                    break;

                case CommonStatusCodes.ERROR:

                    H.log("CommonStatusCodes", "ERROR");

                    break;

            }
        }
    }

    private void setText(String message)
    {
        if (message.contains("."))
        {
            int i = message.indexOf(".");
            message = message.substring(i-6,i);
            H.log("otpIs",message);
            App.tempOTP = message.trim();
        }
    }
}

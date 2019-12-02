package com.nikha.shianikha;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.adoisstudio.helper.H;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;


public class MySMSBroadcastReceiver extends BroadcastReceiver {
    Context context;

    public MySMSBroadcastReceiver() {

    }

    public MySMSBroadcastReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
       /* if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();

            if (extras == null)
                return;

            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
            if (status == null)
                return;

            switch (status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:

                    H.log("switchSuccess", "isExecuted");

                    //This is the full message
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                    if (message != null) {
                        int i = message.indexOf("s");
                        message = message.substring(i + 2, i + 8);
                        App.generatedOtp = message;

                        if (this.context != null) {
                            EditText editText = ((OtpActivity) this.context).findViewById(R.id.edt_otp);
                            editText.setText(message);
                        }
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
        }*/
    }
}

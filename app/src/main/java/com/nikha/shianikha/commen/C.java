package com.nikha.shianikha.commen;


import android.graphics.Bitmap;

import com.adoisstudio.helper.Api;

import java.util.HashMap;
import java.util.Map;

public class C {

    public static final String INVALID_SESSION = "INVALID_SESSION";

    public static final String url = "https://famer.in/app/web_services/famerapi";

    /*public static void checkErrors(final Context context, Json json) {

        if (json.getInt(P.status) == 0 && json.getString(P.err_code).equalsIgnoreCase(INVALID_SESSION)) {
            MessageBox.showOkMessage(context, "Session Expired", "Please login again.",
                    new MessageBox.OnOkListener() {
                        @Override
                        public void onOk() {
                            Session.newInstance(context).clear();

                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                            ((Activity) context).finish();
                        }
                    });
        }//if

    }*///check errors


    /*public static boolean isInternetAvailable(Context context)
    {

        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            H.log("connected",""+connected);
        }
        else
            connected = false;
        H.log("connected",""+connected);


        return connected;
    }*/

    public static Bitmap resizeBitmap(Bitmap image, int maxWidth, int maxHeight) {

        if (maxHeight > 0 && maxWidth > 0) {

            int width = image.getWidth();
            int height = image.getHeight();

            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;

            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }

            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);

            return image;
        } else {
            return image;
        }
    }//resize Bitmap

    public static Api.OnHeaderRequestListener getHeaders() {

        return new Api.OnHeaderRequestListener() {
            @Override
            public Map<String, String> getHeaders() {

                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization","Basic YWRtaW46MTIzNEBhZG1pbg==");
                headers.put("x-api-key","123456");
                headers.put("Content-Type", "application/json");

                return headers;
            }
        };

    }

}//class
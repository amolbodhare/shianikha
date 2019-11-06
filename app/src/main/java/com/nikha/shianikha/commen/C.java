package com.nikha.shianikha.commen;


import android.graphics.Bitmap;

import com.adoisstudio.helper.Api;

import java.util.HashMap;
import java.util.Map;

public class C {

    public static final String INVALID_SESSION = "INVALID_SESSION";


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
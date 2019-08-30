package com.nikha;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.adoisstudio.helper.H;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nikha.shianikha.R;
import com.nikha.shianikha.activities.HomeActivity;

public class MyFCMService extends FirebaseMessagingService {

    private static final String TAG = "MyFCMService";

    public MyFCMService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());

            /*Map data = remoteMessage.getData();

            sendNotification((String) data.get(P.action),//                    (String) data.get(P.action_data),
                    (String) data.get(P.title),
                    (String) data.get(P.description));*/

        }

        sendNotification("", "", "");
    }

    //, String action_data
    private void sendNotification(String action, String title, String messageBody) {

        Drawable drawable = getDrawable(R.drawable.shia_nikah_icon);
        Bitmap bitmap;
        if (drawable == null)
            return;
        else
            bitmap = ((BitmapDrawable) drawable).getBitmap();


        Intent intent = new Intent(this, HomeActivity.class);
        //intent.putExtra(P.action, action);
        //intent.putExtra(P.action_data, action_data);
        H.log("logIs", action + "\t");// + action_data);


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.shia_nikah_icon)
                        //.setLargeIcon(bitmap)
                        .setColor(Color.argb(100, 183, 0, 0))
                        .setContentTitle(convertFromHtml(title))
                        .setContentText(convertFromHtml(messageBody))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
    }

    private Spanned convertFromHtml(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
        else
            return Html.fromHtml(text);
    }
}

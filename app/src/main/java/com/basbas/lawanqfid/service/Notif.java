package com.basbas.lawanqfid.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.basbas.lawanqfid.R;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Notif extends FirebaseMessageService {
    RemoteMessage messageRemote;
    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        Log.e("TAG","notif test "+remoteMessage.getNotification().getBody());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             messageRemote = remoteMessage;

            String notificationTitle = remoteMessage.getNotification().getTitle();
            String notificationContent = remoteMessage.getNotification().getBody();
            String imageUrl = remoteMessage.getNotification().getImageUrl().toString();
            Log.e("TAG","notif test "+imageUrl);
            // lets create a notification with these data
            createNotification(notificationTitle, notificationContent, imageUrl);
        }
    }

    private void createNotification(String notificationTitle, String notificationContent, String imageUrl) {
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(this, getResources().getString(R.string.notification_channel_id));
        // Create a notificationManager object
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        // If android version is greater than 8.0 then create notification channel
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // Create a notification channel
            NotificationChannel notificationChannel = new
                    NotificationChannel(getResources().getString(R.string.notification_channel_id),
                    getResources().getString(R.string.notification_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT);    // Set properties to notification channel
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300});
            // Pass the notificationChannel object to notificationManager
            notificationManager.createNotificationChannel(notificationChannel);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setContentTitle(notificationTitle.toString())
                    .setContentText(notificationContent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setSound(defaultSoundUri)
                    .setAutoCancel(true);

            if (imageUrl != null) {
                Bitmap bitmap = getBitmapfromUrl(imageUrl);
                builder.setStyle(
                        new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitmap)
                                .bigLargeIcon(null)
                ).setLargeIcon(bitmap);
            }

            notificationManager.notify(1, builder.build());

        }




}

    private Bitmap getBitmapfromUrl(String s) {
        try {
            URL url = new URL(s);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            Log.e("awesome", "Error in getting notification image: " + e.getLocalizedMessage());
            return null;
        }
    }
}


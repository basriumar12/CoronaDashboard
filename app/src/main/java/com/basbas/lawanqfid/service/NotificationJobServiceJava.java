package com.basbas.lawanqfid.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.basbas.lawanqfid.R;
import com.basbas.lawanqfid.utama.pref.UserSession;
import com.basbas.lawanqfid.utama.ui.home.HomeActivity;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NotificationJobServiceJava extends JobService {

    public static final int NOTIFICATION_ID = 18;
    public static final String CHANNEL_ID = "notify-smiley";
    private boolean jobCancelled = false;
    private NotificationManager mNotifyManager;


    @Override
    public boolean onStartJob(JobParameters params) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String nama;
            String namaFromPref = new UserSession(this).getUserName().toString();
            if (namaFromPref.equals(null)){
                nama = "";
            } else {
                nama = namaFromPref;

            }

            CharSequence channelName = "Ayo Bersama-sama "+nama + " Lawan Covid-19";
            String channelDescription = "Cuci Tangan, Jaga Jarak dan Jangan Lupa Pakai Masker";
            System.out.println("job terjadwal");
            doBackgroundWork(params);
            PendingIntent contentPendingIntent = PendingIntent.getActivity
                    (this, 0, new Intent(this, HomeActivity.class),
                            PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder
                    (this, CHANNEL_ID)
                    .setContentTitle(channelName)
                    .setContentText(channelDescription)
                  //  .setContentIntent(contentPendingIntent)
                    .setSmallIcon(R.drawable.ic_sembuh)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setAutoCancel(true);

            mNotifyManager.notify(0, builder.build());
        }
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        // Define notification manager object.
        mNotifyManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (CHANNEL_ID,
                            "Job Service notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifications from Job Service");

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        System.out.println("Job cancelled before completion");
        jobCancelled = true;
        return  true;
    }

}

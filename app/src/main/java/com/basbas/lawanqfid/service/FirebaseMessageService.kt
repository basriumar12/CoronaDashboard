package com.basbas.lawanqfid.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.basbas.lawanqfid.service.new.EndlessService
import com.basbas.lawanqfid.service.new.log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Created by Dimas Aprizawandi on 04/03/2020
 * Email : animatorist@gmail.com
 * Mobile App Developer
 */
@RequiresApi(api = Build.VERSION_CODES.M)
open class FirebaseMessageService : FirebaseMessagingService() {
    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.e("NEW_TOKEN", s)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.e("TAG", "notif " + remoteMessage.notification!!.title)
        val title = remoteMessage.notification!!.title
        val body = remoteMessage.notification!!.body
        val imageUrl = remoteMessage.notification!!.imageUrl.toString()
//        NotificationManagers(this).displayNotification(
//                title,
//                body,
//                remoteMessage.data["url"],
//                imageUrl
//        )
        if (remoteMessage.data.size > 0) {
            Log.e("TAG", "notif " + remoteMessage.notification!!.body)

        }

        Intent(this, EndlessService::class.java).also {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                log("Starting the service in >=26 Mode")
                startForegroundService(it)
                return
            }
            log("Starting the service in < 26 Mode")
            startService(it)
        }
        /*if (remoteMessage.getNotification() != null) {
            //Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            new MyNotificationManager(this).displayNotification("", "", remoteMessage.getData().get("url"));
        }*/
    }


    fun notif() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mNotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel =
                    NotificationChannel("Constant.CHANNEL_ID", "Constant.CHANNEL_NAME", importance)
            mChannel.description = "Constant.CHANNEL_DESCRIPTION"
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mNotificationManager.createNotificationChannel(mChannel)
        }
        //        JobScheduler jobScheduler = getSystemService(JobScheduler.class);
//
//        ComponentName componentName = new ComponentName(this, NotificationJobService.class);
//        JobInfo info = new JobInfo.Builder(123, componentName)
//                .setPersisted(true)
//                .setPeriodic(24 * 60 * 60 * 1000L)
//                .build();
//
//        int resultCode = jobScheduler.schedule(info);
//        if ( resultCode == JobScheduler.RESULT_SUCCESS) {
//            System.out.println("sudah terjadwal");
//        } else {
//            System.out.println("gagal terjadwal");
//        }
    }

    companion object {
        private const val TAG = "FCM Service"
    }
}
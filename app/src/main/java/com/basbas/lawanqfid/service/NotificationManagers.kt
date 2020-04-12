package com.basbas.lawanqfid.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.media.AudioAttributes
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.utama.ui.splash.SplashScreenActivity
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by Dimas Aprizawandi on 04/03/2020
 * Email : animatorist@gmail.com
 * Mobile App Developer
 */
class NotificationManagers(private val mCtx: Context) {
    fun displayNotification(
            title: String?,
            body: String?,
            url: String?,
            imageUrl : String?
    ) {


        val soundUri: Uri =
                Uri.parse("android.resource://" + mCtx.packageName + "/" + R.raw.sirene)

//        val contentView = RemoteViews(mCtx.getPackageName(), R.layout.custom_notifaction)
//        contentView.setImageViewResource(R.id.image,
//                R.drawable.ic_access_time)
//        contentView.setTextViewText(R.id.title, title)


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mNotificationManager =
                    mCtx.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            val importance = android.app.NotificationManager.IMPORTANCE_HIGH
            val mChannel =
                    NotificationChannel("Constant.CHANNEL_ID", "Constant.CHANNEL_NAME", importance)
            mChannel.description = "Constant.CHANNEL_DESCRIPTION"
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)


            val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
            mChannel.setSound(soundUri, audioAttributes)

            mNotificationManager.createNotificationChannel(mChannel)
        }
        val drawable = ContextCompat.getDrawable(mCtx, R.mipmap.odp)
        val bitmap = (drawable as BitmapDrawable?)?.bitmap
        val mBuilder =
                NotificationCompat.Builder(mCtx, "Constant.CHANNEL_ID")
                        .setSmallIcon(R.mipmap.odp)
                        .setLargeIcon(bitmap)
                        .setContentTitle(title)
                        .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                        .setSound(soundUri)
                        .setAutoCancel(true)
                        .setOngoing(true)
                        .setContentText(body)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //set image notif
        val imageUrl = getBitmapfromUrl(imageUrl.toString())
        if (imageUrl != null){
            mBuilder.setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(imageUrl)
                    .bigLargeIcon(null)).setLargeIcon(bitmap)
        }

        //intent splash
        val resultIntent = Intent(mCtx, SplashScreenActivity::class.java)
        resultIntent.putExtra("url", url)

        val pendingIntent = PendingIntent.getActivity(
                mCtx, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        mBuilder.setContentIntent(pendingIntent)
        val mNotifyMgr =
                mCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mNotifyMgr?.notify(1, mBuilder.build())
    }

    //get bitmapurl
    private fun getBitmapfromUrl(s: String): Bitmap? {
        return try {
            val url = URL(s)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            Log.e("awesome", "Error in getting notification image: " + e.localizedMessage)
            null
        }
    }

    companion object {
        private var mInstance: NotificationManagers? = null
        @Synchronized
        fun getInstance(context: Context): NotificationManagers? {
            if (mInstance == null) {
                mInstance = NotificationManagers(context)
            }
            return mInstance
        }
    }

}
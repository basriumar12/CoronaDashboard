package id.co.iconpln.airsale.service


import android.content.Context
import android.os.Build
import android.provider.Settings

fun isTimeAutomatic(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.AUTO_TIME,
                0
        ) == 1
    } else {
        TODO("VERSION.SDK_INT < JELLY_BEAN_MR1")
    };
}
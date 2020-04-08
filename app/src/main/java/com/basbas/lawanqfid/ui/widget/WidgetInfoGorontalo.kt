package com.basbas.lawanqfid.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.network.ApiInterface
import com.basbas.lawanqfid.network.ApiServiceFromSpreadsheet
import com.basbas.lawanqfid.ui.data_sebaran.DataGorontaloActivity
import com.basbas.lawanqfid.ui.fragment.home_fragment.model.ResponseDataFromSpreadSheet
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class WidgetInfoGorontalo : AppWidgetProvider() {
    var views: RemoteViews? = null
    var positif: String? = ""
    var meninggal: String? = ""
    var sembuh: String? = ""
    var odp: String? = ""
    var pdp: String? = ""
    fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                        appWidgetId: Int) {
        val widgetText: CharSequence = context.getString(R.string.appwidget_text)
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.view_widget_gorontalo)
        val sdf = SimpleDateFormat("dd MMM  yyyy   ", Locale.getDefault())
        val currentDateandTime = sdf.format(Date())
        views.setTextViewText(R.id.tv_date, "Di Update $currentDateandTime")
        // Create a very simple REST adapter which points the GitHub API endpoint.
        val client = ApiServiceFromSpreadsheet.createService(ApiInterface::class.java)
        // Fetch and print a list of the contributors to this library.
        val call = client.getDataFromSpreadsheet("1zPnkFowaQVUqdNQElbrP9SgDagWPZ7R26mQbXWukKj8", "Sheet1")
        call.enqueue(object : Callback<ResponseDataFromSpreadSheet> {
            override fun onResponse(call: Call<ResponseDataFromSpreadSheet>, response: Response<ResponseDataFromSpreadSheet>) {
                if (response.isSuccessful) {
                    positif = response.body().data?.get(1)?.positif
                    meninggal = response.body().data?.get(1)?.meninggal
                    sembuh = response.body().data?.get(1)?.sembuh
                    odp = response.body().data?.get(1)?.odp
                    pdp = response.body().data?.get(1)?.pdp
                    Log.e("TAG", "sukses get data di widget gtlo $positif")
                    Log.e("TAG", "sukses get data di widget gtlo $sembuh")
                    views.setTextViewText(R.id.tv_positif, positif!!.replace("A", ""))
                    if (response.body().data?.get(1)?.tambah_positif == "") {
                        views.setTextViewText(R.id.tv_tambah_positif, "")
                    } else {
                        views.setTextViewText(R.id.tv_tambah_positif, "+" + response.body().data!![1].tambah_positif!!.replace("A", ""))
                    }
                    views.setTextViewText(R.id.tv_sembuh, sembuh?.replace("A", ""))
                    views.setTextViewText(R.id.tv_meninggal, meninggal?.replace("A", ""))
                    views.setTextViewText(R.id.tv_odp_gorontalo, odp?.replace("A", ""))
                    views.setTextViewText(R.id.tv_pdp_gorontalo, pdp?.replace("A", ""))
                    // Instruct the widget manager to update the widget
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
            }

            override fun onFailure(call: Call<ResponseDataFromSpreadSheet>, t: Throwable) {
                Log.e("TAG", "errror get data di widget gtlo " + t.message)
            }
        })
        //  RemoteViews viewss = new RemoteViews(context.getPackageName(), R.layout.view_widget_gorontalo);
        val mIntent = Intent(context, DataGorontaloActivity::class.java)
        val bundle = Bundle()
        mIntent.putExtras(bundle)
        val pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        views.setOnClickPendingIntent(R.id.rootLayout, pendingIntent)

        val calendar: Calendar = Calendar.getInstance()
        val timeOfDay: Int = calendar.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay >= 0 && timeOfDay < 12) {
            views.setTextViewText(R.id.tv_tag, "Paladu u dumodupo #ToBeleLoUti")

        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            views.setTextViewText(R.id.tv_tag, "Paladu u mohuloonu #ToBeleLoUti")

        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            views.setTextViewText(R.id.tv_tag, "Paladu u lolaango #ToBeleLoUti")

        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            views.setTextViewText(R.id.tv_tag, "Paladu u hui #ToBeleLoUti")

        }

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) { // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val remoteViews = RemoteViews(context.packageName, R.layout.view_widget_gorontalo)
        val thisWidget = ComponentName(context, WidgetInfoGorontalo::class.java)
        appWidgetManager.updateAppWidget(thisWidget, remoteViews)
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) { // Enter relevant functionality for when the last widget is disabled
    }
}
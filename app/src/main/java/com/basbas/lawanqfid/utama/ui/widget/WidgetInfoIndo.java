//package com.basbas.lawanqfid.utama.ui.widget;
//
//import android.app.PendingIntent;
//import android.appwidget.AppWidgetManager;
//import android.appwidget.AppWidgetProvider;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.RemoteViews;
//
//
//import com.basbas.lawanqfid.utama.MainActivity;
//import com.basbas.lawanqfid.R;
//import com.basbas.lawanqfid.utama.network.ApiInterface;
//import com.basbas.lawanqfid.utama.network.ApiServiceFromSpreadsheet;
//import com.basbas.lawanqfid.utama.ui.fragment.home_fragment.model.ResponseDataFromSpreadSheet;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
///**
// * Implementation of App Widget functionality.
// */
//public class WidgetInfoIndo extends AppWidgetProvider {
//
//    RemoteViews views;
//
//    static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager,
//                                final int appWidgetId) {
//
//        CharSequence widgetText = context.getString(R.string.appwidget_text);
//        // Construct the RemoteViews object
//        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.view_widget_indo);
//        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM  yyyy   ", Locale.getDefault());
//        String currentDateandTime = sdf.format(new Date());
//        views.setTextViewText(R.id.tv_date, "Di Update "+currentDateandTime);
//
//
//        // Create a very simple REST adapter which points the GitHub API endpoint.
//        ApiInterface client = ApiServiceFromSpreadsheet.createService(ApiInterface.class);
//        // Fetch and print a list of the contributors to this library.
//        Call<ResponseDataFromSpreadSheet> call = client.getDataFromSpreadsheet("","");
//        call.enqueue(new Callback<ResponseDataFromSpreadSheet>() {
//            @Override
//            public void onResponse(Call<ResponseDataFromSpreadSheet> call, Response<ResponseDataFromSpreadSheet> response) {
//                views.setTextViewText(R.id.tv_positif, response.body().getData().get(0).getPositif());
//
//                if (response.body().getData().get(0).getTambah_positif().equals("")){
//                    views.setTextViewText(R.id.tv_tambah_positif,"");
//                } else{
//                    views.setTextViewText(R.id.tv_tambah_positif, "+"+response.body().getData().get(0).getTambah_positif());
//                }
//
//                views.setTextViewText(R.id.tv_sembuh, response.body().getData().get(0).getSembuh());
//                views.setTextViewText(R.id.tv_meninggal, response.body().getData().get(0).getMeninggal());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDataFromSpreadSheet> call, Throwable t) {
//
//            }
//        });
//
//
//
//        RemoteViews viewss = new RemoteViews(context.getPackageName(), R.layout.view_widget_indo);
//        Intent mIntent = new Intent(context, MainActivity.class);
//        Bundle bundle = new Bundle();
//        mIntent.putExtras(bundle);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        views.setOnClickPendingIntent(R.id.rootLayout, pendingIntent);
//        appWidgetManager.updateAppWidget(appWidgetId, viewss);
//    }
//
//
//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
//    }
//
//    @Override
//    public void onEnabled(Context context) {
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.view_widget_indo);
//        ComponentName thisWidget = new ComponentName(context, WidgetInfoIndo.class);
//        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
//
//        // Enter relevant functionality for when the first widget is created
//    }
//
//    @Override
//    public void onDisabled(Context context) {
//        // Enter relevant functionality for when the last widget is disabled
//    }
//}
//

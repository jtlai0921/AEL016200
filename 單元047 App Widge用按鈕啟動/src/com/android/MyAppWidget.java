package com.android;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class MyAppWidget extends AppWidgetProvider {

	private final String LOG_TAG = "my app widget";

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		Log.d(LOG_TAG, "onDeleted()");
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		Log.d(LOG_TAG, "onDisabled()");
	}

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		Log.d(LOG_TAG, "onEnabled()");

		Intent it = new Intent();
		it.setAction("com.android.MY_OWN_WIDGET_UPDATE");
		PendingIntent penIt = PendingIntent.getBroadcast(context, 0, it, 0);
		RemoteViews viewAppWidget = new RemoteViews(context.getPackageName(),
					R.layout.app_widget);
		viewAppWidget.setOnClickPendingIntent(R.id.btnUpdate, penIt);
		AppWidgetManager appWidgetMan = AppWidgetManager.getInstance(context);
		ComponentName appWidget = new ComponentName(context, MyAppWidget.class);
		appWidgetMan.updateAppWidget(appWidget, viewAppWidget);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);

		if(!intent.getAction().equals("com.android.MY_OWN_WIDGET_UPDATE"))
			return;

		Log.d(LOG_TAG, "onReceive()");

	    AppWidgetManager appWidgetMan = AppWidgetManager.getInstance(context);
	    ComponentName thisAppWidget = new ComponentName(context.getPackageName(), 
	    		MyAppWidget.class.getName());
	    int[] appWidgetIds = appWidgetMan.getAppWidgetIds(thisAppWidget);

	    onUpdate(context, appWidgetMan, appWidgetIds);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.d(LOG_TAG, "onUpdate()");
	}

}

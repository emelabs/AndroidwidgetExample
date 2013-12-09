package com.example.widget;

import java.util.Random;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {
	
	private static final String TAG = "MyWidgetProvider";
	
	
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.d(TAG, "[onUpdate] ");

		// Get all ids
		ComponentName thisWidget = new ComponentName(context, MyWidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds) {
			
			// Create some random data
			int number = (new Random().nextInt(100));

			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			Log.i(TAG, "[onUpdate] " + String.valueOf(number));
			
			// Set the text
			remoteViews.setTextViewText(R.id.update, String.valueOf(number));

			
			// Register an onClick intent
			Intent intent = new Intent(context, MainActivity.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
}

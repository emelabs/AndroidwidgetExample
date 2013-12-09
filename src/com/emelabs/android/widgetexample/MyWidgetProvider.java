package com.emelabs.android.widgetexample;

/* ========================================================================
 * 
 * Copyright (C) 2013 emelabs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================================
 */

import java.util.Random;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {
	
	private static final String TAG = "MyWidgetProvider";
	
	public static final String PREF_KEY_OFFSET = "key_offset";
	
	private int offset = 1;
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.d(TAG, "[onUpdate] ");

		loadOffsetFromPreferences(context);
		
		// Get all ids
		ComponentName thisWidget = new ComponentName(context, MyWidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds) {
			
			// Create some random data
			int number = (new Random().nextInt(100)) + offset;

			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			Log.i(TAG, "[onUpdate] offset:" + offset + " | value:" + String.valueOf(number));
			
			// Set the text
			remoteViews.setTextViewText(R.id.tvValue, String.valueOf(number));

			
			// Register an onClick intent
			Intent intent = new Intent(context, MainActivity.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			remoteViews.setOnClickPendingIntent(R.id.tvValue, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
	private void loadOffsetFromPreferences(Context context){
		try{
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			offset = prefs.getInt(PREF_KEY_OFFSET, offset);
		}catch(Exception e){
			Log.e(TAG, "[loadOffsetFromPreferences]", e);
		}
		Log.v(TAG, "[loadOffsetFromPreferences] offset:" + offset);
	}
}

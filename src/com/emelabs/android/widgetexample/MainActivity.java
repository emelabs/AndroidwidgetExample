package com.emelabs.android.widgetexample;


import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private int widgetId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "[onCreate] entering...");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try{
			widgetId = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
			Log.d(TAG, "[onCreate] widgetId: " + widgetId );
		}catch(Exception e){
			Log.e(TAG, "[onCreate]", e);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.v(TAG, "[onCreateOptionsMenu] entering...");
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	public void onSave(View view){
		Log.v(TAG, "[onSave] entering...");
		
		Intent intent = new Intent(this,MyWidgetProvider.class);
		intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");

		// Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
		// since it seems the onUpdate() is only fired on that:
		int[] ids = {widgetId};
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
		sendBroadcast(intent);
		
		finish();
	}
}

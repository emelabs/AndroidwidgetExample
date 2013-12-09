package com.emelabs.android.widgetexample;


import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private int widgetId;
	
	private EditText edOffset;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "[onCreate] entering...");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try{
			edOffset = (EditText) findViewById(R.id.editText1);
			
			widgetId = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
			Log.d(TAG, "[onCreate] widgetId: " + widgetId );

			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			int offset = prefs.getInt(MyWidgetProvider.PREF_KEY_OFFSET, 1);
			edOffset.setText(String.valueOf(offset));
			
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
		
		try{
			Log.v(TAG, "[onSave] offset:" + edOffset.getText().toString());
			
			Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
			editor.putInt(MyWidgetProvider.PREF_KEY_OFFSET, Integer.valueOf(edOffset.getText().toString()));
			editor.commit();
		}catch(Exception e){
			Log.e(TAG, "[onSave]", e);
		}
		
		Intent intent = new Intent(this,MyWidgetProvider.class);
		intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");

		int[] ids = {widgetId};
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
		sendBroadcast(intent);
		
		finish();
	}
}

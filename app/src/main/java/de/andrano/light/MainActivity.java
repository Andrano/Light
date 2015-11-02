package de.andrano.light;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Light light;
	TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//ClickListener
		textView = (TextView) findViewById(R.id.textView);
		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://andrano.de/Light"));
				startActivity(i);
			}
		});
		// Receiver
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		BroadcastReceiver receiver = new ScreenReceiver();
		registerReceiver(receiver, filter);
	}
	
	@Override
	protected void onResume() {
		if (ScreenReceiver.wasScreenOn) {
			//Screen state not changed
			light = new Light(getApplicationContext());
			light.turnOn();
			Log.d("onResume", "Screen state not changed");
		} else {
			Log.d("onResume", "Screen state changed");
		}
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		if (ScreenReceiver.wasScreenOn) {
			//Screen state not changed
			light.turnOff();
			Log.d("onPause", "Screen state not changed");
		} else {
			Log.d("onPause", "Screen state changed");
		}
		super.onPause();
	}
}

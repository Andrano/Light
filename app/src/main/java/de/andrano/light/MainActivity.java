package de.andrano.light;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
	}
	
	@Override
	protected void onResume() {
		light = new Light(getApplicationContext());
		light.turnOn();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		light.turnOff();
		super.onPause();
	}
}

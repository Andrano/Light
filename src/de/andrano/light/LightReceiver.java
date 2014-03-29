package de.andrano.light;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class LightReceiver extends BroadcastReceiver {

	private static boolean isOn = false;
	private static Light light = null;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
		AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
		ComponentName component = new ComponentName(context, LightProvider.class);
		
		//Update button image
		if (isOn) {
			views.setImageViewResource(R.id.image, R.drawable.ic_widget_off);
		} else {
			views.setImageViewResource(R.id.image, R.drawable.ic_widget_on);
		}
		//Update click handler
		Intent receiver = new Intent(context, LightReceiver.class);
		receiver.setAction("de.andrano.light.receiver");
		receiver.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetManager.getAppWidgetIds(component));
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, receiver, 0);
		
		views.setOnClickPendingIntent(R.id.image, pendingIntent);
		
		widgetManager.updateAppWidget(component, views);
		
		if (isOn) {
			light.turnOff();
			isOn = false;
		} else {
			if (light == null) {
				light = new Light(context);
			}
			light.turnOn();
			isOn = true;
		}
	}

}

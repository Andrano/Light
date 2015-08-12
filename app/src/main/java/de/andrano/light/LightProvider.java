package de.andrano.light;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class LightProvider extends AppWidgetProvider {
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
		Intent receiver = new Intent(context, LightReceiver.class);
		receiver.setAction("de.andrano.light.receiver");
		receiver.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
		PendingIntent intent = PendingIntent.getBroadcast(context, 0, receiver, 0);
		
		//Set views
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
		views.setOnClickPendingIntent(R.id.image, intent);
		
		appWidgetManager.updateAppWidget(appWidgetIds, views);

		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
}

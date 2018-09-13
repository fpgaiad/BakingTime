package br.com.fpgaiad.bakingtime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import br.com.fpgaiad.bakingtime.ui.main.MainActivity;

public class BakingWidgetProvider extends AppWidgetProvider {

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //Set up the intent that starts the BakingIngredientsService
        Intent intentService = new Intent(context, BakingIngredientsService.class);

        //RemoteViews object
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        //Set up the RemoteViews object to use a RemoteViews adapter
        remoteViews.setRemoteAdapter(R.id.widget_list_view, intentService);

        //Intent to launch MainActivity when clicked
        Intent intentStartsMainActivity = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentStartsMainActivity, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_main_layout, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        updateAppWidget(context, appWidgetManager, appWidgetId);
    }
}


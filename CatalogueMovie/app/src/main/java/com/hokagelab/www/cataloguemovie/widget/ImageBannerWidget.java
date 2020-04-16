package com.hokagelab.www.cataloguemovie.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.hokagelab.www.cataloguemovie.R;

/**
 * Implementation of App Widget functionality.
 */
public class ImageBannerWidget extends AppWidgetProvider {
    public static final String TOAST_ACTION = "com.hokagelab.mystackwidget.TOAST_ACTION";
    public static final String UPDATE_WIDGET = "com.hokagelab.mystackwidget.UPDATE_WIDGET";
    public static final String EXTRA_ITEM = "com.hokagelab.mystackwidget.EXTRA_ITEM";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.image_banner_widget);

        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.emptry_view);

        Intent toastIntent = new Intent(context, ImageBannerWidget.class);

        toastIntent.setAction(ImageBannerWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
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
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(TOAST_ACTION)){
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
            Toast.makeText(context, "Rilis pada "+ StackRemoteViewsFactory.listFilm.get(viewIndex).getDate(), Toast.LENGTH_SHORT).show();
        }
        if (intent.getAction().equals(UPDATE_WIDGET)){
            int widgetIDs[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context,ImageBannerWidget.class));

            for (int id:widgetIDs){
                AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(id, R.id.stack_view);
            }
        }
        super.onReceive(context, intent);
    }
}


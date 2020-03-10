package com.kucingselfie.madecourse

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.kucingselfie.madecourse.service.StackWidgetService
import timber.log.Timber


/**
 * Implementation of App Widget functionality.
 */
class MovieBannerWidget : AppWidgetProvider() {

    companion object {
        private const val TOAST_ACTION = "com.kucingselfie.madecourse.TOAST_ACTION"
        const val EXTRA_ITEM = "com.kucingselfie.madecourse.EXTRA_ITEM"

        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            Timber.v("INTENT EXTRA $intent")

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.movie_banner_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)

            Timber.v("APP WIDGET ID 1 $appWidgetId")

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            Timber.v("APP WIDGET ID 2 $appWidgetId")
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action != null) {
            if (intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val thisWidget = ComponentName(context, MovieBannerWidget::class.java)
                val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view)
            }
        }
    }
}
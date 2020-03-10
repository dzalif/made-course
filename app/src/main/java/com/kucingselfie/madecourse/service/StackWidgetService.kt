package com.kucingselfie.madecourse.service

import android.content.Intent
import android.widget.RemoteViewsService
import com.kucingselfie.madecourse.widget.StackRemoteViewsFactory

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)
}
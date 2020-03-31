package com.kucingselfie.movieconsumer

import android.app.Application
import timber.log.Timber

class ConsumerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
package com.kucingselfie.madecourse.alarmmanager

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.api.ApiClient
import com.kucingselfie.madecourse.common.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class AlarmReceiver : BroadcastReceiver() {

    private var mNotificationManager: NotificationManager? = null

    companion object {
        // Notification channel ID.
        const val TYPE_DAILY = "type_daily"
        const val TYPE_RELEASE = "type_release"
        const val EXTRA_TYPE = "type"
        const val ID_DAILY = 111
        const val ID_RELEASE = 112
        const val CHANNEL_ID_DAILY = "Channel_1"
        const val CHANNEL_ID_RELEASE = "Channel_2"
        const val CHANNEL_NAME_DAILY = "Channel_daily"
        const val CHANNEL_NAME_RELEASE = "Channel_release"
    }

    override fun onReceive(context: Context?, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE)
        val notifId = if (type.equals(TYPE_DAILY, ignoreCase = true)) ID_DAILY else ID_RELEASE

        mNotificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (notifId == ID_DAILY) {
            val notificationTitle = context.getString(R.string.app_name)
            val message = context.getString(R.string.notification_text)
            deliverNotification(context, notifId, message, notificationTitle, CHANNEL_ID_DAILY, CHANNEL_NAME_DAILY)
        } else {
            getReleaseToday(context)
        }
    }

    private fun getReleaseToday(context: Context) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val result = ApiClient.create().getTodayRelease(API_KEY, getDateToday(), getDateToday())
                    val movies = result.results

                    withContext(Dispatchers.Main) {
                        var notifId = 2
                        for (i in movies) {
                            val title = i.title
                            val message = i.title + " has been release today"
                            deliverNotification(
                                context,
                                notifId,
                                message,
                                title,
                                CHANNEL_ID_RELEASE,
                                CHANNEL_NAME_RELEASE
                            )
                            notifId++
                        }
                    }
                } catch (e: Exception) {
                    Timber.e("RESPONSE ERROR : ${e.message}")
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDateToday() : String {
        val dateFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        dateFormatter.isLenient = false
        val today = Date()
        return dateFormatter.format(today)
    }

    private fun deliverNotification(
        context: Context,
        notifId: Int,
        message: String,
        title: String,
        channelId: String,
        channelName: String
    ) {
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT)

            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManagerCompat.notify(notifId, notification)
    }

    fun setDailyAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_TYPE, type)

        val calendarDaily = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 7)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarDaily.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(context, "Alarm setup", Toast.LENGTH_SHORT).show()
    }

    fun setReleaseAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_TYPE, type)

        val calendarRelease = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarRelease.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        Toast.makeText(context, "Alarm setup", Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = if (type.equals(TYPE_DAILY, ignoreCase = true)) ID_DAILY else ID_RELEASE
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Turning off notification", Toast.LENGTH_SHORT).show()
    }
}
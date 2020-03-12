package com.kucingselfie.madecourse.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.util.gone
import com.kucingselfie.madecourse.util.visible
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private var mNotificationManager: NotificationManager? = null

    companion object {
        private const val NOTIFICATION_ID = 0
        private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        initNavController()
        initBottomNav()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {
            // Create the NotificationChannel with all the parameters.
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Stand up notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notifies every 15 minutes to stand up and walk"
            mNotificationManager!!.createNotificationChannel(notificationChannel)
        }
    }

    private fun initBottomNav() {
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(navigationListener)
    }

    private fun initNavController() {
        navController = navHostFragment.findNavController()
        toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener(navigationListener)
    }

    private val navigationListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            invalidateOptionsMenu()
            when(destination.id) {
                R.id.movieFragment -> {
                    supportActionBar?.title = getString(R.string.movie)
                    hideToolbarBack()
                    showBottomNavigation(true)
                }
                R.id.TVShowFragment -> {
                    supportActionBar?.title = getString(R.string.tv_show)
                    hideToolbarBack()
                    showBottomNavigation(true)
                }
                R.id.favoriteFragment -> {
                    supportActionBar?.title = getString(R.string.favorite)
                    hideToolbarBack()
                    showBottomNavigation(true)
                }
                R.id.reminderFragment -> {
                    showBottomNavigation(false)
                }
                else -> showToolbarBack()
            }
        }

    private fun showBottomNavigation(shouldShow: Boolean) {
        if (shouldShow) bottomNavigationView.visible()
        else bottomNavigationView.gone()
    }

    private fun showToolbarBack() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun hideToolbarBack() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp()
    }
}

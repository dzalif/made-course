package com.kucingselfie.madecourse.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.kucingselfie.madecourse.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavController()
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
                R.id.homeFragment -> {
                    supportActionBar?.title = getString(R.string.home)
                    hideToolbarBack()
                }
                R.id.movieFragment -> {
                    supportActionBar?.title = getString(R.string.movie)
                }
                R.id.TVShowFragment -> {
                    supportActionBar?.title = getString(R.string.tv_show)
                }
                else -> showToolbarBack()
            }
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

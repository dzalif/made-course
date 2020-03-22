package com.kucingselfie.madecourse.ui.reminder

import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kucingselfie.madecourse.alarmmanager.AlarmReceiver
import com.kucingselfie.madecourse.alarmmanager.AlarmReceiver.Companion.TYPE_DAILY
import com.kucingselfie.madecourse.alarmmanager.AlarmReceiver.Companion.TYPE_RELEASE
import com.kucingselfie.madecourse.databinding.ReminderFragmentBinding


class ReminderFragment : Fragment() {

    private lateinit var viewModel: ReminderViewModel
    private lateinit var bind: ReminderFragmentBinding
    private lateinit var mNotificationManager: NotificationManager
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = ReminderFragmentBinding.inflate(inflater)
        mNotificationManager = activity?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        preferences = requireActivity().getSharedPreferences("moviePref", Context.MODE_PRIVATE)
        editor = preferences.edit()
        alarmReceiver = AlarmReceiver()

        switchDailyListener()
        initPreferences()
        return bind.root
    }

    private fun initPreferences() {
        val daily = preferences.getBoolean("daily_reminder", false)
        val release = preferences.getBoolean("release_reminder", false)

        bind.switchDaily.isChecked = daily
        bind.switchRelease.isChecked = release
    }

    private fun switchDailyListener() {
        bind.switchDaily.setOnCheckedChangeListener { _, isChecked ->
            editor.putBoolean("daily_reminder", isChecked)
            editor.apply()
            if (isChecked) {
                alarmReceiver.setDailyAlarm(requireContext(), TYPE_DAILY)
            } else {
                alarmReceiver.cancelAlarm(requireContext(), TYPE_DAILY)
            }
        }

        bind.switchRelease.setOnCheckedChangeListener { _, isChecked ->
            editor.putBoolean("release_reminder", isChecked)
            editor.apply()
            if (isChecked) {
                alarmReceiver.setReleaseAlarm(requireContext(), TYPE_RELEASE)
            } else {
                alarmReceiver.cancelAlarm(requireContext(), TYPE_RELEASE)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)
    }

}

package com.dicoding.habitapp.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.habitapp.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            //TODO 11 : Update theme based on value in ListPreference
            val prefDarkMode = findPreference<ListPreference>(getString(R.string.pref_key_dark))
            prefDarkMode?.setOnPreferenceChangeListener { _, newValue ->
                when (newValue.toString()) {
                    "on" -> {
                        updateTheme(MODE_NIGHT_YES)
                    }
                    "off" -> {
                        updateTheme(MODE_NIGHT_NO)
                    }
                    "follow_system" -> {
                        updateTheme(MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                }
                true
            }

            val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
            prefNotification?.setOnPreferenceChangeListener { preference, newValue ->
//                val workManager = WorkManager.getInstance(preference.context)
//                val channelName = getString(R.string.notify_channel_name)
//
//                val channelData = Data.Builder()
//                    .putString(NOTIFICATION_CHANNEL_ID, channelName)
//                    .build()
//
//                val oneTimeWorkRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
//                    .setInputData(channelData)
//                    .build()

                if (newValue == true) {
//                    workManager.enqueue(oneTimeWorkRequest)
                    preference.sharedPreferences?.edit()?.putBoolean(getString(R.string.pref_key_notify), true)?.apply()
                } else if (newValue == false) {
//                    workManager.cancelWorkById(oneTimeWorkRequest.id)
                    preference.sharedPreferences?.edit()?.putBoolean(getString(R.string.pref_key_notify), false)?.apply()
                }
                true
            }
        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}
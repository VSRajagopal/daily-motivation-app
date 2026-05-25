package com.dailymotivation.ui

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dailymotivation.data.QuoteRepository
import com.dailymotivation.databinding.ActivitySettingsBinding
import com.dailymotivation.widget.NotificationScheduler
import java.util.Locale

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private var notifHour = 8
    private var notifMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings"

        loadSettings()
        setupListeners()
    }

    private fun loadSettings() {
        val (enabled, hour, minute) = QuoteRepository.getNotificationSettings(this)
        notifHour = hour
        notifMinute = minute
        binding.switchNotifications.isChecked = enabled
        updateTimeDisplay()

        val theme = QuoteRepository.getTheme(this)
        val radioId = when (theme) {
            "light" -> binding.rbLight.id
            "dark" -> binding.rbDark.id
            else -> binding.rbAuto.id
        }
        binding.rgTheme.check(radioId)
        updateNotifControls(enabled)
    }

    private fun setupListeners() {
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            updateNotifControls(isChecked)
            saveNotificationSettings(isChecked)
        }

        binding.btnPickTime.setOnClickListener {
            TimePickerDialog(this, { _, hour, minute ->
                notifHour = hour
                notifMinute = minute
                updateTimeDisplay()
                saveNotificationSettings(binding.switchNotifications.isChecked)
            }, notifHour, notifMinute, false).show()
        }

        binding.rgTheme.setOnCheckedChangeListener { _, checkedId ->
            val theme = when (checkedId) {
                binding.rbLight.id -> "light"
                binding.rbDark.id -> "dark"
                else -> "auto"
            }
            QuoteRepository.saveTheme(this, theme)
            applyTheme(theme)
        }
    }

    private fun applyTheme(theme: String) {
        when (theme) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    private fun updateTimeDisplay() {
        val amPm = if (notifHour < 12) "AM" else "PM"
        val displayHour = when {
            notifHour == 0 -> 12
            notifHour > 12 -> notifHour - 12
            else -> notifHour
        }
        binding.tvNotifTime.text = String.format(Locale.getDefault(), "%d:%02d %s", displayHour, notifMinute, amPm)
    }

    private fun updateNotifControls(enabled: Boolean) {
        binding.btnPickTime.isEnabled = enabled
        binding.tvNotifTime.alpha = if (enabled) 1f else 0.4f
    }

    private fun saveNotificationSettings(enabled: Boolean) {
        QuoteRepository.saveNotificationSettings(this, enabled, notifHour, notifMinute)
        NotificationScheduler.scheduleIfEnabled(this)
        val msg = if (enabled) "Notifications scheduled at ${binding.tvNotifTime.text}" else "Notifications disabled"
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}

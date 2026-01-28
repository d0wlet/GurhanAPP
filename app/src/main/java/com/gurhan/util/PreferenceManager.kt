package com.gurhan.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("gurhan_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LAST_READ_SURAH_ID = "last_read_surah_id"
        private const val KEY_LAST_READ_SURAH_NAME = "last_read_surah_name"
        private const val KEY_LAST_READ_VERSE_ID = "last_read_verse_id"
        
        // New Settings
        private const val KEY_FONT_SIZE = "settings_font_size"
        private const val KEY_KEEP_SCREEN_ON = "settings_keep_screen_on"
        private const val KEY_NOTIFICATIONS_ENABLED = "settings_notifications_enabled"
        private const val KEY_DARK_MODE = "settings_dark_mode"
    }

    fun saveLastRead(surahId: Int, surahName: String, verseId: Int) {
        prefs.edit().apply {
            putInt(KEY_LAST_READ_SURAH_ID, surahId)
            putString(KEY_LAST_READ_SURAH_NAME, surahName)
            putInt(KEY_LAST_READ_VERSE_ID, verseId)
            apply()
        }
    }

    fun getLastReadSurahId(): Int = prefs.getInt(KEY_LAST_READ_SURAH_ID, 1) // Default Fatiha
    fun getLastReadSurahName(): String = prefs.getString(KEY_LAST_READ_SURAH_NAME, "Fatiha") ?: "Fatiha"
    fun getLastReadVerseId(): Int = prefs.getInt(KEY_LAST_READ_VERSE_ID, 1)

    // Font Size (Scale factor, default 1.0f)
    fun setFontSize(size: Float) = prefs.edit().putFloat(KEY_FONT_SIZE, size).apply()
    fun getFontSize(): Float = prefs.getFloat(KEY_FONT_SIZE, 1.0f)

    // Keep Screen On
    fun setKeepScreenOn(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_KEEP_SCREEN_ON, enabled).apply()
        keepScreenOnFlow.value = enabled
    }
    fun isKeepScreenOn(): Boolean = prefs.getBoolean(KEY_KEEP_SCREEN_ON, false)

    // Notifications
    fun setNotificationsEnabled(enabled: Boolean) = prefs.edit().putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled).apply()
    fun areNotificationsEnabled(): Boolean = prefs.getBoolean(KEY_NOTIFICATIONS_ENABLED, true)

    // Dark Mode (0: System, 1: Light, 2: Dark)
    fun setDarkMode(mode: Int) {
        prefs.edit().putInt(KEY_DARK_MODE, mode).apply()
        darkModeFlow.value = mode
    }
    fun getDarkMode(): Int = prefs.getInt(KEY_DARK_MODE, 0)

    // Flows for reactive updates
    val darkModeFlow = kotlinx.coroutines.flow.MutableStateFlow(getDarkMode())
    val keepScreenOnFlow = kotlinx.coroutines.flow.MutableStateFlow(isKeepScreenOn())

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        when (key) {
            KEY_DARK_MODE -> darkModeFlow.value = getDarkMode()
            KEY_KEEP_SCREEN_ON -> keepScreenOnFlow.value = isKeepScreenOn()
        }
    }

    init {
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }
}

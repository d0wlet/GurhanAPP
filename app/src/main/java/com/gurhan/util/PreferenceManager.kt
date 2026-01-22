package com.gurhan.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("gurhan_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LAST_READ_SURAH_ID = "last_read_surah_id"
        private const val KEY_LAST_READ_SURAH_NAME = "last_read_surah_name"
        private const val KEY_LAST_READ_VERSE_ID = "last_read_verse_id"
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
}

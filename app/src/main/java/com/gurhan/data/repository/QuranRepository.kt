package com.gurhan.data.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.gurhan.data.model.Surah
import com.gurhan.data.model.Verse
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class QuranRepository(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "quran.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_SURAHS = "surahs"
        private const val TABLE_VERSES = "verses"
    }

    init {
        // Copy DB from assets if not exists
        val dbPath = context.getDatabasePath(DATABASE_NAME)
        if (!dbPath.exists()) {
            dbPath.parentFile?.mkdirs()
            try {
                copyDatabase(dbPath)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun copyDatabase(dbPath: File) {
        context.assets.open(DATABASE_NAME).use { input ->
            FileOutputStream(dbPath).use { output ->
                input.copyTo(output)
            }
        }
    }

    // Required overrides
    override fun onCreate(db: SQLiteDatabase?) {
        // Database is pre-populated, nothing to create
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // For now, if version changes, we might want to delete and re-copy
        context.deleteDatabase(DATABASE_NAME)
        val dbPath = context.getDatabasePath(DATABASE_NAME)
        copyDatabase(dbPath)
    }

    fun getAllSurahs(): List<Surah> {
        val surahs = mutableListOf<Surah>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT id, name, arabicName, revelationType, versesCount FROM $TABLE_SURAHS", null)
        
        if (cursor.moveToFirst()) {
            do {
                surahs.add(Surah(
                    id = cursor.getInt(0),
                    name = cursor.getString(1),
                    arabicName = cursor.getString(2),
                    revelationType = cursor.getString(3),
                    versesCount = cursor.getInt(4)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return surahs
    }
    
    fun getSurahById(id: Int): Surah? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT id, name, arabicName, revelationType, versesCount FROM $TABLE_SURAHS WHERE id = ?", arrayOf(id.toString()))
        var surah: Surah? = null
        if (cursor.moveToFirst()) {
            surah = Surah(
                id = cursor.getInt(0),
                name = cursor.getString(1),
                arabicName = cursor.getString(2),
                revelationType = cursor.getString(3),
                versesCount = cursor.getInt(4)
            )
        }
        cursor.close()
        return surah
    }
    
    fun getVersesBySurahId(surahId: Int): List<Verse> {
        val verses = mutableListOf<Verse>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT id, verseNumber, text, arabicText FROM $TABLE_VERSES WHERE surahId = ? ORDER BY verseNumber", arrayOf(surahId.toString()))
        
        if (cursor.moveToFirst()) {
            do {
                verses.add(Verse(
                    surahId = surahId, 
                    verseNumber = cursor.getInt(1),
                    turkmenTranslation = cursor.getString(2),
                    arabicText = cursor.getString(3)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return verses
    }
    
    fun searchSurahs(query: String): List<Surah> {
        val surahs = mutableListOf<Surah>()
        val db = this.readableDatabase
        val likeQuery = "%$query%"
        val cursor = db.rawQuery("SELECT id, name, arabicName, revelationType, versesCount FROM $TABLE_SURAHS WHERE name LIKE ? OR arabicName LIKE ?", arrayOf(likeQuery, likeQuery))
        
        if (cursor.moveToFirst()) {
            do {
                surahs.add(Surah(
                    id = cursor.getInt(0),
                    name = cursor.getString(1),
                    arabicName = cursor.getString(2),
                    revelationType = cursor.getString(3),
                    versesCount = cursor.getInt(4)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return surahs
    }

    fun getVerseOfTheDay(): Pair<Surah, Verse>? {
        // Same logic: Date based seed, but now SQL random or just deterministic math
        val calendar = java.util.Calendar.getInstance()
        val dayOfYear = calendar.get(java.util.Calendar.DAY_OF_YEAR)
        val year = calendar.get(java.util.Calendar.YEAR)
        val seed = year * 1000 + dayOfYear
        val random = java.util.Random(seed.toLong())
        
        // Count surahs
        val surahCount = 114 // Known
        val randomSurahId = random.nextInt(surahCount) + 1
        
        val surah = getSurahById(randomSurahId) ?: return null
        
        // Get random verse from this surah
        // To be deterministic with same seed, we need to know verse count or pick from list
        // Since we are not caching all verses, let's just fetch count first
        
        val db = this.readableDatabase
        // Efficient count
        // Or just fetch all verses for this surah (cheap for one surah)
        val verses = getVersesBySurahId(randomSurahId)
        if (verses.isEmpty()) return null
        
        val randomVerseIndex = random.nextInt(verses.size)
        val verse = verses[randomVerseIndex]
        
        return Pair(surah, verse)
    }
}

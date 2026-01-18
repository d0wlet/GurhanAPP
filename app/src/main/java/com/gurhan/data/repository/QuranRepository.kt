package com.gurhan.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gurhan.data.model.Surah
import com.gurhan.data.model.Verse
import java.io.IOException

class QuranRepository(private val context: Context) {

    private var cachedSurahs: List<SurahWithVerses>? = null
    
    // Internal model matching JSON structure
    private data class SurahWithVerses(
        val id: Int,
        val name: String,
        val arabicName: String,
        val revelationType: String,
        val versesCount: Int,
        val verses: List<VerseJson>
    )

    private data class VerseJson(
        val number: Int,
        val text: String
    )

    private fun loadData() {
        if (cachedSurahs != null) return
        
        val jsonString: String
        try {
            jsonString = context.assets.open("quran_data.json").bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<SurahWithVerses>>() {}.type
            cachedSurahs = Gson().fromJson(jsonString, listType)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            cachedSurahs = emptyList()
        }
    }

    fun getAllSurahs(): List<Surah> {
        loadData()
        return cachedSurahs?.map { 
             Surah(it.id, it.name, it.arabicName, it.revelationType, it.versesCount)
        } ?: emptyList()
    }
    
    fun getSurahById(id: Int): Surah? {
        loadData()
        val s = cachedSurahs?.find { it.id == id } ?: return null
        return Surah(s.id, s.name, s.arabicName, s.revelationType, s.versesCount)
    }
    
    fun getVersesBySurahId(surahId: Int): List<Verse> {
        loadData()
        val surah = cachedSurahs?.find { it.id == surahId } ?: return emptyList()
        return surah.verses.map { 
            Verse(
                surahId = surahId, 
                verseNumber = it.number, 
                arabicText = "", 
                turkmenTranslation = it.text
            )
        }
    }
    
    fun searchSurahs(query: String): List<Surah> {
        loadData()
        return getAllSurahs().filter { 
            it.name.contains(query, ignoreCase = true) ||
            it.arabicName.contains(query) ||
            it.id.toString().contains(query)
        }
    }
}

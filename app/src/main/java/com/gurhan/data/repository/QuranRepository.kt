package com.gurhan.data.repository

import android.content.Context
import android.util.Log
import androidx.annotation.Keep
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.gurhan.data.model.Surah
import com.gurhan.data.model.Verse
import java.io.IOException

class QuranRepository(private val context: Context) {

    private var cachedSurahs: List<SurahWithVerses>? = null
    
    // Internal model matching JSON structure
    @Keep
    private data class SurahWithVerses(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("arabicName") val arabicName: String,
        @SerializedName("revelationType") val revelationType: String,
        @SerializedName("versesCount") val versesCount: Int,
        @SerializedName("verses") val verses: List<VerseJson>
    )

    @Keep
    private data class VerseJson(
        @SerializedName("number") val number: Int,
        @SerializedName("text") val text: String
    )

    private fun loadData() {
        if (cachedSurahs != null) return
        
        val jsonString: String
        try {
            jsonString = context.assets.open("quran_data.json").bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<SurahWithVerses>>() {}.type
            cachedSurahs = Gson().fromJson(jsonString, listType)
        } catch (e: Exception) {
            Log.e("QuranRepository", "Error loading/parsing quran data", e)
            e.printStackTrace()
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

    fun getVerseOfTheDay(): Pair<Surah, Verse>? {
        loadData()
        val surahs = cachedSurahs ?: return null
        if (surahs.isEmpty()) return null
        
        // Use current date as seed
        val calendar = java.util.Calendar.getInstance()
        val dayOfYear = calendar.get(java.util.Calendar.DAY_OF_YEAR)
        val year = calendar.get(java.util.Calendar.YEAR)
        
        // Simple deterministic random based on date
        val seed = year * 1000 + dayOfYear
        val random = java.util.Random(seed.toLong())
        
        // Pick a random Surah (weighted by verse count? No, simple random for now)
        // Check filtering short surahs? Maybe avoid very short ones?
        // Let's just pick one.
        val randomSurahIndex = random.nextInt(surahs.size)
        val splitSurah = surahs[randomSurahIndex]
        
        if (splitSurah.verses.isEmpty()) return null
        
        val randomVerseIndex = random.nextInt(splitSurah.verses.size)
        val verseJson = splitSurah.verses[randomVerseIndex]
        
        val surah = Surah(splitSurah.id, splitSurah.name, splitSurah.arabicName, splitSurah.revelationType, splitSurah.versesCount)
        val verse = Verse(splitSurah.id, verseJson.number, "", verseJson.text)
        
        return Pair(surah, verse)
    }
}

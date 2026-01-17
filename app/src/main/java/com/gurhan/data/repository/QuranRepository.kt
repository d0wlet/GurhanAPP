package com.gurhan.data.repository

import com.gurhan.data.model.Surah
import com.gurhan.data.model.Verse

class QuranRepository {
    
    fun getAllSurahs(): List<Surah> {
        return listOf(
            Surah(1, "Fatiha", "الفاتحة", "Mekke", 7),
            Surah(2, "Bakara", "البقرة", "Medine", 286),
            Surah(3, "Al-i Imran", "آل عمران", "Medine", 200),
            // Add all 114 Surahs here - truncated for brevity
            // This would normally be loaded from a JSON file or database
        )
    }
    
    fun getSurahById(id: Int): Surah? {
        return getAllSurahs().find { it.id == id }
    }
    
    fun getVersesBySurahId(surahId: Int): List<Verse> {
        // Sample verses - would normally load from data source
        return listOf(
            Verse(
                surahId = surahId,
                verseNumber = 1,
                arabicText = "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ",
                turkmenTranslation = "Rehimli we Mähirbanly Allahyň ady bilen"
            )
        )
    }
    
    fun searchSurahs(query: String): List<Surah> {
        return getAllSurahs().filter { 
            it.name.contains(query, ignoreCase = true) ||
            it.arabicName.contains(query)
        }
    }
}

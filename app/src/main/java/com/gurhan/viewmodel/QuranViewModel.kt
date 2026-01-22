package com.gurhan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gurhan.data.model.Surah
import com.gurhan.data.repository.QuranRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuranViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = QuranRepository(application)
    private val preferenceManager = com.gurhan.util.PreferenceManager(application)
    
    private val _allSurahs = MutableStateFlow<List<Surah>>(emptyList())
    
    private val _surahs = MutableStateFlow<List<Surah>>(emptyList())
    val surahs: StateFlow<List<Surah>> = _surahs.asStateFlow()
    
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    // Last Read State
    private val _lastReadSurah = MutableStateFlow(preferenceManager.getLastReadSurahName())
    val lastReadSurah: StateFlow<String> = _lastReadSurah.asStateFlow()
    
    private val _lastReadVerse = MutableStateFlow(preferenceManager.getLastReadVerseId())
    val lastReadVerse: StateFlow<Int> = _lastReadVerse.asStateFlow()
    
    private val _lastReadSurahId = MutableStateFlow(preferenceManager.getLastReadSurahId())
    val lastReadSurahId: StateFlow<Int> = _lastReadSurahId.asStateFlow()
    
    init {
        loadSurahs()
        refreshLastRead()
    }
    
    fun refreshLastRead() {
        _lastReadSurah.value = preferenceManager.getLastReadSurahName()
        _lastReadVerse.value = preferenceManager.getLastReadVerseId()
        _lastReadSurahId.value = preferenceManager.getLastReadSurahId()
    }
    
    fun saveLastRead(surahId: Int, surahName: String, verseId: Int) {
        preferenceManager.saveLastRead(surahId, surahName, verseId)
        refreshLastRead()
    }
    
    private fun loadSurahs() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val fullList = repository.getAllSurahs()
                _allSurahs.value = fullList
                _surahs.value = fullList
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun onSearch(query: String) {
        if (query.isBlank()) {
            _surahs.value = _allSurahs.value
            return
        }
        
        val lowerQuery = query.lowercase()
        _surahs.value = _allSurahs.value.filter { surah ->
            surah.name.lowercase().contains(lowerQuery) ||
            surah.arabicName.contains(lowerQuery) ||
            surah.id.toString() == lowerQuery
        }
    }
}

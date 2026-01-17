package com.gurhan.data.model

data class Surah(
    val id: Int,
    val name: String,
    val arabicName: String,
    val revelationType: String, // "Mekke" or "Medine"
    val versesCount: Int
)

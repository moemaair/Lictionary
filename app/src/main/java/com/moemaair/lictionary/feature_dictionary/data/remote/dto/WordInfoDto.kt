package com.moemaair.lictionary.feature_dictionary.data.remote.dto

data class WordInfoDto(
    val meanings: List<Meaning>,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val sourceUrls: List<String>,
    val word: String
)
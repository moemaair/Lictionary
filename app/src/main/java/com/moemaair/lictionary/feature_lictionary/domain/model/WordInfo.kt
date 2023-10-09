package com.moemaair.lictionary.feature_lictionary.domain.model


data class WordInfo(  // Mapper class of WordInfoDto
    val id: Int? = null,
    val meanings: List<Meaning>,
    val phonetic: String?,
    val word: String,
    val phonetics: List<Phonetics>
)
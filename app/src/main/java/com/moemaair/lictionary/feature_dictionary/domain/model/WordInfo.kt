package com.moemaair.lictionary.feature_dictionary.domain.model


data class WordInfo(  // Mapper class of WordInfoDto
    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String
)
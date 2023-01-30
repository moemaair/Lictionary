package com.moemaair.lictionary.feature_dictionary.domain.model

import com.moemaair.lictionary.feature_dictionary.domain.model.Meaning

data class WordInfo(  // Mapper class of WordInfoDto
    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String
)
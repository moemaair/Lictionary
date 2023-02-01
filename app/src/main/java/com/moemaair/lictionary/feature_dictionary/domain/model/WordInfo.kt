package com.moemaair.lictionary.feature_dictionary.domain.model

import Phonetics
import com.moemaair.lictionary.feature_dictionary.data.remote.PhoneticDto
import com.moemaair.lictionary.feature_dictionary.data.remote.dto.MeaningDto

data class WordInfo(  // Mapper class of WordInfoDto
    val meanings: List<Meaning>,
    val phonetic: String?,
    val word: String,
    val phonetics: List<Phonetics>
)
package com.moemaair.lictionary.feature_dictionary.data.remote

import com.moemaair.lictionary.feature_dictionary.domain.model.WordInfo


data class WordInfoDto(
    val meanings: List<MeaningDto>,
    val phonetic: String,
    val phonetics: List<PhoneticDto>,
    val word: String
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic,
            word = word
        )
    }
}
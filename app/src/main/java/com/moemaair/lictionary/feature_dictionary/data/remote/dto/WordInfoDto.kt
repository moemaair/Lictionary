package com.moemaair.lictionary.feature_dictionary.data.remote.dto

import com.moemaair.lictionary.feature_dictionary.data.local.entity.WordInfoEntity

data class WordInfoDto(
    val meanings: List<Meaning>,
    val phonetic: String,  // symbol
    val word: String
){
    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic,
            word = word
        )
    }
}
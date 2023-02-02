package com.moemaair.lictionary.feature_dictionary.data.remote.dto

import com.moemaair.lictionary.feature_dictionary.data.local.entity.WordInfoEntity


data class WordInfoDto(
    val meanings: List<MeaningDto>,
    val phonetic: String?,
    val phonetics: List<PhoneticDto>,
    val word: String
) {
    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic,
            phonetics = phonetics.map { it.toPhontics() },
            word = word,
        )
    }


}
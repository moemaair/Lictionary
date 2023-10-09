package com.moemaair.lictionary.feature_lictionary.data.remote.dto

import com.moemaair.lictionary.feature_lictionary.domain.model.Phonetics


data class PhoneticDto(
    val audio: String?,
    val sourceUrl: String?,
    val text: String?
){
    fun toPhontics(): Phonetics {
        return Phonetics(
            audio = audio,
            sourceUrl = sourceUrl,
            text = text
        )
    }
}
package com.moemaair.lictionary.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moemaair.lictionary.feature_dictionary.domain.model.WordInfo
import com.moemaair.lictionary.feature_dictionary.domain.model.Meaning

@Entity
data class WordInfoEntity(
    val word: String,
    val phonetic: String,
    val meanings: List<Meaning>,
    @PrimaryKey val id: Int? = null
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings,
            word = word,
            phonetic = phonetic
        )
    }
}

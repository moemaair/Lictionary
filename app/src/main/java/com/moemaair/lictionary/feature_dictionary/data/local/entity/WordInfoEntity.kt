package com.moemaair.lictionary.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moemaair.lictionary.feature_dictionary.data.remote.dto.Meaning
import com.moemaair.lictionary.feature_dictionary.data.remote.dto.WordInfoDto

@Entity(tableName = "lictionary_table")
data class WordInfoEntity(

    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String,
    @PrimaryKey val id: Int? = null
){
    fun toWordInfo(): WordInfoDto{
        return WordInfoDto(
            meanings = meanings,
            phonetic = phonetic,
            word = word
        )
    }
}

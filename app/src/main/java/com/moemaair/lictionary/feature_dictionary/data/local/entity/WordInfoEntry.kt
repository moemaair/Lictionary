package com.moemaair.lictionary.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moemaair.lictionary.feature_dictionary.data.remote.dto.Meaning
import com.moemaair.lictionary.feature_dictionary.data.remote.dto.Phonetic
import com.moemaair.lictionary.feature_dictionary.data.remote.dto.WordInfoDto

@Entity(tableName = "lictionary_table")
data class WordInfoEntry(
    @PrimaryKey val id: Int,
//    val meanings: List<Meaning>,
//    val phonetic: String,
//    val phonetics: List<Phonetic>,
//    val word: String
){
//    fun toWordInfo(): WordInfoDto{
//        return WordInfoDto(
////            meanings = meanings,
////            phonetic = phonetic,
////            phonetics = phonetics,
////            word = word
//        )
//    }
}

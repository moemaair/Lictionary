package com.moemaair.lictionary.feature_lictionary.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.moemaair.lictionary.feature_lictionary.data.local.PhoneticsTypeConverter
import com.moemaair.lictionary.feature_lictionary.domain.model.WordInfo
import com.moemaair.lictionary.feature_lictionary.domain.model.Meaning
import com.moemaair.lictionary.feature_lictionary.domain.model.Phonetics


@TypeConverters(PhoneticsTypeConverter::class)
@Entity
data class WordInfoEntity(
    val word: String,
    val phonetic: String?,
    val meanings: List<Meaning>,
    val phonetics: List<Phonetics>,
    @PrimaryKey val id: Int? = null
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            id = id,
            meanings = meanings,
            word = word,
            phonetic = phonetic,
            phonetics = phonetics
        )
    }
}

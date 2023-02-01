package com.moemaair.lictionary.feature_dictionary.data.remote.dto

import com.moemaair.lictionary.feature_dictionary.data.remote.DefinitionDto
import com.moemaair.lictionary.feature_dictionary.domain.model.Meaning

data class MeaningDto(
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String,
){
    fun toMeaning(): Meaning{
        return Meaning(
            definitions = definitions.map{it.toDefination()},
            partOfSpeech = partOfSpeech,
        )
    }
}
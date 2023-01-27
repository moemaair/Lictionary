package com.moemaair.lictionary.feature_dictionary.data.remote.dto

data class MeaningDto(
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String,
){
    fun toMeaning(): MeaningDto{
        return MeaningDto(
            definitions = definitions.map{it.toDefination()},
            partOfSpeech = partOfSpeech,

        )
    }
}
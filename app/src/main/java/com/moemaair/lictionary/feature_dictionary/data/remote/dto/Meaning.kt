package com.moemaair.lictionary.feature_dictionary.data.remote.dto

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String,
){
    fun toMeaning(): Meaning{
        return Meaning(
            definitions = definitions.map{it.toDefination()},
            partOfSpeech = partOfSpeech,

        )
    }
}
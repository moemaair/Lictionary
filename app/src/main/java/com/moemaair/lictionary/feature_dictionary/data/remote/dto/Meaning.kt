package com.moemaair.lictionary.feature_dictionary.data.remote.dto

data class Meaning(
    val antonyms: List<String>,
    val definitions: List<Definition>,
    val partOfSpeech: String,
    val synonyms: List<String>
){
    fun toMeaning(): Meaning{
        return Meaning(
            antonyms = antonyms,
            definitions = definitions,
            partOfSpeech = partOfSpeech,
            synonyms = synonyms
        )
    }
}
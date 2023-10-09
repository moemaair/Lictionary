package com.moemaair.lictionary.feature_lictionary.data.remote

data class DefinitionDto(
    val antonyms: List<String>,
    val definition: String,
    val example: String?,
    val synonyms: List<String>
){
    fun toDefination():DefinitionDto{
        return DefinitionDto(
            antonyms = antonyms,
            definition = definition,
            example = example,
            synonyms = synonyms
        )
    }
}
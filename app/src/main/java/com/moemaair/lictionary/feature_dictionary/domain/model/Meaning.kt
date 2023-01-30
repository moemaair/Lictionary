package com.moemaair.lictionary.feature_dictionary.domain.model


data class Meaning(  // Mapper class of Meaning dto
    val definitions: List<Defination>,
    val partOfSpeech: String
)
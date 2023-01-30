package com.moemaair.lictionary.feature_dictionary.domain.model

import com.moemaair.lictionary.feature_dictionary.data.remote.DefinitionDto


data class Meaning(  // Mapper class of Meaning dto
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String
)
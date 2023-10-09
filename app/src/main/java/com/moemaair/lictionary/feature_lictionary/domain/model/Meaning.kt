package com.moemaair.lictionary.feature_lictionary.domain.model

import com.moemaair.lictionary.feature_lictionary.data.remote.DefinitionDto


data class Meaning(  // Mapper class of Meaning dto
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String
)
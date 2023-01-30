package com.moemaair.lictionary.feature_dictionary.presentation


import com.moemaair.lictionary.feature_dictionary.domain.model.WordInfo

data class WordInfoState(
    val wordInfoItems: List<WordInfo> = emptyList(),
    val isLoading: Boolean = false
)




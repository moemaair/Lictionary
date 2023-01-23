package com.moemaair.lictionary.feature_dictionary.presentation

import WordInfo

data class WordInfoState(
    val wordInfoItems: List<WordInfo> = emptyList(),
    val isLoading: Boolean = false
)




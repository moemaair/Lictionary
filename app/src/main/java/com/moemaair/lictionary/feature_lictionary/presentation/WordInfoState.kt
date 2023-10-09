package com.moemaair.lictionary.feature_lictionary.presentation


import com.moemaair.lictionary.feature_lictionary.domain.model.WordInfo

data class WordInfoState(
    val wordInfoItems: List<WordInfo> = emptyList(),
    val isLoading: Boolean = false
)




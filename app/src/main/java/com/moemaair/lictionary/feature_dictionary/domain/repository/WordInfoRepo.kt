package com.moemaair.lictionary.feature_dictionary.domain


import com.moemaair.lictionary.core.util.Resource
import com.moemaair.lictionary.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepo {
    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}
package com.moemaair.lictionary.feature_dictionary.domain.repository

import WordInfo
import com.moemaair.lictionary.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface WordInfoRepo {
    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}
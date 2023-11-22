package com.moemaair.lictionary.feature_lictionary.domain.use_case

import com.moemaair.lictionary.core.util.Resource
import com.moemaair.lictionary.feature_lictionary.domain.repository.WordInfoRepo
import com.moemaair.lictionary.feature_lictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfo (
    private val repo: WordInfoRepo
){
    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        if(word.isBlank()) {
            return flow {  }
        }
        return repo.getWordInfo(word)
    }
}
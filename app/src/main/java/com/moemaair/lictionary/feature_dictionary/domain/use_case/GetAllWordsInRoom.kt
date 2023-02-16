package com.moemaair.lictionary.feature_dictionary.domain.use_case


import com.moemaair.lictionary.feature_dictionary.domain.repository.WordInfoRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllWordsInRoom(
    private val repo: WordInfoRepo
){
//    operator fun invoke(): WordInfo {
//        //return repo.getAllWordInfos()
//    }
}
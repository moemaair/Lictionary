package com.moemaair.lictionary.feature_lictionary.data.repository

import com.moemaair.lictionary.core.util.Resource
import com.moemaair.lictionary.feature_lictionary.data.local.WordInfoDao
import com.moemaair.lictionary.feature_lictionary.data.remote.LictionaryApi
import com.moemaair.lictionary.feature_lictionary.domain.model.WordInfo
import com.moemaair.lictionary.feature_lictionary.domain.repository.WordInfoRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepoImpl(
    private val api: LictionaryApi,
    private val dao:WordInfoDao
) : WordInfoRepo {



    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfos = dao.getWordInfos(word).map { it.toWordInfo()}
        emit(Resource.Loading(data = wordInfos))

        try {
            val remoteWordInfos = api.getWordInfo(word)
            dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })

        } catch(e: HttpException) {
            emit(Resource.Error(
                message = "Sorry pal, we couldn't find definitions for the word you were looking for.",
                data = wordInfos
            ))
        } catch(e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection.",
                data = wordInfos
            ))
        }

        val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfos))
    }

    override fun deleteAll(){
        dao.deleteAllWordInfos()
    }




}
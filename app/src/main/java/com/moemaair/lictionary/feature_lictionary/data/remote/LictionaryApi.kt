package com.moemaair.lictionary.feature_lictionary.data.remote

import com.moemaair.lictionary.feature_lictionary.data.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface LictionaryApi {
    @GET("/api/v2/entries/en/{word}")
    suspend fun getWordInfo(
        @Path("word") word: String
    ): List<WordInfoDto>

    companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev/"
    }


}
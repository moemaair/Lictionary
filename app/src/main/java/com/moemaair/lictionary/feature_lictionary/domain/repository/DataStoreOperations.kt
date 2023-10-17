package com.moemaair.lictionary.feature_lictionary.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun getTokenId(tokeid: String)
    fun readTokenId(): Flow<String>

}
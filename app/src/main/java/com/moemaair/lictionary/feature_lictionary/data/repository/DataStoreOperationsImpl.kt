package com.moemaair.lictionary.feature_lictionary.data.repository

import com.moemaair.lictionary.feature_lictionary.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow

class DataStoreOperationsImpl: DataStoreOperations {
    override suspend fun getTokenId(tokeid: String) {
        TODO("Not yet implemented")
    }

    override fun readTokenId(): Flow<String> {
        TODO("Not yet implemented")
    }

}
package com.moemaair.lictionary.feature_lictionary.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun getEmailofUser(email: String)
    fun readEmailofUser(): Flow<String>

    suspend fun getFullnameofUser(fullname: String)
    fun readFullnameofUser(): Flow<String>

}
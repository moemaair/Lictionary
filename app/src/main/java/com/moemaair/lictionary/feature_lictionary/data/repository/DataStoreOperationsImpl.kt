package com.moemaair.lictionary.feature_lictionary.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moemaair.lictionary.MainVm
import com.moemaair.lictionary.feature_lictionary.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "pref_name") // name of datastore

class DataStoreOperationsImpl(context: Context): DataStoreOperations {

    val mainVm: MainVm = MainVm()

    private object PreferencesKey {
        val tokeid = stringPreferencesKey(name = "tokenid_key")

    }
    private val dataStore = context.dataStore

    override suspend fun getTokenId(tokeid: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.tokeid] = tokeid

        }
    }
    override fun readTokenId(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if(exception is IOException){
                    emit(emptyPreferences())
                } else{
                    throw exception
                }
            }
            .map { preferences ->
                val tokenid = preferences[PreferencesKey.tokeid] ?: ""
                tokenid


            }
    }


}


package com.moemaair.lictionary.feature_lictionary.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.moemaair.lictionary.core.util.Constants.EMAIL
import com.moemaair.lictionary.core.util.Constants.FIRSTNAME
import com.moemaair.lictionary.core.util.Constants.FULLNAME
import com.moemaair.lictionary.core.util.Constants.PICTURE
import com.moemaair.lictionary.core.util.Constants.PREFERENCE_NAME
import com.moemaair.lictionary.feature_lictionary.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME) // name of datastore

class DataStoreOperationsImpl(context: Context): DataStoreOperations {

    private object PreferencesKey {
        val email = stringPreferencesKey(name = EMAIL)
        val fullname = stringPreferencesKey(name = FULLNAME)
        val givenName = stringPreferencesKey(name = FIRSTNAME)
        val picture = stringPreferencesKey(name = PICTURE)
    }
    private val dataStore = context.dataStore

    override suspend fun getEmailofUser(email: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.email] = email
        }
    }
    override fun readEmailofUser(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if(exception is IOException){
                    emit(emptyPreferences())
                } else{
                    throw exception
                }
            }
            .map { preferences ->
                val email = preferences[PreferencesKey.email] ?: ""
                email

            }
    }

    override suspend fun getFullnameofUser(fullname: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.fullname] = fullname
        }
    }

    override fun readFullnameofUser(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if(exception is IOException){
                    emit(emptyPreferences())
                } else{
                    throw exception
                }
            }
            .map { preferences ->
                val fullname = preferences[PreferencesKey.fullname] ?: ""
                fullname

            }
    }


    override suspend fun getGivenNameofUser(firstname: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.givenName] = firstname
        }
    }

    override fun readGivenNameofUser(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if(exception is IOException){
                    emit(emptyPreferences())
                } else{
                    throw exception
                }
            }
            .map { preferences ->
                val firstname = preferences[PreferencesKey.givenName] ?: ""
                firstname

            }
    }

    override suspend fun getUserPic(pic: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.picture] = pic
        }
    }

    override fun readUserPic(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if(exception is IOException){
                    emit(emptyPreferences())
                } else{
                    throw exception
                }
            }
            .map { preferences ->
                val picture = preferences[PreferencesKey.picture] ?: ""
                picture

            }
    }
}


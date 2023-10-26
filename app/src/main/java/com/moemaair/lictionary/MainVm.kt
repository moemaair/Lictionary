package com.moemaair.lictionary

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.auth0.android.jwt.DecodeException
import com.auth0.android.jwt.JWT
import com.moemaair.lictionary.core.util.Constants
import com.moemaair.lictionary.feature_lictionary.data.local.Claim
import com.moemaair.lictionary.feature_lictionary.data.local.UserDetail
import com.moemaair.lictionary.feature_lictionary.data.repository.dataStore
import com.moemaair.lictionary.feature_lictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Base64

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCE_NAME) // name of datastore

class MainVm: ViewModel() {

    fun getUserFromTokenId(
        tokenId: String,
    ): UserDetail {
        var jwt = JWT(tokenId)

        return UserDetail(
                    email = jwt.claims[Claim.EMAIL]?.asString(),
                    fullName = jwt.claims[Claim.FUll_NAME]?.asString(),
                    picture = jwt.claims[Claim.PICTURE]?.asString()
        )

    }

    val _greeting: MutableStateFlow<String> = MutableStateFlow("")
    val greeting  get() = _greeting.asStateFlow()


    fun times(): String {
        val hour = LocalDateTime.now().hour
        val twelve = "00"
        if (hour == twelve.toInt() || hour < 12) {
            return "Good Morning!"

        } else if (hour >= 12 || hour < 18) {
            return "Good Afternoon!"

        } else {
            return "Good Evening!"

        }
    }

    fun setGreeting(): String{
        _greeting.value = times()
        return _greeting.toString()
    }




}



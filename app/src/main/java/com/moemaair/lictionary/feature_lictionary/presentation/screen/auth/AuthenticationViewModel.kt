package com.moemaair.lictionary.feature_lictionary.presentation.screen.auth
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.moemaair.lictionary.core.util.Constants
import com.moemaair.lictionary.core.util.Constants.APP_ID
import com.moemaair.lictionary.feature_lictionary.data.local.Claim
import com.moemaair.lictionary.feature_lictionary.data.local.UserDetail
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthenticationViewModel : ViewModel() {


    var loadingState = mutableStateOf(false)
        private set

    var authenticated = mutableStateOf(false)
        private set

    fun setLoadingState(loading: Boolean) {
        loadingState.value = loading
    }

    fun signInWithMongoAtlas(
        tokenId: String,
        onSuccess: (Boolean) -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    App.create(Constants.APP_ID).login(
                       Credentials.jwt(tokenId)
                    ).loggedIn

                }
                withContext(Dispatchers.Main) {
                    if (result) {
                        onSuccess(result)
                        delay(500)
                        authenticated.value = true
                    } else {
                        onError(Exception("User is not logged in."))
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e)
                }
            }
        }


    }

}
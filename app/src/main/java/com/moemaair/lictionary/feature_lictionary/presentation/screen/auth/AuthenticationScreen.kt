package com.moemaair.lictionary.feature_lictionary.presentation.screen.auth

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import com.moemaair.lictionary.core.util.Constants.CLIENT_ID
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticationScreen(
    authenticated: Boolean,
    oneTapSignInState: OneTapSignInState, // library state for one tap google sign in
    messageBarState: MessageBarState, // library state with predefined data- { Snackbar }
    onDialogDismissed: (String) -> Unit,
    onTokenReceived: (String) -> Unit,
    loadingState: Boolean,
    onButtonClick: () -> Unit,
    navigateToHome: () -> Unit
) {
    //var user: GoogleUser? by remember { mutableStateOf(null) }
    Scaffold(
        content = {
            ContentWithMessageBar(messageBarState = messageBarState) {
                AuthenticationScreenContent(
                    loadingState = loadingState,
                    onButtonClick = onButtonClick
                )
            }
        }
    )

    OneTapSignInWithGoogle(
        state = oneTapSignInState,
        clientId = CLIENT_ID ,
        onTokenIdReceived = { tokenId ->
            //user = getUserFromTokenId(tokenId = tokenId)
            onTokenReceived(tokenId)

            //Log.d("LOG", viewmodel.getUserFromTokenId(it).toString())
        },
        onDialogDismissed = { message ->
            onDialogDismissed(message)
        }
    )

    LaunchedEffect(key1 = authenticated){
        if(authenticated){
            // navigate to homescreen only when authenticate is true
            navigateToHome()
        }
    }
    
}
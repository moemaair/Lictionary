package com.moemaair.lictionary.feature_lictionary.presentation.screen.auth

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.moemaair.lictionary.core.util.Constants.CLIENT_ID
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle

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
            onTokenReceived(tokenId)
            Log.d("TAG", "AuthenticationScreen: $tokenId")
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
package com.moemaair.lictionary.feature_dictionary.presentation.screen.auth

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.moemaair.lictionary.core.util.Constants.CLIENT_ID
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarPosition
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle

@OptIn(ExperimentalFoundationApi::class)
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
            ContentWithMessageBar(messageBarState = messageBarState, position = MessageBarPosition.BOTTOM,
                successContainerColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                successContentColor = Color.Black
            ) {
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
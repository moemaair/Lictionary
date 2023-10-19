package com.moemaair.lictionary.feature_lictionary.presentation.screen.auth

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moemaair.lictionary.MainVm
import com.moemaair.lictionary.core.util.Constants
import com.moemaair.lictionary.core.util.Constants.CLIENT_ID
import com.moemaair.lictionary.feature_lictionary.data.repository.DataStoreOperationsImpl
import com.moemaair.lictionary.feature_lictionary.domain.repository.DataStoreOperations
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarPosition
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticationScreen(     // where we have tokenid
    authenticated : Boolean,
    oneTapSignInState: OneTapSignInState, // library state for one tap google sign in
    messageBarState: MessageBarState, // library state with predefined data- { Snackbar }
    onDialogDismissed: (String) -> Unit,
    onTokenReceived: (String) -> Unit,
    loadingState: Boolean,
    onButtonClick: () -> Unit,
    navigateToHome: ()-> Unit
) {
    var context = LocalContext.current
    var scope = rememberCoroutineScope()

    var authVm: AuthenticationViewModel = viewModel()
    var mainVm: MainVm = MainVm()
    val token by DataStoreOperationsImpl(context).readTokenId().collectAsState(initial = "loading")

    Scaffold(
        content = {
            ContentWithMessageBar(messageBarState = messageBarState, position = MessageBarPosition.BOTTOM) {
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
        onTokenIdReceived = {it
            onTokenReceived(it)
           scope.launch {
               DataStoreOperationsImpl(context).getTokenId(tokeid = it)
               Log.d("DS", token)
           }
            //Log.d("LOG", mainVm.getUserFromTokenId(it).picture.toString())
        },
        onDialogDismissed = {message->
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
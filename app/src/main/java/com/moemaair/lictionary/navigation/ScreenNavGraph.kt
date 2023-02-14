package com.moemaair.lictionary.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moemaair.lictionary.R
import com.moemaair.lictionary.feature_dictionary.presentation.screen.History
import com.moemaair.lictionary.feature_dictionary.presentation.screen.auth.AuthenticationScreen
import com.moemaair.lictionary.feature_dictionary.presentation.screen.auth.AuthenticationViewModel
import com.moemaair.lictionary.feature_dictionary.presentation.screen.home.Home
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState

@Composable
fun ScreenNavGraph(
    startDestination: String,
    navController: NavHostController) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ){
        authenticationScreen(
            navigateToHome = {
                navController.previousBackStackEntry
                navController.navigate(Screen.Home.route)
            }
        )
        home(
            navigateToHistory = {
               navController.popBackStack()
               navController.navigate(Screen.History.route)
            }
        )
        history()

    }
}


fun NavGraphBuilder.authenticationScreen(
    navigateToHome: () -> Unit
){
    composable(route = Screen.Authentication.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val authenticated by viewModel.authenticated
        val oneTapSignInState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        val loadingState by viewModel.loadingState
        AuthenticationScreen(
            authenticated = authenticated,
            oneTapSignInState = oneTapSignInState,
            messageBarState = messageBarState,
            onDialogDismissed = { message->
                messageBarState.addError(Exception(message))
            },
            onTokenReceived ={ tokenId->
                viewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = { it->
                        if(it){
                            messageBarState.addSuccess("Succefully Authenticated")
                        }
                        viewModel.setLoadingState(false)
                    },
                    onError = { it ->
                        messageBarState.addError(it)
                        viewModel.setLoadingState(true)
                    }
                )
            },
            loadingState = loadingState,
            onButtonClick = {
                oneTapSignInState.open()
                viewModel.setLoadingState(true)
            },
            navigateToHome = navigateToHome
        )
    }
}

fun NavGraphBuilder.home(navigateToHistory: () -> Unit){
    composable(route = Screen.Home.route){
       Home(
           navigateToHistory =navigateToHistory ,
           icon = R.drawable.audio_icon
       )
    }
}

fun NavGraphBuilder.history(){
    composable(route = Screen.History.route){
        History()
    }
}
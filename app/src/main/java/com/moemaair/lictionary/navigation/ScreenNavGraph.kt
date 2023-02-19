package com.moemaair.lictionary.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moemaair.lictionary.R
import com.moemaair.lictionary.core.util.Constants.APP_ID
import com.moemaair.lictionary.feature_dictionary.presentation.screen.auth.AuthenticationScreen
import com.moemaair.lictionary.feature_dictionary.presentation.screen.auth.AuthenticationViewModel
import com.moemaair.lictionary.feature_dictionary.presentation.screen.home.Home
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
        )
        home(
            navController = navController
        )

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

fun NavGraphBuilder.home(
    navController: NavHostController
){
    composable(route = Screen.Home.route){
        val scope = rememberCoroutineScope()
       Home(
           icon = R.drawable.audio_icon,
           onClickLogOut = {
               scope.launch (Dispatchers.IO) {
                   App.create(APP_ID).currentUser?.logOut()
               }
           },
           navController = navController
       )
    }
}

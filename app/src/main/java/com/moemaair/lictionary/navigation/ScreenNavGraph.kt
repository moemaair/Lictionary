package com.moemaair.lictionary.navigation

import AuthenticationViewModel
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moemaair.lictionary.MainScreen
import com.moemaair.lictionary.feature_dictionary.domain.model.WordInfo
import com.moemaair.lictionary.feature_dictionary.presentation.screen.History
import com.moemaair.lictionary.feature_dictionary.presentation.screen.auth.AuthenticationScreen
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState

@Composable
fun ScreenNavGraph(startDestination: String, navController: NavHostController) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ){
        authenticationScreen()
        history()
    }
}

//home graph

fun NavGraphBuilder.authenticationScreen(){
    composable(route = Screen.Authentication.route) {
        val authenticationViewModel: AuthenticationViewModel = viewModel()
        val oneTapSignInState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        val loadingState by authenticationViewModel.loadingState
        AuthenticationScreen(
            oneTapSignInState = oneTapSignInState,
            messageBarState =messageBarState ,
            onDialogDismissed = {
                    Log.d("tag", it)
            },
            onTokenReceived = { token ->
                Log.d("token", token)
            },
            loadingState = loadingState,
            onButtonClick = {
                oneTapSignInState.open()
                authenticationViewModel.setLoadingState(true)
            }
        )
    }
}

fun NavGraphBuilder.home(){
    composable(route = Screen.Home.route){
        MainScreen()
    }
}

fun NavGraphBuilder.history(){
    composable(route = Screen.History.route){
        History()
    }
}
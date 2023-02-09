package com.moemaair.lictionary.navigation

import AuthenticationViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moemaair.lictionary.feature_dictionary.presentation.screen.auth.AuthenticationScreen

@Composable
fun ScreenNavGraph(startDestination: String, navController: NavHostController) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ){

    }
}

//home graph

fun NavGraphBuilder.authenticationScreen(){
    composable(route = Screen.Authentication.route) {
        val authenticationViewModel: AuthenticationViewModel = viewModel()
        AuthenticationScreen(
            oneTapSignInState = ,
            messageBarState = ,
            onDialogDismissed = ,
            onTokenReceived = ,
            loadingState =
        ) {

        }
    }
}
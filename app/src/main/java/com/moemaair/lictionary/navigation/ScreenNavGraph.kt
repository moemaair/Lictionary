package com.moemaair.lictionary.navigation


import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.auth0.android.jwt.JWT
import com.moemaair.lictionary.MainVm
import com.moemaair.lictionary.R
import com.moemaair.lictionary.core.util.Constants.APP_ID
import com.moemaair.lictionary.core.util.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.moemaair.lictionary.feature_lictionary.data.repository.DataStoreOperationsImpl
import com.moemaair.lictionary.feature_lictionary.presentation.screen.auth.AuthenticationScreen
import com.moemaair.lictionary.feature_lictionary.presentation.screen.auth.AuthenticationViewModel
import com.moemaair.lictionary.feature_lictionary.presentation.screen.home.Home
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
        WriteRoute()

    }
}


fun NavGraphBuilder.authenticationScreen(
    navigateToHome: () -> Unit
){

    composable(route = Screen.Authentication.route) {
        var context = LocalContext.current
        val tokenid by DataStoreOperationsImpl(context).readTokenId().collectAsState(initial = "loading")
        val viewModel: AuthenticationViewModel = viewModel()
        val mainVm: MainVm = MainVm()
        val authenticated by viewModel.authenticated
        val oneTapSignInState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        val loadingState by viewModel.loadingState


        //Log.i("DS", mainVm.getUserFromTokenId(tokenid, jwt.header.toString(), jwt.signature.toString()).toString())
        AuthenticationScreen(
            authenticated = authenticated,
            oneTapSignInState = oneTapSignInState,
            messageBarState = messageBarState,
            loadingState = oneTapSignInState.opened,
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
                    },
                    context = context
                )
            },
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
        val viewModel: AuthenticationViewModel = viewModel()
        val scope = rememberCoroutineScope()
       Home(
           icon = R.drawable.audio_icon,
           onClickLogOut = {
               scope.launch (Dispatchers.IO) {
                   App.create(APP_ID).currentUser?.logOut()
               }
           },
           navController = navController,

       )
    }
}

fun NavGraphBuilder.WriteRoute(){
    composable(route = Screen.Write.route,
        arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT_KEY){
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ){}
}

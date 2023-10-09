package com.moemaair.lictionary


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.moemaair.lictionary.core.util.Constants.APP_ID
import com.moemaair.lictionary.feature_lictionary.presentation.MainViewModel
import com.moemaair.lictionary.navigation.Screen
import com.moemaair.lictionary.navigation.ScreenNavGraph
import com.moemaair.lictionary.ui.theme.LictionaryTheme
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.mongodb.App


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java] // getting the instance of MainViewModel

        setContent {
            LictionaryTheme {
                val navController = rememberNavController()
                ScreenNavGraph(
                    startDestination = getStartDestination(),
                    navController = navController
                )
            }
        }

    }
}

private fun getStartDestination(): String{
    val user = App.create(APP_ID).currentUser
    return if(user != null && user.loggedIn)
        Screen.Home.route else
        Screen.Authentication.route

}

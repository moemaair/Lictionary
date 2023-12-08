package com.moemaair.lictionary.navigation
sealed class Screen (val route: String){
    object Home: Screen(route = "Home")
    object Authentication : Screen(route = "Authentication")

    object Onboarding : Screen(route = "Onboarding")
}
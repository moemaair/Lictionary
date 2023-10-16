package com.moemaair.lictionary.navigation

import com.moemaair.lictionary.core.util.Constants.WRITE_SCREEN_ARGUMENT_KEY

sealed class Screen (val route: String){
    object Home: Screen(route = "Home")
    object Authentication : Screen(route = "Authentication")

    object Write: Screen(route = "Write?$WRITE_SCREEN_ARGUMENT_KEY ={$WRITE_SCREEN_ARGUMENT_KEY}"){
        fun passDiaryId(lictionaryId: String): String{
            return "Write?$WRITE_SCREEN_ARGUMENT_KEY  = $lictionaryId"
        }
    }
}
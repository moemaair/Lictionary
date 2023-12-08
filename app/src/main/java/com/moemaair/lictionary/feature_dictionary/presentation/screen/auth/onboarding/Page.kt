package com.moemaair.lictionary.feature_dictionary.presentation.screen.auth.onboarding

import androidx.annotation.DrawableRes
import com.moemaair.lictionary.R

data class Page(
    val description: String,
    @DrawableRes val image:Int
){
    val pages: List<Page> = listOf(
        Page(
            description = "pager 1" ,
            image  = R.drawable.dictionary_rafiki
        ),
        Page(
            description = "pager 2",
            image = R.drawable.dictionary_rafiki
        ),
        Page(
            description = "pager 2",
            image = R.drawable.dictionary_rafiki
        ),
    )
}

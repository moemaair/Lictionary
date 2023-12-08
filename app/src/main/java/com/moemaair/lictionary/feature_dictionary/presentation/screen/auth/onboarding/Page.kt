package com.moemaair.lictionary.feature_dictionary.presentation.screen.auth.onboarding

import android.util.JsonReader
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import com.moemaair.lictionary.R
import com.moemaair.lictionary.feature_dictionary.presentation.LegoLottie
import com.moemaair.lictionary.feature_dictionary.presentation.Search_with_ease_lottie
import java.io.InputStream

data class Page(
    val description: String,
)

val pages = listOf(
    Page(
        description = "Type in a word or phrase to discover its meaning, pronunciation, and more" ,

    ),
    Page(
        description = "Dive deep into comprehensive word definitions, including example sentences and usage\n" ,

    ),
    Page(
        description = "Hear the correct pronunciation of words to enhance your language skills" ,

    ),
    Page(
        description = "No internet? No problem! All previous search content will be offline use and never be without your trusted Lictionary."
    )

)

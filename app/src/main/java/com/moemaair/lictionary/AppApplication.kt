package com.moemaair.lictionary

import android.app.Application
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.moemaair.lictionary.feature_dictionary.presentation.screen.auth.onboarding.HorizontalPagerComponent
import com.moemaair.lictionary.navigation.Screen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication: Application()
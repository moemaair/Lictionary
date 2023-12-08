package com.moemaair.lictionary.feature_dictionary.presentation.screen.auth.onboarding

import android.graphics.drawable.GradientDrawable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


//--------------- where all logic goes for pager designing
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerComponent(
    navHostController: NavHostController
) {
    val pageCount = 3
    val pageState = rememberPagerState(
        initialPage = 0,
    ) {
        // provide pageCount
        pageCount
    }

    androidx.compose.material.Surface {
        Column {
            HorizontalPager(
                state = pageState,
                userScrollEnabled = true,
                reverseLayout = false,
                //beyondBoundsPageCount = 0,
                //flingBehavior = PagerDefaults.flingBehavior(state = pageState),
                // key = null,

            ){
                PagerScreen(
                    page = pages[it],
                    pagerState = pageState,
                    navHostController = navHostController
                )
            }
        }
    }
}
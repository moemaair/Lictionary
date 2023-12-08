package com.moemaair.lictionary.feature_dictionary.presentation.screen.auth.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.R
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.moemaair.lictionary.feature_dictionary.presentation.LegoLottie
import com.moemaair.lictionary.feature_dictionary.presentation.Search_with_ease_lottie
import com.moemaair.lictionary.navigation.Screen
import kotlinx.coroutines.launch


//---------------------The page that user interact with.
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerScreen(page: Page, pagerState: PagerState, navHostController:NavHostController) {
    var current = pagerState.currentPage
    val scope = rememberCoroutineScope()
    val navHostController =
    Box(modifier = Modifier.fillMaxSize()){
        //------- section lottie and des
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier
                .size(300.dp)
            ){
                when(current){
                    0 -> Search_with_ease_lottie()
                    1 -> Search_with_ease_lottie()
                    2 -> Search_with_ease_lottie()
                }

            }
            Text(text = page.description,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        }


        //---------- buttons next and back
        Box(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)){
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if(pagerState.currentPage > 0) OutlinedButton(onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage -1
                        )
                    }
                }) {
                    Text(text = "Back")
                }

                if(pagerState.currentPage < 2) {
                    Button(onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1
                            )
                        }
                    },
                        colors = ButtonDefaults.elevatedButtonColors()
                    ) {
                        Text(text = "Next", color = androidx.compose.material.MaterialTheme.colors.primaryVariant)
                    }
                }else{
                    // to authenticate screen
                    Button(onClick = {
                        navHostController.navigate(Screen.Authentication.route)

                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = androidx.compose.material.MaterialTheme.colors.primaryVariant
                        )
                    ) {
                        Text(text = "Continue", )
                    }
                }
            }
        }
    }

}
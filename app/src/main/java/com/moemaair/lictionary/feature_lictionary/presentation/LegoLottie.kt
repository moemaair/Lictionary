package com.moemaair.lictionary.feature_lictionary.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*

@Composable
fun LegoLottie(

) {
    val compositionResult: LottieCompositionResult =
        rememberLottieComposition(LottieCompositionSpec.Asset("lottie/Lottie_Lego.json"))

    val progress by animateLottieCompositionAsState(
        compositionResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
    )

    LottieAnimation(
        compositionResult.value,
        progress,
        modifier = Modifier.size(100.dp)
    )


}


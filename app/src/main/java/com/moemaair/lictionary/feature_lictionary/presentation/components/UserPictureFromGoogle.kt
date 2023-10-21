package com.moemaair.lictionary.feature_lictionary.presentation.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.moemaair.lictionary.R

@Composable
fun UserPictureFromGoogle(pic: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(pic)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.man),
        contentDescription = "image from google",
        contentScale = ContentScale.Crop,
        modifier = Modifier.clip(shape = CircleShape)
    )
    
}
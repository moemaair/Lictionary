package com.moemaair.lictionary.feature_dictionary.presentation.screen.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moemaair.lictionary.R
import com.moemaair.lictionary.feature_dictionary.presentation.components.GoogleButton

@Composable
fun AuthenticationScreenContent(
    loadingState: Boolean,
    onButtonClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Column(modifier = Modifier.padding(30.dp, 0.dp),verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            //dummy text and icon
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.dictionary_rafiki),
                    contentDescription = "",
                    modifier = Modifier
                )
                // sign in with google button
                   Column(modifier = Modifier.fillMaxWidth().weight(10f),
                       verticalArrangement = Arrangement.SpaceBetween,
                       horizontalAlignment = Alignment.CenterHorizontally) {
                       GoogleButton(
                           loadingState = loadingState,
                           onClick = onButtonClick
                       )
                   }
                Column(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "unlock the power of words", style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.primaryVariant,
                        fontStyle = FontStyle.Italic
                    )
                    Text(text = "Lictionary", style = MaterialTheme.typography.h5)
                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun preview() {
    AuthenticationScreenContent(false,{})
}
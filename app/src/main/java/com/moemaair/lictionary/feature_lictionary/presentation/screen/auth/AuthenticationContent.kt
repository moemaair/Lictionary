package com.moemaair.lictionary.feature_lictionary.presentation.screen.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moemaair.lictionary.R
import com.moemaair.lictionary.feature_lictionary.presentation.components.GoogleButton

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
                   Column(modifier = Modifier.fillMaxWidth(),
                       verticalArrangement = Arrangement.SpaceBetween,
                       horizontalAlignment = Alignment.CenterHorizontally) {
                       GoogleButton(
                           loadingState = loadingState,
                           onClick = onButtonClick
                       )
                   }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "powered by Lictionary", style = MaterialTheme.typography.labelMedium)
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
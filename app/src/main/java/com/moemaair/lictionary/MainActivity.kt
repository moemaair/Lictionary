package com.moemaair.lictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.moemaair.lictionary.ui.theme.LictionaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LictionaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { TopAppBar() {
            OutlinedTf()
        }}
    ) {}
}

@Composable
fun OutlinedTf(text: String) {
    var value = remember {
        mutableStateOf("")
    }

    TextField(value = value, onValueChange = {value = value})

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LictionaryTheme {
        HomeScreen()
    }
}
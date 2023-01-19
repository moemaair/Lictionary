package com.moemaair.lictionary

import MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moemaair.lictionary.ui.theme.LictionaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LictionaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    //color = MaterialTheme.colors.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)

        ) {
            Box(modifier = Modifier
                .fillMaxWidth()) {
                OutlinedTf()
            }
        }}
    ) {

    }
}

@Composable
fun OutlinedTf() {
          val viewModel: MainViewModel = MainViewModel()

        var _state by remember {
            mutableStateOf("")
        }
          OutlinedTextField(
              value = _state,
              onValueChange = { newIt ->
                  _state = newIt
              },
              modifier = Modifier.background(Color.Transparent)
                  .fillMaxWidth(),
              trailingIcon ={
                  Icon(Icons.Outlined.Search, contentDescription = "")
              }
          )


}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LictionaryTheme {
        HomeScreen()
    }
}
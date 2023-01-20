package com.moemaair.lictionary

import MainViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moemaair.lictionary.ui.theme.LictionaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

               HomeScreen()

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
        //adding the no data illustration
        Column(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center)) {
            Image(painter = painterResource(id = R.drawable.image_null), contentDescription = "", modifier = Modifier
                .size(300.dp))
        }

    }
}

@Composable
fun OutlinedTf() {
          val viewModel: MainViewModel = MainViewModel()
        var context = LocalContext.current
        var _state by remember {
            mutableStateOf("")
        }
          OutlinedTextField(
              value = _state,
              onValueChange = { newIt ->
                  _state = newIt
              },
              modifier = Modifier
                  .background(Color.Transparent)
                  .fillMaxWidth(),
              trailingIcon ={
                  IconButton(onClick = { Toast.makeText(context, "", Toast.LENGTH_SHORT).show() }) {
                      Icon(Icons.Outlined.Search, contentDescription = "")
                  }

              },
              singleLine = true
          )


}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LictionaryTheme {
        HomeScreen()
    }
}
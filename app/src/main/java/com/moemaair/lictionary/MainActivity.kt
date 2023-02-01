package com.moemaair.lictionary


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moemaair.lictionary.feature_dictionary.presentation.MainViewModel
import com.moemaair.lictionary.feature_dictionary.presentation.WordInfoItem
import com.moemaair.lictionary.ui.theme.LictionaryTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LictionaryTheme {
                MainScreen()
            }
        }

    }
}

@Composable
fun MainScreen() {
    var viewModel: MainViewModel = hiltViewModel()
    var state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    val audioVector = ImageVector.vectorResource(id = R.drawable.audio_icon)

    var textState by remember { mutableStateOf(TextFieldValue()) }
    var txt by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()

    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current.applicationContext

    val isVisible by remember {
        derivedStateOf {
            viewModel.searchQuery.value.isNotBlank()
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is MainViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar("Lictionary",
                backgroundColor = MaterialTheme.colors.primaryVariant
            ) {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        },
        drawerContent = {
            DrawerContent()
        }

    ){
        Column{
            Box(modifier = Modifier.fillMaxSize()){
                Box(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxSize()
                )
                {
                    if(state.isLoading ) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    else{
                        LazyColumn(modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(20.dp, 100.dp, 20.dp, 0.dp))
                        {

                            items(state.wordInfoItems.size) { i ->
                                val wordInfo = state.wordInfoItems[i]
                                if(i > 0) {
                                    Text(
                                        text = "Previously Searched",
                                        color = if (isSystemInDarkTheme()) Color.White.copy(alpha = ContentAlpha.disabled) else Color.Black.copy(alpha = ContentAlpha.disabled),
                                        modifier = Modifier
                                            .padding(0.dp, 10.dp)
                                            .align(Alignment.Center),
                                        style = MaterialTheme.typography.subtitle2,
                                    )
                                    Spacer(modifier = Modifier.height(0.dp))
                                }
                                WordInfoItem(
                                    wordInfo = wordInfo,
                                    audioVector
                                )
                                if(i < state.wordInfoItems.size - 1) {
                                    Divider()
                                }
                            }

                        }
                    }



                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .height(90.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colors.primaryVariant,
                                MaterialTheme.colors.primary
                            )
                        )
                    )
                )
                {
                    OutlinedTextField(
                        value = viewModel.searchQuery.value.trim(),
                        onValueChange = viewModel::onSearch,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .offset(0.dp, (30).dp)
                            .padding(10.dp, 6.dp)
                            .shadow(5.dp),
                        placeholder = { Text(text = "Search for words...", color = Color.LightGray)},
                        leadingIcon = { IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "")
                        }},
                        trailingIcon = {
                                       if(isVisible){
                                          IconButton(onClick = {
                                              viewModel._searchQuery.value = ""
                                          }) {
                                              Icon(imageVector = Icons.Default.Close, contentDescription = "")
                                          }
                                       }
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            textColor = Color.Black,
                            trailingIconColor = MaterialTheme.colors.primaryVariant,
                            leadingIconColor = MaterialTheme.colors.primaryVariant,
                            focusedIndicatorColor = Color.LightGray,
                            cursorColor = Color.Black
                        ),

                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        singleLine = true,
                        maxLines = 1
                    )

                }
            }
        }

    }
}

@Composable
fun DrawerContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Drawer Test")
    }
}


@Composable
fun AppBar(
    title : String,
    backgroundColor: Color,
    onMenuClick : () -> Unit) {

    TopAppBar(
        elevation = 7.dp,
        backgroundColor = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onMenuClick()}) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
            }
            Text(text = title,
                style = MaterialTheme.typography.h3,
                color = if(!isSystemInDarkTheme()) Color.White else Color.Black
            )
            Spacer(modifier = Modifier.size(24.dp))
        }

    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LictionaryTheme {
        MainScreen()
    }
}
package com.moemaair.lictionary.feature_dictionary.presentation.screen.home

import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.moemaair.lictionary.core.util.Constants.APP_ID
import com.moemaair.lictionary.core.util.shareApp
import com.moemaair.lictionary.feature_dictionary.presentation.MainViewModel
import com.moemaair.lictionary.feature_dictionary.presentation.WordInfoItem
import com.moemaair.lictionary.navigation.Screen
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun Home(
    icon: Int,
    historyClicked : Boolean = false,
    onClickLogOut: () -> Unit,
    navController: NavHostController
) {
    var viewModel: MainViewModel = hiltViewModel()
    var state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    val audioVector = ImageVector.vectorResource(id = icon)

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
        content = {
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
//                        if(!isVisible){
//                            Text(text = "Try searching for a word",
//                                color = if(isSystemInDarkTheme()) MaterialTheme.colors.primary else Color.LightGray,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .align(Alignment.TopCenter)
//                                    .padding(top = 150.dp),
//                                textAlign = TextAlign.Center, fontSize = 14.sp)
//                        }
                        else{
                            LazyColumn(modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(20.dp, 100.dp, 20.dp, 0.dp))
                            {
                                items(state.wordInfoItems.size) { i ->
                                    val wordInfo = state.wordInfoItems[i]
                                    if(i < 1) {
                                        Text(
                                            text = "Other Definations...",
                                           // color = if (isSystemInDarkTheme()) Color.White.copy(alpha = ContentAlpha.disabled) else Color.Black.copy(alpha = ContentAlpha.disabled),
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
                            placeholder = { Text(text = "Search for words...", color = Color.LightGray) },
                            leadingIcon = { IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Default.Search, contentDescription = "")
                            }
                            },
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
        },
        drawerContent = {
            DrawerContent(
               com.moemaair.lictionary.R.drawable.man,
                onClickLogOut = onClickLogOut,
                navController= navController
            )
        },
    )
}

@Composable
fun DrawerContent(
    icon: Int,
    onClickLogOut: () -> Unit,
    navController: NavHostController
) {
    var viewModel = viewModel<MainViewModel>()
    var ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(start = 10.dp)) {
        //icon image
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = icon), contentDescription = "", modifier = Modifier
                    .height(100.dp)
                    .scale(0.8f))
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "Account Owner", style = MaterialTheme.typography.subtitle1)
            }
            Divider()
            Spacer(modifier = Modifier.height(10.dp))
        }

        //support
        item {
            Column(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 20.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "Support", style = MaterialTheme.typography.h4)
                //row 1 (send feedback)
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val sendIntent = Intent(Intent.ACTION_SEND)
                        sendIntent.type = "text/plain"
                        sendIntent.putExtra(
                            Intent.EXTRA_EMAIL,
                            arrayOf("ibrahimohamed81@outlook.com")
                        )
                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback")

                        val chooser = Intent.createChooser(sendIntent, "Send Email")
                        ContextCompat.startActivity(ctx, chooser, null)
                    }
                    .padding(0.dp, 20.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "email icon")
                    Text(text = "Send Feedback")
                }
                Divider()
                //row 2 rate app
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp)
                    .clickable {
                        val openPlayStore = Intent(Intent.ACTION_VIEW)
                        openPlayStore.data =
                            Uri.parse("https://play.google.com/store/apps/details?id=com.moemaair.lictionary")
                        ctx.startActivity(openPlayStore)
                    }, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "email icon")
                    Text(text = "Rate this app")
                }
                Divider()
                //row 3 share app
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp)
                    .clickable {
                        ctx.shareApp("https://play.google.com/store/apps/details?id=com.moemaair.lictionary")
                    },
                    horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "email icon")
                    Text(text = "Share this app")
                }
                Divider()
            }
        }
        //setting

        item {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)) {
                Text(text = "Setting", style = MaterialTheme.typography.h4)
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(imageVector = Icons.Default.DarkMode, contentDescription = "dark mode")
                        Text(text = "Dark mode")
                    }
                    // Declaring a boolean value for storing checked state
                    var mCheckedState = remember{ mutableStateOf(false) } // not toggled darkmode icon

                    // Creating a Switch, when value changes,
                    // it updates mCheckedState value
                    Switch(checked = mCheckedState.value,
                        onCheckedChange = {mCheckedState.value = it
                            viewModel.setDarkmode(mCheckedState.value)

                        },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = MaterialTheme.colors.primaryVariant,
                            checkedThumbColor = MaterialTheme.colors.primary
                        )

                    )
                }

                Divider()
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp)
                    .clickable {
                    },
                    horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(imageVector = Icons.Default.History, contentDescription = "History")
                    Text(text = "History")
                }
            }
            Divider()
        }

        //other
        item {

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)) {
                Text(text = "Other", style = MaterialTheme.typography.h4)
                Row(modifier = Modifier.padding(0.dp, 30.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = "dark mode")
                    Text(text = "App version 1.0.0" )
                }
                Divider()
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = true, onClick = {
                        onClickLogOut()
                        navController.navigate(Screen.Authentication.route)
                    }
                    )
                    .padding(0.dp, 30.dp),horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(imageVector = Icons.Default.PowerSettingsNew, contentDescription = "log out")
                    Text(text = "Log out")
                }
                Divider()

            }
        }
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


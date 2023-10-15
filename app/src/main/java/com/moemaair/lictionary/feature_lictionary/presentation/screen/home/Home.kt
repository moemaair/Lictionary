package com.moemaair.lictionary.feature_lictionary.presentation.screen.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
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
import androidx.navigation.NavHostController
import com.moemaair.lictionary.R
import com.moemaair.lictionary.core.util.shareApp
import com.moemaair.lictionary.feature_lictionary.presentation.MainViewModel
import com.moemaair.lictionary.feature_lictionary.presentation.WordInfoItem
import com.moemaair.lictionary.navigation.Screen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(
    icon: Int,
    onClickLogOut: () -> Unit,
    navController: NavHostController
) {
    var viewModel: MainViewModel = hiltViewModel()
    var state = viewModel.state.value
    //val scaffoldState = rememberScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }

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
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is MainViewModel.UIEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AppBar("Lictionary",
                backgroundColor = MaterialTheme.colorScheme.inversePrimary
            ) {

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
                        if(!isVisible){
                            Text(text = "Try searching for a word",
                                color = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.LightGray,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.TopCenter)
                                    .padding(top = 150.dp),
                                textAlign = TextAlign.Center, fontSize = 14.sp)
                        }
                        else{
                            LazyColumn(modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(20.dp, 100.dp, 20.dp, 0.dp))
                            {
                                items(state.wordInfoItems.size) { i ->
                                    val wordInfo = state.wordInfoItems[i]
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
                                    MaterialTheme.colorScheme.inversePrimary,
                                    MaterialTheme.colorScheme.primary
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
                            }
                        )

//                            colors = TextFieldDefaults.textFieldColors(
//                                backgroundColor = Color.White,
//                                textColor = Color.Black,
//                                trailingIconColor = MaterialTheme.colors.primaryVariant,
//                                leadingIconColor = MaterialTheme.colors.primaryVariant,
//                                focusedIndicatorColor = Color.LightGray,
//                                cursorColor = Color.Black
//                            ),
//
//                            keyboardOptions = KeyboardOptions(
//                                imeAction = ImeAction.Search
//                            ),
//                            singleLine = true,
//                            maxLines = 1
//                        )

                    }
                }
            }
            ModalNavigationDrawerSample(
                onClickLogOut = {

                },
                navController = navController
            )

        },

    )
}

@Composable
fun ModalNavigationDrawerSample(
    onClickLogOut: () -> Unit,
    navController: NavHostController
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    // icons to mimic drawer destinations
    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
    val selectedItem = remember { mutableStateOf(items[0]) }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item, contentDescription = null) },
                        label = { Text(item.name) },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                        },
                        //modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            var viewModel = viewModel<MainViewModel>()
            var ctx = LocalContext.current
            val scope = rememberCoroutineScope()
            Column(modifier = Modifier
                //.background(brush = Brush.verticalGradient(backgroundColor))
                .padding(start = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    //icon image
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                            Image(painter = painterResource(id = R.drawable.man), contentDescription = "", modifier = Modifier
                                .height(100.dp)
                                .scale(0.8f))
                            Spacer(modifier = Modifier.height(30.dp))
                            Text(text = "Account Owner", style = MaterialTheme.typography.labelSmall)
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
                            Text(text = "Support", style = MaterialTheme.typography.headlineLarge)
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
                    //other
                    item {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)) {
                            Text(text = "Other", style = MaterialTheme.typography.headlineMedium)
                            Row(modifier = Modifier.padding(0.dp, 30.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Icon(imageVector = Icons.Default.Info, contentDescription = "dark mode")
                                Text(text = "App version 1.1.2" )
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

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 5.dp), contentAlignment = Alignment.BottomCenter){
                    Text(text = "Unlock the power of words", style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
        }
    )
}


@Composable
fun DrawerContent(
    icon: Int,
    onClickLogOut: () -> Unit,
    navController: NavHostController,
    backgroundColor: List<Color> = listOf(
        MaterialTheme.colorScheme.inversePrimary,
        MaterialTheme.colorScheme.primary
    )
) {
    var viewModel = viewModel<MainViewModel>()
    var ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier
        .background(brush = Brush.verticalGradient(backgroundColor))
        .padding(start = 10.dp),
    verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            //icon image
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = icon), contentDescription = "", modifier = Modifier
                        .height(100.dp)
                        .scale(0.8f))
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(text = "Account Owner", style = MaterialTheme.typography.labelSmall)
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
                    Text(text = "Support", style = MaterialTheme.typography.headlineLarge)
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
            //other
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)) {
                    Text(text = "Other", style = MaterialTheme.typography.headlineMedium)
                    Row(modifier = Modifier.padding(0.dp, 30.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "dark mode")
                        Text(text = "App version 1.1.2" )
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

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 5.dp), contentAlignment = Alignment.BottomCenter){
            Text(text = "Unlock the power of words", style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title : String,
    backgroundColor: Color,
    onMenuClick : () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    TopAppBar(
        //elevation = 7.dp,
        //backgroundColor = backgroundColor
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}


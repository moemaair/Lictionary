package com.moemaair.lictionary.feature_lictionary.presentation.screen.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.moemaair.lictionary.R
import com.moemaair.lictionary.core.util.Constants
import com.moemaair.lictionary.core.util.shareApp
import com.moemaair.lictionary.feature_lictionary.data.repository.DataStoreOperationsImpl
import com.moemaair.lictionary.feature_lictionary.presentation.LegoLottie
import com.moemaair.lictionary.feature_lictionary.presentation.MainViewModel
import com.moemaair.lictionary.feature_lictionary.presentation.WordInfoItem
import com.moemaair.lictionary.navigation.Screen
import com.moemaair.lictionary.ui.theme.AngryColor
import com.moemaair.lictionary.ui.theme.md_theme_light_tertiaryContainer
import kotlinx.coroutines.flow.collectLatest

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCE_NAME) // name of datastore
/*...........................Home....................................................*/
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
    var drawerState = rememberDrawerState(DrawerValue.Closed)
    var isDrawerOpen by remember { mutableStateOf(false) }

    val email by DataStoreOperationsImpl(context).readEmailofUser().collectAsState(initial = Constants.EMAIL)
    val fullname by DataStoreOperationsImpl(context).readFullnameofUser().collectAsState(initial = Constants.FULLNAME)


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
                backgroundColor = MaterialTheme.colorScheme.inversePrimary,
                onMenuClick = {
                    isDrawerOpen = !isDrawerOpen
                },
                onClickLogOut = { onClickLogOut()},
                navController = navController
            )
        },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()

                , verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /*...........................col 25%....................................................*/
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.dp)
                        .weight(0.25f)
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

                    Box( modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp, 4.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Column {
                            Text(text = "Hello, $fullname")
                            Spacer(modifier = Modifier.height(30.dp))
                            OutlinedTextField(
                                value = viewModel.searchQuery.value.trim(),
                                onValueChange = viewModel::onSearch,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(100.dp))

                                    .background(Color.White)
                                    .padding(10.dp, 2.dp),
                                placeholder = {
                                    Text(
                                        text = "Search for words...",
                                        color = Color.LightGray, style = MaterialTheme.typography.labelMedium
                                    )
                                },
                                leadingIcon = {
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(imageVector = Icons.Default.Search, contentDescription = "")
                                    }
                                },
                                trailingIcon = {
                                    if (isVisible) {
                                        IconButton(onClick = {
                                            viewModel._searchQuery.value = ""
                                        }) {
                                            Icon(imageVector = Icons.Default.Close, contentDescription = "")
                                        }
                                    }
                                },

                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Search
                                ),
                                singleLine = true,
                                maxLines = 1,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent)
                            )
                        }

                    }

                }


                /*...........................col 75%....................................................*/

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.75f)

                ) {

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .height(15.dp)
                                    .align(Alignment.Center)
                                ,
                                trackColor = md_theme_light_tertiaryContainer)
                        }
                        if (!isVisible) {
                            Column (modifier = Modifier
                                .fillMaxSize()
                                .align(alignment = Alignment.Center) ){
                                LegoLottie()
                                Text(
                                    text = "Try searching for a word ",
                                    modifier = Modifier
                                        .fillMaxWidth(),

                                    textAlign = TextAlign.Center, fontSize = 14.sp
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier

                            )
                            {
                                items(state.wordInfoItems.size) { i ->
                                    val wordInfo = state.wordInfoItems[i]
                                    WordInfoItem(
                                        wordInfo = wordInfo,
                                        audioVector
                                    )
                                    if (i < state.wordInfoItems.size - 1) {
                                        Divider()
                                    }
                                }

                            }
                        }



                    }



                }

            }

            if (isDrawerOpen) {
                ModalNavigationDrawer(
                    onClickLogOut = {
                    },
                    navController = navController,
                    drawerState = drawerState
                )
            }
            drawerState = rememberDrawerState(initialValue = DrawerValue.Open)





        }



    )
}

/*...........................ModalNavigationDrawer....................................................*/
@Composable
fun ModalNavigationDrawer(
    onClickLogOut: () -> Unit,
    navController: NavHostController,
    drawerState: DrawerState
) {
    val drawerState = drawerState
    val scope = rememberCoroutineScope()
    // icons to mimic drawer destinations

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                R.drawable.man,
                onClickLogOut = onClickLogOut,
                navController = navController
            )

        },
        content = {

        }
    )

}

/*...........................DrawerContent....................................................*/
@Composable
fun DrawerContent(
    icon: Int,
    onClickLogOut: () -> Unit,
    navController: NavHostController,
) {
    var viewModel = viewModel<MainViewModel>()
    var ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val email by DataStoreOperationsImpl(ctx).readEmailofUser().collectAsState(initial = Constants.EMAIL)
    val fullname by DataStoreOperationsImpl(ctx).readFullnameofUser().collectAsState(initial = Constants.FULLNAME)


    Column(modifier = Modifier
        .fillMaxWidth(0.75f)
        .background(MaterialTheme.colorScheme.background)
        .fillMaxHeight(),
    verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)) {
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            //icon image
            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = icon), contentDescription = "", modifier = Modifier
                        .height(70.dp)
                        .scale(0.8f))
                    Spacer(modifier = Modifier.height(30.dp))
                    Column {
                        Text(text = fullname, style = MaterialTheme.typography.titleSmall)
                        Text(text = email, style = MaterialTheme.typography.labelSmall)
                    }
                }
                Divider()
                Spacer(modifier = Modifier.height(7.dp))
            }
            //support
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 15.dp),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = "SUPPORT", style = MaterialTheme.typography.titleMedium)
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
                        Icon(imageVector = Icons.Filled.Email, contentDescription = "email icon")
                        Text(text = "Send Feedback", style = MaterialTheme.typography.titleSmall)
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
                        Icon(imageVector = Icons.Filled.ThumbUp, contentDescription = "email icon")
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
                        Icon(imageVector = Icons.Filled.Share, contentDescription = "email icon")
                        Text(text = "Share this app", style = MaterialTheme.typography.titleMedium)
                    }
                    Divider()
                }
            }
            //other
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)) {
                    Text(text = "OTHER", style = MaterialTheme.typography.titleMedium)
                    Row(modifier = Modifier.padding(0.dp, 30.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(imageVector = Icons.Filled.Info, contentDescription = "dark mode")
                        Text(text = "App version 1.2.2", style = MaterialTheme.typography.titleMedium )
                    }
                    Divider()
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = true, onClick = {
                            //onClickLogOut()
                            navController.navigate(Screen.Authentication.route)
                        }
                        )
                        .padding(0.dp, 30.dp),horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(imageVector = Icons.Filled.Info, tint = AngryColor, contentDescription = "log out")
                        Text(text = "Log out", color = AngryColor , style = MaterialTheme.typography.titleMedium)
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


/*...........................TOPAPPBAR....................................................*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title : String,
    backgroundColor: Color,
    onMenuClick : () -> Unit,
    onClickLogOut: () -> Unit,
    navController: NavHostController
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(canScroll = { true })

    CenterAlignedTopAppBar(
        //elevation = 7.dp,
        //backgroundColor = backgroundColor
        title = { 
                Text(text = title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.background)
        },
        modifier = Modifier.fillMaxWidth()
        ,
        navigationIcon = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(Icons.Filled.Menu, tint = MaterialTheme.colorScheme.background, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = {
                onClickLogOut()
                navController.navigate(Screen.Authentication.route)
            }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        scrollBehavior = scrollBehavior
    )
}


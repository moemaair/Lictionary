package com.moemaair.lictionary.feature_lictionary.presentation.screen.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController


import com.moemaair.lictionary.MainVm
import com.moemaair.lictionary.R
import com.moemaair.lictionary.core.util.Constants
import com.moemaair.lictionary.core.util.Constants.FIRSTNAME
import com.moemaair.lictionary.core.util.Constants.FULLNAME
import com.moemaair.lictionary.core.util.shareApp
import com.moemaair.lictionary.feature_lictionary.data.repository.DataStoreOperationsImpl
import com.moemaair.lictionary.feature_lictionary.presentation.LegoLottie
import com.moemaair.lictionary.feature_lictionary.presentation.MainViewModel
import com.moemaair.lictionary.feature_lictionary.presentation.WordInfoItem
import com.moemaair.lictionary.navigation.Screen
import com.moemaair.lictionary.ui.theme.AngryColor
import com.moemaair.lictionary.ui.theme.md_theme_dark_errorContainer
import com.moemaair.lictionary.ui.theme.md_theme_light_error
import com.moemaair.lictionary.ui.theme.md_theme_light_tertiaryContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val firstname_ by DataStoreOperationsImpl(context).readGivenNameofUser().collectAsState(initial = FIRSTNAME)
    val fulname by DataStoreOperationsImpl(context).readFullnameofUser().collectAsState(initial = FULLNAME)

    val mainVm: MainVm = viewModel()

    val firstname = firstname_.replaceFirstChar { it.uppercase() }
    var isDrawerOpen by remember { mutableStateOf(false) } // state of drawer is false in default
    var lifecycleOwner = LocalLifecycleOwner.current
    val greeting = mainVm.greeting.collectAsStateWithLifecycle()



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


    var homeText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
            append("Hi, $firstname")
        }
        append("\n")
        withStyle(
            style = SpanStyle( fontWeight = FontWeight.Bold, fontSize = 17.sp)
        ){
            DisposableEffect(key1 = lifecycleOwner){
                val eventObserver = LifecycleEventObserver{ _, event ->
                    when(event){
                        Lifecycle.Event.ON_RESUME -> {
                            mainVm.setGreeting()
                        }
                        else -> {}
                    }
                }
                lifecycleOwner.lifecycle.addObserver(eventObserver)

                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(eventObserver)
                }
            }
            append(
               greeting.value.toString()
            )
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
                /*...........................col 30%....................................................*/
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.dp)
                        .weight(0.3f)
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
                            Text(text = homeText,   // here
                                modifier = Modifier.padding(10.dp, 0.dp),
                                color = MaterialTheme.colorScheme.background,
                            )

                            Spacer(modifier = Modifier.height(30.dp))
                            WordTextBox(
                                isVisible = isVisible
                            )
                        }

                    }

                }


                /*...........................col 75%....................................................*/

                Column(
                    modifier = Modifier

                        .fillMaxSize()
                        .height(0.dp)
                        .weight(0.75f)


                ) {
                   Box(
                       modifier = Modifier.fillMaxSize(),
                       contentAlignment = Alignment.Center
                   ) {
                       if (state.isLoading) {
                           Column( modifier = Modifier.fillMaxWidth(),
                               horizontalAlignment = Alignment.CenterHorizontally){
                               LaunchedEffect(Unit){
                                   delay(1000)
                               }
                               CircularProgressIndicator(
                                   modifier = Modifier
                                       .height(15.dp),
                                   trackColor = md_theme_light_tertiaryContainer
                               )
                               Spacer(modifier = Modifier.height(25.dp))
                               Text(text = "Please wait...",  textAlign = TextAlign.Center, fontSize = 12.sp)
                           }
                       }
                       if (!isVisible) {
                           Column(
                               modifier = Modifier
                                   .fillMaxSize()
                                   .padding(30.dp),
                               horizontalAlignment = Alignment.CenterHorizontally,
                               verticalArrangement = Arrangement.Center
                           ) {
                              // LegoLottie()
                               Text(
                                   text = "Try searching for a word ",
                                   modifier = Modifier
                                       .fillMaxWidth(),
                                   fontSize = 14.sp,
                                   textAlign = TextAlign.Center
                               )

                           }
                       } else {
                           LazyColumn(
                               modifier = Modifier.padding(horizontal = 20.dp)
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
                drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
                ModalNavigationDrawer(
                    onClickLogOut = {
                          onClickLogOut()
                    },
                    navController = navController,
                    drawerState = drawerState
                )

            }

        }

    )




}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordTextBox(
    isVisible: Boolean
) {
    val viewModel: MainViewModel = viewModel()
    val value = viewModel.searchQuery.value.trim()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = viewModel::onSearch,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(10.dp, 2.dp),

        placeholder = {
            Text(
                text = "Search for words...",
                color = Color.Black, style = MaterialTheme.typography.labelLarge
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
            autoCorrect = true,
            imeAction = ImeAction.Search

        ),
        keyboardActions = KeyboardActions(
            onDone = {
            viewModel.onSearch(value)
        }
        ),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent)
    )
}



/*...........................ModalNavigationDrawer....................................................*/
@Composable
fun ModalNavigationDrawer(
    onClickLogOut: () -> Unit,
    navController: NavHostController,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()

    var viewModel = viewModel<MainViewModel>()
    var mainVm = viewModel<MainVm>()
    var ctx = LocalContext.current


    val email_  by DataStoreOperationsImpl(ctx).readEmailofUser().collectAsState(initial = Constants.EMAIL)
    val email = email_.replaceFirstChar { it.uppercase() }

    val fullname_ by DataStoreOperationsImpl(ctx).readFullnameofUser().collectAsState(initial = Constants.FULLNAME)
    val fullname = fullname_.replaceFirstChar { it.uppercase() }

    var isHovered by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }

    val backgroundColor = if (isHovered) MaterialTheme.colorScheme.primary else Color.Transparent

    var showProgress by remember {
        mutableStateOf(false)
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(modifier = Modifier
                .fillMaxWidth(0.8f)
                .shadow(elevation = 12.dp, ambientColor = Color.LightGray)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxHeight(),
            ) {
                LazyColumn(modifier = Modifier
                    .fillMaxSize()

                ) {
                    item {
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                    //icon image
                    item {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.inversePrimary,
                                        MaterialTheme.colorScheme.primary
                                    )
                                )
                            )
                            .padding(15.dp),
                            horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                            Image(imageVector = Icons.Filled.Person, contentDescription = "",
                                    modifier = Modifier
                                        .size(50.dp)
                            )

                            Spacer(modifier = Modifier.height(50.dp))
                            Column(modifier = Modifier.padding(start = 15.dp)) {
                                Text(text = fullname, style = MaterialTheme.typography.titleSmall)
                                Text(text = email, style = MaterialTheme.typography.labelSmall)
                            }
                        }

                        Spacer(modifier = Modifier.height(7.dp))
                    }
                    //support
                    item {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(20.dp),
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            //row 1 (send feedback)
                            Row(modifier = Modifier
                                .hoverable(interactionSource = interactionSource)
                                .fillMaxWidth()
                                .background(backgroundColor)
                                .clip(shape = RoundedCornerShape(12.dp))
                                .padding(0.dp, 20.dp)
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
                                ,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Icon(imageVector = ImageVector.vectorResource(R.drawable.baseline_headphones_24) , tint = MaterialTheme.colorScheme.onBackground, contentDescription = "email icon")
                                Text(text = "Help and Support", style = MaterialTheme.typography.titleSmall)
                            }

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

                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                    //Log out
                    item {
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        )
                        {
                            Divider(modifier = Modifier.padding(bottom = 40.dp))
                            TextButton(
                                onClick = {
                                    showProgress = !showProgress
                                    onClickLogOut()
                                    navController.navigate(Screen.Authentication.route)
                                },
                                modifier = Modifier
                                    .shadow(10.dp)
                                    .background(md_theme_dark_errorContainer),
                                shape = MaterialTheme.shapes.medium,
                            ){
                                Text(text = "Log out", textAlign = TextAlign.Center, modifier = Modifier
                                    .fillMaxWidth(), color = Color.White , style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }
                   item{
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


            }
        },
        gesturesEnabled = true,
        content = {

        }
    )

    if(showProgress){
        CircularProgressIndicator(
            modifier = Modifier
                .height(15.dp),
            trackColor = md_theme_light_tertiaryContainer
        )
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
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var vm: MainVm = viewModel()
    val openDialog = remember { mutableStateOf(false)  }

    CenterAlignedTopAppBar(
        title = { 
                Text(text = title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.background)
        },
        modifier = Modifier.fillMaxWidth()
        ,
        navigationIcon = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(
                    Icons.Filled.Menu,
                    tint = MaterialTheme.colorScheme.background,

                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = {
                openDialog.value = true

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
    if (openDialog.value) {
       AlertDialog(
           onDismissRequest = {
               openDialog.value = false
           },
           confirmButton = {
               Button(onClick = {
                   openDialog.value = false
               }) {
                   Text(text = "Ok")
               }
           },
           dismissButton = {
               Button(onClick = {
                   openDialog.value = false
               }) {
                   Text(text = "Cancel")
               }
           } ,
           icon = {
               Icon(
                   painter = painterResource(R.drawable.baseline_info_24),
                   contentDescription = "print"
               )
           },

           title = {
               Column {
                   Text(text = "Welcome to Lictionary", textAlign = TextAlign.Center, fontSize = 17.sp)
                   Text(text = "Language: en",  color = Color.Gray,
                       textAlign = TextAlign.Center, fontSize = 12.sp)
               }
           },
           text = {
               Column{
                   Text(text = "version 1.2.2 ",
                       textAlign = TextAlign.Center, fontSize = 14.sp)
                   Text(text = "Copyright © by Mohamed Ibrahim. All rights reserved. ",

                       textAlign = TextAlign.Center, fontSize = 10.sp)
               }
           },

       )
    }

}


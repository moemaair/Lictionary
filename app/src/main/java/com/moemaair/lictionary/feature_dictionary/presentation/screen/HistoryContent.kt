package com.moemaair.lictionary.feature_dictionary.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoDelete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.moemaair.lictionary.feature_dictionary.presentation.MainViewModel
import com.moemaair.lictionary.feature_dictionary.presentation.components.HistoryCard
import com.moemaair.lictionary.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryContent (
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
       Scaffold(
           topBar = {
               TopAppBar(elevation = 7.dp) {
                   Box(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(6.dp)
                   )
                   {
                       Box( modifier = Modifier
                           .fillMaxWidth(),
                       ) {
                           IconButton(modifier = Modifier.align(Alignment.CenterStart), onClick = {
                               navController.navigate(Screen.Home.route)
                           }) {
                               Icon(imageVector =  Icons.Default.ArrowBack, contentDescription ="" )
                           }
                           Text(text = "History", modifier = Modifier.align(Alignment.Center))
                       }
                   }

               }
           },
           floatingActionButtonPosition = FabPosition.Center,
           floatingActionButton = {
               FloatingActionButton(onClick = {
                      scope.launch (Dispatchers.IO){
                             viewModel.deleteAll()
                      }
               }) {

                   Icon(imageVector =  Icons.Default.AutoDelete,
                       contentDescription = "edit",
                       tint = MaterialTheme.colorScheme.onSurface
                   )

               }
           }
       ){
           Column(modifier = Modifier.padding()) {
               HistoryCard()
           }
       }

   }

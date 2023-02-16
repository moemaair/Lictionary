package com.moemaair.lictionary.feature_dictionary.presentation.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moemaair.lictionary.feature_dictionary.data.local.entity.WordInfoEntity
import com.moemaair.lictionary.feature_dictionary.presentation.MainViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HistoryCard(
) {
    val vm: MainViewModel = viewModel()
    val distinctWords by vm.distinctWords.collectAsState(emptyList())
    // Fetch all words from the repository
    LaunchedEffect(Unit) {
        vm.fetchAllWords()

    }

    // Render the UI using Compose
    Column {
        LazyColumn {
            items(distinctWords) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clickable {
                        Log.d("id: ", it.id.toString())
                    },

                    horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                    Text(text = it.word.toString(), fontSize = 17.sp)
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.ArrowRight, contentDescription = "")
                        }
                    }
                }
            }

        }
}


@Preview
@Composable
fun HistoryCardprev() {
    //HistoryCard()
}
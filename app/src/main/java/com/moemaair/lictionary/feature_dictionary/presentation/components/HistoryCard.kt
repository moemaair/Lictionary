package com.moemaair.lictionary.feature_dictionary.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
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
        Text(text = "Distinct Words")
        LazyColumn {
            items(distinctWords) {
                Text(text = it.word.toSet().toString())
            }

        }
    }

}

@Preview
@Composable
fun HistoryCardprev() {
    //HistoryCard()
}
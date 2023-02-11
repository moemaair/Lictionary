package com.moemaair.lictionary.feature_dictionary.presentation.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moemaair.lictionary.feature_dictionary.domain.model.WordInfo
import com.moemaair.lictionary.feature_dictionary.presentation.MainViewModel

@Composable
fun HistoryContent   (
    wordInfo: WordInfo
) {
    LazyColumn {
        item {
            Text(text = wordInfo.word.toString())
        }
    }
}
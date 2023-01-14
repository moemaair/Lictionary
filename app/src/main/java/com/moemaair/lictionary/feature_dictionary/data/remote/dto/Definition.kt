package com.moemaair.lictionary.feature_dictionary.data.remote.dto

data class Definition(
    val antonyms: List<String>,
    val definition: String,
    val example: String?,
    val synonyms: List<String>
)
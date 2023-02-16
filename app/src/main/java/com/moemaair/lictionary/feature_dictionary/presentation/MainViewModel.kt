package com.moemaair.lictionary.feature_dictionary.presentation


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moemaair.lictionary.core.util.Resource
import com.moemaair.lictionary.feature_dictionary.data.repository.WordInfoRepoImpl
import com.moemaair.lictionary.feature_dictionary.domain.model.WordInfo
import com.moemaair.lictionary.feature_dictionary.domain.repository.WordInfoRepo
import com.moemaair.lictionary.feature_dictionary.domain.use_case.GetAllWordsInRoom
import com.moemaair.lictionary.feature_dictionary.domain.use_case.GetWordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWordInfo: GetWordInfo,
    private val repo: WordInfoRepoImpl
) : ViewModel(){

    private val _distinctWords = MutableStateFlow<List<WordInfo>>(emptyList())
    val distinctWords: StateFlow<List<WordInfo>> = _distinctWords

    var history: State<String> = mutableStateOf("")

    var _searchQuery = mutableStateOf("")
    var searchQuery: State<String> = _searchQuery

    var _darkmode = mutableStateOf(true)

    fun setDarkmode(darkmode: Boolean){
        _darkmode.value = darkmode
    }

    private var _state = mutableStateOf(WordInfoState())
    var state: State<WordInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            //delay(500L)
            getWordInfo(query)
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                wordInfoItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                wordInfoItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(UIEvent.ShowSnackbar(
                                result.message ?: "Unknown error"
                            ))
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                wordInfoItems = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

      fun fetchAllWords() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllWordInfos()
                .flowOn(Dispatchers.IO)
                .map { it }
                .distinctUntilChanged()
                .collect {
                    Log.d("TAG", ": ${it[0].word}")
                    _distinctWords.emit(it)
                }

        }
      }


    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
    }

}
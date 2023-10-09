package com.moemaair.lictionary.feature_lictionary.presentation


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moemaair.lictionary.core.util.Resource
import com.moemaair.lictionary.feature_lictionary.data.repository.WordInfoRepoImpl
import com.moemaair.lictionary.feature_lictionary.domain.model.WordInfo
import com.moemaair.lictionary.feature_lictionary.domain.use_case.GetWordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWordInfo: GetWordInfo,
    private val repo: WordInfoRepoImpl
) : ViewModel() {

    private val _distinctWords = MutableStateFlow<List<WordInfo>>(emptyList())
    val distinctWords: StateFlow<List<WordInfo>> = _distinctWords

    var _searchQuery = mutableStateOf("")
    var searchQuery: State<String> = _searchQuery

    var checkIfDarkmode = mutableStateOf(false)

    fun setDarkmode(darkmode: Boolean) {
        checkIfDarkmode.value = darkmode
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
            delay(500L)
            getWordInfo(query)
                .onEach { result ->
                    when (result) {
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
                            _eventFlow.emit(
                                UIEvent.ShowSnackbar(
                                    result.message ?: "Unknown error"
                                )
                            )
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

    fun deleteAll(){
        repo.deleteAll()
    }
    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
    }

}
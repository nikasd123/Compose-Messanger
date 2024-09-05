package com.kaz4.composemessanger.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaz4.composemessanger.domain.use_cases.CheckForAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkForAuthUseCase: CheckForAuthUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            val isAuthenticated = checkForAuthUseCase.hasAuth()
            _uiState.update { currentState ->
                currentState.copy(isAuthenticated = isAuthenticated)
            }
        }
    }

    fun onEvent(event: MainUiEvent) {
        when (event) {
            MainUiEvent.CheckAuth -> checkAuthStatus()
        }
    }
}

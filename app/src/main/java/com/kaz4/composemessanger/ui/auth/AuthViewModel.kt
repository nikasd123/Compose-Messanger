package com.kaz4.composemessanger.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaz4.composemessanger.domain.use_cases.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    fun processIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.EnterPhoneNumber -> enterPhoneNumber(intent.phoneNumber)
            is AuthIntent.EnterCountryCode -> enterCountryCode(intent.countryCode)
            is AuthIntent.SendAuthCode -> sendAuthCode()
            is AuthIntent.EnterVerificationCode -> enterVerificationCode(intent.code, intent.phoneNumber)
            is AuthIntent.VerifyAuthCode -> verifyAuthCode()
        }
    }

    private fun enterPhoneNumber(phoneNumber: String) {
        _state.update { it.copy(phoneNumber = phoneNumber) }
    }

    private fun enterCountryCode(countryCode: String) {
        _state.update { it.copy(countryCode = countryCode) }
    }

    private fun enterVerificationCode(code: String, phoneNumber: String) {
        _state.update { it.copy(verificationCode = code, phoneNumber = phoneNumber) }
    }

    private fun sendAuthCode() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val fullPhoneNumber = _state.value.countryCode + _state.value.phoneNumber
            val isSuccess = authUseCase.sendAuthCode(fullPhoneNumber)
            _state.update { it.copy(isLoading = false, isCodeSent = isSuccess == true) }
        }
    }

    private fun verifyAuthCode() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val response = authUseCase.checkAuthCode(
                _state.value.phoneNumber,
                _state.value.verificationCode
            )
            Log.d("AuthViewModel", "Response: $response")
            if (response != null) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isUserExist = response.isUserExist,
                        errorMessage = null
                    )
                }
            } else {
                _state.update {
                    it.copy(isLoading = false, errorMessage = "Verification failed")
                }
            }
        }
    }
}

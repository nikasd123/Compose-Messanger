package com.kaz4.composemessanger.domain.use_cases

import com.kaz4.composemessanger.domain.models.AuthCodeResponse
import com.kaz4.composemessanger.domain.repository.AuthRepository
import com.kaz4.composemessanger.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    suspend fun sendAuthCode(phoneNumber: String): Boolean? {
        //val cleanedPhoneNumber = phoneNumber.filter { it.isDigit() || it == '+' }
        return authRepository.sendAuthCode(phoneNumber)
    }

    suspend fun checkAuthCode(phoneNumber: String, code: String): AuthCodeResponse? {
        //val cleanedPhoneNumber = phoneNumber.filter { it.isDigit() || it == '+' }
        val response = authRepository.checkAuthCode(phoneNumber, code)

        response?.let {
            sharedPreferencesRepository.setToken(it.accessToken)
            sharedPreferencesRepository.setRefreshToken(it.refreshToken)
        }

        return response
    }
}
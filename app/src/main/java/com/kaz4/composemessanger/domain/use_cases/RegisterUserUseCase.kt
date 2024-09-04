package com.kaz4.composemessanger.domain.use_cases

import com.kaz4.composemessanger.domain.models.AuthCodeResponse
import com.kaz4.composemessanger.domain.repository.AuthRepository
import com.kaz4.composemessanger.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    suspend fun registerUser(phoneNumber: String, name: String, userName: String): AuthCodeResponse? {
        //val cleanedPhoneNumber = phoneNumber.filter { it.isDigit() || it == '+' }
        val response = authRepository.register(phoneNumber, name, userName)

        response?.let {
            sharedPreferencesRepository.setToken(it.accessToken)
            sharedPreferencesRepository.setRefreshToken(it.refreshToken)
        }

        return response
    }
}
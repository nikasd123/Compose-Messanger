package com.kaz4.composemessanger.domain.use_cases

import com.kaz4.composemessanger.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class CheckForAuthUseCase @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
){
    fun hasAuth(): Boolean {
        val response = sharedPreferencesRepository.getToken()
        return !response.isNullOrEmpty()
    }
}
package com.kaz4.composemessanger.domain.repository

import com.kaz4.composemessanger.domain.models.AuthCodeResponse

interface AuthRepository {
    suspend fun sendAuthCode(phoneNumber: String): Boolean?
    suspend fun checkAuthCode(phoneNumber: String, authCode: String): AuthCodeResponse?
}
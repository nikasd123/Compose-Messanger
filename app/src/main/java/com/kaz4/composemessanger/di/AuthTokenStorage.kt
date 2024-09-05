package com.kaz4.composemessanger.di

import android.util.Log
import com.kaz4.composemessanger.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

interface AuthTokenStorage {
    var authToken: String
    var authTokenWithoutBearer: String
    var refreshToken: String
}

private const val TOKEN_TYPE = "Bearer"

class AuthTokenStorageImpl @Inject constructor(
    private val preferences: SharedPreferencesRepository
) : AuthTokenStorage {

    override var authToken: String
        get() {
            val token = preferences.getToken().orEmpty()
            Log.d("AuthTokenStorage", "Getting auth token: $token")
            return "$TOKEN_TYPE $token"
        }
        set(value) {
            Log.d("AuthTokenStorage", "Setting auth token: $value")
            preferences.setToken(value)
        }

    override var refreshToken: String
        get() {
            val token = preferences.getRefreshToken()
            Log.d("AuthTokenStorage","Getting refresh token: $token")
            return token
        }
        set(value) {
            Log.d("AuthTokenStorage", "Setting refresh token: $value")
            preferences.setRefreshToken(value)
        }

    override var authTokenWithoutBearer: String
        get() = preferences.getToken().orEmpty()
        set(_) {}
}
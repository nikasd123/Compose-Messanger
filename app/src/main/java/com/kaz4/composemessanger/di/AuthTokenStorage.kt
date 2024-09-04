package com.kaz4.composemessanger.di

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
            val authToken = preferences.getToken().orEmpty()
            return "$TOKEN_TYPE $authToken"
        }
        set(value) {
            preferences.setToken(value)
        }

    override var refreshToken: String
        get() = preferences.getRefreshToken()
        set(value) {
            preferences.setRefreshToken(value)
        }

    override var authTokenWithoutBearer: String
        get() = preferences.getToken().orEmpty()
        set(_) {}
}
package com.kaz4.composemessanger.data.storage

import android.content.Context
import com.kaz4.composemessanger.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(
    private val context: Context
): SharedPreferencesRepository {

    companion object{
        const val AUTH_TOKEN_TAG = "token"
        const val SHARED_PREFERENCES = "APP_PREFERENCES"
        const val REFRESH_TOKEN_TAG = "refreshToken"
    }

    override fun setToken(token: String) {
        context
            .getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putString(AUTH_TOKEN_TAG, token)
            .apply()
    }

    override fun getToken(): String? =
        context
            .getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
            .getString(AUTH_TOKEN_TAG, "")

    override fun getRefreshToken(): String =
        context
            .getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
            .getString(REFRESH_TOKEN_TAG, "").orEmpty()
    override fun setRefreshToken(value: String) {
        context
            .getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putString(REFRESH_TOKEN_TAG, value)
            .apply()
    }
}
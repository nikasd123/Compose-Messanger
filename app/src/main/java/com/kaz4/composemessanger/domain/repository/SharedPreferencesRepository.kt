package com.kaz4.composemessanger.domain.repository

interface SharedPreferencesRepository {
    fun setToken(token: String)
    fun getToken(): String?
    fun getRefreshToken(): String
    fun setRefreshToken(value: String)
}
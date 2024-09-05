package com.kaz4.composemessanger.domain.repository

import com.kaz4.composemessanger.domain.models.ProfileData

interface SharedPreferencesRepository {
    fun setToken(token: String)
    fun getToken(): String?
    fun getRefreshToken(): String
    fun setRefreshToken(value: String)

    fun saveUserProfile(userProfile: ProfileData)
    fun getUserProfile(): ProfileData?
}
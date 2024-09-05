package com.kaz4.composemessanger.data.storage

import android.content.Context
import com.kaz4.composemessanger.domain.models.ProfileData
import com.kaz4.composemessanger.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(
    private val context: Context
): SharedPreferencesRepository {

    companion object{
        const val AUTH_TOKEN_TAG = "token"
        const val SHARED_PREFERENCES = "APP_PREFERENCES"
        const val REFRESH_TOKEN_TAG = "refreshToken"
        const val PROFILE_NAME = "profile_name"
        const val PROFILE_USERNAME = "profile_username"
        const val PROFILE_CITY = "profile_city"
        const val PROFILE_BIRTHDAY = "profile_birthday"
        const val PROFILE_PHONE = "profile_phone"
        const val PROFILE_AVATAR = "profile_avatar"
        const val PROFILE_STATUS = "profile_status"
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

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    override fun saveUserProfile(userProfile: ProfileData) {
        sharedPreferences.edit().apply {
            putString(PROFILE_NAME, userProfile.name)
            putString(PROFILE_USERNAME, userProfile.username)
            putString(PROFILE_CITY, userProfile.city)
            putString(PROFILE_BIRTHDAY, userProfile.birthday)
            putString(PROFILE_PHONE, userProfile.phone)
            putString(PROFILE_AVATAR, userProfile.avatar)
            putString(PROFILE_STATUS, userProfile.status)
            apply()
        }
    }

    override fun getUserProfile(): ProfileData =
        ProfileData(
            name = sharedPreferences.getString(PROFILE_NAME, "") ?: "",
            username = sharedPreferences.getString(PROFILE_USERNAME, "") ?: "",
            city = sharedPreferences.getString(PROFILE_CITY, "") ?: "",
            birthday = sharedPreferences.getString(PROFILE_BIRTHDAY, ""),
            phone = sharedPreferences.getString(PROFILE_PHONE, "") ?: "",
            avatar = sharedPreferences.getString(PROFILE_AVATAR, "") ?: "",
            status = sharedPreferences.getString(PROFILE_STATUS, "") ?: "",
        )
}
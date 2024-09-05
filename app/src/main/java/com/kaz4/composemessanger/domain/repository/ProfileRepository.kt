package com.kaz4.composemessanger.domain.repository

import com.kaz4.composemessanger.domain.models.ProfileData
import com.kaz4.composemessanger.domain.models.UserProfileResponse
import com.kaz4.composemessanger.domain.models.UserUpdateRequest

interface ProfileRepository {
    suspend fun getUserInfo(): UserProfileResponse?
    suspend fun updateUserInfo(userUpdateRequest: UserUpdateRequest): ProfileData?
}
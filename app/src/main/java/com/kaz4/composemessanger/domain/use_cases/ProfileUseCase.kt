package com.kaz4.composemessanger.domain.use_cases

import com.kaz4.composemessanger.domain.models.ProfileData
import com.kaz4.composemessanger.domain.models.UserProfileResponse
import com.kaz4.composemessanger.domain.models.UserUpdateRequest
import com.kaz4.composemessanger.domain.repository.ProfileRepository
import com.kaz4.composemessanger.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    suspend fun getUserInfo(): UserProfileResponse? = profileRepository.getUserInfo()

    suspend fun updateUserInfo(profileData: UserUpdateRequest): ProfileData? {
        val updatedProfile = profileRepository.updateUserInfo(profileData)
        updatedProfile?.let { sharedPreferencesRepository.saveUserProfile(it) }
        return updatedProfile
    }

    fun getStoredUserProfile(): ProfileData? = sharedPreferencesRepository.getUserProfile()

    fun saveUserProfileLocally(profileData: ProfileData) {
        sharedPreferencesRepository.saveUserProfile(profileData)
    }
}

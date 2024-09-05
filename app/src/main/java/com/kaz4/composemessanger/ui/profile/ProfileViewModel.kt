package com.kaz4.composemessanger.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaz4.composemessanger.domain.models.Avatar
import com.kaz4.composemessanger.domain.models.ProfileData
import com.kaz4.composemessanger.domain.models.UserUpdateRequest
import com.kaz4.composemessanger.domain.use_cases.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    fun processIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadUserProfile -> loadUserProfile()
            is ProfileIntent.UpdateUserProfile -> updateUserProfile(intent.userProfile)
        }
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val storedProfile = profileUseCase.getStoredUserProfile()
            //if (storedProfile != null && isValidProfile(storedProfile)) {
                _state.update { state ->
                    state.copy(isLoading = false, userProfile = storedProfile)
                }
            //} else {
            //    val userProfileResponse = profileUseCase.getUserInfo()
            //    userProfileResponse?.profileData?.let { profileData ->
            //        _state.update {
            //            it.copy(isLoading = false, userProfile = profileData)
            //        }
            //        profileUseCase.saveUserProfileLocally(profileData)
            //    } ?: run {
            //        _state.update {
            //            it.copy(isLoading = false, errorMessage = "Failed to load user profile")
            //        }
            //    }
            //}
        }
    }

    private fun isValidProfile(profile: ProfileData): Boolean =
        profile.name.isNotBlank() && profile.phone.isNotBlank()

    private fun updateUserProfile(profileData: ProfileData) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val updatedProfile = profileUseCase.updateUserInfo(convertToUserUpdateRequest(profileData))
            updatedProfile?.let { profile ->
                _state.update { state ->
                    state.copy(isLoading = false, userProfile = profile, successMessage = "Profile updated successfully")
                }
                profileUseCase.saveUserProfileLocally(profile)
            } ?: run {
                _state.update {
                    it.copy(isLoading = false, errorMessage = "Failed to update profile")
                }
            }
        }
    }

    private fun convertToUserUpdateRequest(profileData: ProfileData): UserUpdateRequest {
        return UserUpdateRequest(
            name = profileData.name,
            username = profileData.username,
            birthday = profileData.birthday ?: "",
            city = profileData.city,
            vk = profileData.vk,
            instagram = profileData.instagram,
            status = profileData.status,
            avatar = Avatar(
                filename = profileData.avatar,
                base64 = profileData.avatar
            )
        )
    }
}

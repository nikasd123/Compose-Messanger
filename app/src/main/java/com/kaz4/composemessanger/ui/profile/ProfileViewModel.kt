package com.kaz4.composemessanger.ui.profile

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaz4.composemessanger.R
import com.kaz4.composemessanger.domain.models.Avatar
import com.kaz4.composemessanger.domain.models.ProfileData
import com.kaz4.composemessanger.domain.models.UserUpdateRequest
import com.kaz4.composemessanger.domain.use_cases.ProfileUseCase
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val application: Application
) : ViewModel() {

    private val context: Context
        get() = getApplication(application)

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
            if (storedProfile != null && isValidProfile(storedProfile)) {
                _state.update { state ->
                    state.copy(isLoading = false, userProfile = storedProfile)
                }
            } else {
                val userProfileResponse = profileUseCase.getUserInfo()
                userProfileResponse?.profileData?.let { profileData ->
                    _state.update {
                        it.copy(isLoading = false, userProfile = profileData)
                    }
                    profileUseCase.saveUserProfileLocally(profileData)
                } ?: run {
                    _state.update {
                        it.copy(isLoading = false, errorMessage = context.getString(
                            R.string.load_profile_error))
                    }
                }
            }
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
                    state.copy(isLoading = false, userProfile = profile, successMessage = context.getString(
                        R.string.load_profile_success))
                }
                profileUseCase.saveUserProfileLocally(profile)
            } ?: run {
                _state.update {
                    it.copy(isLoading = false, errorMessage = context.getString(
                        R.string.update_profile_error))
                }
            }
        }
    }

    fun clearSuccessMessage() {
        _state.update { it.copy(successMessage = null) }
    }

    fun clearErrorMessage() {
        _state.update { it.copy(errorMessage = null) }
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
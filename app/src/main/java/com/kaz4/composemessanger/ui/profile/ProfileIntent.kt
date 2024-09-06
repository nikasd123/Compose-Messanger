package com.kaz4.composemessanger.ui.profile

import com.kaz4.composemessanger.domain.models.ProfileData

sealed class ProfileIntent {
    data object LoadUserProfile : ProfileIntent()
    data class UpdateUserProfile(val userProfile: ProfileData) : ProfileIntent()
}

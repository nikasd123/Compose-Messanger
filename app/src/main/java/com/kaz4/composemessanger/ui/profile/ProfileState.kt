package com.kaz4.composemessanger.ui.profile

import com.kaz4.composemessanger.domain.models.ProfileData

data class ProfileState(
    val isLoading: Boolean = false,
    val userProfile: ProfileData? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null
)


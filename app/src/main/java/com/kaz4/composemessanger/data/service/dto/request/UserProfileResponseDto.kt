package com.kaz4.composemessanger.data.service.dto.request

import com.google.gson.annotations.SerializedName
import com.kaz4.composemessanger.data.service.dto.response.ProfileDataResponseDto
import com.kaz4.composemessanger.data.service.dto.response.toDomain
import com.kaz4.composemessanger.domain.models.UserProfileResponse

data class UserProfileResponseDto(
    @SerializedName("profile_data")
    val profileData: ProfileDataResponseDto?
)

internal fun UserProfileResponseDto.toDomain() =
    profileData?.toDomain()?.let {
        UserProfileResponse(
            profileData = it
        )
    }
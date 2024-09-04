package com.kaz4.composemessanger.data.service.dto.response

import com.google.gson.annotations.SerializedName
import com.kaz4.composemessanger.domain.models.AuthCodeResponse

data class AuthCodeResponseDto(
    @SerializedName("refresh_token") val refreshToken: String?,
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("user_id") val userId: Int?,
    @SerializedName("is_user_exists") val isUserExist: Boolean?
)

internal fun AuthCodeResponseDto.toDomain() = AuthCodeResponse(
    refreshToken = refreshToken ?: "",
    accessToken = accessToken ?: "",
    userId = userId ?: 0,
    isUserExist = isUserExist ?: false
)

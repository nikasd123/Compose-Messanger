package com.kaz4.composemessanger.data.service.dto.request

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequestDto(
    @SerializedName("refresh_token") val refreshToken: String
)

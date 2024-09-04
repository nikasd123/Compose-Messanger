package com.kaz4.composemessanger.data.service.dto.request

import com.google.gson.annotations.SerializedName

data class AuthCodeRequestDto(
    @SerializedName("phone") val phoneNumber: String
)

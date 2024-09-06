package com.kaz4.composemessanger.data.service.dto.request

import com.google.gson.annotations.SerializedName

data class CheckAuthCodeRequestDto(
    @SerializedName("phone") val phoneNumber: String,
    @SerializedName("code") val code: String
)

package com.kaz4.composemessanger.data.service.dto.request

import com.google.gson.annotations.SerializedName
import com.kaz4.composemessanger.domain.models.Avatar

data class AvatarDto(
    @SerializedName("filename")
    val filename: String?,

    @SerializedName("base_64")
    val base64: String?
)

internal fun AvatarDto.toDomain() = Avatar(
    filename = filename ?: "",
    base64 = base64 ?: ""
)
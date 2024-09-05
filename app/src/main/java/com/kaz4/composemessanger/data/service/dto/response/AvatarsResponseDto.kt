package com.kaz4.composemessanger.data.service.dto.response

import com.google.gson.annotations.SerializedName
import com.kaz4.composemessanger.domain.models.Avatars

data class AvatarsResponseDto(
    @SerializedName("avatar")
    val avatar: String?,

    @SerializedName("bigAvatar")
    val bigAvatar: String?,

    @SerializedName("miniAvatar")
    val miniAvatar: String?,

    @SerializedName("filename")
    val filename: String?,

    @SerializedName("base_64")
    val base64: String?
)

fun AvatarsResponseDto.toDomain() = Avatars(
    avatar = avatar ?: "",
    bigAvatar = bigAvatar ?: "",
    miniAvatar = miniAvatar ?: "",
    filename = filename ?: "",
    base64 = base64 ?: ""
)
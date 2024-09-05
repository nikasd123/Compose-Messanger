package com.kaz4.composemessanger.data.service.dto.request

import com.google.gson.annotations.SerializedName
import com.kaz4.composemessanger.domain.models.Avatar
import com.kaz4.composemessanger.domain.models.UserUpdateRequest

data class UserUpdateRequestDto(
    @SerializedName("name")
    val name: String?,

    @SerializedName("username")
    val username: String?,

    @SerializedName("birthday")
    val birthday: String?,

    @SerializedName("city")
    val city: String?,

    @SerializedName("vk")
    val vk: String?,

    @SerializedName("instagram")
    val instagram: String?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("avatar")
    val avatar: AvatarDto?
)

internal fun UserUpdateRequestDto.toDomain() = UserUpdateRequest(
    name = name ?: "",
    username = username ?: "",
    birthday = birthday ?: "",
    city = city ?: "",
    vk = vk ?: "",
    instagram = instagram ?: "",
    status = status ?: "",
    avatar = avatar?.toDomain() ?: Avatar("", "")
)
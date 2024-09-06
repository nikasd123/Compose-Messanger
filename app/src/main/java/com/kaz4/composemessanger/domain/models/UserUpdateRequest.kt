package com.kaz4.composemessanger.domain.models

import com.kaz4.composemessanger.data.service.dto.request.UserUpdateRequestDto

data class UserUpdateRequest(
    val name: String,
    val username: String,
    val birthday: String,
    val city: String,
    val vk: String,
    val instagram: String,
    val status: String,
    val avatar: Avatar
)

internal fun UserUpdateRequest.toDto() = UserUpdateRequestDto(
    name = name,
    username = username,
    birthday = birthday,
    city = city,
    vk = vk,
    instagram = instagram,
    status = status,
    avatar = avatar.toDto()
)
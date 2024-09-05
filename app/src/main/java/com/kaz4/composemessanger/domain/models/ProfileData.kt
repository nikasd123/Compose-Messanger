package com.kaz4.composemessanger.domain.models

import com.kaz4.composemessanger.data.service.dto.response.AvatarsResponseDto
import com.kaz4.composemessanger.data.service.dto.response.ProfileDataResponseDto

data class ProfileData(
    val name: String,
    val username: String,
    val birthday: String?,
    val city: String,
    val vk: String = "",
    val instagram: String = "",
    val status: String,
    val avatar: String,
    val id: Int = 0,
    val last: String = "",
    val online: Boolean = true,
    val created: String = "",
    val phone: String = "",
    val completedTask: Int = 0,
    val avatars: Avatars? = null
)

internal fun ProfileData.toResponseDto() = ProfileDataResponseDto(
    name = this.name,
    username = this.username,
    birthday = this.birthday,
    city = this.city,
    vk = this.vk,
    instagram = this.instagram,
    status = this.status,
    avatar = this.avatar,
    id = this.id,
    last = this.last,
    online = this.online,
    created = this.created,
    phone = this.phone,
    completedTask = this.completedTask,
    avatars = AvatarsResponseDto(
        avatar = this.avatars?.avatar,
        bigAvatar = this.avatars?.bigAvatar,
        miniAvatar = this.avatars?.miniAvatar,
        filename = this.avatars?.filename,
        base64 = this.avatars?.base64
    )
)
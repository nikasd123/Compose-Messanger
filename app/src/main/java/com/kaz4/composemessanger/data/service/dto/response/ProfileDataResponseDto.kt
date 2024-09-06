package com.kaz4.composemessanger.data.service.dto.response

import com.google.gson.annotations.SerializedName
import com.kaz4.composemessanger.domain.models.Avatars
import com.kaz4.composemessanger.domain.models.ProfileData

data class ProfileDataResponseDto(
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
    val avatar: String?,

    @SerializedName("id")
    val id: Int?,

    @SerializedName("last")
    val last: String?,

    @SerializedName("online")
    val online: Boolean?,

    @SerializedName("created")
    val created: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("completed_task")
    val completedTask: Int? = 0,

    @SerializedName("avatars")
    val avatars: AvatarsResponseDto?
)

internal fun ProfileDataResponseDto.toDomain() =
    ProfileData(
        name = name ?: "",
        username = username ?: "",
        birthday = birthday,
        city = city ?: "",
        vk = vk ?: "",
        instagram = instagram ?: "",
        status = status ?: "",
        avatar = avatar ?: "",
        id = id ?: 0,
        last = last ?: "",
        online = online ?: false,
        created = created ?: "",
        phone = phone ?: "",
        completedTask = completedTask ?: 0,
        avatars = avatars?.toDomain() ?: Avatars("", "", "", "", "")
    )
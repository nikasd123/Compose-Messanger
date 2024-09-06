package com.kaz4.composemessanger.domain.models

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
package com.kaz4.composemessanger.domain.models

data class Avatars(
    val avatar: String,
    val bigAvatar: String,
    val miniAvatar: String,
    val filename: String = "test",
    val base64: String = "test"
)
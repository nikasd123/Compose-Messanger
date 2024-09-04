package com.kaz4.composemessanger.domain.models

data class AuthCodeResponse(
    val refreshToken: String,
    val accessToken: String,
    val userId: Int,
    val isUserExist: Boolean
)
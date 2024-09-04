package com.kaz4.composemessanger.ui.auth

data class AuthState(
    val phoneNumber: String = "",
    val countryCode: String = "+7",
    val fullPhoneNumber: String = "",
    val verificationCode: String = "",
    val isCodeSent: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isUserExist: Boolean? = null
)
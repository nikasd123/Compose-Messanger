package com.kaz4.composemessanger.ui.auth

sealed class AuthIntent {
    data class EnterPhoneNumber(val phoneNumber: String) : AuthIntent()
    data class EnterCountryCode(val countryCode: String) : AuthIntent()
    data object SendAuthCode : AuthIntent()
    data class EnterVerificationCode(val code: String, val phoneNumber: String) : AuthIntent()
    data object VerifyAuthCode : AuthIntent()
    data class RegisterUser(val phoneNumber: String, val name: String, val username: String) : AuthIntent()
}

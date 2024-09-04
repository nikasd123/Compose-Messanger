package com.kaz4.composemessanger.data.service.api

import com.kaz4.composemessanger.data.service.dto.request.AuthCodeRequestDto
import com.kaz4.composemessanger.data.service.dto.request.CheckAuthCodeRequestDto
import com.kaz4.composemessanger.data.service.dto.request.RegisterRequestDto
import com.kaz4.composemessanger.data.service.dto.response.AuthCodeResponseDto
import com.kaz4.composemessanger.data.service.dto.response.SuccessDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface API {

    @POST(SENT_AUTH_CODE)
    suspend fun sendAuthCode(@Body authCodeRequestDto: AuthCodeRequestDto): SuccessDto

    @POST(CHECK_AUTH_CODE)
    suspend fun checkAuthCode(@Body authCodeRequestDto: CheckAuthCodeRequestDto): AuthCodeResponseDto

    @POST(REGISTER)
    suspend fun register(@Body registerRequestDto: RegisterRequestDto): AuthCodeResponseDto

    @POST(REFRESH_TOKEN)
    fun refreshToken(): Call<AuthCodeResponseDto>

    companion object{
        private const val SENT_AUTH_CODE = "users/send-auth-code/"
        private const val CHECK_AUTH_CODE = "users/check-auth-code/"
        private const val REGISTER = "users/register/"
        private const val REFRESH_TOKEN = "users/refresh-token/"
    }
}
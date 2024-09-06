package com.kaz4.composemessanger.data.service.api

import com.kaz4.composemessanger.data.service.dto.request.AuthCodeRequestDto
import com.kaz4.composemessanger.data.service.dto.request.CheckAuthCodeRequestDto
import com.kaz4.composemessanger.data.service.dto.request.RefreshTokenRequestDto
import com.kaz4.composemessanger.data.service.dto.request.RegisterRequestDto
import com.kaz4.composemessanger.data.service.dto.request.UserProfileResponseDto
import com.kaz4.composemessanger.data.service.dto.request.UserUpdateRequestDto
import com.kaz4.composemessanger.data.service.dto.response.AuthCodeResponseDto
import com.kaz4.composemessanger.data.service.dto.response.ProfileDataResponseDto
import com.kaz4.composemessanger.data.service.dto.response.SuccessDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface API {

    @POST(SENT_AUTH_CODE)
    suspend fun sendAuthCode(@Body authCodeRequestDto: AuthCodeRequestDto): SuccessDto?

    @POST(CHECK_AUTH_CODE)
    suspend fun checkAuthCode(@Body authCodeRequestDto: CheckAuthCodeRequestDto): AuthCodeResponseDto?

    @POST(REGISTER)
    suspend fun register(@Body registerRequestDto: RegisterRequestDto): AuthCodeResponseDto?

    @POST(REFRESH_TOKEN)
    suspend fun refreshToken(@Body refreshTokenRequestDto: RefreshTokenRequestDto): AuthCodeResponseDto?

    @GET(GET_USER_INFO)
    suspend fun getUserInfo(): UserProfileResponseDto?

    @PUT(UPDATE_USER_INFO)
    suspend fun updateUserInfo(@Body userUpdateRequest: UserUpdateRequestDto): ProfileDataResponseDto?

    companion object{
        private const val SENT_AUTH_CODE = "users/send-auth-code/"
        private const val CHECK_AUTH_CODE = "users/check-auth-code/"
        private const val REGISTER = "users/register/"
        private const val REFRESH_TOKEN = "users/refresh-token/"
        private const val GET_USER_INFO = "users/me/"
        private const val UPDATE_USER_INFO = "users/me/"
    }
}
package com.kaz4.composemessanger.data.service

import android.util.Log
import com.kaz4.composemessanger.data.service.api.API
import com.kaz4.composemessanger.data.service.dto.request.AuthCodeRequestDto
import com.kaz4.composemessanger.data.service.dto.request.CheckAuthCodeRequestDto
import com.kaz4.composemessanger.data.service.dto.request.RegisterRequestDto
import com.kaz4.composemessanger.data.service.dto.response.toDomain
import com.kaz4.composemessanger.domain.models.AuthCodeResponse
import com.kaz4.composemessanger.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: API
) : AuthRepository {
    override suspend fun sendAuthCode(phoneNumber: String): Boolean? = withContext(Dispatchers.IO) {
        try {
            val requestModel = AuthCodeRequestDto(phoneNumber)
            val response = api.sendAuthCode(requestModel)
            return@withContext response?.isSuccess
        } catch (e: HttpException) {
            Log.e("HTTP_EXCEPTION", "HTTP error ${e.code()}: ${e.message()}")
            return@withContext null
        } catch (e: IOException) {
            Log.e("NETWORK_EXCEPTION", "Network error: ${e.message}")
            return@withContext null
        } catch (e: Exception) {
            Log.e("GENERAL_EXCEPTION", "Unexpected error: $e")
            return@withContext null
        }

    }

    override suspend fun checkAuthCode(
        phoneNumber: String, authCode: String
    ): AuthCodeResponse? = withContext(Dispatchers.IO) {
            try {
                val requestModel = CheckAuthCodeRequestDto(phoneNumber, authCode)
                val response = api.checkAuthCode(requestModel)
                return@withContext response?.toDomain()
            } catch (e: HttpException) {
                Log.e("HTTP_EXCEPTION", "HTTP error ${e.code()}: ${e.message()}")
                return@withContext null
            } catch (e: IOException) {
                Log.e("NETWORK_EXCEPTION", "Network error: ${e.message}")
                return@withContext null
            } catch (e: Exception) {
                Log.e("GENERAL_EXCEPTION", "Unexpected error: $e")
                return@withContext null
            }
        }

    override suspend fun register(
        phoneNumber: String,
        name: String,
        userName: String
    ): AuthCodeResponse? = withContext(Dispatchers.IO) {
        try {
            val requestModel =
                RegisterRequestDto(phone = phoneNumber, name = name, userName = userName)
            val response = api.register(requestModel)
            return@withContext response?.toDomain()
        } catch (e: HttpException) {
            Log.e("HTTP_EXCEPTION", "HTTP error ${e.code()}: ${e.message()}")
            return@withContext null
        } catch (e: IOException) {
            Log.e("NETWORK_EXCEPTION", "Network error: ${e.message}")
            return@withContext null
        } catch (e: Exception) {
            Log.e("GENERAL_EXCEPTION", "Unexpected error: $e")
            return@withContext null
        }
    }
}
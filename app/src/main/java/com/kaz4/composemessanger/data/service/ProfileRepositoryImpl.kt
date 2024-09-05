package com.kaz4.composemessanger.data.service

import android.util.Log
import com.kaz4.composemessanger.data.service.api.API
import com.kaz4.composemessanger.data.service.dto.request.toDomain
import com.kaz4.composemessanger.data.service.dto.response.ProfileDataResponseDto
import com.kaz4.composemessanger.data.service.dto.response.toDomain
import com.kaz4.composemessanger.domain.models.ProfileData
import com.kaz4.composemessanger.domain.models.UserProfileResponse
import com.kaz4.composemessanger.domain.models.UserUpdateRequest
import com.kaz4.composemessanger.domain.models.toDto
import com.kaz4.composemessanger.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: API
): ProfileRepository {
    override suspend fun getUserInfo(): UserProfileResponse? = withContext(Dispatchers.IO) {
        try {
            val response = api.getUserInfo()
            return@withContext response?.toDomain()
        } catch (e: HttpException) {
            val response = e.response()
            val errorBody = response?.errorBody()?.string()
            Log.e("HTTP_EXCEPTION", "HTTP error ${e.code()}: ${e.message} $errorBody")
            return@withContext null
        } catch (e: IOException) {
            Log.e("NETWORK_EXCEPTION", "Network error: ${e.message}")
            return@withContext null
        } catch (e: Exception) {
            Log.e("GENERAL_EXCEPTION", "Unexpected error: $e")
            return@withContext null
        }
    }

    override suspend fun updateUserInfo(userUpdateRequest: UserUpdateRequest): ProfileData? = withContext(Dispatchers.IO) {
        try {
            val requestDto = userUpdateRequest.toDto()
            val response = api.updateUserInfo(requestDto)
            return@withContext response?.toDomain()
        } catch (e: HttpException) {
            handleHttpException(e)
            return@withContext null
        } catch (e: IOException) {
            handleIOException(e)
            return@withContext null
        } catch (e: Exception) {
            handleGeneralException(e)
            return@withContext null
        }
    }

    private fun convertToProfileDataResponseDto(userUpdateRequest: UserUpdateRequest): ProfileDataResponseDto =
        ProfileDataResponseDto(
            name = userUpdateRequest.name,
            username = userUpdateRequest.username,
            birthday = userUpdateRequest.birthday,
            city = userUpdateRequest.city,
            vk = userUpdateRequest.vk,
            instagram = userUpdateRequest.instagram,
            status = userUpdateRequest.status,
            avatar = userUpdateRequest.avatar.filename,
            id = null,
            last = null,
            online = null,
            created = null,
            phone = null,
            completedTask = null,
            avatars = null
        )

    private fun handleHttpException(e: HttpException) {
        Log.e("HTTP_EXCEPTION", "HTTP error ${e.code()}: ${e.message}")
    }

    private fun handleIOException(e: IOException) {
        Log.e("NETWORK_EXCEPTION", "Network error: ${e.message}")
    }

    private fun handleGeneralException(e: Exception) {
        Log.e("GENERAL_EXCEPTION", "Unexpected error: $e")
    }
}
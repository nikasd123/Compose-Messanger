package com.kaz4.composemessanger.di

import android.util.Log
import com.kaz4.composemessanger.data.service.dto.request.RefreshTokenRequestDto
import com.kaz4.composemessanger.data.service.dto.response.AuthCodeResponseDto
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val authTokenStorage: AuthTokenStorage,
    private val tokenServiceHolder: TokenServiceHolder
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d("AAA", "TokenAuthenticator: authenticate called")
        if (response.code != 401) return null

        if (responseCount(response) >= 3) {
            Log.d("AAA", "TokenAuthenticator: too many retries")
            return null
        }

        val newTokenData = refreshToken()
        if (newTokenData == null) {
            Log.d("AAA", "TokenAuthenticator: refreshToken returned null")
            return null
        }

        Log.d("AAA", "TokenAuthenticator: refreshToken successful")

        authTokenStorage.authToken = newTokenData.accessToken ?: ""
        authTokenStorage.refreshToken = newTokenData.refreshToken ?: ""

        Log.d("AAA", "TokenAuthenticator: retrying request with new token")

        return response.request.newBuilder()
            .header("Authorization", "Bearer ${authTokenStorage.authTokenWithoutBearer}")
            .build()
    }

    private fun refreshToken(): AuthCodeResponseDto? {
        Log.d("AAA", "TokenAuthenticator: refreshToken called")
        return try {
            runBlocking {
                val refreshTokenRequest = RefreshTokenRequestDto(refreshToken = authTokenStorage.refreshToken)
                val response = tokenServiceHolder.tokenService?.refreshToken(refreshTokenRequest)
                Log.d("AAA", "TokenAuthenticator: refreshToken response = $response")
                response
            }
        } catch (e: Exception) {
            Log.d("AAA", "TokenAuthenticator: refreshToken Exception: ${e.message}")
            null
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var priorResponse: Response? = response.priorResponse
        while (priorResponse != null) {
            count++
            priorResponse = priorResponse.priorResponse
        }
        Log.d("AAA", "TokenAuthenticator: responseCount = $count")
        return count
    }
}
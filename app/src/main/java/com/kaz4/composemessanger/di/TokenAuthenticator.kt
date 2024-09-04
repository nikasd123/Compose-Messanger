package com.kaz4.composemessanger.di

import com.kaz4.composemessanger.data.service.dto.response.AuthCodeResponseDto
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val authTokenStorage: AuthTokenStorage,
    private val tokenServiceHolder: TokenServiceHolder
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 3) return null
        val newTokenData = refreshToken() ?: return null

        authTokenStorage.authToken = newTokenData.accessToken ?: ""
        authTokenStorage.refreshToken = newTokenData.refreshToken ?: ""

        return response.request.newBuilder()
            .header("Authorization", authTokenStorage.authToken)
            .build()
    }

    private fun refreshToken(): AuthCodeResponseDto? =
        try {
            val refreshTokenResponse = tokenServiceHolder.tokenService?.refreshToken()?.execute()
            refreshTokenResponse?.body()
        } catch (e: Exception) {
            null
        }

    private fun responseCount(response: Response): Int {
        var count = 1
        var priorResponse: Response? = response.priorResponse
        while (priorResponse != null) {
            count++
            priorResponse = priorResponse.priorResponse
        }
        return count
    }
}
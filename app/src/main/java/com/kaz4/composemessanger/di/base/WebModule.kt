package com.kaz4.composemessanger.di.base

import com.kaz4.composemessanger.data.service.api.API
import com.kaz4.composemessanger.data.service.dto.request.RefreshTokenRequestDto
import com.kaz4.composemessanger.data.service.dto.response.AuthCodeResponseDto
import com.kaz4.composemessanger.di.AuthTokenStorage
import com.kaz4.composemessanger.di.AuthTokenStorageImpl
import com.kaz4.composemessanger.di.TokenServiceHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WebModule {
    private const val BASE_URL = "https://plannerok.ru/api/v1/"
    private const val SEND_AUTH_CODE = "/users/send-auth-code/"
    private const val CHECK_AUTH_CODE = "/users/check-auth-code/"
    private const val REGISTER = "/users/register/"
    private const val REFRESH_TOKEN = "/users/refresh-token/"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createAuthInterceptor(authTokenStorage: AuthTokenStorage): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val newRequest: Request = if (request.url.encodedPath.contains(SEND_AUTH_CODE)
                || request.url.encodedPath.contains(CHECK_AUTH_CODE)
                || request.url.encodedPath.contains(REGISTER)
            ) {
                request.newBuilder().build()
            } else {
                val token = authTokenStorage.authTokenWithoutBearer
                request.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            }

            chain.proceed(newRequest)
        }
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): API = retrofit.create(API::class.java)

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,
                             authTokenStorage: AuthTokenStorage,
                             tokenServiceHolder: TokenServiceHolder
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(createAuthInterceptor(authTokenStorage))
        //.addInterceptor(errorInterceptor(authTokenStorage, tokenServiceHolder)) // Добавляем новый interceptor
        .build()

    private fun errorInterceptor(authTokenStorage: AuthTokenStorage, tokenServiceHolder: TokenServiceHolder): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val response = chain.proceed(originalRequest)

            if (response.code == 500) {
                // Обновляем токен
                val newTokenData = refreshToken(authTokenStorage, tokenServiceHolder)
                if (newTokenData != null) {
                    authTokenStorage.authToken = newTokenData.accessToken ?: ""
                    authTokenStorage.refreshToken = newTokenData.refreshToken ?: ""

                    // Повторяем запрос с новым токеном
                    val newRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer ${authTokenStorage.authTokenWithoutBearer}")
                        .build()
                    return@Interceptor chain.proceed(newRequest)
                }
            }

            return@Interceptor response
        }
    }

    private fun refreshToken(authTokenStorage: AuthTokenStorage, tokenServiceHolder: TokenServiceHolder): AuthCodeResponseDto? {
        return try {
            runBlocking {
                val refreshTokenRequest = RefreshTokenRequestDto(refreshToken = authTokenStorage.refreshToken)
                val response = tokenServiceHolder.tokenService?.refreshToken(refreshTokenRequest)
                response
            }
        } catch (e: Exception) {
            null
        }
    }

    @Singleton
    @Provides
    fun apiServiceHolder(): TokenServiceHolder = TokenServiceHolder()

    @Provides
    @Singleton
    fun provideAuthTokenStorage(authTokenStorageImpl: AuthTokenStorageImpl): AuthTokenStorage =
        authTokenStorageImpl
}
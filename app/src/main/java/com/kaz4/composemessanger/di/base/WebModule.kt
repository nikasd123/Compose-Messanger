package com.kaz4.composemessanger.di.base

import android.util.Log
import com.kaz4.composemessanger.data.service.api.API
import com.kaz4.composemessanger.di.AuthTokenStorage
import com.kaz4.composemessanger.di.AuthTokenStorageImpl
import com.kaz4.composemessanger.di.TokenAuthenticator
import com.kaz4.composemessanger.di.TokenServiceHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit, tokenServiceHolder: TokenServiceHolder): API {
        val api = retrofit.create(API::class.java)
        tokenServiceHolder.tokenService = api
        return api
    }

    @Singleton
    @Provides
    fun provideTokenServiceHolder(): TokenServiceHolder = TokenServiceHolder()

    @Provides
    @Singleton
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authTokenStorage: AuthTokenStorage,
        tokenServiceHolder: TokenServiceHolder
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(createAuthInterceptor(authTokenStorage, tokenServiceHolder))
        .authenticator(TokenAuthenticator(authTokenStorage, tokenServiceHolder))
        .build()

    private fun createAuthInterceptor(authTokenStorage: AuthTokenStorage, tokenServiceHolder: TokenServiceHolder): Interceptor {
        return Interceptor { chain ->
            Log.d("TokenAuthenticator", "Token service: ${tokenServiceHolder.tokenService}")
            val request = chain.request()
            Log.d("AuthTokenStorage", "Current refresh token: ${authTokenStorage.refreshToken}")
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
    fun provideAuthTokenStorage(authTokenStorageImpl: AuthTokenStorageImpl): AuthTokenStorage =
        authTokenStorageImpl
}
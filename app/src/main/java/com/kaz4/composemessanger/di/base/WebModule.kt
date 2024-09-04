package com.kaz4.composemessanger.di.base

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

    private fun authInterceptor(authTokenStorage: AuthTokenStorage): Interceptor = Interceptor { chain ->
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

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): API = retrofit.create(API::class.java)

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authTokenStorage: AuthTokenStorage,
        tokenServiceHolder: TokenServiceHolder
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(authInterceptor(authTokenStorage))
        .authenticator(TokenAuthenticator(authTokenStorage, tokenServiceHolder))
        .build()

    @Singleton
    @Provides
    fun apiServiceHolder(): TokenServiceHolder = TokenServiceHolder()

    @Provides
    @Singleton
    fun provideAuthTokenStorage(authTokenStorageImpl: AuthTokenStorageImpl): AuthTokenStorage =
        authTokenStorageImpl
}
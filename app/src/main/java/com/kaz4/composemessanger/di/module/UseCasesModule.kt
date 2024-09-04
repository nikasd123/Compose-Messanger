package com.kaz4.composemessanger.di.module

import com.kaz4.composemessanger.domain.repository.AuthRepository
import com.kaz4.composemessanger.domain.repository.SharedPreferencesRepository
import com.kaz4.composemessanger.domain.use_cases.AuthUseCase
import com.kaz4.composemessanger.domain.use_cases.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    @Singleton
    fun provideAuthUseCase(
        authRepository: AuthRepository,
        sharedPreferencesRepository: SharedPreferencesRepository
    ): AuthUseCase = AuthUseCase(authRepository, sharedPreferencesRepository)

    @Provides
    @Singleton
    fun provideRegisterUserUseCase(
        authRepository: AuthRepository,
        sharedPreferencesRepository: SharedPreferencesRepository
    ): RegisterUserUseCase = RegisterUserUseCase(authRepository, sharedPreferencesRepository)

}
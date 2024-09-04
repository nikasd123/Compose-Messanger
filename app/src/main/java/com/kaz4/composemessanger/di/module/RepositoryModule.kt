package com.kaz4.composemessanger.di.module

import android.content.Context
import com.kaz4.composemessanger.data.service.AuthRepositoryImpl
import com.kaz4.composemessanger.data.service.api.API
import com.kaz4.composemessanger.data.storage.SharedPreferencesRepositoryImpl
import com.kaz4.composemessanger.domain.repository.AuthRepository
import com.kaz4.composemessanger.domain.repository.SharedPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(api: API): AuthRepository = AuthRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(@ApplicationContext context: Context): SharedPreferencesRepository =
        SharedPreferencesRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context
}
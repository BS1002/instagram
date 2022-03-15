package com.mahfuznow.instagram.di

import com.mahfuznow.instagram.data.repository.MainRepository
import com.mahfuznow.instagram.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun getRepository(mainRepository: MainRepository): Repository = mainRepository
}
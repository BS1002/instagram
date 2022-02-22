package com.mahfuznow.instagram.di

import com.mahfuznow.instagram.repository.remote.PhotoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val PHOTO_BASE_URL = "https://raw.githubusercontent.com/BS1002/api/main/"

    @Singleton
    @Provides
    @Named("photo")
    fun getPhotoRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(PHOTO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getPhotoApi(@Named("photo") photoRetrofit: Retrofit): PhotoApi {
        return photoRetrofit.create(PhotoApi::class.java)
    }
}
package com.mahfuznow.instagram.di

import com.mahfuznow.instagram.data.api.remote.PhotoApi
import com.mahfuznow.instagram.data.api.remote.UserApi
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
    private const val USER_BASE_URL = "https://randomuser.me/"

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

    @Singleton
    @Provides
    @Named("user")
    fun getUserRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(USER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getUserApi(@Named("user") userRetrofit: Retrofit): UserApi {
        return userRetrofit.create(UserApi::class.java)
    }
}
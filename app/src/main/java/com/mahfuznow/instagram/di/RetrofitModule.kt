package com.mahfuznow.instagram.di

import com.mahfuznow.instagram.data.api.remote.PostApi
import com.mahfuznow.instagram.data.api.remote.TagApi
import com.mahfuznow.instagram.data.api.remote.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val DUMMY_API_BASE_URL = "https://dummyapi.io/data/v1/"

    //This is required to provide Authentication credential through the Header of each HTTP request
    @Singleton
    @Provides
    @Named("dummyApiOkHttpClient")
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor {
            val request: Request = it.request().newBuilder().addHeader("app-id", "621686e2c810c916a819005b").build()
            it.proceed(request)
        }.build()
    }

    @Singleton
    @Provides
    @Named("dummyApi")
    fun getPhotoRetrofitInstance(@Named("dummyApiOkHttpClient") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DUMMY_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun getPostApi(@Named("dummyApi") dummyApiRetrofit: Retrofit): PostApi {
        return dummyApiRetrofit.create(PostApi::class.java)
    }

    @Singleton
    @Provides
    fun getUserApi(@Named("dummyApi") dummyApiRetrofit: Retrofit): UserApi {
        return dummyApiRetrofit.create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun getTagApi(@Named("dummyApi") dummyApiRetrofit: Retrofit): TagApi {
        return dummyApiRetrofit.create(TagApi::class.java)
    }
}
package com.mahfuznow.instagram.repository.remote

import com.mahfuznow.instagram.model.Photo
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET


interface PhotoApi {
    @GET("photos_100.json")
    suspend fun getPhotos(): Response<List<Photo>>
}

package com.mahfuznow.instagram.data.api.remote

import com.mahfuznow.instagram.data.model.Photo
import retrofit2.Response
import retrofit2.http.GET


interface PhotoApi {
    @GET("photos_100.json")
    suspend fun getPhotos(): Response<List<Photo>>
}

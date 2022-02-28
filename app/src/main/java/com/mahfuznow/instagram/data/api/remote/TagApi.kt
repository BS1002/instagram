package com.mahfuznow.instagram.data.api.remote

import com.mahfuznow.instagram.data.model.TagData
import retrofit2.http.GET

interface TagApi {
    @GET("tag")
    suspend fun getTagData(): TagData
}

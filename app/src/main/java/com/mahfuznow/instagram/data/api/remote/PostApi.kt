package com.mahfuznow.instagram.data.api.remote

import com.mahfuznow.instagram.data.model.PostsData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {
    @GET("post")
    suspend fun getPostsData(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): PostsData
}
